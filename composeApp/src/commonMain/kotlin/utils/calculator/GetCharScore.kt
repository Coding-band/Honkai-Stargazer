package utils.calculator

import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.float
import kotlinx.serialization.json.int
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import utils.CharWeightList
import types.Character
import utils.hoyolab.AttributeExchange
import kotlin.math.max
import kotlin.math.min
import kotlin.math.pow

fun getCharScore(character: Character, schoolIndex: Int): Float {
    val scoreWeight = CharWeightList.INSTANCE
    val charId = character.officialId
    val charStatus = character.characterStatus

    //若果暫時沒有權重 必須返回空值
    if (scoreWeight !is JsonObject) { return -2f }
    if(scoreWeight.jsonObject.isEmpty()){ return -3f}
    if(scoreWeight.jsonObject[charId.toString()] == null){ return -4f}
    if(scoreWeight.jsonObject[charId.toString()]!!.jsonArray[schoolIndex] !is JsonObject){ return -5f}

    val lcAdviceScores = 10 //推薦光錐加分
    val lcSuitableScores = 5 //一般光錐加分
    /**
     * 初始化
     **/
    val charScoreWeight = scoreWeight.jsonObject[charId.toString()]!!.jsonArray[schoolIndex]; //對應角色流派内，該角色評分權重
    val charLightconeID = charStatus?.equippingLightcone?.officialId; //角色使用中的光錐
    val charLightconeSuper = charStatus?.equippingLightcone?.superimposition ?: 0; //角色使用中的光錐疊影

    val charLevel = charStatus?.characterLevel ?: 0; //角色等級
    val charSoulLvl = charStatus?.eidolon; //角色的星魂等級
    val charHsrProperties = charStatus?.characterProperties; //角色的Attr & Addi

    // 光錐分數 -> 占比10+4% (10 : 推薦 || +4 : 疊影)
    var lightconeScore = 0f
    if(charLightconeID != null){
        lightconeScore = if (charScoreWeight.jsonObject["advice_lightcone"]!!.jsonArray.filter { it.jsonPrimitive.int == charLightconeID }.isNotEmpty()) {
            lcAdviceScores.toFloat()
        } else if (charScoreWeight.jsonObject["normal_lightcone"]!!.jsonArray.filter { it.jsonPrimitive.int == charLightconeID }.isNotEmpty()) {
            lcSuitableScores.toFloat()
        } else {
            0f
        }
    }
    lightconeScore += max(charLightconeSuper - 1,0) //疊影 = [2,3,4,5] -> 每次加1分

    // 星魂分數 -> 占比6%
    var soulScore = 0f
    if(charSoulLvl != null){
        val soulRequire = charScoreWeight.jsonObject["soul"]!!.jsonArray
        for(i in 0 until soulRequire.size){
            if(soulRequire[i].jsonPrimitive.int < 0) continue
            if (charSoulLvl >= soulRequire[i].jsonPrimitive.int) {
                soulScore += (6 / soulRequire.size)
            } else {
                break
            }
        }
    }

    // 行跡分數 -> 占比34%
    var traceScore = 0f
    val traceWeight = charScoreWeight.jsonObject["trace"]!!.jsonObject
    if(charStatus?.traceBasicAtkLevel != null && traceWeight.jsonObject["normal_atk"]!!.jsonPrimitive.float > 0){
        traceScore += (charStatus.traceBasicAtkLevel * traceWeight.jsonObject["normal_atk"]!!.jsonPrimitive.float) / 3
    }
    if(charStatus?.traceSkillLevel != null && traceWeight.jsonObject["skill"]!!.jsonPrimitive.float > 0){
        traceScore += (charStatus.traceSkillLevel * traceWeight.jsonObject["skill"]!!.jsonPrimitive.float) / 3
    }
    if(charStatus?.traceUltimateLevel != null && traceWeight.jsonObject["ultimate"]!!.jsonPrimitive.float > 0){
        traceScore += (charStatus.traceUltimateLevel * traceWeight.jsonObject["ultimate"]!!.jsonPrimitive.float) / 3
    }
    if(charStatus?.traceTalentLevel != null && traceWeight.jsonObject["talent"]!!.jsonPrimitive.float > 0){
        traceScore += (charStatus.traceTalentLevel * traceWeight.jsonObject["talent"]!!.jsonPrimitive.float) / 3
    }

    //突破分數 -> 占比6% -> 0%, 因爲Hoyolab沒有提供突破等級
    //var promotionScore = if(charPromotion?.toFloat() == -1f) 0f else charPromotion?.toFloat() ?: 0f
    //等級分數 -> 占比6%
    val levelScore = (charLevel.toFloat() / 80) * 6

    // 屬性分數 -> 占比60%
    var attrScore = 0f
    var attrWeightSum = 0f //總權重淨值 (1.5+2+1+...)

    val attrGradKeys = charScoreWeight.jsonObject["grad"]!!.jsonObject.keys //獲取所有有畢業值的屬性
    val attrWeightValidKeys = charScoreWeight.jsonObject["attr"]!!.jsonObject.keys //獲取所有有畢業值的屬性
    //println("attrGradKeys: $attrGradKeys, attrWeightValidKeys: $attrWeightValidKeys")
    for (key in attrWeightValidKeys) {
        if (attrGradKeys.contains(key)){
            attrWeightSum += charScoreWeight.jsonObject["attr"]!!.jsonObject[key]!!.jsonPrimitive.float
        }
    }

    charHsrProperties?.map {it ->
        val name = it.attributeExchange.key
        val attrValue = min(
            it.valueFinal + (if(name == "sp_rate") 1 else 0), // sp_rate屬性加 100%
            if(name == "sp_rate") 2.0f else Float.MAX_VALUE // sp_rate屬性最大值為200%
        )
        val weightValue = charScoreWeight.jsonObject["attr"]!!.jsonObject[name]
        val gradValue = charScoreWeight.jsonObject["grad"]!!.jsonObject[name]

        //println("attrValue: $attrValue, attrWeightSum: $attrWeightSum, charLevel : $charLevel, weightValue: $weightValue, gradValue: $gradValue")

        if (gradValue == null || weightValue == null) {
            //...如果沒有畢業分，做甚麼？只能不算
        } else {
            attrScore += ((attrValue / gradValue.jsonPrimitive.float) // 畢業比率
            * (0.5f * (charLevel.toFloat().pow(2) / 80) / 40) // 角色等級Curve
            * (weightValue.jsonPrimitive.float / attrWeightSum) * 60) // 佔比
        }
    }
    //println("{lightconeScore: $lightconeScore, soulScore: $soulScore, traceScore: $traceScore, attrScore: $attrScore, levelScore: $levelScore}")
    return lightconeScore + soulScore + traceScore + attrScore + levelScore
}

fun getCharRange(score : Float) : String {
    if (score < 20) { return "D" }
    else if (score < 40) { return "C" }
    else if (score < 60) { return "B" }
    else if (score < 80) { return "A" }
    else if (score < 100) { return "S" }
    return "SS"
}

fun getGradAttrAndValue(character: Character, schoolIndex: Int): ArrayList<Pair<AttributeExchange, Float>> {
    if (character.characterStatus == null || character.characterStatus!!.characterProperties == null) return arrayListOf()
    val scoreWeight = CharWeightList.INSTANCE
    val charId = character.officialId

    //若果暫時沒有權重 必須返回空值
    if (scoreWeight !is JsonObject || scoreWeight.jsonObject.isEmpty() || scoreWeight.jsonObject[charId.toString()] == null || scoreWeight.jsonObject[charId.toString()]!!.jsonArray[schoolIndex] !is JsonObject) {
        return arrayListOf()
    }
    val charScoreWeight = scoreWeight.jsonObject[charId.toString()]!!.jsonArray[schoolIndex]; //對應角色流派内，該角色評分權重
    val gradPairs = arrayListOf<Pair<AttributeExchange, Float>>()
    character.characterStatus!!.characterProperties!!.map {it ->
        val name = it.attributeExchange.key
        val attrValue = min(
            it.valueFinal + (if(name == "sp_rate") 1 else 0), // sp_rate屬性加 100%
            if(name == "sp_rate") 2.0f else Float.MAX_VALUE // sp_rate屬性最大值為200%
        )
        val weightValue = charScoreWeight.jsonObject["attr"]!!.jsonObject[name]
        val gradValue = charScoreWeight.jsonObject["grad"]!!.jsonObject[name]

        if (gradValue == null || weightValue == null) {
            //...如果沒有畢業分，做甚麼？只能不算
        } else {
            gradPairs.add(AttributeExchange.getAttrKeyByMihomoKey(name, isForRelic = false) to gradValue.jsonPrimitive.float)
        }
    }
    return gradPairs
}