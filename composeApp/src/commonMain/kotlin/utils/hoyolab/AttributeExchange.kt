package utils.hoyolab

import kotlinx.serialization.Serializable
import types.Attribute
import utils.annotation.VersionUpdateCheck

@Serializable

data class AttributeExchange(
    val key: String,
    val type: String = "",
    val attribute: Attribute = Attribute.ATTR_UNKNOWN,
    val isPercent: Boolean? = null,
    val isForRelic: Boolean? = null,
    val id: Int = -1
){
    companion object{
        val ATTREX_UNKNOWN = AttributeExchange(key = "unknown")

        @VersionUpdateCheck
        val AttrExchangeList = arrayListOf<AttributeExchange>(
            AttributeExchange(id = 1,key = "hp", attribute = Attribute.ATTR_HP, type = "HPDelta", isPercent = false), //Using
            AttributeExchange(id = 2,key = "atk", attribute = Attribute.ATTR_ATK, type = "AttackDelta", isPercent = false), //Using
            AttributeExchange(id = 3,key = "def", attribute = Attribute.ATTR_DEF,type = "DefenceDelta", isPercent = false), //Using
            AttributeExchange(id = 4,key = "spd", attribute = Attribute.ATTR_SPD,type = "SpeedDelta", isPercent = false), //Using
            AttributeExchange(id = 5,key = "crit_rate",attribute = Attribute.ATTR_CRIT_RATE, type = "CriticalChanceBase", isPercent = true), //Using
            AttributeExchange(id = 6,key = "crit_dmg", attribute = Attribute.ATTR_CRIT_DMG,type = "CriticalDamageBase", isPercent = true), //Using
            AttributeExchange(id = 7,key = "heal_rate", attribute = Attribute.ATTR_HEAL_RATE,type = "HealRatioBase", isPercent = true), //Using

            AttributeExchange(id = 9,key = "sp_rate", attribute = Attribute.ATTR_SP_RATE,type = "SPRatioBase", isPercent = true), //Using
            AttributeExchange(id = 10,key = "effect_hit", attribute = Attribute.ATTR_EFFECT_HIT,type = "StatusProbabilityBase", isPercent = true), //Using
            AttributeExchange(id = 11,key = "effect_res", attribute = Attribute.ATTR_EFFECT_RES,type = "StatusResistanceBase", isPercent = true), //Using
            AttributeExchange(id = 12,key = "physical_dmg", attribute = Attribute.ATTR_PHYSICAL_DMG,type = "PhysicalAddedRatio", isPercent = true),  //Using
            AttributeExchange(id = 13,key = "physical_res", isPercent = true),
            AttributeExchange(id = 14,key = "fire_dmg", attribute = Attribute.ATTR_FIRE_DMG, type = "FireAddedRatio", isPercent = true), //Using
            AttributeExchange(id = 15,key = "fire_res", isPercent = true),
            AttributeExchange(id = 16,key = "ice_dmg", attribute = Attribute.ATTR_ICE_DMG, type = "IceAddedRatio", isPercent = true), //Using
            AttributeExchange(id = 17,key = "ice_res", isPercent = true),
            AttributeExchange(id = 18,key = "lightning_dmg", attribute = Attribute.ATTR_LIGHTNING_DMG, type = "ThunderAddedRatio", isPercent = true), //Using
            AttributeExchange(id = 19,key = "lightning_res", isPercent = true),
            AttributeExchange(id = 20,key = "wind_dmg", attribute = Attribute.ATTR_WIND_DMG, type = "WindAddedRatio", isPercent = true), //Using
            AttributeExchange(id = 21,key = "wind_res", isPercent = true),
            AttributeExchange(id = 22,key = "quantum_dmg", attribute = Attribute.ATTR_QUANTUM_DMG, type = "QuantumAddedRatio", isPercent = true), //Using
            AttributeExchange(id = 23,key = "quantum_res", isPercent = true),
            AttributeExchange(id = 24,key = "imaginary_dmg", attribute = Attribute.ATTR_IMAGINARY_DMG, type = "ImaginaryAddedRatio", isPercent = true), //Using
            AttributeExchange(id = 25,key = "imaginary_res", isPercent = true),
            AttributeExchange(id = 26,key = "hp_base", attribute = Attribute.ATTR_HP, isPercent = false),
            AttributeExchange(id = 27,key = "hp", attribute = Attribute.ATTR_HP, type = "HPDelta", isPercent = false, isForRelic = true), //Using, for relics
            AttributeExchange(id = 28,key = "atk_base", attribute = Attribute.ATTR_ATK, isPercent = false),
            AttributeExchange(id = 29,key = "atk", attribute = Attribute.ATTR_ATK,type = "AttackDelta", isPercent = false, isForRelic = true), //Using, for relics
            AttributeExchange(id = 30,key = "def_base", attribute = Attribute.ATTR_DEF,isPercent = false),
            AttributeExchange(id = 31,key = "def", attribute = Attribute.ATTR_DEF,type = "DefenceDelta", isPercent = false, isForRelic = true), //Using, for relics
            AttributeExchange(id = 32,key = "hp", attribute = Attribute.ATTR_HP,type = "HPAddedRatio", isPercent = true, isForRelic = true), //Using, for relics
            AttributeExchange(id = 33,key = "atk", attribute = Attribute.ATTR_ATK,type = "AttackAddedRatio", isPercent = true, isForRelic = true), //Using, for relics
            AttributeExchange(id = 34,key = "def", attribute = Attribute.ATTR_DEF,type = "DefenceAddedRatio", isPercent = true, isForRelic = true), //Using, for relics
            AttributeExchange(id = 35,key = "spd", attribute = Attribute.ATTR_SPD,type = "SpeedDelta", isPercent = false, isForRelic = true), //Using, for relics, SPEED SHOULD ALWAYS BE NON-PERCENT
            AttributeExchange(id = 36,key = "get_heal_rate", isPercent = true),
            AttributeExchange(id = 37,key = "physical_res", isPercent = true),
            AttributeExchange(id = 38,key = "fire_res", isPercent = true),
            AttributeExchange(id = 39,key = "ice_res", isPercent = true),
            AttributeExchange(id = 40,key = "lightning_res", isPercent = true),
            AttributeExchange(id = 41,key = "wind_res", isPercent = true),
            AttributeExchange(id = 42,key = "quantum_res", isPercent = true),
            AttributeExchange(id = 43,key = "imaginary_res", isPercent = true),

            AttributeExchange(id = 51,key = "spd", attribute = Attribute.ATTR_SPD,type = "SpeedDelta", isPercent = false, isForRelic = true), //Using, for relics - SPEED SHOULD ALWAYS BE NON-PERCENT
            AttributeExchange(id = 52,key = "crit_rate", attribute = Attribute.ATTR_CRIT_RATE,type = "CriticalChanceBase", isPercent = true, isForRelic = true), //Using, for relics
            AttributeExchange(id = 53,key = "crit_dmg", attribute = Attribute.ATTR_CRIT_DMG,type = "CriticalDamageBase", isPercent = true, isForRelic = true), //Using, for relics
            AttributeExchange(id = 54,key = "sp_rate", attribute = Attribute.ATTR_SP_RATE,type = "SPRatioBase", isPercent = true, isForRelic = true), //Using, for relics
            AttributeExchange(id = 55,key = "heal_rate", attribute = Attribute.ATTR_HEAL_RATE,type = "HealRatioBase", isPercent = true, isForRelic = true), //Using, for relics
            AttributeExchange(id = 56,key = "effect_hit", attribute = Attribute.ATTR_EFFECT_HIT,type = "StatusProbabilityBase", isPercent = true, isForRelic = true), //Using, for relics
            AttributeExchange(id = 57,key = "effect_res", attribute = Attribute.ATTR_EFFECT_RES,type = "StatusResistanceBase", isPercent = true, isForRelic = true), //Using, for relics
            AttributeExchange(id = 58,key = "break_dmg", attribute = Attribute.ATTR_BREAK_DMG,type = "BreakDamageAddedRatioBase", isPercent = true, isForRelic = false), //Using, for relics
            AttributeExchange(id = 59,key = "break_dmg", attribute = Attribute.ATTR_BREAK_DMG, type = "BreakDamageAddedRatioBase", isPercent = true, isForRelic = true), //Using, for relics
            AttributeExchange(id = 60,key = "sp", isPercent = false), //Using, for relics?
        )

        //FIX : Cannot find if case : isForRelic = true and Expect key is xx_dmg
        //To avoid that case, if isForRelic/isPercent is null, still expect as ok to find
        fun getAttrKeyByPropertyType(property_type: Int, isPercent: Boolean? = null, isForRelic: Boolean? = null): AttributeExchange {
            return AttrExchangeList.firstOrNull {
                it.id == property_type
                        && (if (isPercent != null) (it.isPercent == null || it.isPercent == isPercent) else true)
                        && (if (isForRelic != null) (it.isForRelic == null || it.isForRelic == isForRelic) else true)
            } ?: ATTREX_UNKNOWN
        }
        fun getAttrKeyByMihomoType(type: String, isPercent: Boolean? = null, isForRelic: Boolean? = null): AttributeExchange {
            return AttrExchangeList.firstOrNull {
                it.type == type
                        && (if (isPercent != null) (it.isPercent == null || it.isPercent == isPercent) else true)
                        && (if (isForRelic != null) (it.isForRelic == null || it.isForRelic == isForRelic) else true)
            } ?: ATTREX_UNKNOWN
        }
        fun getAttrKeyByMihomoKey(key: String, isPercent: Boolean? = null, isForRelic: Boolean? = null): AttributeExchange {
            return AttrExchangeList.firstOrNull {
                it.key == key
                        && (if (isPercent != null) (it.isPercent == null || it.isPercent == isPercent) else true)
                        && (if (isForRelic != null) (it.isForRelic == null || it.isForRelic == isForRelic) else true)
            } ?: ATTREX_UNKNOWN
        }

        /**
         *
         * @param key E.g. "hp"
         * @param isMainAttr Called by Main Attr or Sub Attr?
         * @returns E.g. "HPDelta"
         */
        fun getGeneralAttrTypeByKey(key : String, isMainAttr : Boolean = false) : String {
            when(key){
                "hp" -> return (if(isMainAttr) "HPAddedRatio" else "HPDelta")
                "atk" -> return (if(isMainAttr) "AttackAddedRatio" else "AttackDelta")
                "def" -> return (if(isMainAttr) "DefenceAddedRatio" else "DefenceDelta")
                "spd" -> return "SpeedDelta"
                "crit_rate" -> return "CriticalChanceBase"
                "crit_dmg" -> return "CriticalDamageBase"
                "sp_rate" -> return "SPRatioBase"
                "heal_rate" -> return "HealRatioBase"
                "effect_hit" -> return "StatusProbabilityBase"
                "effect_res" -> return "StatusResistanceBase"
                "break_dmg" -> return "BreakDamageAddedRatioBase"
                "physical_dmg" -> return "PhysicalAddedRatio"
                "fire_dmg" -> return "FireAddedRatio"
                "ice_dmg" -> return "IceAddedRatio"
                "lightning_dmg" -> return "ThunderAddedRatio"
                "wind_dmg" -> return "WindAddedRatio"
                "quantum_dmg" -> return "QuantumAddedRatio"
                "imaginary_dmg" -> return "ImaginaryAddedRatio"
                else -> return "UnknownAttrType"
            }
        }

        fun getKeyByGeneralAttrType(generalAttrType : String) : String {
            when(generalAttrType){
                "HPAddedRatio" -> return "hp"
                "HPDelta" -> return "hp"
                "AttackAddedRatio" -> return "atk"
                "AttackDelta" -> return "atk"
                "DefenceAddedRatio"-> return "def"
                "DefenceDelta" -> return "def"
                "SpeedDelta" -> return "spd"
                "CriticalChanceBase" -> return "crit_rate"
                "CriticalDamageBase" -> return "crit_dmg"
                "SPRatioBase" -> return "sp_rate"
                "HealRatioBase" -> return "heal_rate"
                "StatusProbabilityBase" -> return "effect_hit"
                "StatusResistanceBase" -> return "effect_res"
                "BreakDamageAddedRatioBase" -> return "break_dmg"
                "PhysicalAddedRatio" -> return "physical_dmg"
                "FireAddedRatio" -> return "fire_dmg"
                "IceAddedRatio" -> return "ice_dmg"
                "ThunderAddedRatio" -> return "lightning_dmg"
                "WindAddedRatio" -> return "wind_dmg"
                "QuantumAddedRatio" -> return "quantum_dmg"
                "ImaginaryAddedRatio" -> return "imaginary_dmg"
                else -> return "UnknownKey"
            }
        }
    }
}