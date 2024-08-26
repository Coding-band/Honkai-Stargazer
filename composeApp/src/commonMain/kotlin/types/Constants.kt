package types

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import components.HomePageBlocks
import files.Character
import files.Event
import files.Expedition
import files.Lightcone
import files.LotterySimulator
import files.MOCMissionPart1
import files.MOCMissionPart2
import files.MOCMissionPart3
import files.Map
import files.MemoryOfChaos
import files.MemoryOfChaosLeaderboard
import files.PureFiction
import files.PureFictionLeaderboard
import files.Relic
import files.Res
import files.ScoreLevelLeaderboard
import files.Stamina
import files.UIDSearch
import files.WrapAnalysis
import files.ico_lost_img
import files.phorphos_alien_fill
import files.phorphos_atom_fill
import files.phorphos_baseball_cap_fill
import files.phorphos_calendar_fill
import files.phorphos_chart_bar_fill
import files.phorphos_chart_bar_horizontal_fill
import files.phorphos_film_slate_fill
import files.phorphos_map_trifold_fill
import files.phorphos_medal_military_fill
import files.phorphos_moon_fill
import files.phorphos_person_fill
import files.phorphos_planet_fill
import files.phorphos_shooting_star_fill
import files.phorphos_star_of_david_fill
import files.phorphos_sword_fill
import files.phorphos_trophy_fill
import files.phorphos_users_fill
import files.ranking_a_text
import files.ranking_b_text
import files.ranking_c_text
import files.ranking_d_text
import files.ranking_s_text
import files.ranking_ss_text
import org.jetbrains.compose.resources.DrawableResource
import utils.UtilTools
import utils.annotation.DoItLater
import utils.navigation.Screen


class Constants {
    companion object {

        //From top to bottom
        val CARD_BG_COLOR_RARE_1 = listOf(Color(0xFF191621), Color(0xFF8F8F95))
        val CARD_BG_COLOR_RARE_2 = listOf(Color(0xFF374860), Color(0xFF3F797C))
        val CARD_BG_COLOR_RARE_3 = listOf(Color(0xFF393A5C), Color(0xFF497AB8))
        val CARD_BG_COLOR_RARE_4 = listOf(Color(0xFF404165), Color(0xFF9763CE))
        val CARD_BG_COLOR_RARE_5 = listOf(Color(0xFF905A52), Color(0xFFC8A471))
        val CARD_BG_COLOR_RARE_UNKNOWN = listOf(Color(0xFF905273), Color(0xFF71B8C8))

        val SPLASH_PAGE_DISPLAY_MILLSEC_DEFAULT = 1500L
        val SPLASH_PAGE_DISPLAY_MILLSEC_ADS = 5000L

        val TRACE_TREE_BASE_WIDTH = 325.dp;
        val TRACE_TREE_BASE_HEIGHT = 405.dp ;

        val TRACE_TREE_BTN_CORE_BASE_SIZE = 56.dp;
        val TRACE_TREE_BTN_SUBCORE_BASE_SIZE = 64.dp ;
        val TRACE_TREE_BTN_EXTEND_BASE_SIZE = 32.dp ;

        val TRACE_TREE_IMG_CORE_BASE_SIZE = 36.dp;
        val TRACE_TREE_IMG_SUBCORE_BASE_SIZE = 48.dp ;
        val TRACE_TREE_IMG_EXTEND_BASE_SIZE = 24.dp ;

        val EIDOLON_IMG_BASE_SIZE = 150.dp ;
        val EIDOLON_FRAME_BASE_WIDTH = 352.dp ;
        val EIDOLON_FRAME_BASE_HEIGHT = 290.dp ;

        val CHAR_CARD_HEIGHT = 102.dp
        val CHAR_CARD_WIDTH = 80.dp
        val CHAR_CARD_TITLE_HEIGHT = 20.dp

        val LC_CARD_HEIGHT = 102.dp
        val LC_CARD_WIDTH = 80.dp
        val LC_CARD_TITLE_HEIGHT = 20.dp

        val RELIC_CARD_HEIGHT = 102.dp
        val RELIC_CARD_WIDTH = 80.dp
        val RELIC_CARD_TITLE_HEIGHT = 20.dp

        val MATERIAL_CARD_HEIGHT = 80.dp
        val MATERIAL_CARD_WIDTH = 58.dp
        val MATERIAL_CARD_TITLE_HEIGHT = 20.dp

        val ADVICE_RELIC_SELECTED_BAR_WIDTH = 40.dp
        val ADVICE_RELIC_UNSELECT_BAR_WIDTH = 10.dp
        val ADVICE_RELIC_BAR_HEIGHT = 4.dp

        val INFO_MIN_WIDTH = 320.dp
        val INFO_MAX_WIDTH = 450.dp

        @DoItLater("Declare when handling pad layout")
        val SCREEN_MIN_WIDTH = 320.dp
        val SCREEN_MAX_WIDTH = 480.dp
        val SCREEN_HOME_PAGE_PAD_WIDTH = 412.dp
        val SCREEN_PAD_REQUIRE_WIDTH = 600.dp
        val SCREEN_SAVE_PADDING = 18.dp

        fun getCardBgColorByRare(rare: Int): List<Color> {
            when (rare) {
                1 -> return CARD_BG_COLOR_RARE_1
                2 -> return CARD_BG_COLOR_RARE_2
                3 -> return CARD_BG_COLOR_RARE_3
                4 -> return CARD_BG_COLOR_RARE_4
                5 -> return CARD_BG_COLOR_RARE_5
            }
            return CARD_BG_COLOR_RARE_UNKNOWN
        }

        fun getTraceTreeScale(newWidth: Dp) : Float{
            return newWidth.div(TRACE_TREE_BASE_WIDTH)
        }

        fun getEidolonScale(newWidth: Dp) : Float{
            return newWidth.div(EIDOLON_FRAME_BASE_WIDTH)
        }

        fun getScoreRankingFont(ranking: String): DrawableResource {
            return when(ranking){
                "SS" -> Res.drawable.ranking_ss_text
                "S" -> Res.drawable.ranking_s_text
                "A" -> Res.drawable.ranking_a_text
                "B" -> Res.drawable.ranking_b_text
                "C" -> Res.drawable.ranking_c_text
                "D" -> Res.drawable.ranking_d_text
                else -> Res.drawable.ico_lost_img
            }
        }


        var HOME_PAGE_ITEMS = arrayListOf<HomePageBlocks.HomePageBlockItem>(
            HomePageBlocks.HomePageBlockItem(
                itemTitleRId = Res.string.Character,
                itemIconId = Res.drawable.phorphos_person_fill,
                itemOnClickToNavigate = Screen.CharacterListPage,
            ),
            HomePageBlocks.HomePageBlockItem(
                itemTitleRId = Res.string.Lightcone,
                itemIconId = Res.drawable.phorphos_sword_fill,
                itemOnClickToNavigate = Screen.LightconeListPage,
            ),
            HomePageBlocks.HomePageBlockItem(
                itemTitleRId = Res.string.Relic,
                itemIconId = Res.drawable.phorphos_baseball_cap_fill,
                itemOnClickToNavigate = Screen.RelicListPage,
            ),
            HomePageBlocks.HomePageBlockItem(
                itemTitleRId = Res.string.UIDSearch,
                itemIconId = Res.drawable.phorphos_alien_fill,
                itemOnClickToNavigate = Screen.UIDSearchPageScreen,
            ),
            @DoItLater("Add Time Count Down later")
            HomePageBlocks.HomePageBlockItem(
                itemTitleRId = Res.string.Stamina,
                itemIconId = Res.drawable.phorphos_moon_fill,
                itemType = HomePageBlocks.HomePageBlockItem.HomePageBlockItemType.W2H1,
                itemTopHighlight = "${UserAccount.INSTANCE.userNote.currStamina}",
                itemTop = "/240",
                itemBottom = "今天18:16"
            ),
            HomePageBlocks.HomePageBlockItem(
                itemTitle = "${UserAccount.INSTANCE.userNote.currTrainScore}/${UserAccount.INSTANCE.userNote.maxTrainScore}",
                itemIconId = Res.drawable.phorphos_calendar_fill
            ),

            HomePageBlocks.HomePageBlockItem(
                itemTitle = "${UtilTools().formatDecimal(UserAccount.INSTANCE.userNote.currUniversialScore, isUnited = true)}/${UtilTools().formatDecimal(UserAccount.INSTANCE.userNote.targetUniversialScore, isUnited = true)}",
                itemIconId = Res.drawable.phorphos_planet_fill
            ),

            @DoItLater("Add Time Count Down later")
            HomePageBlocks.HomePageBlockItem(
                itemTitleRId = Res.string.Expedition,
                itemIconId = Res.drawable.phorphos_users_fill,
                itemType = HomePageBlocks.HomePageBlockItem.HomePageBlockItemType.W2H1,
                itemTopHighlight = "${UserAccount.INSTANCE.userNote.expedition.filter { it.status == "Finished" }.size}",
                itemTop = "/${UserAccount.INSTANCE.userNote.expedition.size}",
                itemBottom = if(UserAccount.INSTANCE.userNote.expedition.filter { it.status == "Finished" }.size == UserAccount.INSTANCE.userNote.expedition.size){
                    "Done"
                }else "In Progress"
            ),
            HomePageBlocks.HomePageBlockItem(
                itemTitleRId = Res.string.MemoryOfChaos,
                itemIconId = Res.drawable.phorphos_medal_military_fill,
                itemOnClickToNavigate = Screen.MemoryOfChaosMissionPageScreen
            ),
            HomePageBlocks.HomePageBlockItem(
                itemTitleRId = Res.string.PureFiction,
                itemIconId = Res.drawable.phorphos_atom_fill,
                itemOnClickToNavigate = Screen.PureFictionMissionPageScreen
            ),
            HomePageBlocks.HomePageBlockItem(
                itemTitleRId = Res.string.Event,
                itemIconId = Res.drawable.phorphos_film_slate_fill,
                itemOnClickToNavigate = Screen.EventListPageScreen
            ),
            HomePageBlocks.HomePageBlockItem(
                itemTitleRId = Res.string.ScoreLevelLeaderboard,
                itemIconId = Res.drawable.phorphos_trophy_fill
            ),
            HomePageBlocks.HomePageBlockItem(
                itemTitleRId = Res.string.MemoryOfChaosLeaderboard,
                itemIconId = Res.drawable.phorphos_chart_bar_fill
            ),
            HomePageBlocks.HomePageBlockItem(
                itemTitleRId = Res.string.PureFictionLeaderboard,
                itemIconId = Res.drawable.phorphos_chart_bar_horizontal_fill
            ),
            HomePageBlocks.HomePageBlockItem(
                itemTitleRId = Res.string.Map,
                itemIconId = Res.drawable.phorphos_map_trifold_fill,
                itemOnClickToNavigate = Screen.MapPageScreen
            ),
            HomePageBlocks.HomePageBlockItem(
                itemTitleRId = Res.string.LotterySimulator,
                itemIconId = Res.drawable.phorphos_star_of_david_fill
            ),
            HomePageBlocks.HomePageBlockItem(
                itemTitleRId = Res.string.WrapAnalysis,
                itemIconId = Res.drawable.phorphos_shooting_star_fill
            ),
            /*

            HomePageBlocks.HomePageBlockItem(
                itemTitleRId = Res.string.MOCMissionPart1,
                itemIconId = Res.drawable.phorphos_shooting_star_fill,
                itemOnClickToNavigate = Screen.BlankScreen
            ),
            HomePageBlocks.HomePageBlockItem(
                itemTitleRId = Res.string.MOCMissionPart2,
                itemIconId = Res.drawable.phorphos_shooting_star_fill,
                itemOnClickToNavigate = Screen.WithBGScreen
            ),
            HomePageBlocks.HomePageBlockItem(
                itemTitleRId = Res.string.MOCMissionPart3,
                itemIconId = Res.drawable.phorphos_shooting_star_fill,
                itemOnClickToNavigate = Screen.WithBGHeaderScreen
            ),
             */
        )
    }
}