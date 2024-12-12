package type

import kotlinx.serialization.Serializable
import type.HsrProperties
import type.Lightcone
import type.Relic

@Serializable
data class CharacterStatus(
    var ascension : Int = -1,
    var eidolon : Int = -1,
    var characterLevel : Int = -1,
    var ultimateEnergyRequire : Int = 0, //終結技能量
    var aggro : Int = 0, //嘲諷值

    var traceBasicAtkLevel : Int = -1, //普攻
    var traceSkillLevel : Int = -1, //戰技
    var traceUltimateLevel : Int = -1, //終結技
    var traceTalentLevel : Int = -1, //天賦

    var equippingLightcone: Lightcone? = null,
    var equippingRelicHead: Relic? = null,
    var equippingRelicHands: Relic? = null,
    var equippingRelicBody: Relic? = null,
    var equippingRelicFeet: Relic? = null,
    var equippingRelicPlanar: Relic? = null,
    var equippingRelicLinkRope: Relic? = null,

    var characterProperties: ArrayList<HsrProperties>? = null,
    var isHelper: Boolean = false,

    )