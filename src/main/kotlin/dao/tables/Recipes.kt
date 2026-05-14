package dao.tables

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.javatime.timestamp

object Recipes : IntIdTable(
    name = "nutrition_app.recipes",
    columnName = "recipe_id"
) {

    val recipeCategoryId = reference(
        name = "recipe_category_id",
        foreign = RecipeCategories,
        onDelete = ReferenceOption.RESTRICT
    )

    val mealTypeId = reference(
        name = "meal_type_id",
        foreign = MealTypes,
        onDelete = ReferenceOption.RESTRICT
    )

    val name = varchar("name", 255)

    val description = text("description")

    val createdAt = timestamp("created_at")

    init {
        index(
            customIndexName = "idx_recipes_meal_type_id",
            columns = arrayOf(mealTypeId)
        )
    }
}