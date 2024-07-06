package utils.calculator

import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.int
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import types.Material
import kotlin.math.roundToInt

// 根據角色等級取得 升級角色所需素材數值

val lvMin = arrayOf(1, 20, 30, 40, 50, 60, 70)
val lvMax = arrayOf(20, 30, 40, 50, 60, 70, 80)
val charExpId = arrayOf(409960,409961,409962)
val lcExpId = arrayOf(694487,694488,694489)

//This can be extend if necessery

fun getCharMaterialData(
    jsonElement: JsonElement, beginLv: Int, endLv: Int
): HashMap<Int, Material> {
    val charLevelData = jsonElement.jsonObject["levelData"]
    val charExpData = jsonElement.jsonObject["calculator"]!!.jsonObject["expCost"]
    val charItemRef = jsonElement.jsonObject["itemReferences"]
    if (beginLv < 1 || endLv > 80 || beginLv >= 80 || charLevelData == null || charItemRef == null || charExpData == null) return HashMap()

    var startPointer = 0;
    var endPointer = 0;

    val materialList : HashMap<Int, Material> = HashMap()

    for (x in lvMax.indices) {
        if (beginLv < lvMax[x] && beginLv >= lvMin[x]) {
            //lv20已突破 -> lv40未突破的假設
            startPointer = x
        }
        if (endLv <= lvMax[x] && endLv > lvMin[x]) {
            //lv20已突破 -> lv40未突破的假設
            endPointer = x
        }
    }

    for (ascLvl in startPointer..<endPointer) {
        // 突破素材
        for (costData in charLevelData.jsonArray[ascLvl].jsonObject["cost"]!!.jsonArray) {
            val id = costData.jsonObject["id"]!!.jsonPrimitive.int
            if(materialList[id] === null){materialList[id] = Material(
                id,
                charItemRef.jsonObject[id.toString()]!!.jsonObject["name"]!!.jsonPrimitive.content,
                charItemRef.jsonObject[id.toString()]!!.jsonObject["rarity"]!!.jsonPrimitive.int,
                0
            )}
            materialList[id]!!.count += costData.jsonObject["count"]!!.jsonPrimitive.int
        }
    }

    for(id in charExpId){
        if(materialList[id] === null){materialList[id] = Material(
            id,
            charItemRef.jsonObject[id.toString()]!!.jsonObject["name"]!!.jsonPrimitive.content,
            charItemRef.jsonObject[id.toString()]!!.jsonObject["rarity"]!!.jsonPrimitive.int,
            0
        )}
    }

    //等級經驗 - 必須遵守突破原則
    var tmpEXP = 0;
    for (lvl in beginLv..<endLv) {
        tmpEXP += (charExpData.jsonObject[lvl.toString()]!!.jsonPrimitive.int);
    }
    materialList[409960]!!.count += (tmpEXP / 20000); //紫色
    materialList[409961]!!.count += ((tmpEXP % 20000) / 5000); //藍色
    materialList[409962]!!.count += (((tmpEXP % 20000) % 5000) / 1000f).roundToInt() //綠色


    return materialList
}


fun getLcMaterialData(
    jsonElement: JsonElement, beginLv: Int, endLv: Int
): HashMap<Int, Material> {
    val lcLevelData = jsonElement.jsonObject["levelData"]
    val lcExpData = jsonElement.jsonObject["calculator"]!!.jsonObject["expCost"]
    val lcItemRef = jsonElement.jsonObject["itemReferences"]
    if (beginLv < 1 || endLv > 80 || beginLv >= 80 || lcLevelData == null || lcItemRef == null || lcExpData == null) return HashMap()

    var startPointer = 0;
    var endPointer = 0;

    val materialList : HashMap<Int, Material> = HashMap()

    for (x in lvMax.indices) {
        if (beginLv < lvMax[x] && beginLv >= lvMin[x]) {
            //lv20已突破 -> lv40未突破的假設
            startPointer = x
        }
        if (endLv <= lvMax[x] && endLv > lvMin[x]) {
            //lv20已突破 -> lv40未突破的假設
            endPointer = x
        }
    }

    for (ascLvl in startPointer..<endPointer) {
        // 突破素材
        for (costData in lcLevelData.jsonArray[ascLvl].jsonObject["cost"]!!.jsonArray) {
            val id = costData.jsonObject["id"]!!.jsonPrimitive.int
            if(materialList[id] === null){materialList[id] = Material(
                id,
                lcItemRef.jsonObject[id.toString()]!!.jsonObject["name"]!!.jsonPrimitive.content,
                lcItemRef.jsonObject[id.toString()]!!.jsonObject["rarity"]!!.jsonPrimitive.int,
                0
            )}
            materialList[id]!!.count += costData.jsonObject["count"]!!.jsonPrimitive.int
        }
    }

    for(id in lcExpId){
        if(materialList[id] === null){materialList[id] = Material(
            id,
            lcItemRef.jsonObject[id.toString()]!!.jsonObject["name"]!!.jsonPrimitive.content,
            lcItemRef.jsonObject[id.toString()]!!.jsonObject["rarity"]!!.jsonPrimitive.int,
            0
        )}
    }

    //等級經驗 - 必須遵守突破原則
    var tmpEXP = 0;
    for (lvl in beginLv..<endLv) {
        tmpEXP += (lcExpData.jsonObject[lvl.toString()]!!.jsonPrimitive.int);
    }
    materialList[694487]!!.count += (tmpEXP / 6000); //紫色
    materialList[694488]!!.count += ((tmpEXP % 6000) / 2000); //藍色
    materialList[694489]!!.count += (((tmpEXP % 6000) % 2000) / 500f).roundToInt() //綠色


    return materialList
}