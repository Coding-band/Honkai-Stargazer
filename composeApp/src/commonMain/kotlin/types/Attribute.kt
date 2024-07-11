package types

import files.ATTR_ATK
import files.ATTR_BREAK_DMG
import files.ATTR_CRIT_DMG
import files.ATTR_CRIT_RATE
import files.ATTR_DEF
import files.ATTR_EFFECT_HIT
import files.ATTR_EFFECT_RES
import files.ATTR_FIRE_DMG
import files.ATTR_HEAL_RATE
import files.ATTR_HP
import files.ATTR_ICE_DMG
import files.ATTR_IMAGINARY_DMG
import files.ATTR_LIGHTNING_DMG
import files.ATTR_PHYSICAL_DMG
import files.ATTR_QUANTUM_DMG
import files.ATTR_SPD
import files.ATTR_SP_RATE
import files.ATTR_WIND_DMG
import files.HaveNotUsed
import files.Res
import files.ic_atk
import files.ic_break_dmg
import files.ic_crit_dmg
import files.ic_crit_rate
import files.ic_def
import files.ic_effect_hit_rate
import files.ic_effect_res
import files.ic_energy_regeneration_rate
import files.ic_fire
import files.ic_hp
import files.ic_ice
import files.ic_imaginary
import files.ic_lightning
import files.ic_outgoing_healing_boost
import files.ic_physical
import files.ic_quatumn
import files.ic_speed
import files.ic_unknown
import files.ic_wind
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource

/**
 * This is the enum class that about Character's Attributes
 */
enum class Attribute(var chName : String, var subName : String, var resName : StringResource, var iconWhite : DrawableResource) {
    ATTR_ATK("攻擊力","atk", Res.string.ATTR_ATK,Res.drawable.ic_atk),
    ATTR_CRIT_RATE("暴擊率","crit_rate", Res.string.ATTR_CRIT_RATE,Res.drawable.ic_crit_rate),
    ATTR_CRIT_DMG("暴擊傷害","crit_dmg", Res.string.ATTR_CRIT_DMG,Res.drawable.ic_crit_dmg),
    ATTR_BREAK_DMG("擊破特攻","break_dmg", Res.string.ATTR_BREAK_DMG,Res.drawable.ic_break_dmg),
    ATTR_QUANTUM_DMG("量子屬性傷害","quantum_dmg", Res.string.ATTR_QUANTUM_DMG,Res.drawable.ic_quatumn),
    ATTR_IMAGINARY_DMG("虛數屬性傷害","imaginary_dmg", Res.string.ATTR_IMAGINARY_DMG,Res.drawable.ic_imaginary),
    ATTR_PHYSICAL_DMG("物理屬性傷害","physical_dmg", Res.string.ATTR_PHYSICAL_DMG,Res.drawable.ic_physical),
    ATTR_WIND_DMG("風屬性傷害","wind_dmg", Res.string.ATTR_WIND_DMG,Res.drawable.ic_wind),
    ATTR_FIRE_DMG("火屬性傷害","fire_dmg", Res.string.ATTR_FIRE_DMG,Res.drawable.ic_fire),
    ATTR_ICE_DMG("冰屬性傷害","ice_dmg", Res.string.ATTR_ICE_DMG,Res.drawable.ic_ice),
    ATTR_LIGHTNING_DMG("雷屬性傷害","lightning_dmg", Res.string.ATTR_LIGHTNING_DMG,Res.drawable.ic_lightning),
    ATTR_HP("生命值","hp", Res.string.ATTR_HP,Res.drawable.ic_hp),
    ATTR_DEF("防禦力","def", Res.string.ATTR_DEF,Res.drawable.ic_def),
    ATTR_HEAL_RATE("治療加成","heal_rate", Res.string.ATTR_HEAL_RATE,Res.drawable.ic_outgoing_healing_boost),
    ATTR_SPD("速度","spd", Res.string.ATTR_SPD,Res.drawable.ic_speed),
    ATTR_EFFECT_HIT("效果命中","effect_hit", Res.string.ATTR_EFFECT_HIT,Res.drawable.ic_effect_hit_rate),
    ATTR_EFFECT_RES("效果抗性","effect_res", Res.string.ATTR_EFFECT_RES,Res.drawable.ic_effect_res),
    ATTR_SP_RATE("充能效率","sp_rate", Res.string.ATTR_SP_RATE,Res.drawable.ic_energy_regeneration_rate),
    ATTR_UNKNOWN("未知","unknown", Res.string.HaveNotUsed,Res.drawable.ic_unknown),


}