package dao.tables

import org.jetbrains.exposed.dao.id.IntIdTable

object RecipeCategories : IntIdTable(
    name = "nutrition_app.recipe_categories",
    columnName = "recipe_category_id"
) {

    val name = varchar("name", 100).uniqueIndex()

    val description = text("description")
}