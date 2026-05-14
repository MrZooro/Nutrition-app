package dao.tables

import org.jetbrains.exposed.dao.id.IntIdTable

object ActivityLevels : IntIdTable(
    name = "nutrition_app.activity_levels",
    columnName = "activity_level_id"
) {

    val name = varchar("name", 100).uniqueIndex()

    val coefficient = decimal("coefficient", precision = 4, scale = 2)
}