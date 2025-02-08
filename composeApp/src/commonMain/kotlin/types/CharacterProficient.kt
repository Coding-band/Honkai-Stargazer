package types

import kotlinx.serialization.Serializable

@Serializable
data class CharacterProficient(
    val playerId: Int = 0,
    val playerName: String = "Unknown",

    val charId: Int = 8001,
    val schoolId: Int = 0,

    val charTotalScore: Float = 0f,
    val charSoul : Int = 0,
    val lcId: Int? = null,

    val rank: Int = -1,
    val totalRecords: Int = -1,
)