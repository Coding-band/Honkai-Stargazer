package utils.app

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

@Serializable
class CharWeightList(){
    companion object{
        var INSTANCE = Preferences().CharWeightList.getCharWeightList()

        fun forceUpdate(){
            val json = (Preferences().CharWeightList.getCharWeightList())
            if(json is JsonObject && json.isNotEmpty()){
                INSTANCE = json
            }
        }
    }
}


/*
@Serializable
data class RelicAttr(
    val relicType: String,
    val propertyName: String
)

@Serializable
data class Trace(
    val talent: Float,
    val normal_atk: Float,
    val skill: Float,
    val ultimate: Float
)

@Serializable
data class Grad(
    val def: Float? = null,
    val spd: Float? = null,
    val effect_hit: Float? = null,
    val atk: Float? = null,
    val crit_rate: Float? = null,
    val crit_dmg: Float? = null
)

@Serializable
data class Attr(
    val hp: Float? = null,
    val def: Float? = null,
    val spd: Float? = null,
    val effect_hit: Float? = null,
    val effect_res: Float? = null,
    val sp_rate: Float? = null,
    val nothing: Float? = null,
    val atk: Float? = null,
    val crit_rate: Float? = null,
    val crit_dmg: Float? = null,
    val wind_dmg: Float? = null,
    val break_dmg: Float? = null,
    val fire_dmg: Float? = null,
    val imaginary_dmg: Float? = null
)

@Serializable
data class RelicScore(
    val main: List<String>
)

@Serializable
data class CharacterData(
    val charId: Int,
    val keywords: List<Int>,
    val advice_lightcone: List<Int>,
    val normal_lightcone: List<Int>,
    val advice_relic: List<List<Int>>,
    val advice_ornament: List<Int>,
    val advice_relic_attr: List<RelicAttr>,
    val advice_relic_sub: List<String>,
    val soul: List<Int>,
    val trace: Trace,
    val grad: Grad,
    val attr: Attr,
    val team: List<List<String>>,
    val relicScore: RelicScore
)

fun parseJson(jsonString: String): Map<String, List<CharacterData>> {
    val json = Json { ignoreUnknownKeys = true }
    val jsonObject = json.parseToJsonElement(jsonString).jsonObject
    return jsonObject.mapValues { entry ->
        json.decodeFromJsonElement<List<CharacterData>>(entry.value)
    }
}
 */