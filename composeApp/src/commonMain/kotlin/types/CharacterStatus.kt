package types

import androidx.annotation.IntRange
import kotlinx.serialization.Serializable

/**
 * This is the first ideal version, changes will be made at future
 */
@Serializable
data class CharacterStatus(
    var atkBase : Double,
    var atkAdd : Double,
    var hpBase : Double,
    var hpAdd : Double,
    var defBase : Double,
    var defAdd : Double,
    var critRate : Double,
    var critDMG : Double,
    var aggro : Double,
    var speedBase : Double,
    var speedAdd : Double,

    @IntRange(0,6) var ascension : Int,
    @IntRange(0,6) var eidolon : Int,
    @IntRange(1,80) var characterLevel : Int,

    @IntRange(1,15) var traceBasicAtkLevel : Int, //普攻
    @IntRange(1,15) var traceSkillLevel : Int, //戰技
    @IntRange(1,15) var traceUltimateLevel : Int, //終結技
    @IntRange(1,15) var traceTalentLevel : Int, //天賦

    var equippingLightcone: Lightcone,
    var equippingRelicHead: Relic,
    var equippingRelicHands: Relic,
    var equippingRelicBody: Relic,
    var equippingRelicFeet: Relic,
    var equippingRelicPlanar: Relic,
    var equippingRelicLinkRope: Relic,


)