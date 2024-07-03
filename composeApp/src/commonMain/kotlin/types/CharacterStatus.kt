package types

import androidx.annotation.IntRange
import kotlinx.serialization.Serializable

/**
 * This is the first ideal version, changes will be made at future
 */
@Serializable
data class CharacterStatus(
    var atkBase : Float = 0f,
    var atkAdd : Float= 0f,
    var hpBase : Float= 0f,
    var hpAdd : Float= 0f,
    var defBase : Float= 0f,
    var defAdd : Float= 0f,
    var critRate : Float= 0f,
    var critDMG : Float = 0f,
    var speedBase : Float= 0f,
    var speedAdd : Float= 0f,

    @IntRange(0,6) var ascension : Int = -1,
    @IntRange(0,6) var eidolon : Int = -1,
    @IntRange(1,80) var characterLevel : Int = -1,
    var ultimateEnergyRequire : Int = 0, //終結技能量
    var aggro : Int = 0, //嘲諷值

    @IntRange(1,15) var traceBasicAtkLevel : Int = -1, //普攻
    @IntRange(1,15) var traceSkillLevel : Int = -1, //戰技
    @IntRange(1,15) var traceUltimateLevel : Int = -1, //終結技
    @IntRange(1,15) var traceTalentLevel : Int = -1, //天賦

    var equippingLightcone: Lightcone? = null,
    var equippingRelicHead: Relic? = null,
    var equippingRelicHands: Relic? = null,
    var equippingRelicBody: Relic? = null,
    var equippingRelicFeet: Relic? = null,
    var equippingRelicPlanar: Relic? = null,
    var equippingRelicLinkRope: Relic? = null,


)