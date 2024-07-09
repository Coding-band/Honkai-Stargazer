package types


import androidx.annotation.IntRange
import androidx.compose.ui.graphics.ImageBitmap
import kotlinx.serialization.Serializable
import utils.UtilTools
import utils.annotation.VersionUpdateCheck

@Serializable
open class Material(
    var officialId: Int,
    var name: String?,
    @IntRange(1,5) var rarity: Int,
    var count: Int,
    var isDisplayCount : Boolean = false,


) {
    companion object {
        @VersionUpdateCheck
        fun getMaterialImageById(materialId : Int): ImageBitmap {
            when (materialId){
                //add in 2.3.0
                673757 -> return UtilTools().getAssetsWebpByFileName(UtilTools.ImageFolderType.MATERIAL_ICON,"material_exquisite_colored_draft")
                589816 -> return UtilTools().getAssetsWebpByFileName(UtilTools.ImageFolderType.MATERIAL_ICON,"material_dynamic_outlining")
                874343 -> return UtilTools().getAssetsWebpByFileName(UtilTools.ImageFolderType.MATERIAL_ICON,"material_rough_sketch")

                //add in 2.2.0
                836267 -> return UtilTools().getAssetsWebpByFileName(UtilTools.ImageFolderType.MATERIAL_ICON,"material_countertemporal_shot")
                920208 -> return UtilTools().getAssetsWebpByFileName(UtilTools.ImageFolderType.MATERIAL_ICON,"material_destined_expiration")
                866635 -> return UtilTools().getAssetsWebpByFileName(UtilTools.ImageFolderType.MATERIAL_ICON,"material_ipc_work_permit")
                386840 -> return UtilTools().getAssetsWebpByFileName(UtilTools.ImageFolderType.MATERIAL_ICON,"material_lost_echo_of_the_shared_wish")
                635681 -> return UtilTools().getAssetsWebpByFileName(UtilTools.ImageFolderType.MATERIAL_ICON,"material_meteoric_bullet")
                //add in 2.1.0
                673758 -> return UtilTools().getAssetsWebpByFileName(UtilTools.ImageFolderType.MATERIAL_ICON,"material_divine_amber")
                673753 -> return UtilTools().getAssetsWebpByFileName(UtilTools.ImageFolderType.MATERIAL_ICON,"material_myriad_fruit")
                589817 -> return UtilTools().getAssetsWebpByFileName(UtilTools.ImageFolderType.MATERIAL_ICON,"material_crystal_meteorites")
                874344 -> return UtilTools().getAssetsWebpByFileName(UtilTools.ImageFolderType.MATERIAL_ICON,"material_scattered_stardust")
                589812 -> return UtilTools().getAssetsWebpByFileName(UtilTools.ImageFolderType.MATERIAL_ICON,"material_nourishing_honey")
                874339 -> return UtilTools().getAssetsWebpByFileName(UtilTools.ImageFolderType.MATERIAL_ICON,"material_alien_tree_seed")
                983280 -> return UtilTools().getAssetsWebpByFileName(UtilTools.ImageFolderType.MATERIAL_ICON,"material_raging_heart")
                //add in 2.0.0
                635680 -> return UtilTools().getAssetsWebpByFileName(UtilTools.ImageFolderType.MATERIAL_ICON,"material_borisin_teeth")
                920207 -> return UtilTools().getAssetsWebpByFileName(UtilTools.ImageFolderType.MATERIAL_ICON,"material_lupitoxin_sawteeth")
                836266 -> return UtilTools().getAssetsWebpByFileName(UtilTools.ImageFolderType.MATERIAL_ICON,"material_moon_madness_fang")
                549209 -> return UtilTools().getAssetsWebpByFileName(UtilTools.ImageFolderType.MATERIAL_ICON,"material_dream_collection_component")
                633150 -> return UtilTools().getAssetsWebpByFileName(UtilTools.ImageFolderType.MATERIAL_ICON,"material_dream_flow_valve")
                717091 -> return UtilTools().getAssetsWebpByFileName(UtilTools.ImageFolderType.MATERIAL_ICON,"material_dream_making_engine")
                67221 -> return UtilTools().getAssetsWebpByFileName(UtilTools.ImageFolderType.MATERIAL_ICON,"material_dream_fridge")
                549210 -> return UtilTools().getAssetsWebpByFileName(UtilTools.ImageFolderType.MATERIAL_ICON,"material_tatters_of_thought")
                633151 -> return UtilTools().getAssetsWebpByFileName(UtilTools.ImageFolderType.MATERIAL_ICON,"material_fragments_of_impression")
                717092 -> return UtilTools().getAssetsWebpByFileName(UtilTools.ImageFolderType.MATERIAL_ICON,"material_shards_of_desires")
                351748 -> return UtilTools().getAssetsWebpByFileName(UtilTools.ImageFolderType.MATERIAL_ICON,"material_dream_flamer")
                874346 -> return UtilTools().getAssetsWebpByFileName(UtilTools.ImageFolderType.MATERIAL_ICON,"material_firmament_note")
                589819 -> return UtilTools().getAssetsWebpByFileName(UtilTools.ImageFolderType.MATERIAL_ICON,"material_celestial_section")
                673760 -> return UtilTools().getAssetsWebpByFileName(UtilTools.ImageFolderType.MATERIAL_ICON,"material_heavenly_melody")
                673759 -> return UtilTools().getAssetsWebpByFileName(UtilTools.ImageFolderType.MATERIAL_ICON,"material_heaven_incinerator")
                874345 -> return UtilTools().getAssetsWebpByFileName(UtilTools.ImageFolderType.MATERIAL_ICON,"material_fiery_spirit")
                589818 -> return UtilTools().getAssetsWebpByFileName(UtilTools.ImageFolderType.MATERIAL_ICON,"material_starfire_essence")

                //星瓊
                900001 -> return UtilTools().getAssetsWebpByFileName(UtilTools.ImageFolderType.MATERIAL_ICON,"material_stella_jade")
                //Lightcone EXP
                694487 -> return UtilTools().getAssetsWebpByFileName(UtilTools.ImageFolderType.MATERIAL_ICON,"material_refined_aether")
                694488 -> return UtilTools().getAssetsWebpByFileName(UtilTools.ImageFolderType.MATERIAL_ICON,"material_condensed_aether")
                694489 -> return UtilTools().getAssetsWebpByFileName(UtilTools.ImageFolderType.MATERIAL_ICON,"material_sparse_aether")
                //add in 1.6.0
                470781 -> return UtilTools().getAssetsWebpByFileName(UtilTools.ImageFolderType.MATERIAL_ICON,"material_past_evils_of_the_borehole_planet_disaster")

                29328 -> return UtilTools().getAssetsWebpByFileName(UtilTools.ImageFolderType.MATERIAL_ICON,"material_credit")
                125435 -> return UtilTools().getAssetsWebpByFileName(UtilTools.ImageFolderType.MATERIAL_ICON,"material_tracks_of_destiny")
                186254 -> return UtilTools().getAssetsWebpByFileName(UtilTools.ImageFolderType.MATERIAL_ICON,"material_regret_of_infinite_ochema")
                409960 -> return UtilTools().getAssetsWebpByFileName(UtilTools.ImageFolderType.MATERIAL_ICON,"material_travelers_guide")
                409961 -> return UtilTools().getAssetsWebpByFileName(UtilTools.ImageFolderType.MATERIAL_ICON,"material_adventure_log")
                409962 -> return UtilTools().getAssetsWebpByFileName(UtilTools.ImageFolderType.MATERIAL_ICON,"material_travel_encounters")
                549438 -> return UtilTools().getAssetsWebpByFileName(UtilTools.ImageFolderType.MATERIAL_ICON,"material_extinguished_core")
                633379 -> return UtilTools().getAssetsWebpByFileName(UtilTools.ImageFolderType.MATERIAL_ICON,"material_glimmering_core")
                635675 -> return UtilTools().getAssetsWebpByFileName(UtilTools.ImageFolderType.MATERIAL_ICON,"material_key_of_inspiration")
                717320 -> return UtilTools().getAssetsWebpByFileName(UtilTools.ImageFolderType.MATERIAL_ICON,"material_squirming_core")
                836261 -> return UtilTools().getAssetsWebpByFileName(UtilTools.ImageFolderType.MATERIAL_ICON,"material_key_of_wisdom")
                866634 -> return UtilTools().getAssetsWebpByFileName(UtilTools.ImageFolderType.MATERIAL_ICON,"material_netherworld_token")
                920202 -> return UtilTools().getAssetsWebpByFileName(UtilTools.ImageFolderType.MATERIAL_ICON,"material_key_of_knowledge")
                267806 -> return UtilTools().getAssetsWebpByFileName(UtilTools.ImageFolderType.MATERIAL_ICON,"material_ascendant_debris")
                549504 -> return UtilTools().getAssetsWebpByFileName(UtilTools.ImageFolderType.MATERIAL_ICON,"material_immortal_scionette")
                633445 -> return UtilTools().getAssetsWebpByFileName(UtilTools.ImageFolderType.MATERIAL_ICON,"material_immortal_aeroblossom")
                635671 -> return UtilTools().getAssetsWebpByFileName(UtilTools.ImageFolderType.MATERIAL_ICON,"material_seed_of_abundance")
                717386 -> return UtilTools().getAssetsWebpByFileName(UtilTools.ImageFolderType.MATERIAL_ICON,"material_immortal_lumintwig")
                836257 -> return UtilTools().getAssetsWebpByFileName(UtilTools.ImageFolderType.MATERIAL_ICON,"material_flower_of_eternity")
                920198 -> return UtilTools().getAssetsWebpByFileName(UtilTools.ImageFolderType.MATERIAL_ICON,"material_sprout_of_life")
                549503 -> return UtilTools().getAssetsWebpByFileName(UtilTools.ImageFolderType.MATERIAL_ICON,"material_artifexs_module")
                633444 -> return UtilTools().getAssetsWebpByFileName(UtilTools.ImageFolderType.MATERIAL_ICON,"material_artifexs_cogwheel")
                635670 -> return UtilTools().getAssetsWebpByFileName(UtilTools.ImageFolderType.MATERIAL_ICON,"material_harmonic_tune")
                717385 -> return UtilTools().getAssetsWebpByFileName(UtilTools.ImageFolderType.MATERIAL_ICON,"material_artifexs_gyreheart")
                836256 -> return UtilTools().getAssetsWebpByFileName(UtilTools.ImageFolderType.MATERIAL_ICON,"material_stellaris_symphony")
                920197 -> return UtilTools().getAssetsWebpByFileName(UtilTools.ImageFolderType.MATERIAL_ICON,"material_ancestral_hymn")
                67220 -> return UtilTools().getAssetsWebpByFileName(UtilTools.ImageFolderType.MATERIAL_ICON,"material_gelid_chitin")
                635673 -> return UtilTools().getAssetsWebpByFileName(UtilTools.ImageFolderType.MATERIAL_ICON,"material_shattered_blade")
                836259 -> return UtilTools().getAssetsWebpByFileName(UtilTools.ImageFolderType.MATERIAL_ICON,"material_worldbreaker_blade")
                920200 -> return UtilTools().getAssetsWebpByFileName(UtilTools.ImageFolderType.MATERIAL_ICON,"material_lifeless_blade")
                549407 -> return UtilTools().getAssetsWebpByFileName(UtilTools.ImageFolderType.MATERIAL_ICON,"material_silvermane_badge")
                633348 -> return UtilTools().getAssetsWebpByFileName(UtilTools.ImageFolderType.MATERIAL_ICON,"material_silvermane_insignia")
                635674 -> return UtilTools().getAssetsWebpByFileName(UtilTools.ImageFolderType.MATERIAL_ICON,"material_arrow_of_the_beast_hunter")
                717289 -> return UtilTools().getAssetsWebpByFileName(UtilTools.ImageFolderType.MATERIAL_ICON,"material_silvermane_medal")
                836260 -> return UtilTools().getAssetsWebpByFileName(UtilTools.ImageFolderType.MATERIAL_ICON,"material_arrow_of_the_starchaser")
                920201 -> return UtilTools().getAssetsWebpByFileName(UtilTools.ImageFolderType.MATERIAL_ICON,"material_arrow_of_the_demon_slayer")
                983279 -> return UtilTools().getAssetsWebpByFileName(UtilTools.ImageFolderType.MATERIAL_ICON,"material_searing_steel_blade")
                635669 -> return UtilTools().getAssetsWebpByFileName(UtilTools.ImageFolderType.MATERIAL_ICON,"material_obsidian_of_dread")
                836255 -> return UtilTools().getAssetsWebpByFileName(UtilTools.ImageFolderType.MATERIAL_ICON,"material_obsidian_of_obsession")
                920196 -> return UtilTools().getAssetsWebpByFileName(UtilTools.ImageFolderType.MATERIAL_ICON,"material_obsidian_of_desolation")
                468392 -> return UtilTools().getAssetsWebpByFileName(UtilTools.ImageFolderType.MATERIAL_ICON,"material_suppressing_edict")
                351747 -> return UtilTools().getAssetsWebpByFileName(UtilTools.ImageFolderType.MATERIAL_ICON,"material_nail_of_the_ape")
                635668 -> return UtilTools().getAssetsWebpByFileName(UtilTools.ImageFolderType.MATERIAL_ICON,"material_endurance_of_bronze")
                836254 -> return UtilTools().getAssetsWebpByFileName(UtilTools.ImageFolderType.MATERIAL_ICON,"material_safeguard_of_amber")
                920195 -> return UtilTools().getAssetsWebpByFileName(UtilTools.ImageFolderType.MATERIAL_ICON,"material_oath_of_steel")
                151161 -> return UtilTools().getAssetsWebpByFileName(UtilTools.ImageFolderType.MATERIAL_ICON,"material_shape_shifters_lightning_staff")
                549437 -> return UtilTools().getAssetsWebpByFileName(UtilTools.ImageFolderType.MATERIAL_ICON,"material_thiefs_instinct")
                633378 -> return UtilTools().getAssetsWebpByFileName(UtilTools.ImageFolderType.MATERIAL_ICON,"material_usurpers_scheme")
                717319 -> return UtilTools().getAssetsWebpByFileName(UtilTools.ImageFolderType.MATERIAL_ICON,"material_conquerors_will")
                549408 -> return UtilTools().getAssetsWebpByFileName(UtilTools.ImageFolderType.MATERIAL_ICON,"material_ancient_part")
                633349 -> return UtilTools().getAssetsWebpByFileName(UtilTools.ImageFolderType.MATERIAL_ICON,"material_ancient_spindle")
                717290 -> return UtilTools().getAssetsWebpByFileName(UtilTools.ImageFolderType.MATERIAL_ICON,"material_ancient_engine")
                866633 -> return UtilTools().getAssetsWebpByFileName(UtilTools.ImageFolderType.MATERIAL_ICON,"material_broken_teeth_of_iron_wolf")
                468391 -> return UtilTools().getAssetsWebpByFileName(UtilTools.ImageFolderType.MATERIAL_ICON,"material_golden_crown_of_the_past_shadow")
                985668 -> return UtilTools().getAssetsWebpByFileName(UtilTools.ImageFolderType.MATERIAL_ICON,"material_destroyers_final_road")
                270195 -> return UtilTools().getAssetsWebpByFileName(UtilTools.ImageFolderType.MATERIAL_ICON,"material_guardians_lament")
                351746 -> return UtilTools().getAssetsWebpByFileName(UtilTools.ImageFolderType.MATERIAL_ICON,"material_void_cast_iron")
                151160 -> return UtilTools().getAssetsWebpByFileName(UtilTools.ImageFolderType.MATERIAL_ICON,"material_lightning_crown_of_the_past_shadow")
                983278 -> return UtilTools().getAssetsWebpByFileName(UtilTools.ImageFolderType.MATERIAL_ICON,"material_endotherm_chitin")
                267805 -> return UtilTools().getAssetsWebpByFileName(UtilTools.ImageFolderType.MATERIAL_ICON,"material_storm_eye")
                67219 -> return UtilTools().getAssetsWebpByFileName(UtilTools.ImageFolderType.MATERIAL_ICON,"material_horn_of_snow")
                782692 -> return UtilTools().getAssetsWebpByFileName(UtilTools.ImageFolderType.MATERIAL_ICON,"material_enigmatic_ectostella")
            }
            return UtilTools().getAssetsWebpByFileName(UtilTools.ImageFolderType.AVATAR_ICON,"1006")
        }
    }

}