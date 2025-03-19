package ui.navigation

import files.AboutTheApp
import files.ActionOrderTitle
import files.ApocalypticShadow
import files.ChangeWallPaper
import files.CharacterList
import files.Event
import files.Expedition
import files.ExpeditionFullName
import files.HaveNotUsed
import files.LightconeList
import files.Login
import files.MOCMyBattleReport
import files.Map
import files.MemoryOfChaos
import files.PureFiction
import files.RelicList
import files.Res
import files.ScoreLevelLeaderboard
import files.Setting
import files.UIDSearch
import files.UserInfoGameData
import files.phorphos_alien_fill
import files.phorphos_align_left_fill
import files.phorphos_atom_fill
import files.phorphos_baseball_cap_fill
import files.phorphos_film_slate_fill
import files.phorphos_film_slate_regular
import files.phorphos_game_controller_fill
import files.phorphos_game_controller_regular
import files.phorphos_hourglass_fill
import files.phorphos_house_fill
import files.phorphos_map_trifold_fill
import files.phorphos_medal_military_fill
import files.phorphos_note_blank_regular
import files.phorphos_person_fill
import files.phorphos_sliders_horizontal_fill
import files.phorphos_star_fill
import files.phorphos_star_regular
import files.phorphos_sword_fill
import files.phorphos_trophy_fill
import files.phorphos_users_fill
import ui.components.HeaderData
import ui.components.defaultHeaderData

sealed class Screen(val route: String, val headerData: HeaderData = defaultHeaderData) {
    /**
    Example of a new page:
    ```
    data object CharacterListPage : Screen(
        "CharacterListPage",
        HeaderData(
            titleRId = Res.string.CharacterList,
            titleIconId = Res.drawable.phorphos_person_fill
        )
    )
    ```
     */

    data object RootPage : Screen(
        "RootPage",
        HeaderData(titleIconId = Res.drawable.phorphos_house_fill)
    )
    data object SplashPage : Screen(
        "SplashPage",
        HeaderData(titleIconId = Res.drawable.phorphos_house_fill)
    )

    data object HomePage : Screen(
        "HomePage",
        HeaderData(titleIconId = Res.drawable.phorphos_house_fill)
    )
    data object BlankPage : Screen(
        "BlankPage",
        HeaderData(titleIconId = Res.drawable.phorphos_note_blank_regular)
    )

    data object CharacterListPage : Screen(
        "CharacterListPage",
        HeaderData(titleRId = Res.string.CharacterList, titleIconId = Res.drawable.phorphos_person_fill)
    )

    data object LightconeListPage : Screen(
        "LightconeListPage",
        HeaderData(titleRId = Res.string.LightconeList, titleIconId = Res.drawable.phorphos_sword_fill)
    )

    data object RelicListPage : Screen(
        "RelicListPage",
        HeaderData(titleRId = Res.string.RelicList, titleIconId = Res.drawable.phorphos_baseball_cap_fill)
    )

    data object CharacterInfoPage : Screen(
        "CharacterInfoPage",
        HeaderData(titleRId = Res.string.HaveNotUsed, titleIconId = Res.drawable.phorphos_person_fill)
    )
    data object LightconeInfoPage : Screen(
        "LightconeInfoPage",
        HeaderData(titleRId = Res.string.HaveNotUsed, titleIconId = Res.drawable.phorphos_sword_fill)
    )
    data object RelicInfoPage : Screen(
        "RelicInfoPage",
        HeaderData(titleRId = Res.string.HaveNotUsed, titleIconId = Res.drawable.phorphos_baseball_cap_fill)
    )
    data object SettingScreen : Screen(
        "SettingScreen",
        HeaderData(titleRId = Res.string.Setting, titleIconId = Res.drawable.phorphos_sliders_horizontal_fill)
    )
    data object BackgroundSettingScreen : Screen(
        "BackgroundSettingScreen",
        HeaderData(titleRId = Res.string.ChangeWallPaper, titleIconId = Res.drawable.phorphos_sliders_horizontal_fill)
    )
    data object HoyolabLoginPageScreen : Screen(
        "HoyolabLoginPageScreen",
        HeaderData(titleRId = Res.string.Login, titleIconId = Res.drawable.phorphos_person_fill)
    )
    data object EventListPageScreen : Screen(
        "EventListPageScreen",
        HeaderData(titleRId = Res.string.Event, titleIconId = Res.drawable.phorphos_film_slate_fill)
    )
    data object EventContentPageScreen : Screen(
        "EventContentPageScreen",
        HeaderData(titleRId = Res.string.Event, titleIconId = Res.drawable.phorphos_film_slate_regular)
    )
    data object MapPageScreen : Screen(
        "MapPageScreen",
        HeaderData(titleRId = Res.string.Map, titleIconId = Res.drawable.phorphos_map_trifold_fill)
    )
    data object UserInfoPageScreen : Screen(
        "UserInfoPageScreen",
        HeaderData(titleRId = Res.string.UserInfoGameData, titleIconId = Res.drawable.phorphos_game_controller_regular)
    )
    data object UserCharacterPageScreen : Screen(
        "UserCharacterPageScreen",
        HeaderData(titleRId = Res.string.UserInfoGameData, titleIconId = Res.drawable.phorphos_game_controller_regular)
    )
    data object UIDSearchPageScreen : Screen(
        "UIDSearchPageScreen",
        HeaderData(titleRId = Res.string.UIDSearch, titleIconId = Res.drawable.phorphos_alien_fill)
    )
    data object MemoryOfChaosMissionPageScreen : Screen(
        "MemoryOfChaosMissionPageScreen",
        HeaderData(titleRId = Res.string.MemoryOfChaos, titleIconId = Res.drawable.phorphos_medal_military_fill)
    )
    data object BattleChroniclePageScreen : Screen(
        "BattleChroniclePageScreen",
        HeaderData(titleRId = Res.string.MOCMyBattleReport, titleIconId = Res.drawable.phorphos_game_controller_fill)
    )
    data object PureFictionMissionPageScreen : Screen(
        "PureFictionMissionPageScreen",
        HeaderData(titleRId = Res.string.PureFiction, titleIconId = Res.drawable.phorphos_atom_fill)
    )
    data object AboutStargazerPageScreen : Screen(
        "AboutStargazerPageScreen",
        HeaderData(titleRId = Res.string.AboutTheApp, titleIconId = Res.drawable.phorphos_star_fill)
    )
    data object ExpeditionPageScreen : Screen(
        "ExpeditionPageScreen",
        HeaderData(titleRId = Res.string.ExpeditionFullName, titleIconId = Res.drawable.phorphos_users_fill)
    )
    data object ProficientLeaderboardPageScreen : Screen(
        "ProficientLeaderboardPageScreen",
        HeaderData(titleRId = Res.string.ScoreLevelLeaderboard, titleIconId = Res.drawable.phorphos_trophy_fill)
    )
    data object ActionOrderListPageScreen : Screen(
        "ActionOrderListPageScreen",
        HeaderData(titleRId = Res.string.ActionOrderTitle, titleIconId = Res.drawable.phorphos_align_left_fill)
    )
    data object ActionOrderSimulatorPageScreen : Screen(
        "ActionOrderSimulatorPageScreen",
        HeaderData(titleRId = Res.string.ActionOrderTitle, titleIconId = Res.drawable.phorphos_align_left_fill)
    )
    data object IIRCHomePageScreen : Screen(
        "IIRCHomePageScreen",
        HeaderData(titleIconId = Res.drawable.phorphos_star_fill)
    )
    data object ApocalypticShadowMissionPageScreen : Screen(
        "ApocalypticShadowMissionPageScreen",
        HeaderData(titleIconId = Res.drawable.phorphos_hourglass_fill, titleRId = Res.string.ApocalypticShadow)
    )
}