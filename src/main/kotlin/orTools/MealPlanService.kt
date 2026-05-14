package orTools

import com.google.ortools.sat.CpModel
import com.google.ortools.sat.CpSolver
import com.google.ortools.sat.CpSolverStatus
import com.google.ortools.sat.LinearExpr
import data.NutritionTargetsCreate
import data.RecipeSummary

class MealPlanService {

    private val model = CpModel()
    private val solver = CpSolver()

    fun generate(recipes: List<RecipeSummary>, nutritionTargets: NutritionTargetsCreate) {

        println("Begining")
        val days = 7
        val mealsPerDay = 4

        val x = Array(days) { d ->
            Array(mealsPerDay) { m ->
                Array(recipes.size) { r ->
                    model.newBoolVar("x_d${d}_m${m}_r${r}")
                }
            }
        }

        println("Begining2")
        for (d in 0 until days) {

            val calExpr = LinearExpr.newBuilder()
            val proteinExpr = LinearExpr.newBuilder()
            val fatsExpr = LinearExpr.newBuilder()
            val carbohydratesExpr = LinearExpr.newBuilder()
            val fiberExpr = LinearExpr.newBuilder()
            val saturatedFatsExpr = LinearExpr.newBuilder()
            val addedSugarsExpr = LinearExpr.newBuilder()
            val vitaminCExpr = LinearExpr.newBuilder()
            val vitaminB1Expr = LinearExpr.newBuilder()
            val vitaminAExpr = LinearExpr.newBuilder()
            val calciumExpr = LinearExpr.newBuilder()
            val magnesiumExpr = LinearExpr.newBuilder()
            val ironExpr = LinearExpr.newBuilder()
            val phosphorusExpr = LinearExpr.newBuilder()
            val sodiumExpr = LinearExpr.newBuilder()
            //val totalWeightExpr = LinearExpr.newBuilder()

            for (m in 0 until mealsPerDay) {
                val atLeasOneExpr = LinearExpr.newBuilder()

                for (r in recipes.indices) {
                    atLeasOneExpr.addTerm(x[d][m][r], 1L)

                    val cal = recipes[r].nutrients.calories
                    calExpr.addTerm(x[d][m][r], cal.toLong())

                    val protein = recipes[r].nutrients.proteins
                    proteinExpr.addTerm(x[d][m][r], protein.toLong())

                    val fats = recipes[r].nutrients.fats
                    fatsExpr.addTerm(x[d][m][r], fats.toLong())

                    val carbohydrates = recipes[r].nutrients.carbohydrates
                    carbohydratesExpr.addTerm(x[d][m][r], carbohydrates.toLong())

                    val fiber = recipes[r].nutrients.fiber
                    fiberExpr.addTerm(x[d][m][r], fiber.toLong())

                    val saturatedFats = recipes[r].nutrients.saturatedFats
                    saturatedFatsExpr.addTerm(x[d][m][r], saturatedFats.toLong())

                    val addedSugars = recipes[r].nutrients.addedSugars
                    addedSugarsExpr.addTerm(x[d][m][r], addedSugars.toLong())

                    val vitaminC = recipes[r].nutrients.vitaminC
                    vitaminCExpr.addTerm(x[d][m][r], vitaminC.toLong())

                    val vitaminB1 = recipes[r].nutrients.vitaminB1
                    vitaminB1Expr.addTerm(x[d][m][r], vitaminB1.toLong())

                    val vitaminA = recipes[r].nutrients.vitaminA
                    vitaminAExpr.addTerm(x[d][m][r], vitaminA.toLong())

                    val calcium = recipes[r].nutrients.calcium
                    calciumExpr.addTerm(x[d][m][r], calcium.toLong())

                    val magnesium = recipes[r].nutrients.magnesium
                    magnesiumExpr.addTerm(x[d][m][r], magnesium.toLong())

                    val iron = recipes[r].nutrients.iron
                    ironExpr.addTerm(x[d][m][r], iron.toLong())

                    val phosphorus = recipes[r].nutrients.phosphorus
                    phosphorusExpr.addTerm(x[d][m][r], phosphorus.toLong())

                    val sodium = recipes[r].nutrients.sodium
                    sodiumExpr.addTerm(x[d][m][r], sodium.toLong())

//                    val totalWeight = recipes[r].nutrients.totalWeight
//                    totalWeightExpr.addTerm(x[d][m][r], totalWeight.toLong())
                }

                model.addGreaterOrEqual(atLeasOneExpr, 1L) // хотя бы 1 рецепт
                model.addLessOrEqual(atLeasOneExpr, 3L)
            }

            model.addGreaterOrEqual(calExpr, nutritionTargets.caloriesMin.toLong())
            model.addLessOrEqual(calExpr, nutritionTargets.caloriesMax.toLong())

            model.addGreaterOrEqual(proteinExpr, nutritionTargets.proteinsMin.toLong())
            model.addLessOrEqual(proteinExpr, nutritionTargets.proteinsMax.toLong())

            model.addGreaterOrEqual(fatsExpr, nutritionTargets.fatsMin.toLong())
            model.addLessOrEqual(fatsExpr, nutritionTargets.fatsMax.toLong())

            model.addGreaterOrEqual(carbohydratesExpr, nutritionTargets.carbohydratesMin.toLong())
            model.addLessOrEqual(carbohydratesExpr, nutritionTargets.carbohydratesMax.toLong())

//            model.addGreaterOrEqual(fiberExpr, nutritionTargets.dietaryFiberMin.toLong())
//            model.addLessOrEqual(fiberExpr, nutritionTargets.dietaryFiberMax.toLong())

            model.addLessOrEqual(saturatedFatsExpr, nutritionTargets.saturatedFatsMax.toLong())

            model.addLessOrEqual(addedSugarsExpr, nutritionTargets.addedSugarsMax.toLong())

            model.addGreaterOrEqual(vitaminCExpr, nutritionTargets.vitaminCMg.toLong())

//            model.addGreaterOrEqual(vitaminB1Expr, nutritionTargets.vitaminB1Mg.toLong())

            model.addGreaterOrEqual(vitaminAExpr, nutritionTargets.vitaminAMg.toLong())

            model.addGreaterOrEqual(calciumExpr, nutritionTargets.calciumMg.toLong())

            model.addGreaterOrEqual(magnesiumExpr, nutritionTargets.magnesiumMg.toLong())

            model.addGreaterOrEqual(ironExpr, nutritionTargets.ironMg.toLong())

            model.addGreaterOrEqual(phosphorusExpr, nutritionTargets.phosphorusMg.toLong())

            model.addGreaterOrEqual(sodiumExpr, nutritionTargets.sodiumMinMg.toLong())
//            model.addLessOrEqual(sodiumExpr, nutritionTargets.sodiumMaxMg.toLong())
        }

        println("Begining3")
        for (d in 0 until days) {
            for (m in 0 until mealsPerDay) {
                val expr = LinearExpr.newBuilder()

                for (r in recipes.indices) {
                    expr.addTerm(x[d][m][r], 1L)
                }

                model.addGreaterOrEqual(expr, 1L) // хотя бы 1 рецепт
                model.addLessOrEqual(expr, 3L)
            }
        }

        println("Begining4")
        val status = solver.solve(model)

        if (status == CpSolverStatus.FEASIBLE || status == CpSolverStatus.OPTIMAL) {
            println("Solution found")

            for (d in 0 until days) {

                println("DAY $d")

                for (m in 0 until mealsPerDay) {

                    println(" Meal $m")

                    for (r in recipes.indices) {

                        if (solver.booleanValue(x[d][m][r])) {

                            val recipe = recipes[r]

                            println("   ${recipe.name}")
                        }
                    }
                }
            }
        } else {
            println("No solution")
        }
    }
}