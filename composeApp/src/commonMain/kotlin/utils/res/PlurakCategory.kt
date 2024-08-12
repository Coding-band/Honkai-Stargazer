package utils.res

internal enum class PluralCategory {
    ZERO,
    ONE,
    TWO,
    FEW,
    MANY,
    OTHER;

    companion object {
        fun fromString(name: String): PluralCategory? {
            return entries.firstOrNull {
                it.name.equals(name, true)
            }
        }
    }
}