import dao.Dao
import data.CreateUserRequest
import data.GenerateUserRequest
import data.NutritionTargetsCreate
import data.RecipeSummary
import data.toRecipeSummary
import io.github.smiley4.ktorswaggerui.dsl.routing.post
import io.github.smiley4.ktorswaggerui.dsl.routing.route
import io.github.smiley4.ktorswaggerui.routing.openApiSpec
import io.github.smiley4.ktorswaggerui.routing.swaggerUI
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import orTools.MealPlanService

fun Application.configureRouting(dao: Dao, calculations: Calculation, mealPlanService: MealPlanService) {

    routing {

        route("/users") {
            post({
                description = "Создание пользователя"

                request {
                    body<CreateUserRequest>()
                }

                response {
                    HttpStatusCode.OK to {
                        body<Map<String, Any>>()
                    }
                }

            }) {
                try {
                    val newUser = call.receive<CreateUserRequest>()

                    // базовая валидация (можно заменить на Ktor Validation)
                    require(newUser.age in 1..120) { "Invalid age" }
                    require(newUser.weightKg > 0) { "Weight must be > 0" }
                    require(newUser.heightCm > 0) { "Height must be > 0" }

                    val dietTypeId = dao.getUserDietTypeId(newUser.dietType)
                    require(dietTypeId != null) { "Couldn't find the type of diet" }

                    val activityGroupId = dao.getActivityLevelId(newUser.activityGroup)
                    require(activityGroupId != null) { "Couldn't find the activity group" }

                    val userId = dao.createUser(newUser, dietTypeId, activityGroupId)

                    val bmr = calculations.calculateBmr(newUser.gender, newUser.weightKg, newUser.heightCm, newUser.age)
                    val totalEnergy = calculations.calculateEnergy(bmr, newUser.activityGroup, newUser.dietType)

                    val totalEnergyRange = calculations.calculateValueRange(totalEnergy, newUser.dietType.spreadEnergy)

                    val proteins = calculations.calculateNutrientRange(
                        nutrientEnergy = 4.0,
                        minPercentage = newUser.dietType.minProteins,
                        maxPercentage = newUser.dietType.maxProteins,
                        totalEnergy
                    )

                    val fats = calculations.calculateNutrientRange(
                        nutrientEnergy = 9.0,
                        minPercentage = newUser.dietType.minFats,
                        maxPercentage = newUser.dietType.maxFats,
                        totalEnergy
                    )

                    val carbohydrates = calculations.calculateNutrientRange(
                        nutrientEnergy = 4.0,
                        minPercentage = newUser.dietType.minCarbohydrates,
                        maxPercentage = newUser.dietType.maxCarbohydrates,
                        totalEnergy
                    )

                    val nutritionTargets = dao.createNutritionTargets(
                        NutritionTargetsCreate(
                            userId = userId,
                            caloriesMin = totalEnergyRange.first,
                            caloriesMax = totalEnergyRange.second,
                            proteinsMin = proteins.first,
                            proteinsMax = proteins.second,
                            fatsMin = fats.first,
                            fatsMax = fats.second,
                            carbohydratesMin = carbohydrates.first,
                            carbohydratesMax = carbohydrates.second,
                            saturatedFatsMax = calculations.calculateNutrientWeight(9.0, 10.0, totalEnergy),
                            addedSugarsMax = calculations.calculateNutrientWeight(4.0, 10.0, totalEnergy),
                            vitaminB1Mg = totalEnergy / 1000 * 0.6,
                            calciumMg = calculations.getCalcium(newUser.age),
                            rationWeightG = totalEnergy
                        )
                    )

                    call.respond(
                        status = HttpStatusCode.OK,
                        mapOf("userId" to userId, "nutritionTargets" to nutritionTargets)
                    )

                } catch (e: Exception) {
                    call.respond(
                        HttpStatusCode.BadRequest,
                        mapOf("error" to (e.message ?: "Unknown error"))
                    )
                }
            }
        }

        route("swagger") {
            swaggerUI("/openapi.json")
        }

        route("openapi.json") {
            openApiSpec()
        }

        route("test") {
            post({
                description = "Тест генарции рациона"

                request {
                    body<GenerateUserRequest>()
                }

                response {
                    HttpStatusCode.OK to {
                        body<Map<String, Any>>()
                    }
                }

            }) {
                val request = call.receive<GenerateUserRequest>()

                if (request.firstMessage == "test") {
                    val list = dao.getAllRecipes()

                    println("Let's go")

                    val recipeSummaryList: MutableList<RecipeSummary> = mutableListOf()
                    list.forEach {
                        recipeSummaryList.add(it.toRecipeSummary())
                    }

                    val nutritionTargets = dao.getUserNutritionTargetById(request.nutritionTargetsId)
                    require(nutritionTargets != null) { "Couldn't find the nutrition targets" }

                    mealPlanService.generate(recipeSummaryList.toList(), nutritionTargets)

                    call.respond(
                        status = HttpStatusCode.OK,
                        mapOf("Ok" to "Let's go!!!")
                    )
                } else {
                    call.respond(
                        status = HttpStatusCode.OK,
                        mapOf("error" to "Unknown error")
                    )
                }
            }
        }
    }
}