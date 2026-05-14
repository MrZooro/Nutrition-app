package dao.tables

import org.jetbrains.exposed.dao.id.IntIdTable

object DietTypes : IntIdTable(
    name = "nutrition_app.diet_types",
    columnName = "diet_type_id"
) {

    val code = varchar("code", 50).uniqueIndex()

    val name = varchar("name", 100).uniqueIndex()

    val description = text("description")
}