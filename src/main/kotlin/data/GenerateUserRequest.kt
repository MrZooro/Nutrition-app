package data

import kotlinx.serialization.Serializable

@Serializable
data class GenerateUserRequest(
    val firstMessage: String,
    val nutritionTargetsId: Int
)