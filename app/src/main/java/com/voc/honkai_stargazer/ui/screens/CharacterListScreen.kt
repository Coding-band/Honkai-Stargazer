package com.voc.honkai_stargazer.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.FilterAlt
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.voc.honkai_stargazer.navigation.Screens
import com.voc.honkai_stargazer.vm.CharacterListViewModel
import com.voc.honkai_stargazer.vm.HomeViewModel
import com.voc.honkai_stargazer.vm.MainViewModel

@Composable
fun CharacterListScreen(
    modifier: Modifier = Modifier,
    viewModel: CharacterListViewModel = hiltViewModel(),
    mainViewModel: MainViewModel,
) {
    var text by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current

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
                Icon(Icons.Outlined.FilterAlt, "", modifier = Modifier.clickable { focusManager.clearFocus() })
            },
            trailingIcon = {
                Icon(Icons.Filled.Search, "", )
            },
            colors = TextFieldDefaults.textFieldColors(
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent, //hide the indicator
            )
        )
    }
}