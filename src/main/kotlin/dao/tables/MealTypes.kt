package dao.tables

import org.jetbrains.exposed.dao.id.IntIdTable

object MealTypes : IntIdTable(
    name = "nutrition_app.meal_types",
    columnName = "meal_type_id"
) {

    val name = varchar("name", 50).uniqueIndex()
}