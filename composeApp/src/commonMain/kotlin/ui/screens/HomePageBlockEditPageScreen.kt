package ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import dev.chrisbanes.haze.HazeState
import files.Res
import files.phorphos_arrows_clockwise_fill
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jetbrains.compose.resources.DrawableResource
import types.UserAccount
import ui.components.BackIcon
import ui.components.HomePageBlocks
import ui.components.PageHeader
import ui.navigation.Screen
import utils.app.FontSizeNormal16

@Composable
fun HomePageBlockEditPageScreen(
    navigator: NavHostController,
    hazeState: HazeState
){
    Box(modifier = Modifier.fillMaxSize()){
        FlowRow{
            // Item that shown
            Column {
                // Title
                Text(
                    text = "已展示物件",
                    style = FontSizeNormal16(),
                    modifier = Modifier.padding(top = 4.dp, bottom = 4.dp)
                )

                val showListState = rememberLazyListState()
                // Reorderable List
                LazyColumn(modifier = Modifier.wrapContentHeight()) {

                }
            }
            // Item that not shown
            Column {
                // Title
                Text(
                    text = "未展示物件",
                    style = FontSizeNormal16(),
                    modifier = Modifier.padding(top = 4.dp, bottom = 4.dp)
                )

                // Reorderable List

                val noShowListState = rememberLazyListState()
                LazyColumn(modifier = Modifier.wrapContentHeight()) {  }
            }

        }

        PageHeader(
            navigator = navigator,
            headerData = Screen.ExpeditionPageScreen.headerData,
            hazeState = hazeState,
            backIconId = BackIcon.BACK,
        )
    }
}

@Composable
private fun ItemListBlock(itemId: Int, itemTitleRId: String?, itemTitle: String?, itemIconId: DrawableResource){
    Row(modifier = Modifier.padding(12.dp)) {

    }
}