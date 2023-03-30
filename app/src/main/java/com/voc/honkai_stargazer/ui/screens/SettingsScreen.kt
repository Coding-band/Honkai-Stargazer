package com.voc.honkai_stargazer.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.voc.honkai_stargazer.navigation.Screens
import com.voc.honkai_stargazer.ui.theme.Grey4F
import com.voc.honkai_stargazer.ui.theme.Grey78
import com.voc.honkai_stargazer.vm.MainViewModel
import com.voc.honkai_stargazer.vm.SettingsViewModel

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel(),
    navController: NavController,
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Settings",
            color = Grey4F,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(8.dp)
        )
        Text(
            text = "Languages",
            color = Grey78,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
        Text(
            text = "Coming Soon!",
            color = Grey78,
            fontWeight = FontWeight.Light,
            fontStyle = FontStyle.Italic,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
        Text(
            text = "About",
            color = Grey4F,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(8.dp)
        )
        Text(
            text = "This application is NOT owned by miHoYo, this application is developed by fans of this game. All resources from the game are taken from open sources.",
            color = Grey78,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
        Text(
            text = "Credit",
            color = Grey4F,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(8.dp)
        )
        Text(
            text = "Xectorda",
            color = Grey78,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
        Text(
            text = "Honkai: Star Rail Wiki | Fandom",
            color = Grey78,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
    }
}