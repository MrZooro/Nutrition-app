package dao.tables

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.javatime.timestamp

object UserNutritionTargets : IntIdTable(
    name = "nutrition_app.user_nutrition_targets",
    columnName = "target_id"
) {

    val userId = reference(
        name = "user_id",
        foreign = Users,
        onDelete = ReferenceOption.CASCADE
    ).uniqueIndex()

    val proteinsMin = decimal("proteins_min", precision = 5, scale = 2)
    val proteinsMax = decimal("proteins_max", precision = 5, scale = 2)

    val fatsMin = decimal("fats_min", precision = 5, scale = 2)
    val fatsMax = decimal("fats_max", precision = 5, scale = 2)

    val carbohydratesMin = decimal("carbohydrates_min", precision = 5, scale = 2)
    val carbohydratesMax = decimal("carbohydrates_max", precision = 5, scale = 2)

    val caloriesMin = decimal("calories_min", precision = 8, scale = 2)
    val caloriesMax = decimal("calories_max", precision = 8, scale = 2)

    val dietaryFiberMin = decimal("dietary_fiber_min", precision = 5, scale = 2)
    val dietaryFiberMax = decimal("dietary_fiber_max", precision = 5, scale = 2)

    val saturatedFatsMax = decimal("saturated_fats_max", precision = 8, scale = 2)
    val addedSugarsMax = decimal("added_sugars_max", precision = 8, scale = 2)

    val vitaminCMg = decimal("vitamin_c_mg", precision = 8, scale = 2)
    val vitaminB1Mg = decimal("vitamin_b1_mg", precision = 8, scale = 2)
    val vitaminAMg = decimal("vitamin_a_mg", precision = 8, scale = 2)

    val calciumMg = decimal("calcium_mg", precision = 8, scale = 2)
    val magnesiumMg = decimal("magnesium_mg", precision = 8, scale = 2)
    val ironMg = decimal("iron_mg", precision = 8, scale = 2)
    val phosphorusMg = decimal("phosphorus_mg", precision = 8, scale = 2)

    val sodiumMax = decimal("sodium_max", precision = 8, scale = 2)
    val sodiumMin = decimal("sodium_min", precision = 8, scale = 2)

    val rationWeightG = decimal("ration_weight_g", precision = 8, scale = 2)

    val updatedAt = timestamp("updated_at")

    init {
        index(
            customIndexName = "user_nutrition_targets_user_id_key",
            columns = arrayOf(userId)
        )
    }
}