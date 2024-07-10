/*
 * Project Honkai Stargazer and app Stargazer (星穹觀星者) were
 * Organized & Develop by Coding Band.
 * Copyright © 2024 Coding Band 版權所有
 */

package types

import files.Fire
import files.HaveNotUsed
import files.Ice
import files.Imaginary
import files.Lightning
import files.Physical
import files.Quantum
import files.Res
import files.Wind
import files.app_name
import files.element_fire
import files.element_ice
import files.element_imaginary
import files.element_lightning
import files.element_physical
import files.element_quantum
import files.element_wind
import files.ic_fire
import files.ic_ice
import files.ic_imaginary
import files.ic_lightning
import files.ic_physical
import files.ic_quatumn
import files.icon_wind
import files.pom_pom_failed_issue
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource

/**
 * 屬性 CombatType
 */
enum class CombatType (var chName : String, var resName: StringResource, var iconWhite : DrawableResource, var iconColor : DrawableResource, localeStringId : StringResource = Res.string.app_name){
    Imaginary("虛數", Res.string.Imaginary, Res.drawable.ic_imaginary, Res.drawable.element_imaginary),
    Quantum("量子", Res.string.Quantum, Res.drawable.ic_quatumn, Res.drawable.element_quantum),
    Lightning("雷", Res.string.Lightning, Res.drawable.ic_lightning, Res.drawable.element_lightning),
    Fire("火", Res.string.Fire, Res.drawable.ic_fire, Res.drawable.element_fire),
    Ice("冰", Res.string.Ice, Res.drawable.ic_ice, Res.drawable.element_ice),
    Wind("風", Res.string.Wind, Res.drawable.icon_wind, Res.drawable.element_wind),
    Physical("物理", Res.string.Physical, Res.drawable.ic_physical, Res.drawable.element_physical),
    Unspecified("未知",Res.string.HaveNotUsed, Res.drawable.pom_pom_failed_issue, Res.drawable.pom_pom_failed_issue);
}