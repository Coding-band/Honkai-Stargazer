/*
 * Project Honkai Stargazer and app Stargazer (星穹觀星者) were
 * Organized & Develop by Coding Band.
 * Copyright © 2024 Coding Band 版權所有
 */

package types

import files.Abundance
import files.Destruction
import files.Erudition
import files.Harmony
import files.HaveNotUsed
import files.Hunt
import files.Nihility
import files.Preservation
import files.Res
import files.app_name
import files.path_the_abundance
import files.path_the_abundance_abyss
import files.path_the_destruction
import files.path_the_destruction_abyss
import files.path_the_erudition
import files.path_the_erudition_abyss
import files.path_the_harmony
import files.path_the_harmony_abyss
import files.path_the_hunt
import files.path_the_hunt_abyss
import files.path_the_nihility
import files.path_the_nihility_abyss
import files.path_the_preservation
import files.path_the_preservation_abyss
import files.pom_pom_failed_issue
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource


enum class Path (var chName : String,var resName : StringResource,var iconWhite : DrawableResource, var iconAbyss : DrawableResource, localeStringId : StringResource = Res.string.app_name){
    Abundance("豐饒",Res.string.Abundance, Res.drawable.path_the_abundance, Res.drawable.path_the_abundance_abyss),
    Destruction("毀滅",Res.string.Destruction, Res.drawable.path_the_destruction, Res.drawable.path_the_destruction_abyss),
    Erudition("智識",Res.string.Erudition, Res.drawable.path_the_erudition, Res.drawable.path_the_erudition_abyss),
    Harmony("同諧",Res.string.Harmony, Res.drawable.path_the_harmony, Res.drawable.path_the_harmony_abyss),
    Hunt("巡獵",Res.string.Hunt, Res.drawable.path_the_hunt, Res.drawable.path_the_hunt_abyss),
    Nihility("虛無",Res.string.Nihility, Res.drawable.path_the_nihility, Res.drawable.path_the_nihility_abyss),
    Preservation("存謢",Res.string.Preservation, Res.drawable.path_the_preservation, Res.drawable.path_the_preservation_abyss),
    Unspecified("未知",Res.string.HaveNotUsed, Res.drawable.pom_pom_failed_issue, Res.drawable.pom_pom_failed_issue);
}