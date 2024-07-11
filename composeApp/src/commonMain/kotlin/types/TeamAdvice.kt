package types

import files.HaveNotUsed
import files.Res
import files.element_fire
import files.element_ice
import files.element_imaginary
import files.element_lightning
import files.element_physical
import files.element_quantum
import files.element_wind
import files.ic_atk
import files.ic_break_dmg
import files.ic_break_effect
import files.ic_crit_dmg
import files.ic_crit_rate
import files.ic_def
import files.ic_effect_hit_rate
import files.ic_effect_res
import files.ic_energy_regeneration_rate
import files.ic_kurukuru
import files.ic_outgoing_healing_boost
import files.ic_speed
import files.ic_unknown
import files.path_the_abundance
import files.path_the_destruction
import files.path_the_erudition
import files.path_the_harmony
import files.path_the_hunt
import files.path_the_nihility
import files.path_the_preservation
import files.pom_pom_failed_issue
import files.vocchi
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import utils.annotation.VersionUpdateCheck


data class TeamAdvice(
    val adviceId: Int,
    val cnName: String,
    val iconWhite: DrawableResource,
    val resName: StringResource = Res.string.HaveNotUsed,
)


@VersionUpdateCheck
fun getTeamAdviceById(id : Int) : TeamAdvice{
    return when(id){
        -401 -> TeamAdvice(-401,"輔助", Res.drawable.ic_unknown)
        -402 -> TeamAdvice(-402,"副C", Res.drawable.ic_unknown)
        -403 -> TeamAdvice(-403,"主C", Res.drawable.ic_unknown)
        -207 -> TeamAdvice(-207,"豐饒", Res.drawable.path_the_abundance)
        -206 -> TeamAdvice(-206,"同諧", Res.drawable.path_the_harmony)
        -205 -> TeamAdvice(-205,"毀滅", Res.drawable.path_the_destruction)
        -204 -> TeamAdvice(-204,"虛無", Res.drawable.path_the_nihility)
        -203 -> TeamAdvice(-203,"智識", Res.drawable.path_the_erudition)
        -202 -> TeamAdvice(-202,"巡獵", Res.drawable.path_the_hunt)
        -201 -> TeamAdvice(-201,"存護", Res.drawable.path_the_preservation)
        -107 -> TeamAdvice(-107,"量子", Res.drawable.element_quantum)
        -106 -> TeamAdvice(-106,"雷", Res.drawable.element_lightning)
        -105 -> TeamAdvice(-105,"物理", Res.drawable.element_physical)
        -104 -> TeamAdvice(-104,"虛數", Res.drawable.element_imaginary)
        -103 -> TeamAdvice(-103,"火", Res.drawable.element_fire)
        -102 -> TeamAdvice(-102,"風", Res.drawable.element_wind)
        -101 -> TeamAdvice(-101,"冰", Res.drawable.element_ice)
        -301 -> TeamAdvice(-301,"DOT", Res.drawable.vocchi)
        -302 -> TeamAdvice(-302,"DOT易傷", Res.drawable.vocchi)
        -303 -> TeamAdvice(-303,"KuruKuru", Res.drawable.ic_kurukuru)
        -304 -> TeamAdvice(-304,"分擔傷害", Res.drawable.ic_unknown)
        -305 -> TeamAdvice(-305,"代價", Res.drawable.ic_unknown)
        -306 -> TeamAdvice(-306,"充能", Res.drawable.ic_energy_regeneration_rate)
        -307 -> TeamAdvice(-307,"加速", Res.drawable.ic_speed)
        -308 -> TeamAdvice(-308,"全體攻擊", Res.drawable.ic_unknown)
        -309 -> TeamAdvice(-309,"全體治療", Res.drawable.ic_outgoing_healing_boost)
        -310 -> TeamAdvice(-310,"全體傷害", Res.drawable.ic_unknown)
        -311 -> TeamAdvice(-311,"再動", Res.drawable.ic_unknown)
        -312 -> TeamAdvice(-312,"再現", Res.drawable.ic_unknown)
        -313 -> TeamAdvice(-313,"回復", Res.drawable.ic_unknown)
        -314 -> TeamAdvice(-314,"自我恢復", Res.drawable.ic_unknown)
        -315 -> TeamAdvice(-315,"攻擊加成", Res.drawable.ic_atk)
        -316 -> TeamAdvice(-316,"易傷", Res.drawable.ic_break_dmg)
        -317 -> TeamAdvice(-317,"治療", Res.drawable.ic_outgoing_healing_boost)
        -318 -> TeamAdvice(-318,"持續恢復", Res.drawable.ic_outgoing_healing_boost)
        -319 -> TeamAdvice(-319,"降抗", Res.drawable.ic_effect_res)
        -320 -> TeamAdvice(-320,"弱點植入", Res.drawable.ic_effect_hit_rate)
        -321 -> TeamAdvice(-321,"弱點擊破", Res.drawable.ic_effect_hit_rate)
        -322 -> TeamAdvice(-322,"特殊機制", Res.drawable.ic_unknown)
        -323 -> TeamAdvice(-323,"能量回復", Res.drawable.ic_energy_regeneration_rate)
        -324 -> TeamAdvice(-324,"追加攻擊", Res.drawable.ic_unknown)
        -325 -> TeamAdvice(-325,"逃課大師", Res.drawable.ic_unknown)
        -326 -> TeamAdvice(-326,"淨化", Res.drawable.ic_unknown)
        -327 -> TeamAdvice(-327,"產點", Res.drawable.ic_unknown)
        -328 -> TeamAdvice(-328,"終結技", Res.drawable.ic_unknown)
        -329 -> TeamAdvice(-329,"復活", Res.drawable.ic_unknown)
        -330 -> TeamAdvice(-330,"提前行動", Res.drawable.ic_unknown)
        -331 -> TeamAdvice(-331,"減防", Res.drawable.ic_break_effect)
        -332 -> TeamAdvice(-332,"減速", Res.drawable.ic_unknown)
        -333 -> TeamAdvice(-333,"減傷", Res.drawable.ic_unknown)
        -334 -> TeamAdvice(-334,"解除狀態", Res.drawable.ic_unknown)
        -335 -> TeamAdvice(-335,"嘲諷", Res.drawable.ic_unknown)
        -336 -> TeamAdvice(-336,"增傷", Res.drawable.ic_unknown)
        -337 -> TeamAdvice(-337,"擴散攻擊", Res.drawable.ic_unknown)
        -338 -> TeamAdvice(-338,"暴擊", Res.drawable.ic_crit_rate)
        -339 -> TeamAdvice(-339,"暴擊加成", Res.drawable.ic_crit_rate)
        -340 -> TeamAdvice(-340,"暴擊率加成", Res.drawable.ic_crit_rate)
        -341 -> TeamAdvice(-341,"暴擊傷害", Res.drawable.ic_crit_dmg)
        -342 -> TeamAdvice(-342,"暴擊傷害加成", Res.drawable.ic_crit_dmg)
        -343 -> TeamAdvice(-343,"護盾", Res.drawable.ic_def)
        -344 -> TeamAdvice(-344,"體系核心", Res.drawable.ic_unknown)
        -345 -> TeamAdvice(-345,"降低防禦", Res.drawable.ic_break_effect)
        -346 -> TeamAdvice(-346,"禁錮", Res.drawable.ic_unknown)
        -347 -> TeamAdvice(-347,"超擊破", Res.drawable.ic_effect_hit_rate)
        else -> TeamAdvice(0,"None",Res.drawable.pom_pom_failed_issue)
    }
}