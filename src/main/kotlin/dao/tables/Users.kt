package dao.tables

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.javatime.timestamp
import java.math.BigDecimal

object Users : IntIdTable(
    name = "nutrition_app.users",
    columnName = "user_id"
) {

    val lastName = varchar("last_name", 100)
    val firstName = varchar("first_name", 100)
    val middleName = varchar("middle_name", 100).nullable()

    val email = varchar("email", 255).uniqueIndex()

    val passwordHash = varchar("password_hash", 255)

    val age = integer("age")

    val gender = char("gender")

    val weightKg = decimal("weight_kg", precision = 5, scale = 2)
    val heightCm = decimal("height_cm", precision = 5, scale = 2)

    val activityLevelId = reference(
        name = "activity_level_id",
        foreign = ActivityLevels,
        onDelete = ReferenceOption.RESTRICT
    )

    val dietTypeId = reference(
        name = "diet_type_id",
        foreign = DietTypes,
        onDelete = ReferenceOption.RESTRICT
    )

    val createdAt = timestamp("created_at")

    init {
        index(
            customIndexName = "idx_users_diet_type_id",
            columns = arrayOf(dietTypeId)
        )
    }
}