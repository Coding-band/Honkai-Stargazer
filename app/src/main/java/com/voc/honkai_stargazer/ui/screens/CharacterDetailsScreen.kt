package com.voc.honkai_stargazer.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.voc.honkai_stargazer.models.CharacterEntity
import com.voc.honkai_stargazer.ui.theme.Grey4F
import com.voc.honkai_stargazer.ui.theme.PurpleA4
import com.voc.honkai_stargazer.ui.theme.WhiteFE
import com.voc.honkai_stargazer.util.Constants.CHARACTER_TABS
import com.voc.honkai_stargazer.vm.CharacterDetailsViewModel
import com.voc.honkai_stargazer.vm.MainViewModel

@Composable
fun CharacterDetailsScreen(
    characterName: String,
    viewModel: CharacterDetailsViewModel = hiltViewModel(),
    navController: NavController,
    mainViewModel: MainViewModel
) {
    viewModel.getCharacter(characterName)
    mainViewModel.setBottomBarVisibility(false)
    val character = viewModel.character
    val characterTabIndex = viewModel.characterTabIndex
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopAppBar(
            backgroundColor = Color.Transparent,
            elevation = 0.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(Modifier.height(32.dp)) {
                Row(
                    Modifier
                        .fillMaxHeight()
                        .width(68.dp), verticalAlignment = Alignment.CenterVertically
                ) {
                    CompositionLocalProvider(
                        LocalContentAlpha provides ContentAlpha.high,
                    ) {
                        IconButton(
                            onClick = {
                                navController.popBackStack()
                            },
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Back",
                                tint = Grey4F
                            )
                        }
                    }
                }
                Row(
                    Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        maxLines = 1,
                        text = characterName,
                        color = PurpleA4,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
        TabRow(
            selectedTabIndex = characterTabIndex,
            modifier = Modifier.fillMaxWidth(),
            backgroundColor = Color.Transparent,
            indicator = {
                TabRowDefaults.Indicator(
                    modifier = Modifier
                        .tabIndicatorOffset(it[characterTabIndex]),
                    color = PurpleA4,
                    height = TabRowDefaults.IndicatorHeight
                )
            }
        ) {
            CHARACTER_TABS.forEachIndexed { index: Int, tabTitle: String ->
                Tab(
                    selected = index == characterTabIndex,
                    onClick = {
                        viewModel.setCharacterTabIndex(index)
                    },
                    text = {
                        Text(
                            text = tabTitle,
                            color =
                            if (index == characterTabIndex) {
                                PurpleA4
                            } else {
                                Grey4F
                            },
                            fontWeight =
                            if (index == characterTabIndex) {
                                FontWeight.Bold
                            } else {
                                FontWeight.Normal
                            }
                        )
                    }
                )
            }
        }
        if (character != null) {
            when (characterTabIndex) {
                0 -> Profile(characterEntity = character)
            }
        }
    }
}

@Composable
fun Profile(characterEntity: CharacterEntity) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            backgroundColor = WhiteFE,
            elevation = 2.dp,
        ) {
            Column (modifier = Modifier.padding(8.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text("Rarity")
                        Text("Path")
                        Text("Element")
                        Text("Sex")
                        Text("Role")
                        Text("Affiliation")
                    }
                    Column (
                        horizontalAlignment = Alignment.End
                            ) {
                        Text(text = "${characterEntity.rarity}")
                        Text(text = characterEntity.path)
                        Text(text = characterEntity.element)
                        Text(text = characterEntity.sex)
                        Text(text = characterEntity.role)
                        Text(text = characterEntity.affiliation)

                    }
                }
                Text(characterEntity.description)
            }
        }
    }
}