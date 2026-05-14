package data

data class RecipeSummary(
    val id: Int,
    val name: String,
    val description: String?,
    val mealType: MealType,
    val nutrients: NutritionSummary
)
