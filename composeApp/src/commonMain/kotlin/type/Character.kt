/*
 * Project Honkai Stargazer and app Stargazer (星穹觀星者) were
 * Organized & Develop by Coding Band.
 * Copyright © 2024 Coding Band 版權所有
 */

package type

import androidx.annotation.IntRange
import kotlinx.serialization.Serializable
import utils.annotation.DoItLater
import utils.calculator.AttrData


@Serializable
open class Character(
    var officialId: Int? = -1,
    var registName: String? = "Unknown",
    var fileName: String? = "unknown",
    var localName: String? = "未知",
    @IntRange(4, 5) var rarity: Int = 4,
    var path: Path = Path.Unspecified,
    var combatType: CombatType = CombatType.Unspecified,
    var gender: Gender = Gender.Unspecified,

    //For Character Status
    var characterStatus: CharacterStatus? = null,
    var displayName: String? = "?",
    var version: String? = "1.0.0",
    var characterAttrData: AttrData? = null,
    ){
    enum class Gender{
        Male, Female, Unspecified
    }



    @DoItLater("Rearrange those function later")
    companion object {
    /*
        val charListJson = getCharacterListFromJSON()
        val charExtListJson = getCharacterExtListFromJSON()

        private fun getCharacterListFromJSON() : JsonElement {
            return UtilTools().getAssetsJsonByFilePath("character_data/character_list.json")
        }
        private fun getCharacterExtListFromJSON() : JsonElement {
            return UtilTools().getAssetsJsonByFilePath("character_data/character_ext_list.json")
        }

        fun getCharacterDataFromFileName(characterFileName : String, textLanguage: Language.TextLanguage = Language.TextLanguageInstance) : JsonElement {
            return UtilTools().getAssetsJsonByFilePath("character_data/${textLanguage.folderName}/${characterFileName}.json")
        }

        /**
         * composeResources/files/files/images/character_icon/jade_icon.webp
         */
        fun getCharacterImageFromFileName(imageFolderType: UtilTools.ImageFolderType, characterName : String) : ImageBitmap {
            return UtilTools().getAssetsWebpByFileName(imageFolderType, UtilTools().getImageNameByRegistName(characterName, (imageFolderType === UtilTools.ImageFolderType.CHAR_FULL)))
        }
        fun getCharacterURLFromFileName(imageFolderType: UtilTools.ImageFolderType, characterName : String) : String {
            return UtilTools().getAssetsURLByFileName(imageFolderType, UtilTools().getImageNameByRegistName(characterName, (imageFolderType === UtilTools.ImageFolderType.CHAR_FULL)))
        }

        fun getCharacterImageByteArrayFromFileName(imageFolderType: UtilTools.ImageFolderType, characterName : String) : ByteArray {
            return UtilTools().getAssetsWebpByteArrayByFileName(imageFolderType, UtilTools().getImageNameByRegistName(characterName, (imageFolderType === UtilTools.ImageFolderType.CHAR_FULL)))
        }

        fun getCharacterImageFromOfficialId(imageFolderType: UtilTools.ImageFolderType, charId : String) : ByteArray {
            val listDataJson = charListJson.jsonArray.find { lcData -> lcData.jsonObject["charId"]!!.jsonPrimitive.content == charId } ?: return UtilTools().getLostImgByteArray()
            return getCharacterImageByteArrayFromFileName(imageFolderType, listDataJson.jsonObject["name"]!!.jsonPrimitive.content)
        }

        @OptIn(ExperimentalCoroutinesApi::class)
        fun getCharacterItemFromJSON(charId : String, textLanguage: Language.TextLanguage = Language.TextLanguageInstance, requireAttrData: Boolean = false) : Character {
            return runBlocking {
                val job = async(Dispatchers.Default) {
                    val listDataJson = charListJson.jsonArray.firstOrNull { charData -> charData.jsonObject["charId"]!!.jsonPrimitive.content == charId } ?: return@async Character(path = Path.Unspecified, )
                    val listExtDataJson = charExtListJson.jsonArray.firstOrNull { charData -> charData.jsonObject["officialId"]!!.jsonPrimitive.content == charId } ?: return@async Character(path = Path.Unspecified, )

                    return@async Character(
                        officialId = charId.toInt(),
                        fileName = listDataJson.jsonObject["fileName"]!!.jsonPrimitive.content,
                        registName = (listDataJson.jsonObject["name"]!!.jsonPrimitive.content),
                        rarity = listDataJson.jsonObject["rare"]!!.jsonPrimitive.int,
                        path = (Path.valueOf(listDataJson.jsonObject["path"]!!.jsonPrimitive.content)),
                        version = (listDataJson.jsonObject["version"]!!.jsonPrimitive.content),
                        displayName = listExtDataJson.jsonObject["localeName"]!!.jsonObject[textLanguage.folderName]?.jsonPrimitive?.content ?: "?",
                        combatType = (CombatType.valueOf(listDataJson.jsonObject["element"]!!.jsonPrimitive.content)),
                        characterAttrData = if(requireAttrData){ Json.decodeFromJsonElement<AttrData>(listExtDataJson.jsonObject["attrData"]!!) } else { null },
                    )
                }
                job.await()
                job.getCompleted()
            }
        }

        val Saver: Saver<Character, Any> = Saver(
            save = { Json.encodeToString(it) },
            restore = { Json.decodeFromString<Character>(it as String) }
        )
        val ListSaver: Saver<ArrayList<Character>, Any> = listSaver(
            save = { listOf(Json.encodeToString(it)) },
            restore = { Json.decodeFromString(it[0]) }
        )


     */
    }
}