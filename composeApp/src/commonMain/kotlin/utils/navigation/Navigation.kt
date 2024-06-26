/*
 * Project Honkai Stargazer and app Stargazer (星穹觀星者) were
 * Organized & Develop by Coding Band.
 * Copyright © 2024 Coding Band 版權所有
 */

package utils.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.voc.honkai_stargazer.screen.LightconeListPage
import com.voc.honkai_stargazer.screen.RelicListPage
import components.HeaderData
import components.defaultHeaderData
import files.CharacterList
import files.LightconeList
import files.RelicList
import files.Res
import files.europe
import files.phorphos_baseball_cap_fill
import files.phorphos_house_fill
import files.phorphos_person_fill
import files.phorphos_sword_fill
import screens.CharacterInfoPage
import screens.CharacterListPage
import screens.HomePage
import screens.MakeBackground
import screens.SplashPage

sealed class Screen(val route: String, val headerData: HeaderData = defaultHeaderData){
    object SplashPage : Screen("SplashPage" , HeaderData(titleIconId = Res.drawable.phorphos_house_fill))
    object HomePage : Screen("HomePage" , HeaderData(titleIconId = Res.drawable.phorphos_house_fill))
    object CharacterListPage  : Screen("CharacterListPage",HeaderData(titleRId = Res.string.CharacterList, titleIconId = Res.drawable.phorphos_person_fill))
    object LightconeListPage  : Screen("LightconeListPage",HeaderData(titleRId = Res.string.LightconeList, titleIconId = Res.drawable.phorphos_sword_fill))
    object RelicListPage  : Screen("RelicListPage",HeaderData(titleRId = Res.string.RelicList, titleIconId = Res.drawable.phorphos_baseball_cap_fill))
    object CharacterInfoPage  : Screen("CharacterInfoPage",HeaderData(titleRId = Res.string.europe, titleIconId = Res.drawable.phorphos_person_fill))
}
@Composable
fun Navigation(){
    val navController = rememberNavController()
    //val defaultEnterTransition = EnterTransition.None
    //val defaultExitTransition = ExitTransition.None
    val defaultEnterTransition = EnterTransition.None
    val defaultExitTransition = ExitTransition.None
    NavHost(navController = navController, startDestination = Screen.SplashPage.route){
        composable(route = Screen.SplashPage.route, enterTransition = { defaultEnterTransition} , exitTransition = { defaultExitTransition }){
            SplashPage(navController = navController, headerData = Screen.HomePage.headerData)
        }
        composable(route = Screen.HomePage.route, enterTransition = { defaultEnterTransition} , exitTransition = { defaultExitTransition }){
            RootContent(screen = Screen.HomePage, page = { HomePage(navController = navController, headerData = Screen.HomePage.headerData) })
        }
        composable(route = Screen.CharacterListPage.route, enterTransition = { defaultEnterTransition} , exitTransition = { defaultExitTransition }){
            RootContent(screen = Screen.CharacterListPage, page = { CharacterListPage(navController = navController, headerData = Screen.CharacterListPage.headerData) })
        }
        composable(route = Screen.LightconeListPage.route, enterTransition = { defaultEnterTransition} , exitTransition = { defaultExitTransition }){
            RootContent(screen = Screen.LightconeListPage, page = { LightconeListPage(navController = navController, headerData = Screen.LightconeListPage.headerData) })
        }
        composable(route = Screen.RelicListPage.route, enterTransition = { defaultEnterTransition} , exitTransition = { defaultExitTransition }){
            RootContent(screen = Screen.RelicListPage, page = { RelicListPage(navController = navController, headerData = Screen.RelicListPage.headerData) })
        }
        composable(
            route = "${Screen.CharacterInfoPage.route}/{charName}/{fileName}",
            arguments = listOf((navArgument("fileName") {type = NavType.StringType}), (navArgument("charName") {type = NavType.StringType})),
            enterTransition = { defaultEnterTransition} , exitTransition = { defaultExitTransition }
        ){backStackEntry ->
            RootContent(screen = Screen.CharacterInfoPage, page = { CharacterInfoPage(navController = navController, headerData = Screen.CharacterInfoPage.headerData, backStackEntry = backStackEntry) })
        }

    }
}

@Composable
fun RootContent(screen : Screen, modifier: Modifier = Modifier, page : @Composable () -> Unit){
    Box{
        MakeBackground(screen = screen)
        //Landscape can do ... ?
        Box(modifier = Modifier
        ){
            page()
        }
    }
}