package dao.tables

import org.jetbrains.exposed.dao.id.IntIdTable

object Ingredients : IntIdTable(
    name = "nutrition_app.ingredients",
    columnName = "ingredient_id"
) {

    val name = varchar("name", 255).uniqueIndex()

    val description = text("description")

    val caloriesKcal = decimal("calories_kcal", precision = 8, scale = 2)

    val proteinsG = decimal("proteins_g", precision = 8, scale = 2)

    val fatsG = decimal("fats_g", precision = 8, scale = 2)

    val carbohydratesG = decimal("carbohydrates_g", precision = 8, scale = 2)

    val dietaryFiberG = decimal("dietary_fiber_g", precision = 8, scale = 2)

    val saturatedFatsG = decimal("saturated_fats_g", precision = 8, scale = 2)

    val addedSugarsG = decimal("added_sugars_g", precision = 8, scale = 2)

    val vitaminCMg = decimal("vitamin_c_mg", precision = 8, scale = 2)

    val vitaminB1Mg = decimal("vitamin_b1_mg", precision = 8, scale = 2)

    val vitaminAMg = decimal("vitamin_a_mg", precision = 8, scale = 2)

    val calciumMg = decimal("calcium_mg", precision = 8, scale = 2)

    val magnesiumMg = decimal("magnesium_mg", precision = 8, scale = 2)

    val ironMg = decimal("iron_mg", precision = 8, scale = 2)

    val phosphorusMg = decimal("phosphorus_mg", precision = 8, scale = 2)

    val sodiumMg = decimal("sodium_mg", precision = 8, scale = 2)
}