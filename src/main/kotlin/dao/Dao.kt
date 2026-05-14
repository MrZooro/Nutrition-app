package dao

import dao.tables.*
import data.*
import enums.ActivityGroup
import enums.DietType
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class Dao {

    fun getActivityLevelId(activityGroup: ActivityGroup): Int? {
        return transaction {
            ActivityLevels
                .select(ActivityLevels.id)
                .where { ActivityLevels.name eq activityGroup.name }
                .limit(1)
                .firstOrNull()
                ?.get(ActivityLevels.id)
                ?.value
        }
    }

    fun getUserDietTypeId(dietType: DietType): Int? {
        return transaction {
            DietTypes
                .selectAll()
                .where { DietTypes.code eq dietType.name }
                .limit(1)
                .firstOrNull()
                ?.get(DietTypes.id)
                ?.value
        }
    }

    fun createUser(user: CreateUserRequest, newDietTypeId: Int, activityGroupId: Int): Int {
        return transaction {

            Users.insert { row ->
                row[gender] = user.gender.name.toCharArray().first()

                row[lastName] = user.lastName
                row[firstName] = user.firstName
                row[middleName] = user.middleName

                row[email] = user.email
                row[passwordHash] = user.passwordHash

                row[age] = user.age
                row[weightKg] = user.weightKg.toBigDecimal()
                row[heightCm] = user.heightCm.toBigDecimal()

                row[activityLevelId] = activityGroupId
                row[dietTypeId] = newDietTypeId
            } get Users.id
        }.value
    }

    fun createNutritionTargets(target: NutritionTargetsCreate): Int {

        return transaction {

            UserNutritionTargets.insert { row ->

                row[userId] = target.userId

                row[proteinsMin] = target.proteinsMin.toBigDecimal()
                row[proteinsMax] = target.proteinsMax.toBigDecimal()

                row[fatsMin] = target.fatsMin.toBigDecimal()
                row[fatsMax] = target.fatsMax.toBigDecimal()

                row[carbohydratesMin] = target.carbohydratesMin.toBigDecimal()
                row[carbohydratesMax] = target.carbohydratesMax.toBigDecimal()

                row[caloriesMin] = target.caloriesMin.toBigDecimal()
                row[caloriesMax] = target.caloriesMax.toBigDecimal()

                row[dietaryFiberMin] = target.dietaryFiberMin.toBigDecimal()
                row[dietaryFiberMax] = target.dietaryFiberMax.toBigDecimal()

                row[saturatedFatsMax] = target.saturatedFatsMax.toBigDecimal()
                row[addedSugarsMax] = target.addedSugarsMax.toBigDecimal()

                row[vitaminCMg] = target.vitaminCMg.toBigDecimal()
                row[vitaminB1Mg] = target.vitaminB1Mg.toBigDecimal()
                row[vitaminAMg] = target.vitaminAMg.toBigDecimal()

                row[calciumMg] = target.calciumMg.toBigDecimal()
                row[magnesiumMg] = target.magnesiumMg.toBigDecimal()
                row[ironMg] = target.ironMg.toBigDecimal()
                row[phosphorusMg] = target.phosphorusMg.toBigDecimal()
                row[sodiumMax] = target.sodiumMaxMg.toBigDecimal()
                row[sodiumMin] = target.sodiumMinMg.toBigDecimal()

                row[rationWeightG] = target.rationWeightG.toBigDecimal()
            } get UserNutritionTargets.id
        }.value
    }

    fun getAllRecipes() : List<Recipe> {
         return transaction {
            val rows = (Recipes
                .innerJoin(MealTypes)
                .innerJoin(RecipeIngredients)
                .innerJoin(Ingredients))
                .selectAll()
                .toList()

            rows.groupBy { it[Recipes.id] }.map { (_, group) ->

                val first = group.first()

                Recipe(
                    id = first[Recipes.id].value,
                    name = first[Recipes.name],
                    description = first[Recipes.description],
                    mealType = MealType(
                        id = first[MealTypes.id].value,
                        name = first[MealTypes.name]
                    ),
                    ingredients = group.map {
                        RecipeIngredient(
                            ingredient = Ingredient(
                                id = it[Ingredients.id].value,
                                name = it[Ingredients.name],
                                caloriesKcal = it[Ingredients.caloriesKcal].toDouble(),
                                proteinsG = it[Ingredients.proteinsG].toDouble(),
                                fatsG = it[Ingredients.fatsG].toDouble(),
                                carbohydratesG = it[Ingredients.carbohydratesG].toDouble(),
                                fiberG = it[Ingredients.dietaryFiberG].toDouble(),
                                description = it[Ingredients.description],
                                saturatedFatsG = it[Ingredients.saturatedFatsG].toDouble(),
                                addedSugarsG = it[Ingredients.addedSugarsG].toDouble(),
                                vitaminCMg = it[Ingredients.vitaminCMg].toDouble(),
                                vitaminB1Mg = it[Ingredients.vitaminB1Mg].toDouble(),
                                vitaminAMg = it[Ingredients.vitaminAMg].toDouble(),
                                calciumMg = it[Ingredients.calciumMg].toDouble(),
                                magnesiumMg = it[Ingredients.magnesiumMg].toDouble(),
                                ironMg = it[Ingredients.ironMg].toDouble(),
                                phosphorusMg = it[Ingredients.phosphorusMg].toDouble(),
                                sodiumMg = it[Ingredients.sodiumMg].toDouble(),
                            ),
                            quantityG = it[RecipeIngredients.quantityG].toDouble()
                        )
                    }
                )
            }
        }
    }

    fun getUserNutritionTargetById(targetId: Int): NutritionTargetsCreate? {
        return transaction {

            UserNutritionTargets
                .selectAll()
                .where { UserNutritionTargets.id eq targetId }
                .singleOrNull()
                ?.let { row ->

                    NutritionTargetsCreate(
                        userId = row[UserNutritionTargets.userId].value,

                        proteinsMin = row[UserNutritionTargets.proteinsMin].toDouble(),
                        proteinsMax = row[UserNutritionTargets.proteinsMax].toDouble(),

                        fatsMin = row[UserNutritionTargets.fatsMin].toDouble(),
                        fatsMax = row[UserNutritionTargets.fatsMax].toDouble(),

                        carbohydratesMin = row[UserNutritionTargets.carbohydratesMin].toDouble(),
                        carbohydratesMax = row[UserNutritionTargets.carbohydratesMax].toDouble(),

                        caloriesMin = row[UserNutritionTargets.caloriesMin].toDouble(),
                        caloriesMax = row[UserNutritionTargets.caloriesMax].toDouble(),

                        dietaryFiberMin = row[UserNutritionTargets.dietaryFiberMin].toDouble(),
                        dietaryFiberMax = row[UserNutritionTargets.dietaryFiberMax].toDouble(),

                        saturatedFatsMax = row[UserNutritionTargets.saturatedFatsMax].toDouble(),

                        addedSugarsMax = row[UserNutritionTargets.addedSugarsMax].toDouble(),

                        vitaminCMg = row[UserNutritionTargets.vitaminCMg].toDouble(),

                        vitaminB1Mg = row[UserNutritionTargets.vitaminB1Mg].toDouble(),

                        vitaminAMg = row[UserNutritionTargets.vitaminAMg].toDouble(),

                        calciumMg = row[UserNutritionTargets.calciumMg].toDouble(),

                        magnesiumMg = row[UserNutritionTargets.magnesiumMg].toDouble(),

                        ironMg = row[UserNutritionTargets.ironMg].toDouble(),

                        phosphorusMg = row[UserNutritionTargets.phosphorusMg].toDouble(),

                        sodiumMaxMg = row[UserNutritionTargets.sodiumMax].toDouble(),

                        sodiumMinMg = row[UserNutritionTargets.sodiumMin].toDouble(),

                        rationWeightG = row[UserNutritionTargets.rationWeightG].toDouble()
                    )
                }
        }
    }
}