package utils.calculator

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Serializable
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
@Serializable
data class AttrData(
    var atk: Float,
    var hp: Float,
    var def: Float,
    var spd : Float,
    var aggro: Int,
    var energy: Int,
)
// 根據等級取得角色屬性數值
@OptIn(ExperimentalCoroutinesApi::class)
fun getCharAttrData(jsonElement: JsonElement, level: Int = 1) : AttrData{
    return runBlocking {
        val job = CoroutineScope(Dispatchers.Default).async {
            val charLevelData = jsonElement.jsonObject["levelData"]!!
            var tmpAttrData : AttrData = AttrData(0f,0f,0f,0f,0,0)

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
                tmpAttrData.energy = jsonElement.jsonObject["spRequirement"]!!.jsonPrimitive.int
            }

            return@async tmpAttrData
        }
        job.await()
        job.getCompleted()
    }
}



// 根據等級取得光錐屬性數值
@OptIn(ExperimentalCoroutinesApi::class)
fun getLcAttrData(jsonElement: JsonElement, level: Int = 1) : AttrData{
    return runBlocking {
        val job = async (Dispatchers.Default){
            val charLevelData = jsonElement.jsonObject["levelData"]!!
            var tmpAttrData : AttrData = AttrData(0f,0f,0f,0f,0,0)

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
            }

            return@async  tmpAttrData
        }
        job.await()
        job.getCompleted()
    }
}

