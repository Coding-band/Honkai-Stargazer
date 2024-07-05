package utils.calculator

import androidx.annotation.IntRange
import androidx.compose.ui.graphics.ImageBitmap
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.int
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import types.Material
import utils.UtilTools
import kotlin.math.roundToInt

// 根據角色等級取得 升級角色所需素材數值

val lvMin = arrayOf(1, 20, 30, 40, 50, 60, 70)
val lvMax = arrayOf(20, 30, 40, 50, 60, 70, 80)

//This can be extend if necessery
data class AttrMaterial(
    var id: Int = 1,
    var name: String = "NULL",
    var imageRes: ImageBitmap? = null,
    @IntRange(1, 5) var rarity: Int = 1,
    var count: Int = 0
)

fun getCharMaterialData(
    jsonElement: JsonElement, beginLv: Int, endLv: Int
): ArrayList<AttrMaterial> {
    val charLevelData = jsonElement.jsonObject["levelData"]
    val charExpData = jsonElement.jsonObject["calculator"]!!.jsonObject["expCost"]
    val charItemRef = jsonElement.jsonObject["itemReferences"]
    if (beginLv < 1 || endLv > 80 || beginLv >= 80 || charLevelData == null || charItemRef == null || charExpData == null) return arrayListOf()

    var attrMaterial : ArrayList<AttrMaterial> = arrayListOf(
        AttrMaterial(409960, charItemRef.jsonObject["409960"]!!.jsonObject["name"]!!.jsonPrimitive.content,Material().getMaterialById(409960),4,0),
        AttrMaterial(409961, charItemRef.jsonObject["409961"]!!.jsonObject["name"]!!.jsonPrimitive.content,Material().getMaterialById(409961),3,0),
        AttrMaterial(409962, charItemRef.jsonObject["409962"]!!.jsonObject["name"]!!.jsonPrimitive.content,Material().getMaterialById(409962),2,0),
    )

    for(itemRefKey in charItemRef.jsonObject.keys){
        var tmpAttrMaterial = AttrMaterial()
        var startPointer = 0;
        var endPointer = 0;

        tmpAttrMaterial.id = itemRefKey.toInt()
        tmpAttrMaterial.name = charItemRef.jsonObject[itemRefKey]!!.jsonObject["name"]!!.jsonPrimitive.content
        tmpAttrMaterial.rarity = charItemRef.jsonObject[itemRefKey]!!.jsonObject["rarity"]!!.jsonPrimitive.int
        tmpAttrMaterial.imageRes = UtilTools().getAssetsWebpByFileName(UtilTools.ImageFolderType.MATERIAL_ICON, itemRefKey)

        for (x in lvMin) {
            if (beginLv < lvMax[x] && beginLv >= lvMin[x]) {
                //lv20已突破 -> lv40未突破的假設
                startPointer = x
            }
            if (endLv <= lvMax[x] && endLv > lvMin[x]) {
                //lv20已突破 -> lv40未突破的假設
                endPointer = x
            }
        }

        for (ascLvl in startPointer..endPointer) {
            // 突破素材
            for (costData in charLevelData.jsonArray[ascLvl].jsonObject["cost"]!!.jsonArray) {
                tmpAttrMaterial.count += costData.jsonObject["count"]!!.jsonPrimitive.int
            }
        }

        //等級經驗 - 必須遵守突破原則
        var tmpEXP = 0;
        var tmpCoin = 0;
        for (lvl in beginLv..endLv) {
            tmpEXP += (charExpData.jsonObject[lvl.toString()]!!.jsonPrimitive.int);

            if (lvMax.contains(lvl + 1)) {
                attrMaterial[0].count += (tmpEXP / 20000); //紫色
                attrMaterial[1].count += ((tmpEXP % 20000) / 5000); //藍色
                attrMaterial[2].count += (if((((tmpEXP % 20000) % 5000) / 1000) !== (((tmpEXP % 20000) % 5000) / 1000f).roundToInt()) {
                    (((tmpEXP % 20000) % 5000) / 1000) + 1
                } else {(((tmpEXP % 20000) % 5000) / 1000)}) //綠色

                tmpEXP = 0;
            }
        }

        attrMaterial.add(tmpAttrMaterial)
    }
    return attrMaterial
}


fun getLcMaterialData(
    jsonElement: JsonElement, beginLv: Int, endLv: Int
): ArrayList<AttrMaterial> {
    val lcLevelData = jsonElement.jsonObject["levelData"]
    val lcExpData = jsonElement.jsonObject["calculator"]!!.jsonObject["expCost"]
    val lcItemRef = jsonElement.jsonObject["itemReferences"]
    if (beginLv < 1 || endLv > 80 || beginLv >= 80 || lcLevelData == null || lcItemRef == null || lcExpData == null) return arrayListOf()

    var attrMaterial : ArrayList<AttrMaterial> = arrayListOf(
        AttrMaterial(694487, lcItemRef.jsonObject["694487"]!!.jsonObject["name"]!!.jsonPrimitive.content,Material().getMaterialById(694487),4,0),
        AttrMaterial(694488, lcItemRef.jsonObject["694488"]!!.jsonObject["name"]!!.jsonPrimitive.content,Material().getMaterialById(694488),3,0),
        AttrMaterial(694489, lcItemRef.jsonObject["694489"]!!.jsonObject["name"]!!.jsonPrimitive.content,Material().getMaterialById(694489),2,0),
    )

    for(itemRefKey in lcItemRef.jsonObject.keys){
        var tmpAttrMaterial = AttrMaterial()
        var startPointer = 0;
        var endPointer = 0;

        tmpAttrMaterial.id = itemRefKey.toInt()
        tmpAttrMaterial.name = lcItemRef.jsonObject[itemRefKey]!!.jsonObject["name"]!!.jsonPrimitive.content
        tmpAttrMaterial.rarity = lcItemRef.jsonObject[itemRefKey]!!.jsonObject["rarity"]!!.jsonPrimitive.int
        tmpAttrMaterial.imageRes = UtilTools().getAssetsWebpByFileName(UtilTools.ImageFolderType.MATERIAL_ICON, itemRefKey)

        for (x in lvMin) {
            if (beginLv < lvMax[x] && beginLv >= lvMin[x]) {
                //lv20已突破 -> lv40未突破的假設
                startPointer = x
            }
            if (endLv <= lvMax[x] && endLv > lvMin[x]) {
                //lv20已突破 -> lv40未突破的假設
                endPointer = x
            }
        }

        for (ascLvl in startPointer..endPointer) {
            // 突破素材
            for (costData in lcLevelData.jsonArray[ascLvl].jsonObject["cost"]!!.jsonArray) {
                tmpAttrMaterial.count += costData.jsonObject["count"]!!.jsonPrimitive.int
            }
        }

        //等級經驗 - 必須遵守突破原則
        var tmpEXP = 0;
        var tmpCoin = 0;
        for (lvl in beginLv..endLv) {
            tmpEXP += (lcExpData.jsonObject[lvl.toString()]!!.jsonPrimitive.int);

            if (lvMax.contains(lvl + 1)) {
                attrMaterial[0].count += (tmpEXP / 6000); //紫色
                attrMaterial[1].count += ((tmpEXP % 6000) / 2000); //藍色
                attrMaterial[2].count += (if((((tmpEXP % 6000) % 2000) / 500) !== (((tmpEXP % 6000) % 2000) / 500f).roundToInt()) {
                    (((tmpEXP % 6000) % 2000) / 500) + 1
                } else {(((tmpEXP % 6000) % 2000) / 500)}) //綠色

                tmpEXP = 0;
            }
        }

        attrMaterial.add(tmpAttrMaterial)
    }
    return attrMaterial
}