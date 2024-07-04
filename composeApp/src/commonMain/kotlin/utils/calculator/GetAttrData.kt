package utils.calculator

import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.float
import kotlinx.serialization.json.int
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

/**
 * This file is ref from https://github.com/Coding-band/Honkai-Stargazer/blob/rn_branch/src/utils/calculator/getAttrData.ts
 * (寫算法係我自己吖嘛...拎嚟用返冇問題掛...)
 */
data class CharacterAttrData(
    var atk: Float,
    var hp: Float,
    var def: Float,
    var spd : Float,
    var aggro: Int,
    var energy: Int,
)

// 根據等級取得角色屬性數值
fun getCharAttrData(charJsonElement: JsonElement, level: Int = 1) : CharacterAttrData{
    val charLevelData = charJsonElement.jsonObject["levelData"]!!
    var tmpAttrData : CharacterAttrData = CharacterAttrData(0f,0f,0f,0f,0,0)

    // 找到對應等級的數據
    val dataFromLevel = charLevelData.jsonArray.find { data ->
        if(level == 80) {
            level <= data.jsonObject["maxLevel"]!!.jsonPrimitive.int
        } else {
            level < data.jsonObject["maxLevel"]!!.jsonPrimitive.int
        }
    }

    if(dataFromLevel !== null){
        tmpAttrData.atk = dataFromLevel.jsonObject["attackBase"]!!.jsonPrimitive.float + (dataFromLevel.jsonObject["attackAdd"]!!.jsonPrimitive.float) * (level - 1)
        tmpAttrData.def = dataFromLevel.jsonObject["defenseBase"]!!.jsonPrimitive.float + (dataFromLevel.jsonObject["defenseAdd"]!!.jsonPrimitive.float) * (level - 1)
        tmpAttrData.hp = dataFromLevel.jsonObject["hpBase"]!!.jsonPrimitive.float + (dataFromLevel.jsonObject["hpAdd"]!!.jsonPrimitive.float) * (level - 1)
        tmpAttrData.spd = dataFromLevel.jsonObject["speedBase"]!!.jsonPrimitive.float + (dataFromLevel.jsonObject["speedAdd"]!!.jsonPrimitive.float) * (level - 1)
        tmpAttrData.aggro = dataFromLevel.jsonObject["aggro"]!!.jsonPrimitive.int
        tmpAttrData.energy = charJsonElement.jsonObject["spRequirement"]!!.jsonPrimitive.int
    }

    return tmpAttrData
}