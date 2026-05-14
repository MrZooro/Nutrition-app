package data

data class Ingredient(
    val id: Int,
    val name: String,
    val description: String,

    val caloriesKcal: Double,
    val proteinsG: Double,
    val fatsG: Double,
    val carbohydratesG: Double,

    val fiberG: Double,
    val saturatedFatsG: Double,
    val addedSugarsG: Double,

    val vitaminCMg: Double,
    val vitaminB1Mg: Double,
    val vitaminAMg: Double,

    val calciumMg: Double,
    val magnesiumMg: Double,
    val ironMg: Double,
    val phosphorusMg: Double,
    val sodiumMg: Double
)

