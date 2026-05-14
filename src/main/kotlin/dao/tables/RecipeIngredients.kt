package dao.tables

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption
import java.math.BigDecimal

object RecipeIngredients : IntIdTable(
    name = "nutrition_app.recipe_ingredients",
    columnName = "recipe_ingredient_id"
) {

    val recipeId = reference(
        name = "recipe_id",
        foreign = Recipes,
        onDelete = ReferenceOption.CASCADE
    )

    val ingredientId = reference(
        name = "ingredient_id",
        foreign = Ingredients,
        onDelete = ReferenceOption.RESTRICT
    )

    val quantityG = decimal(
        name = "quantity_g",
        precision = 8,
        scale = 2
    ).check {
        it greater BigDecimal.ZERO
    }

    init {
        uniqueIndex(
            customIndexName = "uq_recipe_ingredient",
            columns = arrayOf(recipeId, ingredientId)
        )

        index(
            customIndexName = "idx_recipe_ingredients_recipe_id",
            columns = arrayOf(recipeId)
        )

        index(
            customIndexName = "idx_recipe_ingredients_ingredient_id",
            columns = arrayOf(ingredientId)
        )
    }
}