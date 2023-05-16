/*
 * Project Honkai Stargazer (崩壞•星穹觀星者) was
 * Created & Develop by Voc-夜芷冰 , Programmer of Xectorda
 * Copyright © 2023 Xectorda 版權所有
 */

package com.voc.honkai_stargazer.util;

import static com.voc.honkai_stargazer.util.LoadAssestData.LoadAssestData;

import android.content.Context;

import com.voc.honkai_stargazer.R;
import com.voc.honkai_stargazer.data.HSRItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ItemRSS {

    public static final String LANG_EN = "en"; //DEFAULT CASE, now for beta test.
    public static final String LANG_ZH_HK = "zh_hk";
    public static final String LANG_ZH_CN = "zh_cn";
    public static final String LANG_RU = "ru";
    public static final String LANG_UK = "uk";
    public static final String LANG_JA_JP = "jp_jp";

    public static final String ELEMENT_FIRE = "Fire";
    public static final String ELEMENT_ICE = "Ice";
    public static final String ELEMENT_PHYSICAL = "Physical";
    public static final String ELEMENT_WIND = "Wind";
    public static final String ELEMENT_LIGHTNING = "Lightning";
    public static final String ELEMENT_QUANTUM = "Quantum";
    public static final String ELEMENT_IMAGINARY = "Imaginary";

    public static final String PATH_PRESERVATION = "Preservation";
    public static final String PATH_DESTRUCTION = "Destruction";
    public static final String PATH_ABSTRUCTION = "Abundance";
    public static final String PATH_NIHILITY = "Nihility";
    public static final String PATH_HARMONY = "Harmony";
    public static final String PATH_ERUDITION = "Erudition";
    public static final String PATH_HUNT = "Hunt";

    public static final String STATUS_RELEASED = "RELEASED";
    public static final String STATUS_UPCOMING = "UPCOMING";
    public static final String STATUS_BETA = "BETA";

    public static final String SEX_MALE = "Male";
    public static final String SEX_FEMALE = "Female";

    public static final String TYPE_CHARACTER = "Character";
    public static final String TYPE_LIGHTCONE = "Lightcone";
    public static final String TYPE_RELIC = "Relic";
    //public static final String TYPE_ORNAMENT = "Ornament";

    public int[] getCharByName(String charNameInFile){
        switch (charNameInFile){
            case "Arlan" : return new int[]{R.drawable.arlan_icon, R.drawable.arlan_splash};
            case "Asta" : return new int[]{R.drawable.asta_icon, R.drawable.asta_splash};
            case "Bailu" : return new int[]{R.drawable.bailu_icon, R.drawable.bailu_splash};
            case "Blade" : return new int[]{R.drawable.blade_icon, R.drawable.blade_splash};
            case "Bronya" : return new int[]{R.drawable.bronya_icon, R.drawable.bronya_splash};
            case "Clara" : return new int[]{R.drawable.clara_icon, R.drawable.clara_splash};
            case "Dan Heng" : return new int[]{R.drawable.dan_heng_icon, R.drawable.dan_heng_splash};
            case "Gepard" : return new int[]{R.drawable.gepard_icon, R.drawable.gepard_splash};
            case "Herta" : return new int[]{R.drawable.herta_icon, R.drawable.herta_splash};
            case "Himeko" : return new int[]{R.drawable.himeko_icon, R.drawable.himeko_splash};
            case "Hook" : return new int[]{R.drawable.hook_icon, R.drawable.hook_splash};
            case "Jing Yuan" : return new int[]{R.drawable.jing_yuan_icon, R.drawable.jing_yuan_splash};
            case "Kafka" : return new int[]{R.drawable.kafka_icon, R.drawable.kafka_splash};
            case "Luocha" : return new int[]{R.drawable.luocha_icon, R.drawable.luocha_splash};
            case "March 7th" : return new int[]{R.drawable.march_7th_icon, R.drawable.march_7th_splash};
            case "Natasha" : return new int[]{R.drawable.natasha_icon, R.drawable.natasha_splash};
            case "Pela" : return new int[]{R.drawable.pela_icon, R.drawable.pela_splash};
            case "Qingque" : return new int[]{R.drawable.qingque_icon, R.drawable.qingque_splash};
            case "Sampo" : return new int[]{R.drawable.sampo_icon, R.drawable.sampo_splash};
            case "Seele" : return new int[]{R.drawable.seele_icon, R.drawable.seele_splash};
            case "Serval" : return new int[]{R.drawable.serval_icon, R.drawable.serval_splash};
            case "Silver Wolf" : return new int[]{R.drawable.silver_wolf_icon, R.drawable.silver_wolf_splash};
            case "Sushang" : return new int[]{R.drawable.sushang_icon, R.drawable.sushang_splash};
            case "Tingyun" : return new int[]{R.drawable.tingyun_icon, R.drawable.tingyun_splash};
            case "Welt" : return new int[]{R.drawable.welt_icon, R.drawable.welt_splash};
            case "Yanqing" : return new int[]{R.drawable.yanqing_icon, R.drawable.yanqing_splash};

            default: return new int[]{R.drawable.ico_lost_img, R.drawable.ico_lost_img};
        }
    }

    public int[] getLightconeByName(String lightconeNameInFile){
        switch (lightconeNameInFile){
            case "Amber" : return new int[] {R.drawable.amber, R.drawable.amber_artwork};
            case "Arrows" : return new int[] {R.drawable.arrows, R.drawable.arrows_artwork};
            case "A Secret Vow" : return new int[] {R.drawable.a_secret_vow, R.drawable.a_secret_vow_artwork};
            case "Adversarial" : return new int[] {R.drawable.a_secret_vow, R.drawable.a_secret_vow_artwork};
            case "Before Dawn" : return new int[] {R.drawable.before_dawn, R.drawable.before_dawn_artwork};
            case "But the Battle Isn't Over" : return new int[] {R.drawable.but_the_battle_isnt_over, R.drawable.but_the_battle_isnt_over_artwork};
            case "Carve the Moon, Weave the Clouds" : return new int[] {R.drawable.carve_the_moon_weave_the_clouds, R.drawable.carve_the_moon_weave_the_clouds_artwork};
            case "Chorus" : return new int[] {R.drawable.chorus, R.drawable.chorus_artwork};
            case "Collapsing Sky" : return new int[] {R.drawable.collapsing_sky, R.drawable.collapsing_sky_artwork};
            case "Cornucopia" : return new int[] {R.drawable.cornucopia, R.drawable.cornucopia_artwork};
            case "Cruising in the Stellar Sea" : return new int[] {R.drawable.cruising_in_the_stellar_sea, R.drawable.cruising_in_the_stellar_sea_artwork};
            case "Dance! Dance! Dance!" : return new int[] {R.drawable.dance_dance_dance, R.drawable.dance_dance_dance_artwork};
            case "Darting Arrow" : return new int[] {R.drawable.darting_arrow, R.drawable.darting_arrow_artwork};
            case "Data Bank" : return new int[] {R.drawable.data_bank, R.drawable.data_bank_artwork};
            case "Day One of My New Life" : return new int[] {R.drawable.day_one_of_my_new_life, R.drawable.day_one_of_my_new_life_artwork};
            case "Defense" : return new int[] {R.drawable.defense, R.drawable.defense_artwork};
            case "Eyes of the Prey" : return new int[] {R.drawable.eyes_of_the_prey, R.drawable.eyes_of_the_prey_artwork};
            case "Echoes of the Coffin" : return new int[] {R.drawable.echoes_of_the_coffin, R.drawable.echoes_of_the_coffin_artwork};
            case "Fermata" : return new int[] {R.drawable.fermata, R.drawable.fermata_artwork};
            case "Fine Fruit" : return new int[] {R.drawable.fine_fruit, R.drawable.fine_fruit_artwork};
            case "Geniuses' Repose" : return new int[] {R.drawable.geniuses_repose, R.drawable.geniuses_repose_artwork};
            case "Good Night and Sleep Well" : return new int[] {R.drawable.good_night_and_sleep_well, R.drawable.good_night_and_sleep_well_artwork};
            case "Hidden Shadow" : return new int[] {R.drawable.hidden_shadow, R.drawable.hidden_shadow_artwork};
            case "In the Name of the World" : return new int[] {R.drawable.in_the_name_of_the_world, R.drawable.in_the_name_of_the_world_artwork};
            case "In the Night" : return new int[] {R.drawable.in_the_night, R.drawable.in_the_night_artwork};
            case "Incessant Rain" : return new int[] {R.drawable.incessant_rain, R.drawable.incessant_rain_artwork};
            case "Landau's Choice" : return new int[] {R.drawable.landaus_choice, R.drawable.landaus_choice_artwork};
            case "Loop" : return new int[] {R.drawable.loop, R.drawable.loop_artwork};
            case "Make the World Clamor" : return new int[] {R.drawable.make_the_world_clamor, R.drawable.make_the_world_clamor_artwork};
            case "Mediation" : return new int[] {R.drawable.mediation, R.drawable.mediation_artwork};
            case "Memories of the Past" : return new int[] {R.drawable.memories_of_the_past, R.drawable.memories_of_the_past_artwork};
            case "Meshing Cogs" : return new int[] {R.drawable.meshing_cogs, R.drawable.meshing_cogs_artwork};
            case "Moment of Victory" : return new int[] {R.drawable.moment_of_victory, R.drawable.moment_of_victory_artwork};
            case "Multiplication" : return new int[] {R.drawable.multiplication, R.drawable.multiplication_artwork};
            case "Mutual Demise" : return new int[] {R.drawable.mutual_demise, R.drawable.mutual_demise_artwork};
            case "Night on the Milky Way" : return new int[] {R.drawable.night_on_the_milky_way, R.drawable.night_on_the_milky_way_artwork};
            case "Nowhere to Run" : return new int[] {R.drawable.nowhere_to_run, R.drawable.nowhere_to_run_artwork};
            case "Only Silence Remains" : return new int[] {R.drawable.only_silence_remains, R.drawable.only_silence_remains_artwork};
            case "On the Fall of an Aeon" : return new int[] {R.drawable.on_the_fall_of_an_aeon, R.drawable.on_the_fall_of_an_aeon_artwork};
            case "Passkey" : return new int[] {R.drawable.passkey, R.drawable.passkey_artwork};
            case "Past and Future" : return new int[] {R.drawable.past_and_future, R.drawable.past_and_future_artwork};
            case "Patience Is All You Need" : return new int[] {R.drawable.patience_is_all_you_need, R.drawable.patience_is_all_you_need_artwork};
            case "Perfect Timing" : return new int[] {R.drawable.perfect_timing, R.drawable.perfect_timing_artwork};
            case "Pioneering" : return new int[] {R.drawable.pioneering, R.drawable.pioneering_artwork};
            case "Planetary Rendezvous" : return new int[] {R.drawable.planetary_rendezvous, R.drawable.planetary_rendezvous_artwork};
            case "Post-Op Conversation" : return new int[] {R.drawable.post_op_conversation, R.drawable.post_op_conversation_artwork};
            case "Quid Pro Quo" : return new int[] {R.drawable.quid_pro_quo, R.drawable.quid_pro_quo_artwork};
            case "Resolution Shines As Pearls of Sweat" : return new int[] {R.drawable.resolution_shines_as_pearls_of_sweat, R.drawable.resolution_shines_as_pearls_of_sweat_artwork};
            case "Return to Darkness" : return new int[] {R.drawable.return_to_darkness, R.drawable.return_to_darkness_artwork};
            case "River Flows in Spring" : return new int[] {R.drawable.river_flows_in_spring, R.drawable.river_flows_in_spring_artwork};
            case "Sagacity" : return new int[] {R.drawable.sagacity, R.drawable.sagacity_artwork};
            case "Shared Feeling" : return new int[] {R.drawable.shared_feeling, R.drawable.shared_feeling_artwork};
            case "Shattered Home" : return new int[] {R.drawable.shattered_home, R.drawable.shattered_home_artwork};
            case "Sleep Like the Dead" : return new int[] {R.drawable.sleep_like_the_dead, R.drawable.sleep_like_the_dead_artwork};
            case "Something Irreplaceable" : return new int[] {R.drawable.something_irreplaceable, R.drawable.something_irreplaceable_artwork};
            case "Subscribe for More!" : return new int[] {R.drawable.subscribe_for_more, R.drawable.subscribe_for_more_artwork};
            case "Swordplay" : return new int[] {R.drawable.swordplay, R.drawable.swordplay_artwork};
            case "Texture of Memories" : return new int[] {R.drawable.texture_of_memories, R.drawable.texture_of_memories_artwork};
            case "The Birth of the Self" : return new int[] {R.drawable.the_birth_of_the_self, R.drawable.the_birth_of_the_self_artwork};
            case "The Moles Welcome You" : return new int[] {R.drawable.the_moles_welcome_you, R.drawable.the_moles_welcome_you_artwork};
            case "The Seriousness of Breakfast" : return new int[] {R.drawable.the_seriousness_of_breakfast, R.drawable.the_seriousness_of_breakfast_artwork};
            case "The Unreachable Side" : return new int[] {R.drawable.the_unreachable_side, R.drawable.the_unreachable_side_artwork};
            case "This Is Me!" : return new int[] {R.drawable.this_is_me, R.drawable.this_is_me_artwork};
            case "Time Waits for No One" : return new int[] {R.drawable.time_waits_for_no_one, R.drawable.time_waits_for_no_one_artwork};
            case "Today Is Another Peaceful Day" : return new int[] {R.drawable.today_is_another_peaceful_day, R.drawable.today_is_another_peaceful_day_artwork};
            case "Trend of the Universal Market" : return new int[] {R.drawable.trend_of_the_universal_market, R.drawable.trend_of_the_universal_market_artwork};
            case "Under the Blue Sky" : return new int[] {R.drawable.under_the_blue_sky, R.drawable.under_the_blue_sky_artwork};
            case "Void" : return new int[] {R.drawable.void_, R.drawable.void_artwork};
            case "Warmth Shortens Cold Nights" : return new int[] {R.drawable.warmth_shortens_cold_nights, R.drawable.warmth_shortens_cold_nights_artwork};
            case "We Are Wildfire" : return new int[] {R.drawable.we_are_wildfire, R.drawable.we_are_wildfire_artwork};
            case "We Will Meet Again" : return new int[] {R.drawable.we_will_meet_again, R.drawable.we_will_meet_again_artwork};
            case "Woof! Walk Time!" : return new int[] {R.drawable.woof_walk_time, R.drawable.woof_walk_time_artwork};


            default: return new int[]{R.drawable.ico_lost_img,R.drawable.ico_lost_img};
        }
    }

    public int[] getRelicByName(String relicNameInFile){
        switch (relicNameInFile){
            case "Band of Sizzling Thunder" : return new int[] {R.drawable.band_of_sizzling_thunder_1, R.drawable.band_of_sizzling_thunder_2, R.drawable.band_of_sizzling_thunder_3, R.drawable.band_of_sizzling_thunder_4};
            case "Belobog of the Architects" : return new int[] {R.drawable.belobog_of_the_architects_5, R.drawable.belobog_of_the_architects_6};
            case "Celestial Differentiator" : return new int[] {R.drawable.celestial_differentiator_5, R.drawable.celestial_differentiator_6};
            case "Champion of Streetwise Boxing" : return new int[] {R.drawable.champion_of_streetwise_boxing_1, R.drawable.champion_of_streetwise_boxing_2, R.drawable.champion_of_streetwise_boxing_3, R.drawable.champion_of_streetwise_boxing_4};
            case "Eagle of Twilight Line" : return new int[] {R.drawable.eagle_of_twilight_line_1, R.drawable.eagle_of_twilight_line_2, R.drawable.eagle_of_twilight_line_3, R.drawable.eagle_of_twilight_line_4};
            case "Firesmith of Lava-Forging" : return new int[] {R.drawable.firesmith_of_lava_forging_1, R.drawable.firesmith_of_lava_forging_2, R.drawable.firesmith_of_lava_forging_3, R.drawable.firesmith_of_lava_forging_4};
            case "Fleet of the Ageless" : return new int[] {R.drawable.fleet_of_the_ageless_5, R.drawable.fleet_of_the_ageless_6};
            case "Genius of Brilliant Stars" : return new int[] {R.drawable.genius_of_brilliant_stars_1, R.drawable.genius_of_brilliant_stars_2, R.drawable.genius_of_brilliant_stars_3, R.drawable.genius_of_brilliant_stars_4};
            case "Guard of Wuthering Snow" : return new int[] {R.drawable.guard_of_wuthering_snow_1, R.drawable.guard_of_wuthering_snow_2, R.drawable.guard_of_wuthering_snow_3, R.drawable.guard_of_wuthering_snow_4};
            case "Hunter of Glacial Forest" : return new int[] {R.drawable.hunter_of_glacial_forest_1, R.drawable.hunter_of_glacial_forest_2, R.drawable.hunter_of_glacial_forest_3, R.drawable.hunter_of_glacial_forest_4};
            case "Inert Salsotto" : return new int[] {R.drawable.inert_salsotto_5, R.drawable.inert_salsotto_6};
            case "Knight of Purity Palace" : return new int[] {R.drawable.knight_of_purity_palace_1, R.drawable.knight_of_purity_palace_2, R.drawable.knight_of_purity_palace_3, R.drawable.knight_of_purity_palace_4};
            case "Musketeer of Wild Wheat" : return new int[] {R.drawable.musketeer_of_wild_wheat_1, R.drawable.musketeer_of_wild_wheat_2, R.drawable.musketeer_of_wild_wheat_3, R.drawable.musketeer_of_wild_wheat_4};
            case "Pan-Galactic Commercial Enterprise" : return new int[] {R.drawable.pan_galactic_commercial_enterprise_5, R.drawable.pan_galactic_commercial_enterprise_6};
            case "Passerby of Wandering Cloud" : return new int[] {R.drawable.passerby_of_wandering_cloud_1, R.drawable.passerby_of_wandering_cloud_2, R.drawable.passerby_of_wandering_cloud_3, R.drawable.passerby_of_wandering_cloud_4};
            case "Space Sealing Station" : return new int[] {R.drawable.space_sealing_station_5, R.drawable.space_sealing_station_6};
            case "Sprightly Vonwacq" : return new int[] {R.drawable.sprightly_vonwacq_5, R.drawable.sprightly_vonwacq_6};
            case "Talia: Kingdom of Banditry" : return new int[] {R.drawable.talia_kingdom_of_banditry_5, R.drawable.talia_kingdom_of_banditry_6};
            case "Thief of Shooting Meteor" : return new int[] {R.drawable.thief_of_shooting_meteor_1, R.drawable.thief_of_shooting_meteor_2, R.drawable.thief_of_shooting_meteor_3, R.drawable.thief_of_shooting_meteor_4};
            case "Wastelander of Banditry Desert" : return new int[] {R.drawable.wastelander_of_banditry_desert_1, R.drawable.wastelander_of_banditry_desert_2, R.drawable.wastelander_of_banditry_desert_3, R.drawable.wastelander_of_banditry_desert_4};

            default:return new int[]{R.drawable.ico_lost_img,R.drawable.ico_lost_img,R.drawable.ico_lost_img,R.drawable.ico_lost_img};
        }
    }

    public String[] getRelicStatusByName(String relicNameInFile, Context context, String LANGUAGE){
        String json_base = LoadAssestData(context,"lightcone_data/"+LANGUAGE+"/lightcone_pc.json");
        String[] feedback = new String[]{"N/A","N/A"};
        //Get data from JSON
        try {
            JSONObject object = new JSONObject(json_base);
            if (object.has(relicNameInFile)){
                JSONArray bonuses = object.getJSONObject(relicNameInFile).getJSONArray("bonuses");
                if (bonuses != null){
                    for(int x = 0 ; x < bonuses.length() ; x++){
                        feedback[x] = bonuses.getJSONObject(x).getString("desc");
                    }
                    return feedback;
                }else{
                    return new String[]{"bonuses NULL","bonuses NULL"};
                }
            }
            return new String[]{object.getJSONObject(relicNameInFile).toString(),"no this name"};
        } catch (JSONException e) {
            feedback = new String[]{"ERROR - JSONExpection",e.getLocalizedMessage()};
            return feedback;
        }
    }

    public int getIconByElement(String element){
        switch (element){
            case ELEMENT_FIRE: return R.drawable.element_fire;
            case ELEMENT_ICE: return R.drawable.element_ice;
            case ELEMENT_IMAGINARY: return R.drawable.element_imaginary;
            case ELEMENT_LIGHTNING: return R.drawable.element_lightning;
            case ELEMENT_PHYSICAL: return R.drawable.element_physical;
            case ELEMENT_QUANTUM: return R.drawable.element_quantum;
            case ELEMENT_WIND: return R.drawable.element_wind;
            default: return R.drawable.ico_lost_img;
        }
    }

    public int getIconByPath(String path){
        switch (path){
            case PATH_ABSTRUCTION: return R.drawable.path_the_abundance;
            case PATH_DESTRUCTION: return R.drawable.path_the_destruction;
            case PATH_ERUDITION: return R.drawable.path_the_erudition;
            case PATH_HARMONY: return R.drawable.path_the_harmony;
            case PATH_HUNT: return R.drawable.path_the_hunt;
            case PATH_NIHILITY: return R.drawable.path_the_nihility;
            case PATH_PRESERVATION: return R.drawable.path_the_preservation;
            default: return R.drawable.ico_lost_img;
        }
    }
    public int getNameByElement(String element){
        switch (element){
            case ELEMENT_FIRE: return R.string.element_fire;
            case ELEMENT_ICE: return R.string.element_ice;
            case ELEMENT_IMAGINARY: return R.string.element_imaginary;
            case ELEMENT_LIGHTNING: return R.string.element_lightning;
            case ELEMENT_PHYSICAL: return R.string.element_physical;
            case ELEMENT_QUANTUM: return R.string.element_quantum;
            case ELEMENT_WIND: return R.string.element_wind;
            default: return R.string.n_a;
        }
    }

    public int getNameByPath(String path){
        switch (path){
            case PATH_ABSTRUCTION: return R.string.path_the_abundance;
            case PATH_DESTRUCTION: return R.string.path_the_destruction;
            case PATH_ERUDITION: return R.string.path_the_erudition;
            case PATH_HARMONY: return R.string.path_the_harmony;
            case PATH_HUNT: return R.string.path_the_hunt;
            case PATH_NIHILITY: return R.string.path_the_nihility;
            case PATH_PRESERVATION: return R.string.path_the_preservation;
            default: return R.string.n_a;
        }
    }
}
