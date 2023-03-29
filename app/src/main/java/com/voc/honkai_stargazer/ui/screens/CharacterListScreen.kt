package com.voc.honkai_stargazer.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.FilterAlt
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.voc.honkai_stargazer.models.CharacterEntity
import com.voc.honkai_stargazer.vm.CharacterListViewModel
import com.voc.honkai_stargazer.ui.theme.WhiteFE

@Composable
fun CharacterListScreen(
    modifier: Modifier = Modifier,
    viewModel: CharacterListViewModel = hiltViewModel(),
    navController: NavController,
) {
    var text by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current

    viewModel.getCharacters("")

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TextField(
            value = text,
            placeholder = {
                Text("Search...")
            },
            onValueChange = {
                text = it
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(32.dp),
            leadingIcon = {
                Icon(
                    Icons.Outlined.FilterAlt,
                    "",
                    modifier = Modifier.clickable { focusManager.clearFocus() })
            },
            trailingIcon = {
                Icon(Icons.Filled.Search, "")
            },
            colors = TextFieldDefaults.textFieldColors(
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent, //hide the indicator
            )
        )

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
        ) {
            viewModel.characterList.let { list ->
                items(list.size) { index ->
                    CharacterCard(
                        characterEntity = list[index],
                        navController = navController
                    )
                }
            }
        }

    }
}

@Composable
fun CharacterCard(
    characterEntity: CharacterEntity,
    navController: NavController
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                navController.navigate(
                    "characterDetails/${characterEntity.name}"
                )
            },
        backgroundColor = WhiteFE,
        elevation = 2.dp,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
        ) {
            Column {
                //TODO: CHARACTER IMAGE
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = characterEntity.name,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Roles: "
                    )
                    Text(
                        text = "Path: "
                    )
                }
                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = characterEntity.rarity.toString(),
                    )
                    Text(
                        text = characterEntity.role
                    )
                    Text(
                        text = characterEntity.path
                    )
                }
            }
        }

    }
}