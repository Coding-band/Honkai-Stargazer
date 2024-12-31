package ui.function.characterInfoPage

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import dev.chrisbanes.haze.HazeState
import kotlinx.coroutines.CoroutineScope
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import moe.tlaster.precompose.navigation.BackStackEntry
import type.Character
import type.Relic

data class CharacterInfoPageState(
    val charInfoJson: MutableState<JsonElement> = mutableStateOf(Json.parseToJsonElement("{}")),
    val charOnlyWeightJson : MutableState<JsonElement> = mutableStateOf(Json.parseToJsonElement("{}")),
    val hazeState: HazeState = HazeState()
)

sealed class CharacterInfoPageIntent {
    data object Initialize : CharacterInfoPageIntent()
    data object RefreshData : CharacterInfoPageIntent()
    data class SetCharacterInfoJson(val charInfoJson: JsonElement) : CharacterInfoPageIntent()
    data class SetHazeState(val hazeState: HazeState) : CharacterInfoPageIntent()
    data class SetCharOnlyWeightJson(val charOnlyWeightJson: JsonElement) : CharacterInfoPageIntent()
}