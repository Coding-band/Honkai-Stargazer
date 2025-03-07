package utils.app

import com.russhwolf.settings.Settings
import getLocalHttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.UserAgent
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.serialization.kotlinx.json.json
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import utils.device.AppInfo.Companion.AppInfoInstance
import utils.starbase.StarbaseAPI

@Serializable
class CharWeightList(){
    companion object{
        const val prefKeyJson = "charWeightListJson"
        var INSTANCE = getWeightListJson()

        fun update(force : Boolean = false){
            if(!Preferences().CharWeightList.isUpdateCharWeightListNow() && !force) return
            val json = getWeightListJson()
            if(json is JsonObject && json.isNotEmpty()){
                INSTANCE = json
                Settings().putString(prefKeyJson, json.toString())
                Preferences().CharWeightList.updatedCharWeightList()
                println("CharWeightList Updated")
            }
        }

        private fun getWeightListJson(): JsonElement{
            return Json.parseToJsonElement(readFromOnlineURL("${StarbaseAPI().getGitHubStaticAssetURL()}/data/charWeightList.json"))
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