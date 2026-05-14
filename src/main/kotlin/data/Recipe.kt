package data

data class Recipe(
    val id: Int,
    val name: String,
    val description: String?,
    val mealType: MealType,
    val ingredients: List<RecipeIngredient>
)

fun Recipe.toNutritionSummary(): NutritionSummary {

    val nutrients = NutritionSummary()

    for (item in ingredients) {
        val factor = item.quantityG / 100.0
        val ingredient = item.ingredient

        nutrients.totalWeight += item.quantityG

        nutrients.calories += ingredient.caloriesKcal * factor
        nutrients.proteins += ingredient.proteinsG * factor
        nutrients.fats += ingredient.fatsG * factor
        nutrients.carbohydrates += ingredient.carbohydratesG * factor

        nutrients.fiber += ingredient.fiberG * factor
        nutrients.saturatedFats += ingredient.saturatedFatsG * factor
        nutrients.addedSugars += ingredient.addedSugarsG * factor

        nutrients.vitaminC += ingredient.vitaminCMg * factor
        nutrients.vitaminB1 += ingredient.vitaminB1Mg * factor
        nutrients.vitaminA += ingredient.vitaminAMg * factor

        nutrients.calcium += ingredient.calciumMg * factor
        nutrients.magnesium += ingredient.magnesiumMg * factor
        nutrients.iron += ingredient.ironMg * factor
        nutrients.phosphorus += ingredient.phosphorusMg * factor
        nutrients.sodium += ingredient.sodiumMg * factor
    }

    return NutritionSummary(
        calories = nutrients.calories,
        proteins = nutrients.proteins,
        fats = nutrients.fats,
        carbohydrates = nutrients.carbohydrates,

        fiber = nutrients.fiber,
        saturatedFats = nutrients.saturatedFats,
        addedSugars = nutrients.addedSugars,

        vitaminC = nutrients.vitaminC,
        vitaminB1 = nutrients.vitaminB1,
        vitaminA = nutrients.vitaminA,

        calcium = nutrients.calcium,
        magnesium = nutrients.magnesium,
        iron = nutrients.iron,
        phosphorus = nutrients.phosphorus,
        sodium = nutrients.sodium,

        totalWeight = nutrients.totalWeight
    )
}

fun Recipe.toRecipeSummary(): RecipeSummary {
    return RecipeSummary(
        id = id,
        name = name,
        description = description,
        mealType = mealType,
        nutrients = toNutritionSummary()
    )
}