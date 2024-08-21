package utils.hoyolab

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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.boolean
import kotlinx.serialization.json.float
import kotlinx.serialization.json.int
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import types.AppInfo.Companion.AppInfoInstance
import types.Attribute
import types.Character
import types.CharacterStatus
import types.HsrProperties
import types.Lightcone
import types.Relic
import types.UserAccount
import utils.Language
import utils.errorLogExport

class MihomoRequest(val uid : String, val language: Language.TextLanguage = Language.TextLanguageInstance) {

    fun getSRInfoParsed() : JsonElement {
        if (uid.toLongOrNull() == null || uid.toLong() < 100000000L){
            return Json.parseToJsonElement("{}")
        }

        val mihomoUrl = "https://api.mihomo.me/sr_info_parsed/${uid}?lang=${language.langCode}"
        val client = getLocalHttpClient {
            install(HttpTimeout){
                requestTimeoutMillis = 4000
            }
            install(ContentNegotiation){
                json()
            }
            install(UserAgent){
                agent = "Stargazer 3 (v${AppInfoInstance.appVersionName})"
            }
            expectSuccess = true

            defaultRequest {
                url(mihomoUrl)
            }
        }

        try {
            return runBlocking {
                return@runBlocking withTimeout(4000) {
                    val response: HttpResponse = client.get(mihomoUrl)
                    //Check whether it is having any errors
                    if (!arrayListOf(200, 201).contains(response.status.value)) {
                        errorLogExport(
                            "MihomoRequest",
                            "getSRInfoParsed(uid = ${uid}, lang = ${language})",
                            Exception("HTTP Error Code ${response.status.value} : ${response.status.description}")
                        )
                        return@withTimeout Json.parseToJsonElement("{}")

                    } else {
                        return@withTimeout response.body()
                    }
                }
            }

        }catch (e : UnresolvedAddressException){
            //Cannot find the Address, maybe bcz of u are offline
            //errorLogExport("HoyolabRequest", "send(url = ${url}, body = ${body})",e)
            e.printStackTrace()
        }catch (e : Exception){
            // All response
            errorLogExport("MihomoRequest", "getSRInfoParsed(uid = ${uid}, lang = ${language})",e)
        }

        return Json.parseToJsonElement("{}")
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getUserAccountByMiHomo() : UserAccount {
        return runBlocking {
            val job = async(Dispatchers.Default){
                val srInfoParsed = getSRInfoParsed()
                val userAccount = UserAccount()

                println("srInfoParsed: $srInfoParsed")

                try {
                    if (srInfoParsed is JsonObject && !srInfoParsed.isEmpty() && !(srInfoParsed.containsKey("detail") && srInfoParsed["detail"]!!.jsonPrimitive.content == "Invalid uid")) {
                        val player = srInfoParsed.jsonObject["player"]
                        val characters = srInfoParsed.jsonObject["characters"]

                        if (player != null) {
                            userAccount.uid = uid
                            userAccount.username = player.jsonObject["nickname"]!!.jsonPrimitive.content
                            userAccount.signature = player.jsonObject["signature"]!!.jsonPrimitive.content
                            userAccount.level = player.jsonObject["level"]!!.jsonPrimitive.int
                            userAccount.ascLevel = player.jsonObject["world_level"]!!.jsonPrimitive.int
                            userAccount.icon = player.jsonObject["avatar"]!!.jsonObject["id"]!!.jsonPrimitive.content //E.g. 202017
                            userAccount.server = HoyolabConst().getServerByUID(uid)
                            userAccount.unlockedCharCount = characters?.jsonArray?.size ?: 0
                        }

                        if (characters != null) {
                            userAccount.characterList.clear()
                            for (characterElement in characters.jsonArray) {
                                val characterObj = characterElement.jsonObject
                                val character =
                                    Character.getCharacterItemFromJSON(characterObj["id"]!!.jsonPrimitive.content)
                                val charSkill = characterObj["skills"]!!.jsonArray

                                //Basic Status
                                val characterStatus = CharacterStatus(
                                    characterLevel = characterObj["level"]!!.jsonPrimitive.int,
                                    ascension = characterObj["promotion"]!!.jsonPrimitive.int,
                                    eidolon = characterObj["rank"]!!.jsonPrimitive.int,
                                    traceBasicAtkLevel = charSkill.filter { it.jsonObject["type"]!!.jsonPrimitive.content == "Normal" }[0].jsonObject["level"]!!.jsonPrimitive.int,
                                    traceSkillLevel = charSkill.filter { it.jsonObject["type"]!!.jsonPrimitive.content == "BPSkill" }[0].jsonObject["level"]!!.jsonPrimitive.int,
                                    traceUltimateLevel = charSkill.filter { it.jsonObject["type"]!!.jsonPrimitive.content == "Ultra" }[0].jsonObject["level"]!!.jsonPrimitive.int,
                                    traceTalentLevel = charSkill.filter { it.jsonObject["type"]!!.jsonPrimitive.content == "Talent" }[0].jsonObject["level"]!!.jsonPrimitive.int,
                                )

                                //Lightcone
                                if(characterObj["light_cone"] != null && characterObj["light_cone"]!! is JsonObject){
                                    val lightconeObj = characterObj["light_cone"]!!.jsonObject
                                    val lightcone =
                                        Lightcone.getLightconeItemFromJSON(lightconeObj["id"]!!.jsonPrimitive.content)
                                    lightcone.level = lightconeObj["level"]!!.jsonPrimitive.int
                                    lightcone.superimposition =
                                        lightconeObj["rank"]!!.jsonPrimitive.int
                                    characterStatus.equippingLightcone = lightcone
                                }

                                //Relics
                                if(characterObj["relics"] != null && characterObj["relics"]!! is JsonArray){
                                    val relicArrJson = characterObj["relics"]!!.jsonArray
                                    for (relicJson in relicArrJson) {
                                        val relicObj = relicJson.jsonObject
                                        val relic = Relic.getRelicItemFromJSON(relicObj["set_id"]!!.jsonPrimitive.content)
                                        relic.level = relicObj["level"]!!.jsonPrimitive.int
                                        //Main
                                        relic.properties.add(
                                            HsrProperties(
                                                attributeExchange = AttributeExchange.getAttrKeyByMihomoType(
                                                    relicObj["main_affix"]!!.jsonObject["type"]!!.jsonPrimitive.content,
                                                    isForRelic = true
                                                ),
                                                valueFinal = relicObj["main_affix"]!!.jsonObject["value"]!!.jsonPrimitive.float,
                                            )
                                        )

                                        //sub_affix
                                        for (subAffix in relicObj["sub_affix"]!!.jsonArray) {
                                            val subAffixObj = subAffix.jsonObject
                                            relic.properties.add(
                                                HsrProperties(
                                                    attributeExchange = AttributeExchange.getAttrKeyByMihomoType(
                                                        subAffixObj["type"]!!.jsonPrimitive.content,
                                                        isForRelic = true
                                                    ),
                                                    valueFinal = subAffixObj["value"]!!.jsonPrimitive.float,
                                                )
                                            )
                                        }

                                        when (relicObj["type"]!!.jsonPrimitive.int) {
                                            1 -> characterStatus.equippingRelicHead = relic
                                            2 -> characterStatus.equippingRelicHands = relic
                                            3 -> characterStatus.equippingRelicBody = relic
                                            4 -> characterStatus.equippingRelicFeet = relic
                                            5 -> characterStatus.equippingRelicPlanar = relic
                                            6 -> characterStatus.equippingRelicLinkRope = relic
                                        }
                                    }
                                }

                                //Properties -> For Relic Scoring
                                /*
                                for (attr in characterObj["properties"]!!.jsonArray) {
                                    val attrObj = attr.jsonObject
                                    val attrEx = AttributeExchange.getAttrKeyByMihomoType(
                                        attrObj["type"]!!.jsonPrimitive.content,
                                        isForRelic = false,
                                        isPercent = attrObj["percent"]!!.jsonPrimitive.boolean
                                    )

                                    properties.add(
                                        HsrProperties(
                                            attributeExchange = attrEx,
                                            valueBase = attrObj["value"]!!.jsonPrimitive.float,
                                            valueFinal = attrObj["value"]!!.jsonPrimitive.float,
                                        )
                                    )
                                }
                                 */

                                //Attr & Addi
                                val attrAddi = arrayListOf<HsrProperties>()

                                //Missing Speed Data
                                attrAddi.add(
                                    HsrProperties(
                                        attributeExchange = AttributeExchange.getAttrKeyByMihomoType(
                                            "SPRatioBase",
                                            isForRelic = false,
                                            isPercent = true
                                        ),
                                        valueBase = 1f,
                                        valueFinal = 1f
                                    )
                                )

                                if(characterObj["attributes"] != null && characterObj["attributes"] is JsonArray){
                                    for (attr in characterObj["attributes"]!!.jsonArray) {
                                        val attrObj = attr.jsonObject
                                        val attrEx = AttributeExchange.getAttrKeyByMihomoKey(
                                            attrObj["field"]!!.jsonPrimitive.content,
                                            isForRelic = false,
                                            isPercent = attrObj["percent"]!!.jsonPrimitive.boolean
                                        )

                                        //Find the index of HsrProperties where hsrProperties.attributeExchange == attrEx
                                        val index = attrAddi.indexOfFirst { it.attributeExchange == attrEx }


                                        if (index == -1) {
                                            attrAddi.add(
                                                HsrProperties(
                                                    attributeExchange = attrEx,
                                                    valueBase = attrObj["value"]!!.jsonPrimitive.float,
                                                    valueFinal = attrObj["value"]!!.jsonPrimitive.float,
                                                )
                                            )
                                        } else {
                                            attrAddi[index].valueBase = attrObj["value"]!!.jsonPrimitive.float + if(attrEx.attribute == Attribute.ATTR_SP_RATE) {1f} else {0f}
                                            attrAddi[index].valueFinal += attrObj["value"]!!.jsonPrimitive.float + if(attrEx.attribute == Attribute.ATTR_SP_RATE) {1f} else {0f}
                                        }
                                    }
                                }

                                if(characterObj["additions"] != null && characterObj["additions"] is JsonArray){
                                    for (attr in characterObj["additions"]!!.jsonArray) {
                                        val attrObj = attr.jsonObject
                                        val attrEx = AttributeExchange.getAttrKeyByMihomoKey(
                                            attrObj["field"]!!.jsonPrimitive.content,
                                            isForRelic = false,
                                            isPercent = attrObj["percent"]!!.jsonPrimitive.boolean
                                        )

                                        //Find the index of HsrProperties where hsrProperties.attributeExchange == attrEx
                                        val index =
                                            attrAddi.indexOfFirst { it.attributeExchange == attrEx }

                                        if (index == -1) {
                                            attrAddi.add(
                                                HsrProperties(
                                                    attributeExchange = attrEx,
                                                    valueAdd = attrObj["value"]!!.jsonPrimitive.float,
                                                    valueFinal = attrObj["value"]!!.jsonPrimitive.float,
                                                )
                                            )
                                        } else {
                                            attrAddi[index].valueAdd = attrObj["value"]!!.jsonPrimitive.float
                                            attrAddi[index].valueFinal += attrObj["value"]!!.jsonPrimitive.float
                                        }
                                    }
                                }

                                characterStatus.characterProperties = attrAddi
                                character.characterStatus = characterStatus

                                userAccount.characterList.add(character)

                            }
                        }
                    }
                }catch (e : Exception){
                    if(
                        srInfoParsed is JsonObject &&
                        !srInfoParsed.isEmpty() &&
                        !(srInfoParsed.containsKey("detail"))
                    ){
                        errorLogExport("MihomoRequest", "getUserAccountByMiHomo()",e)
                    }
                }

                return@async userAccount
            }

            job.await()
            job.getCompleted()
        }
    }

}