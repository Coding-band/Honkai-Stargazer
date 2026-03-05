package ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.russhwolf.settings.Settings
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeSource
import files.ConfirmBTN
import files.Res
import files.Reset
import files.ic_item_add
import files.ic_item_remove
import files.ic_item_reorder
import org.jetbrains.compose.resources.painterResource
import performHapticFeedback
import platformContext
import sh.calvin.reorderable.ReorderableColumn
import ui.components.BackIcon
import ui.components.DropShadow
import ui.components.HomePageBlockItem
import ui.components.PAGE_HEADER_HEIGHT
import ui.components.PageHeader
import ui.components.UIButton
import ui.navigation.Screen
import ui.navigation.popBackStackLimited
import utils.annotation.TranslationPls
import utils.app.Constants
import utils.app.DefaultZIndex
import utils.app.FontSizeNormal14
import utils.app.FontSizeNormal16
import utils.app.Preferences
import utils.app.SG3VerticalScrollbar
import utils.app.pxToDp
import utils.app.removeStrQuote

@Composable
fun HomePageBlockEditPageScreen(
    navigator: NavHostController,
    hazeState: HazeState,
    pageHeader: MutableState<@Composable () -> Unit>,
){
    val reorderList = remember { mutableStateOf(Preferences().HomePageMenu.getShowMenuList()) }

    val listState = rememberLazyListState()

    pageHeader.value = {
        PageHeader(
            navigator = navigator,
            headerData = Screen.HomePageBlockEditPageScreen.headerData,
            hazeState = hazeState,
            backIconId = BackIcon.BACK,
            onBack = { nav ->
                nav.popBackStackLimited()
            },
            listState = listState
        )
    }

    Box(modifier = Modifier
        .fillMaxSize()
    ){
        val actionRowHeight = remember { mutableStateOf(0.dp) }
        val density = LocalDensity.current.density
        LazyColumn(
            state = listState,
            modifier = Modifier
                .padding(start = Constants.SCREEN_SAVE_PADDING, end = Constants.SCREEN_SAVE_PADDING)
                .hazeSource(hazeState, DefaultZIndex)
                .navigationBarsPadding()
        ) {
            item {
                Spacer(modifier = Modifier.statusBarsPadding().height(PAGE_HEADER_HEIGHT + 8.dp))
            }

            // Title
            @TranslationPls
            item {
                Text(
                    text = "已展示物件",
                    style = FontSizeNormal16(),
                    modifier = Modifier.padding(top = 4.dp, bottom = 4.dp)
                )
            }

            // List Item that shown in HomePage Menu
            item {
                ReorderableColumn(
                    list = reorderList.value,
                    onMove = {
                        performHapticFeedback(intensity = 0.5f, context = platformContext)
                    },
                    onSettle = { fromIndex, toIndex ->
                        reorderList.value = reorderList.value.toMutableList().apply {
                            add(toIndex, removeAt(fromIndex))
                        }
                    },
                ){ index, item, isDragging ->
                    key(item){
                        // Now you have to use ReorderableItem to contain!
                        ReorderableItem {
                            val block = Constants.HOME_PAGE_MENU_DEFAULT.firstOrNull { it.itemId == item }
                            if (block != null) {
                                block.itemIsDisplay = true
                                ItemListBlock(blockItem = block, reorderList, draggableModifier = Modifier.draggableHandle())
                            }
                        }
                    }
                }
            }

            // Title - Not Shown
            @TranslationPls
            item {
                Text(
                    text = "未展示物件",
                    style = FontSizeNormal16(),
                    modifier = Modifier.padding(top = 4.dp, bottom = 4.dp)
                )
            }

            // List Item that not shown in HomePage Menu
            item {
                Column {
                    Constants.HOME_PAGE_MENU_DEFAULT.filter {
                        !reorderList.value.contains(it.itemId) &&
                                if (Settings().getBoolean("isUnlockedIIRC", false)) true else it.itemId != Constants.IIRC_MENU_ID //IIRC Easter Egg
                    }.map {item ->
                        val block = Constants.HOME_PAGE_MENU_DEFAULT.firstOrNull { it.itemId == item.itemId }
                        if (block != null) {
                            block.itemIsDisplay = false
                            ItemListBlock(blockItem = block, reorderList)
                        }
                    }

                }
            }

            item {
                Spacer(modifier = Modifier.height(actionRowHeight.value + 8.dp))
            }
        }

        Row(modifier = Modifier
            .align(Alignment.BottomCenter)
            .onGloballyPositioned { actionRowHeight.value = pxToDp(it.size.height, density) }
            .navigationBarsPadding()
            .padding(start = Constants.SCREEN_SAVE_PADDING, end = Constants.SCREEN_SAVE_PADDING, bottom = 4.dp)
        ) {
            DropShadow(
                modifier = Modifier.weight(1f),
                color = Color(0x33000000),
                offset = DpOffset(0.dp, 4.dp),
                radius = 16.dp,
            ) {
                UIButton(
                    textRes = Res.string.Reset,
                    onClick = {
                        Preferences().HomePageMenu.setShowMenuListById(Constants.HOME_PAGE_MENU_ID_LIST)
                        doRecompose.value = !doRecompose.value
                        navigator.popBackStackLimited()
                    },
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            DropShadow(
                modifier = Modifier.weight(1f),
                color = Color(0x33000000),
                offset = DpOffset(0.dp, 4.dp),
                radius = 16.dp,
            ) {
                UIButton(
                    textRes = Res.string.ConfirmBTN,
                    onClick = {
                        Preferences().HomePageMenu.setShowMenuListById(reorderList.value)
                        doRecompose.value = !doRecompose.value
                        navigator.popBackStackLimited()
                    },
                )
            }
        }

        SG3VerticalScrollbar(listState = listState)
    }
}

@Composable
private fun ItemListBlock(blockItem: HomePageBlockItem, listRef: MutableState<List<String>>, draggableModifier: Modifier = Modifier){
    Column(modifier = Modifier.background(Color(0xCCF3F9FF))) {
        Row(modifier = Modifier.padding(12.dp)) {
            // Image that for press to Disable / Enable
            Image(
                painter = painterResource(if(blockItem.itemIsDisplay) Res.drawable.ic_item_remove else Res.drawable.ic_item_add),
                contentDescription = if(blockItem.itemIsDisplay) "Disable" else "Enable",
                modifier = Modifier.size(18.dp).align(Alignment.CenterVertically).clickable {
                    if(blockItem.itemIsDisplay) {
                        listRef.value = listRef.value.toMutableList().apply { remove(blockItem.itemId) }
                    }else{
                        listRef.value = listRef.value.toMutableList().apply { add(blockItem.itemId) }
                    }
                    blockItem.itemIsDisplay = !blockItem.itemIsDisplay
                },
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
                text = "${if(blockItem.itemTitleRId != null) {removeStrQuote(blockItem.itemTitleRId!!)} else null ?: blockItem.itemTitle ?: "?"} (${
                    when(blockItem.itemType) {
                        HomePageBlockItem.HomePageBlockItemType.W1H1 -> "1x1"
                        HomePageBlockItem.HomePageBlockItemType.W2H1 -> "2x1"
                    }
                })",
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