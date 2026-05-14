package data

data class NutritionSummary(
    var calories: Double = 0.0,
    var proteins: Double = 0.0,
    var fats: Double = 0.0,
    var carbohydrates: Double = 0.0,

    var fiber: Double = 0.0,
    var saturatedFats: Double = 0.0,
    var addedSugars: Double = 0.0,

    var vitaminC: Double = 0.0,
    var vitaminB1: Double = 0.0,
    var vitaminA: Double = 0.0,

    var calcium: Double = 0.0,
    var magnesium: Double = 0.0,
    var iron: Double = 0.0,
    var phosphorus: Double = 0.0,
    var sodium: Double = 0.0,

    var totalWeight: Double = 0.0
)
