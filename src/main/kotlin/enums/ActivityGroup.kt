package enums

import kotlinx.serialization.Serializable

@Serializable
enum class ActivityGroup(val value: Double) {
    VERY_LOW(1.4),   // I группа
    LOW(1.6),        // II группа
    MEDIUM(1.9),     // III группа
    HIGH(2.2)        // IV группа
}