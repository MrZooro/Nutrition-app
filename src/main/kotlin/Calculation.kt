import enums.ActivityGroup
import enums.DietType
import enums.GenderType

class Calculation {
    fun calculateBmr(
        gender: GenderType,
        weight: Double,
        height: Double,
        age: Int
    ): Double {
        return when (gender) {
            GenderType.M -> 9.99 * weight + 6.25 * height - 4.92 * age + 5
            GenderType.F -> 9.99 * weight + 6.25 * height - 4.92 * age - 161
        }
    }

    fun calculateEnergy(
        bmr: Double,
        kfa: ActivityGroup,
        dietType: DietType
    ): Double {

        val base = bmr * kfa.value

        return when (dietType) {
            DietType.STANDARD,
            DietType.MECHANICAL_CHEMICAL_SPARING,
            DietType.HIGH_PROTEIN,
            DietType.LOW_PROTEIN -> base

            DietType.LOW_CALORIE -> base * (1 - 0.367)

            DietType.HIGH_CALORIE -> base * (1 + 0.466)
        }
    }

    fun calculateValueRange(value: Double, percent: Double): Pair<Double, Double> {
        val delta = value * (percent / 100.0)

        val min = value - delta
        val max = value + delta

        return Pair(
            first = min,
            second = max
        )
    }

    fun calculateNutrientRange(
        nutrientEnergy: Double,
        minPercentage: Double,
        maxPercentage: Double,
        totalEnergy: Double
    ): Pair<Double, Double> {
        val nutrientFormEnergy = totalEnergy / 100 / nutrientEnergy

        return Pair(
            first = nutrientFormEnergy * minPercentage,
            second = nutrientFormEnergy * maxPercentage
        )
    }

    fun calculateNutrientWeight(nutrientEnergy: Double, percentage: Double, totalEnergy: Double): Double {
        return totalEnergy / 100 / nutrientEnergy * percentage
    }

    fun getCalcium(age: Int): Double {
        return if (age > 65) 1200.0 else 1000.0
    }
}