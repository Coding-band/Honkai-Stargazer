package com.voc.honkai_stargazer.util;

import com.voc.honkai_stargazer.R;

public class ItemRSS {
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

    public int getLightconeByName(String lightconeNameInFile){
        switch (lightconeNameInFile){
            case "Amber" : return R.drawable.amber;
            case "Adversarial" : return R.drawable.adversarial;
            case "Arrows" : return R.drawable.arrows;
            case "A Secret Vow" : return R.drawable.a_secret_vow;
            case "Before Dawn" : return R.drawable.before_dawn;
            case "But the Battle Isn't Over" : return R.drawable.but_the_battle_isnt_over;
            case "Carve the Moon, Weave the Clouds" : return R.drawable.carve_the_moon_weave_the_clouds;
            case "Chorus" : return R.drawable.chorus;
            case "Collapsing Sky" : return R.drawable.collapsing_sky;
            case "Cornucopia" : return R.drawable.cornucopia;
            case "Cruising in the Stellar Sea" : return R.drawable.cruising_in_the_stellar_sea;
            case "Dance! Dance! Dance!" : return R.drawable.dance_dance_dance;
            case "Darting Arrow" : return R.drawable.darting_arrow;
            case "Data Bank" : return R.drawable.data_bank;
            case "Day One of My New Life" : return R.drawable.day_one_of_my_new_life;
            case "Defense" : return R.drawable.defense;
            case "Eyes of the Prey" : return R.drawable.eyes_of_the_prey;
            case "Echoes of the Coffin" : return R.drawable.echoes_of_the_coffin;
            case "Fermata" : return R.drawable.fermata;
            case "Fine Fruit" : return R.drawable.fine_fruit;
            case "Geniuses' Repose" : return R.drawable.geniuses_repose;
            case "Good Night and Sleep Well" : return R.drawable.good_night_and_sleep_well;
            case "Hidden Shadow" : return R.drawable.hidden_shadow;
            case "In the Name of the World" : return R.drawable.in_the_name_of_the_world;
            case "In the Night" : return R.drawable.in_the_night;
            case "Incessant Rain" : return R.drawable.incessant_rain;
            case "Landau's Choice" : return R.drawable.landaus_choice;
            case "Loop" : return R.drawable.loop;
            case "Make the World Clamor" : return R.drawable.make_the_world_clamor;
            case "Mediation" : return R.drawable.mediation;
            case "Memories of the Past" : return R.drawable.memories_of_the_past;
            case "Meshing Cogs" : return R.drawable.meshing_cogs;
            case "Moment of Victory" : return R.drawable.moment_of_victory;
            case "Multiplication" : return R.drawable.multiplication;
            case "Mutual Demise" : return R.drawable.mutual_demise;
            case "Night on the Milky Way" : return R.drawable.night_on_the_milky_way;
            case "Nowhere to Run" : return R.drawable.nowhere_to_run;
            case "Only Silence Remains" : return R.drawable.only_silence_remains;
            case "On the Fall of an Aeon" : return R.drawable.on_the_fall_of_an_aeon;
            case "Passkey" : return R.drawable.passkey;
            case "Patience Is All You Need" : return R.drawable.patience_is_all_you_need;
            case "Past and Future" : return R.drawable.past_and_future;
            case "Perfect Timing" : return R.drawable.perfect_timing;
            case "Pioneering" : return R.drawable.pioneering;
            case "Planetary Rendezvous" : return R.drawable.planetary_rendezvous;
            case "Post-Op Conversation" : return R.drawable.post_op_conversation;
            case "Quid Pro Quo" : return R.drawable.quid_pro_quo;
            case "Resolution Shines As Pearls of Sweat" : return R.drawable.resolution_shines_as_pearls_of_sweat;
            case "Return to Darkness" : return R.drawable.return_to_darkness;
            case "River Flows in Spring" : return R.drawable.river_flows_in_spring;
            case "Sagacity" : return R.drawable.sagacity;
            case "Shared Feeling" : return R.drawable.shared_feeling;
            case "Shattered Home" : return R.drawable.shattered_home;
            case "Sleep Like the Dead" : return R.drawable.sleep_like_the_dead;
            case "Something Irreplaceable" : return R.drawable.something_irreplaceable;
            case "Subscribe for More!" : return R.drawable.subscribe_for_more;
            case "Swordplay" : return R.drawable.swordplay;
            case "Texture of Memories" : return R.drawable.texture_of_memories;
            case "The Birth of the Self" : return R.drawable.the_birth_of_the_self;
            case "The Moles Welcome You" : return R.drawable.the_moles_welcome_you;
            case "The Seriousness of Breakfast" : return R.drawable.the_seriousness_of_breakfast;
            case "The Unreachable Side" : return R.drawable.the_unreachable_side;
            case "This Is Me!" : return R.drawable.this_is_me;
            case "Time Waits for No One" : return R.drawable.time_waits_for_no_one;
            case "Today Is Another Peaceful Day" : return R.drawable.today_is_another_peaceful_day;
            case "Trend of the Universal Market" : return R.drawable.trend_of_the_universal_market;
            case "Under the Blue Sky" : return R.drawable.under_the_blue_sky;
            case "Void" : return R.drawable.void_;
            case "Warmth Shortens Cold Nights" : return R.drawable.warmth_shortens_cold_nights;
            case "We Are Wildfire" : return R.drawable.we_are_wildfire;
            case "We Will Meet Again" : return R.drawable.we_will_meet_again;
            case "Woof! Walk Time!" : return R.drawable.woof_walk_time;

            default: return R.drawable.ico_lost_img;
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
}
