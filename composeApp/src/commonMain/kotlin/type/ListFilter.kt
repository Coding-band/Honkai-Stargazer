package type

data class ListFilter(
    val nameKeyWord: String = "",
    val rarity: Int = 0,
    val combatType: CombatType = CombatType.Unspecified,
    val path: Path = Path.Unspecified,
)