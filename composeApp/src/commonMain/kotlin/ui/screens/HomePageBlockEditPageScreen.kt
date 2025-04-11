package ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import dev.chrisbanes.haze.HazeState
import files.Res
import files.ic_item_add
import files.ic_item_remove
import files.ic_item_reorder
import org.jetbrains.compose.resources.painterResource
import sh.calvin.reorderable.ReorderableItem
import sh.calvin.reorderable.rememberReorderableLazyListState
import ui.components.BackIcon
import ui.components.HomePageBlockItem
import ui.components.PageHeader
import ui.navigation.Screen
import utils.app.Constants
import utils.app.Constants.Companion.HOME_PAGE_MENU_ID_LIST
import utils.app.FontSizeNormal14
import utils.app.FontSizeNormal16
import utils.app.removeStrQuote

@Composable
fun HomePageBlockEditPageScreen(
    navigator: NavHostController,
    hazeState: HazeState
){
    val reorderList = remember { HOME_PAGE_MENU_ID_LIST.toMutableStateList() }

    Box(modifier = Modifier
        .fillMaxSize()
    ){
        FlowRow(modifier = Modifier
            .padding(start = Constants.SCREEN_SAVE_PADDING, end = Constants.SCREEN_SAVE_PADDING)
            .navigationBarsPadding()
        ){
            // Item that shown
            Column {
                // Title
                Text(
                    text = "已展示物件",
                    style = FontSizeNormal16(),
                    modifier = Modifier.padding(top = 4.dp, bottom = 4.dp)
                )

                val showListState = rememberLazyListState()
                val reorderableListState = rememberReorderableLazyListState(showListState){
                    from, to ->
                    // Move from to to
                    reorderList.add(to.index, reorderList.removeAt(from.index))
                }
                // Reorderable List
                LazyColumn(
                    state = showListState,
                    modifier = Modifier.wrapContentHeight()
                ) {
                    //Modify later
                    items(reorderList, key = {it}){item ->
                        val block = Constants.HOME_PAGE_MENU_DEFAULT.firstOrNull { it.itemId == item }

                        if (block != null) {
                            ReorderableItem(reorderableListState, key = block.itemId){
                                ItemListBlock(blockItem = block, reorderList, draggableModifier = Modifier.draggableHandle())
                            }
                        }
                    }
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
                LazyColumn(
                    state = noShowListState,
                    modifier = Modifier.wrapContentHeight()
                ) {
                    items(HOME_PAGE_MENU_ID_LIST.filter { item -> reorderList.contains(item) }){item ->
                        val block = Constants.HOME_PAGE_MENU_DEFAULT.firstOrNull { it.itemId == item }

                        if (block != null) {
                            ItemListBlock(blockItem = block, reorderList)
                        }
                    }
                }
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
private fun ItemListBlock(blockItem: HomePageBlockItem, listRef: SnapshotStateList<String>, draggableModifier: Modifier = Modifier){
    Column(modifier = Modifier.background(Color(0xCCF3F9FF))) {
        Row(modifier = Modifier.padding(12.dp)) {
            // Image that for press to Disable / Enable
            Image(
                painter = painterResource(if(blockItem.itemIsDisplay) Res.drawable.ic_item_remove else Res.drawable.ic_item_add),
                contentDescription = if(blockItem.itemIsDisplay) "Disable" else "Enable",
                modifier = Modifier.size(18.dp).align(Alignment.CenterVertically).clickable { listRef.remove(blockItem.itemId) },
                colorFilter = ColorFilter.tint(Color(0xFF000000)),
            )

            Spacer(modifier = Modifier.size(12.dp))

            // Image for that block
            Image(
                painter = painterResource(blockItem.itemIconId),
                contentDescription = "Block Icon",
                modifier = Modifier.size(24.dp).align(Alignment.CenterVertically),
                colorFilter = ColorFilter.tint(Color(0xFF000000)),
            )

            Spacer(modifier = Modifier.size(6.dp))

            // Text for that block
            Text(
                text = if(blockItem.itemTitleRId != null) {removeStrQuote(blockItem.itemTitleRId!!)} else null ?: blockItem.itemTitle ?: "?",
                style = FontSizeNormal14(),
                color = Color(0xFF000000),
                modifier = Modifier.align(Alignment.CenterVertically).weight(1f),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )

            Spacer(modifier = Modifier.size(6.dp))

            // Drag Icon for that block
            if(blockItem.itemIsDisplay){
                Box(modifier = draggableModifier.padding(8.dp)){
                    Image(
                        painter = painterResource(Res.drawable.ic_item_reorder),
                        contentDescription = "Drag Icon",
                        modifier = Modifier.width(16.dp).align(Alignment.Center),
                        colorFilter = ColorFilter.tint(Color(0xFF000000)),
                    )
                }
            }
        }
        Box(Modifier.fillMaxWidth().height(2.dp).background(Color(0xFF979797)))
    }
}