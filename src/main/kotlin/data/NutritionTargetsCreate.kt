package data

data class NutritionTargetsCreate(
    val userId: Int,

    val proteinsMin: Double,
    val proteinsMax: Double,

    val fatsMin: Double,
    val fatsMax: Double,

    val carbohydratesMin: Double,
    val carbohydratesMax: Double,

    val caloriesMin: Double,
    val caloriesMax: Double,

    val dietaryFiberMin: Double = 20.0,
    val dietaryFiberMax: Double = 25.0,

    val saturatedFatsMax: Double,
    val addedSugarsMax: Double,

    val vitaminCMg: Double = 100.0,
    val vitaminB1Mg: Double,
    val vitaminAMg: Double = 900.0,

    val calciumMg: Double,
    val magnesiumMg: Double = 420.0,
    val ironMg: Double = 10.0,
    val phosphorusMg: Double = 700.0,
    val sodiumMaxMg: Double = 500.0,
    val sodiumMinMg: Double = 2000.0,

    val rationWeightG: Double
)