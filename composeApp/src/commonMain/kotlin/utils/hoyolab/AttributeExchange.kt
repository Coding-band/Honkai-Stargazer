package utils.hoyolab

import kotlinx.serialization.Serializable
import types.Attribute
import utils.annotation.VersionUpdateCheck

@Serializable

data class AttributeExchange(
    val key: String,
    val type: String = "",
    val attribute: Attribute = Attribute.ATTR_UNKNOWN,
    val isPercent: Boolean = false,
    val isForRelic: Boolean = false,
){
    companion object{
        val ATTREX_UNKNOWN = AttributeExchange(key = "unknown")

        @VersionUpdateCheck
        fun getAttrKeyByPropertyType(property_type: Int): AttributeExchange {
            when (property_type) {
                1 -> return AttributeExchange(key = "hp", attribute = Attribute.ATTR_HP, type = "HPDelta", isPercent = false) //Using
                2 -> return AttributeExchange(key = "atk", attribute = Attribute.ATTR_ATK, type = "AttackDelta", isPercent = false) //Using
                3 -> return AttributeExchange(key = "def", attribute = Attribute.ATTR_DEF,type = "DefenceDelta", isPercent = false) //Using
                4 -> return AttributeExchange(key = "spd", attribute = Attribute.ATTR_SPD,type = "SpeedDelta", isPercent = false) //Using
                5 -> return AttributeExchange(key = "crit_rate",attribute = Attribute.ATTR_CRIT_RATE, type = "CriticalChanceBase", isPercent = true) //Using
                6 -> return AttributeExchange(key = "crit_dmg", attribute = Attribute.ATTR_CRIT_DMG,type = "CriticalDamageBase", isPercent = true) //Using
                7 -> return AttributeExchange(key = "heal_rate", attribute = Attribute.ATTR_HEAL_RATE,type = "HealRatioBase", isPercent = true) //Using
                // 8  = return {key  = "?", isPercent  = false};
                9 -> return AttributeExchange(key = "sp_rate", attribute = Attribute.ATTR_SP_RATE,type = "SPRatioBase", isPercent = true) //Using
                10 -> return AttributeExchange(key = "effect_hit", attribute = Attribute.ATTR_EFFECT_HIT,type = "StatusProbabilityBase", isPercent = true) //Using
                11 -> return AttributeExchange(key = "effect_res", attribute = Attribute.ATTR_EFFECT_RES,type = "StatusResistanceBase", isPercent = true) //Using
                12 -> return AttributeExchange(key = "physical_dmg", attribute = Attribute.ATTR_PHYSICAL_DMG,type = "PhysicalAddedRatio", isPercent = true)  //Using
                13 -> return AttributeExchange(key = "physical_res", isPercent = true)
                14 -> return AttributeExchange(key = "fire_dmg", attribute = Attribute.ATTR_FIRE_DMG, type = "FireAddedRatio", isPercent = true) //Using
                15 -> return AttributeExchange(key = "fire_res", isPercent = true)
                16 -> return AttributeExchange(key = "ice_dmg", attribute = Attribute.ATTR_ICE_DMG, type = "IceAddedRatio", isPercent = true) //Using
                17 -> return AttributeExchange(key = "ice_res", isPercent = true)
                18 -> return AttributeExchange(key = "lightning_dmg", attribute = Attribute.ATTR_LIGHTNING_DMG, type = "ThunderAddedRatio", isPercent = true) //Using
                19 -> return AttributeExchange(key = "lightning_res", isPercent = true)
                20 -> return AttributeExchange(key = "wind_dmg", attribute = Attribute.ATTR_WIND_DMG, type = "WindAddedRatio", isPercent = true) //Using
                21 -> return AttributeExchange(key = "wind_res", isPercent = true)
                22 -> return AttributeExchange(key = "quantum_dmg", attribute = Attribute.ATTR_QUANTUM_DMG, type = "QuantumAddedRatio", isPercent = true) //Using
                23 -> return AttributeExchange(key = "quantum_res", isPercent = true)
                24 -> return AttributeExchange(key = "imaginary_dmg", attribute = Attribute.ATTR_IMAGINARY_DMG, type = "ImaginaryAddedRatio", isPercent = true) //Using
                25 -> return AttributeExchange(key = "imaginary_res", isPercent = true)
                26 -> return AttributeExchange(key = "hp_base", attribute = Attribute.ATTR_HP, isPercent = false)
                27 -> return AttributeExchange(key = "hp", attribute = Attribute.ATTR_HP, type = "HPDelta", isPercent = false, isForRelic = true) //Using, for relics
                28 -> return AttributeExchange(key = "atk_base", attribute = Attribute.ATTR_ATK, isPercent = false)
                29 -> return AttributeExchange(key = "atk", attribute = Attribute.ATTR_ATK,type = "AttackDelta", isPercent = false, isForRelic = true) //Using, for relics
                30 -> return AttributeExchange(key = "def_base", attribute = Attribute.ATTR_DEF,isPercent = false)
                31 -> return AttributeExchange(key = "def", attribute = Attribute.ATTR_DEF,type = "DefenceDelta", isPercent = false, isForRelic = true) //Using, for relics
                32 -> return AttributeExchange(key = "hp", attribute = Attribute.ATTR_HP,type = "HPAddedRatio", isPercent = true, isForRelic = true) //Using, for relics
                33 -> return AttributeExchange(key = "atk", attribute = Attribute.ATTR_ATK,type = "AttackAddedRatio", isPercent = true, isForRelic = true) //Using, for relics
                34 -> return AttributeExchange(key = "def", attribute = Attribute.ATTR_DEF,type = "DefenceAddedRatio", isPercent = true, isForRelic = true) //Using, for relics
                35 -> return AttributeExchange(key = "spd", attribute = Attribute.ATTR_SPD,type = "SpeedDelta", isPercent = false, isForRelic = true) //Using, for relics, SPEED SHOULD ALWAYS BE NON-PERCENT
                36 -> return AttributeExchange(key = "get_heal_rate", isPercent = true)
                37 -> return AttributeExchange(key = "physical_res", isPercent = true)
                38 -> return AttributeExchange(key = "fire_res", isPercent = true)
                39 -> return AttributeExchange(key = "ice_res", isPercent = true)
                40 -> return AttributeExchange(key = "lightning_res", isPercent = true)
                41 -> return AttributeExchange(key = "wind_res", isPercent = true)
                42 -> return AttributeExchange(key = "quantum_res", isPercent = true)
                43 -> return AttributeExchange(key = "imaginary_res", isPercent = true)
                // 44 -> return AttributeExchange(key = "wind_res", isPercent = true)
                // 45 -> return AttributeExchange(key = "wind_res", isPercent = true)
                // 46 -> return AttributeExchange(key = "wind_res", isPercent = true)
                // 47 -> return AttributeExchange(key = "wind_res", isPercent = true)
                // 48 -> return AttributeExchange(key = "wind_res", isPercent = true)
                // 49 -> return AttributeExchange(key = "wind_res", isPercent = true)
                // 50 -> return AttributeExchange(key = "wind_res", isPercent = true)
                51 -> return AttributeExchange(key = "spd", attribute = Attribute.ATTR_SPD,type = "SpeedDelta", isPercent = false, isForRelic = true) //Using, for relics - SPEED SHOULD ALWAYS BE NON-PERCENT
                52 -> return AttributeExchange(key = "crit_rate", attribute = Attribute.ATTR_CRIT_RATE,type = "CriticalChanceBase", isPercent = true, isForRelic = true) //Using, for relics
                53 -> return AttributeExchange(key = "crit_dmg", attribute = Attribute.ATTR_CRIT_DMG,type = "CriticalDamageBase", isPercent = true, isForRelic = true) //Using, for relics
                54 -> return AttributeExchange(key = "sp_rate", attribute = Attribute.ATTR_SP_RATE,type = "SPRatioBase", isPercent = true, isForRelic = true) //Using, for relics
                55 -> return AttributeExchange(key = "heal_rate", attribute = Attribute.ATTR_HEAL_RATE,type = "HealRatioBase", isPercent = true, isForRelic = true) //Using, for relics
                56 -> return AttributeExchange(key = "effect_hit", attribute = Attribute.ATTR_EFFECT_HIT,type = "StatusProbabilityBase", isPercent = true, isForRelic = true) //Using, for relics
                57 -> return AttributeExchange(key = "effect_res", attribute = Attribute.ATTR_EFFECT_RES,type = "StatusResistanceBase", isPercent = true, isForRelic = true) //Using, for relics
                58 -> return AttributeExchange(key = "break_dmg", attribute = Attribute.ATTR_BREAK_DMG,type = "BreakDamageAddedRatioBase", isPercent = true, isForRelic = true) //Using, for relics
                59 -> return AttributeExchange(key = "break_dmg", attribute = Attribute.ATTR_BREAK_DMG, type = "BreakDamageAddedRatioBase", isPercent = true, isForRelic = true) //Using, for relics
                60 -> return AttributeExchange(key = "sp", isPercent = false) //Using, for relics?
                else -> return AttributeExchange(key = property_type.toString(), isPercent = false)
            }
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