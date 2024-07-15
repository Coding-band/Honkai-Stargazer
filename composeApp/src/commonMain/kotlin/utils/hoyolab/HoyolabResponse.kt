package utils.hoyolab

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class HoyolabResponse(
    val retcode : Int,
    val message : String,
    val data : JsonElement
)
