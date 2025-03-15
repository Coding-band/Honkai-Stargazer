package utils.app

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.russhwolf.settings.Settings
import files.ActionOrderTitle
import files.Character
import files.Event
import files.Expedition
import files.Lightcone
import files.LotterySimulator
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
import files.intelstellar_resource_corp_white_icon
import files.phorphos_alien_fill
import files.phorphos_align_left_fill
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
import types.UserAccount
import types.UserNoteState
import ui.components.HomePageBlocks
import ui.navigation.CharacterInfoRoute
import ui.navigation.CharacterListRoute
import ui.navigation.EventListRoute
import ui.navigation.ExpeditionRoute
import ui.navigation.IIRCHomePageRoute
import ui.navigation.LightconeListRoute
import ui.navigation.MemoryOfChaosMissionRoute
import ui.navigation.ProficientLeaderboardRoute
import ui.navigation.PureFictionMissionRoute
import ui.navigation.RelicInfoRoute
import ui.navigation.RelicListRoute
import ui.navigation.Screen
import ui.navigation.UIDSearchRoute
import ui.navigation.urlHandler
import utils.annotation.DoItLater


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

        //For Right Corner Page
        val INFO_MIN_WIDTH = 320.dp
        val INFO_MAX_WIDTH = 450.dp

        //For Left Corner Page
        val HOME_WIDTH = 390.dp
        val SCREEN_SAVE_PADDING = 18.dp

        val CLARA_KAMOJI = "(´ • ω • `)"
        val SIMULATOR_LEFT_STATIC_ROW_WIDTH = 154.dp

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

        val LOST_IMAGE_DRAWABLE = Res.drawable.ico_lost_img

        var HOME_PAGE_MENU_DEFAULT = arrayListOf<HomePageBlocks.HomePageBlockItem>(
            HomePageBlocks.HomePageBlockItem(
                itemId = "CharacterListPage",
                itemTitleRId = Res.string.Character,
                itemIconId = Res.drawable.phorphos_person_fill,
                itemOnClickToNavigate = CharacterListRoute,
            ),
            HomePageBlocks.HomePageBlockItem(
                itemId = "LightconeListPage",
                itemTitleRId = Res.string.Lightcone,
                itemIconId = Res.drawable.phorphos_sword_fill,
                itemOnClickToNavigate = LightconeListRoute,
            ),
            HomePageBlocks.HomePageBlockItem(
                itemId = "RelicListPage",
                itemTitleRId = Res.string.Relic,
                itemIconId = Res.drawable.phorphos_baseball_cap_fill,
                itemOnClickToNavigate = RelicListRoute,
            ),
            HomePageBlocks.HomePageBlockItem(
                itemId = "UIDSearchPage",
                itemTitleRId = Res.string.UIDSearch,
                itemIconId = Res.drawable.phorphos_alien_fill,
                itemOnClickToNavigate = UIDSearchRoute,
            ),
            HomePageBlocks.HomePageBlockItem(
                itemId = "Stamina",
                itemTitleRId = Res.string.Stamina,
                itemIconId = Res.drawable.phorphos_moon_fill,
                itemType = HomePageBlocks.HomePageBlockItem.HomePageBlockItemType.W2H1,
                itemOnClickAction = {count -> count.value = (count.value + 1) % 2},
            ).onRefresh { self ->
                if(!UserNoteState.value.isInited){
                    self.itemTopHighlight = "--"
                    self.itemTop = "/--"
                    self.itemBottom = "----"
                }else{
                    self.itemTopHighlight = if(self.itemOnClickCount.value == 0) "${UserNoteState.value.currStamina}" else "${UserNoteState.value.currReserveStamina}"
                    self.itemTop = if(self.itemOnClickCount.value == 0) "/${UserNoteState.value.maxStamina}" else "/2400"
                    self.itemBottom = if(self.itemOnClickCount.value == 0) getFinishTimeStr(UserNoteState.value.staminaRecoverTime) else "----"
                }

            },
            HomePageBlocks.HomePageBlockItem(
                itemId = "DailyMissionPage",
                itemTitle = "--/--",
                itemOnClickAction = {
                    showSuccessToast(
                        if(!UserNoteState.value.isInited) {
                            "--/--"
                        }else{
                            "${UserNoteState.value.currTrainScore}/${UserNoteState.value.maxTrainScore}"
                        }
                    )
                },
                itemIconId = Res.drawable.phorphos_calendar_fill
            ).onRefresh {
                if(!UserNoteState.value.isInited) {
                    it.itemTitle = "--/--"
                }else{
                    it.itemTitle = "${UserNoteState.value.currTrainScore}/${UserNoteState.value.maxTrainScore}"
                }
            },

            HomePageBlocks.HomePageBlockItem(
                itemId = "UniversialScore",
                itemTitle = "--/--",
                itemOnClickAction = { showSuccessToast(
                    if(!UserNoteState.value.isInited) {
                        "--/--"
                    }else{
                        "${formatDecimal(UserNoteState.value.currUniversialScore, isUnited = true)}/${formatDecimal(UserNoteState.value.targetUniversialScore, isUnited = true)}"
                    }
                ) },
                itemIconId = Res.drawable.phorphos_planet_fill
            ).onRefresh {
                if(!UserNoteState.value.isInited) {
                    it.itemTitle = "--/--"
                }else{
                    it.itemTitle = "${formatDecimal(UserNoteState.value.currUniversialScore, isUnited = true)}/${formatDecimal(UserNoteState.value.targetUniversialScore, isUnited = true)}"
                }
            },

            HomePageBlocks.HomePageBlockItem(
                itemId = "ExpeditionPage",
                itemTitleRId = Res.string.Expedition,
                itemIconId = Res.drawable.phorphos_users_fill,
                itemType = HomePageBlocks.HomePageBlockItem.HomePageBlockItemType.W2H1,
                itemOnClickToNavigate = ExpeditionRoute
            ).onRefresh { self ->
                if (!UserNoteState.value.isInited) {
                    self.itemTopHighlight = "--"
                    self.itemTop = "/--"
                    self.itemBottom = "----"
                }else{
                    self.itemTopHighlight = "${UserNoteState.value.availableExpedition}"
                    self.itemTop = "/${UserNoteState.value.totalExpedition}"
                    self.itemBottom =
                        if(UserNoteState.value.expedition.isEmpty()) {
                            "----"
                        } else if(UserNoteState.value.expedition.none { it.remainingTime != 0 }){
                            StatusFinished
                        } else {
                            getFinishTimeStr(UserNoteState.value.expedition.filter { it.remainingTime != 0 }
                                .maxOfOrNull { it.finishTime } ?: 0)
                        }
                }

            },
            HomePageBlocks.HomePageBlockItem(
                itemId = "MOCPage",
                itemTitleRId = Res.string.MemoryOfChaos,
                itemIconId = Res.drawable.phorphos_medal_military_fill,
                itemOnClickToNavigate = MemoryOfChaosMissionRoute
            ),
            HomePageBlocks.HomePageBlockItem(
                itemId = "PFPage",
                itemTitleRId = Res.string.PureFiction,
                itemIconId = Res.drawable.phorphos_atom_fill,
                itemOnClickToNavigate = PureFictionMissionRoute
            ),
            HomePageBlocks.HomePageBlockItem(
                itemId = "EventListPage",
                itemTitleRId = Res.string.Event,
                itemIconId = Res.drawable.phorphos_film_slate_fill,
                itemOnClickToNavigate = EventListRoute
            ),
            HomePageBlocks.HomePageBlockItem(
                itemId = "ScoreLevelLeaderboard",
                itemTitleRId = Res.string.ScoreLevelLeaderboard,
                itemIconId = Res.drawable.phorphos_trophy_fill,
                itemOnClickToNavigate = ProficientLeaderboardRoute
            ),
            HomePageBlocks.HomePageBlockItem(
                itemId = "MemoryOfChaosLeaderboard",
                itemTitleRId = Res.string.MemoryOfChaosLeaderboard,
                itemIconId = Res.drawable.phorphos_chart_bar_fill
            ),
            HomePageBlocks.HomePageBlockItem(
                itemId = "PureFictionLeaderboard",
                itemTitleRId = Res.string.PureFictionLeaderboard,
                itemIconId = Res.drawable.phorphos_chart_bar_horizontal_fill
            ),
            HomePageBlocks.HomePageBlockItem(
                itemId = "MapPage",
                itemTitleRId = Res.string.Map,
                itemIconId = Res.drawable.phorphos_map_trifold_fill,
                //itemOnClickToNavigate = Screen.MapPageScreen
                itemOnClickAction = {
                    urlHandler.openUri("https://act.hoyolab.com/sr/app/interactive-map/index.html?lang=${Language.TextLanguageInstance.hoyolabName}")
                }
            ),

            /*
            HomePageBlocks.HomePageBlockItem(
                itemId = "ActionOrderListPage",
                itemTitleRId = Res.string.ActionOrderTitle,
                itemIconId = Res.drawable.phorphos_align_left_fill,
                itemOnClickAction = {
                    showFunctionIsDevelopingToast()
                },
                //itemOnClickToNavigate = Screen.ActionOrderListPageScreen
            ),

             */
            /*

            HomePageBlocks.HomePageBlockItem(
                itemId = "LotterySimulator",
                itemTitleRId = Res.string.LotterySimulator,
                itemIconId = Res.drawable.phorphos_star_of_david_fill
            ),
            HomePageBlocks.HomePageBlockItem(
                itemId = "WrapAnalysisPage",
                itemTitleRId = Res.string.WrapAnalysis,
                itemIconId = Res.drawable.phorphos_shooting_star_fill
            ),
            */

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