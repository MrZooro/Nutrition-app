package data

import enums.ActivityGroup
import enums.DietType
import enums.GenderType
import kotlinx.serialization.Serializable

@Serializable
data class CreateUserRequest(
    val lastName: String,
    val firstName: String,
    val middleName: String? = null,
    val email: String,
    val passwordHash: String,
    val age: Int,
    val weightKg: Double,
    val heightCm: Double,
    val activityGroup: ActivityGroup,
    val dietType: DietType,
    val gender: GenderType
)