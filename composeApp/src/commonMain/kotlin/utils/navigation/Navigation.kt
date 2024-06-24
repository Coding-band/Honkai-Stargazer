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
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.voc.honkai_stargazer.screen.LightconeListPage
import com.voc.honkai_stargazer.screen.RelicListPage
import components.HeaderData
import components.defaultHeaderData
import files.CharacterList
import files.LightconeList
import files.RelicList
import files.Res
import files.phorphos_baseball_cap_fill
import files.phorphos_house_fill
import files.phorphos_person_fill
import files.phorphos_sword_fill
import screens.CharacterListPage
import screens.HomePage
import screens.MakeBackground

sealed class Screen(val route: String, val headerData: HeaderData = defaultHeaderData){
    object HomePage : Screen("HomeScreen" , HeaderData(titleIconId = Res.drawable.phorphos_house_fill))
    object CharacterListPage  : Screen("CharacterListPage",HeaderData(titleRId = Res.string.CharacterList, titleIconId = Res.drawable.phorphos_person_fill))
    object LightconeListPage  : Screen("LightconeListPage",HeaderData(titleRId = Res.string.LightconeList, titleIconId = Res.drawable.phorphos_sword_fill))
    object RelicListPage  : Screen("RelicListPage",HeaderData(titleRId = Res.string.RelicList, titleIconId = Res.drawable.phorphos_baseball_cap_fill))
}
@Composable
fun Navigation(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.HomePage.route){
        composable(route = Screen.HomePage.route, enterTransition = { EnterTransition.None} , exitTransition = { ExitTransition.None }){
            RootContent(screen = Screen.HomePage, navController = navController, page = { HomePage(navController = navController, headerData = Screen.HomePage.headerData) })
        }

        composable(route = Screen.CharacterListPage.route, enterTransition = { EnterTransition.None} , exitTransition = { ExitTransition.None }){
            RootContent(screen = Screen.CharacterListPage, navController = navController, page = { CharacterListPage(navController = navController, headerData = Screen.CharacterListPage.headerData) })
        }
        composable(route = Screen.LightconeListPage.route, enterTransition = { EnterTransition.None} , exitTransition = { ExitTransition.None }){
            RootContent(screen = Screen.LightconeListPage, navController = navController, page = { LightconeListPage(navController = navController, headerData = Screen.LightconeListPage.headerData) })
        }
        composable(route = Screen.RelicListPage.route, enterTransition = { EnterTransition.None} , exitTransition = { ExitTransition.None }){
            RootContent(screen = Screen.RelicListPage, navController = navController, page = { RelicListPage(navController = navController, headerData = Screen.RelicListPage.headerData) })
        }

    }
}

@Composable
fun RootContent(screen : Screen, modifier: Modifier = Modifier, navController: NavController, page : @Composable () -> Unit){
    Box{
        MakeBackground(screen = screen)
        //Landscape can do ... ?
        Box(modifier = Modifier
        ){
            page()
        }
    }
}