package types

import androidx.compose.ui.graphics.Color
import components.HomePageBlocks
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
                itemIconId = Res.drawable.phorphos_alien_fill
            ),
            HomePageBlocks.HomePageBlockItem(
                itemTitleRId = Res.string.Stamina,
                itemIconId = Res.drawable.phorphos_moon_fill,
                itemType = HomePageBlocks.HomePageBlockItem.HomePageBlockItemType.W2H1,
                itemTopHighlight = "116",
                itemTop = "/240",
                itemBottom = "今天18:16"
            ),
            HomePageBlocks.HomePageBlockItem(
                itemTitle = "100/500",
                itemIconId = Res.drawable.phorphos_calendar_fill
            ),
            HomePageBlocks.HomePageBlockItem(
                itemTitle = "2.4K/14K",
                itemIconId = Res.drawable.phorphos_planet_fill
            ),
            HomePageBlocks.HomePageBlockItem(
                itemTitleRId = Res.string.Expedition,
                itemIconId = Res.drawable.phorphos_users_fill,
                itemType = HomePageBlocks.HomePageBlockItem.HomePageBlockItemType.W2H1,
                itemTopHighlight = "4",
                itemTop = "/4",
                itemBottom = "已完成"
            ),
            HomePageBlocks.HomePageBlockItem(
                itemTitleRId = Res.string.MemoryOfChaos,
                itemIconId = Res.drawable.phorphos_medal_military_fill
            ),
            HomePageBlocks.HomePageBlockItem(
                itemTitleRId = Res.string.PureFiction,
                itemIconId = Res.drawable.phorphos_atom_fill
            ),
            HomePageBlocks.HomePageBlockItem(
                itemTitleRId = Res.string.Event,
                itemIconId = Res.drawable.phorphos_film_slate_fill
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
                itemIconId = Res.drawable.phorphos_map_trifold_fill
            ),
            HomePageBlocks.HomePageBlockItem(
                itemTitleRId = Res.string.LotterySimulator,
                itemIconId = Res.drawable.phorphos_star_of_david_fill
            ),
            HomePageBlocks.HomePageBlockItem(
                itemTitleRId = Res.string.WrapAnalysis,
                itemIconId = Res.drawable.phorphos_shooting_star_fill
            ),
        )
    }
}