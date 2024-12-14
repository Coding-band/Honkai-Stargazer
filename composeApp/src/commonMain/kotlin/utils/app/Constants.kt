package utils.app

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import files.Res
import files.ico_lost_img
import files.ranking_a_text
import files.ranking_b_text
import files.ranking_c_text
import files.ranking_d_text
import files.ranking_s_text
import files.ranking_ss_text
import org.jetbrains.compose.resources.DrawableResource


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
    }
}