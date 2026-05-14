package enums

import kotlinx.serialization.Serializable

@Serializable
enum class DietType(
    val minProteins: Double,
    val maxProteins: Double,
    val minFats: Double,
    val maxFats: Double,
    val minCarbohydrates: Double,
    val maxCarbohydrates: Double,
    val spreadEnergy: Double
) {
    STANDARD(
        minProteins = 12.0,
        maxProteins = 16.0,
        minFats = 20.0,
        maxFats = 30.0,
        minCarbohydrates = 56.0,
        maxCarbohydrates = 58.0,
        spreadEnergy = 5.0
    ),
    MECHANICAL_CHEMICAL_SPARING(
        minProteins = 12.0,
        maxProteins = 16.0,
        minFats = 20.0,
        maxFats = 30.0,
        minCarbohydrates = 56.0,
        maxCarbohydrates = 58.0,
        spreadEnergy = 6.5
    ),
    HIGH_PROTEIN(
        minProteins = 18.0,
        maxProteins = 21.0,
        minFats = 30.0,
        maxFats = 35.0,
        minCarbohydrates = 48.0,
        maxCarbohydrates = 52.0,
        spreadEnergy = 12.7
    ),
    LOW_PROTEIN(
        minProteins = 3.7,
        maxProteins = 9.0,
        minFats = 30.5,
        maxFats = 34.0,
        minCarbohydrates = 60.4,
        maxCarbohydrates = 66.0,
        spreadEnergy = 11.0
    ),
    LOW_CALORIE(
        minProteins = 20.0,
        maxProteins = 21.0,
        minFats = 40.0,
        maxFats = 41.0,
        minCarbohydrates = 38.0,
        maxCarbohydrates = 39.0,
        spreadEnergy = 7.2
    ),
    HIGH_CALORIE(
        minProteins = 15.5,
        maxProteins = 16.7,
        minFats = 30.0,
        maxFats = 32.0,
        minCarbohydrates = 51.6,
        maxCarbohydrates = 55.5,
        spreadEnergy = 7.4
    )
}