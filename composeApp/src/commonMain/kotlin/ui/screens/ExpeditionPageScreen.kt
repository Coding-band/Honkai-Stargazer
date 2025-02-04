package ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import com.multiplatform.webview.web.WebView
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeChild
import files.IsDone
import files.NoDataYet
import files.Res
import files.StatusDays
import files.StatusHours
import files.StatusMinutes
import files.phorphos_arrows_clockwise_fill
import files.phorphos_arrows_clockwise_regular
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import moe.tlaster.precompose.navigation.Navigator
import types.UserAccount
import types.UserAccount.Companion.INSTANCE
import types.UserExpedition
import ui.components.BackIcon
import ui.components.HeaderData
import ui.components.PAGE_HEADER_HEIGHT
import ui.components.PageHeader
import ui.components.RefreshBox
import ui.components.ThemedProgressBar
import ui.components.defaultHeaderData
import utils.app.Constants
import utils.app.Constants.Companion.SCREEN_SAVE_PADDING
import utils.app.FontSizeNormal14
import utils.app.FontSizeNormal16
import utils.app.getFinishTimeStr
import utils.app.getRemainingTimeStr
import utils.app.newImageRequest
import utils.app.removeStrQuote

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ExpeditionPageScreen(
    modifier: Modifier = Modifier,
    navigator: Navigator,
    headerData: HeaderData = defaultHeaderData
) {
    val hazeState = remember { HazeState() }
    val isRefreshing = remember { mutableStateOf(false) }
    /*
    val pullRefreshState = rememberPullRefreshState(
        onRefresh = {
            isRefreshing.value = true
            CoroutineScope(Dispatchers.Default).launch {
                async { UserAccount.refreshNoteData() }.await()
                println("WTF")
                withContext(Dispatchers.Main) { isRefreshing.value = false }
            }
        },
        refreshing = isRefreshing.value
    )
     */
    val scrollState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    Box(modifier = Modifier.fillMaxSize()) {
        RefreshBox(isRefreshing.value) {
            LazyColumn(
                state = scrollState,
                modifier = Modifier
                    //.pullRefresh(pullRefreshState)
                    .fillMaxSize()
                    .padding(top = PAGE_HEADER_HEIGHT, start = SCREEN_SAVE_PADDING, end = SCREEN_SAVE_PADDING)
                    .statusBarsPadding()
                    .navigationBarsPadding()
            ) {
                item { Spacer(modifier = Modifier.height(SCREEN_SAVE_PADDING)) }
                INSTANCE.userNote.expedition.forEachIndexed { index, expendition ->
                    item {
                        ExpenditionItem(expendition, hazeState)
                    }
                    if(index < INSTANCE.userNote.expedition.size - 1){
                        item { Spacer(modifier = Modifier.height(12.dp)) }
                    }
                }
            }

            //PullRefreshIndicator(isRefreshing.value, pullRefreshState, Modifier.align(Alignment.TopCenter).padding(top = PAGE_HEADER_HEIGHT))
        }

        PageHeader(
            navigator = navigator,
            headerData = headerData,
            hazeState = hazeState,
            backIconId = BackIcon.BACK,
            forwardIconId = Res.drawable.phorphos_arrows_clockwise_fill,
            onForward = {
                isRefreshing.value = true
                CoroutineScope(Dispatchers.Default).launch {
                    async { UserAccount.refreshNoteData() }.await()
                    withContext(Dispatchers.Main) { isRefreshing.value = false }
                }
            }
        )
    }
}

@Composable
fun ExpenditionItem(expendition: UserExpedition, hazeState: HazeState) {
    //Frame & Background
    Box(modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight()
        .background(Color(0xCCF3F9FF), RoundedCornerShape(4.dp, 20.dp, 4.dp, 4.dp))
        .clip(shape = RoundedCornerShape(4.dp, 20.dp, 4.dp, 4.dp))
        .hazeChild(hazeState)
    ) {
        //Content
        Column(modifier = Modifier.fillMaxWidth().wrapContentHeight()) {
            //ExpendInfo
            Row(modifier = Modifier
                .padding(start = 12.dp, end = 12.dp, top = 12.dp)
                .fillMaxWidth()
                .wrapContentHeight()
            ) {
                //Expendition's Material Target's Icon
                AsyncImage(
                    model = newImageRequest(context = LocalPlatformContext.current, expendition.materialUrl),
                    modifier = Modifier
                        .padding(4.dp)
                        .background(Color(0x66FFFFFF), CircleShape)
                        .clip(shape = CircleShape)
                        .size(40.dp),
                    contentDescription = null,
                )

                Spacer(modifier = Modifier.width(8.dp))

                Column(modifier = Modifier.wrapContentSize()) {
                    //Expediton's Material Name & Character Icon
                    Row(modifier = Modifier.wrapContentSize()) {
                        Text(
                            text = expendition.materialName,
                            color = Color(0xFF000000),
                            style = FontSizeNormal16(),
                            modifier = Modifier.weight(1f),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Row(modifier = Modifier.wrapContentSize()){
                            expendition.expeditionCharacterIcon.forEachIndexed { index, characterIcon ->
                                AsyncImage(
                                    model = characterIcon,
                                    modifier = Modifier
                                        .clip(shape = CircleShape)
                                        .size(20.dp),
                                    contentDescription = null,
                                )

                                if(index < expendition.expeditionCharacterIcon.size - 1) {
                                    Spacer(modifier = Modifier.width(4.dp))
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    //Expendition's Status
                    Row(modifier = Modifier.wrapContentSize()) {
                        Text(
                            text = if(expendition.remainingTime > 0){ getRemainingTimeStr(expendition.remainingTime) } else {
                                if (expendition.expeditionCharacterIcon.isEmpty()){
                                    removeStrQuote(Res.string.NoDataYet)
                                } else {
                                    removeStrQuote(Res.string.IsDone)
                                }
                            },
                            color = Color(0xFF222222),
                            style = FontSizeNormal14(),
                            modifier = Modifier.weight(1f),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Text(
                            text = if(expendition.remainingTime > 0){ getFinishTimeStr(expendition.remainingTime) } else {
                                ""
                            },
                            color = Color(0xFF222222),
                            style = FontSizeNormal14(),
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))


            //ProgressBar
            Row(modifier = Modifier.padding(start = 12.dp, end = 12.dp, bottom = 12.dp).height(10.dp).fillMaxWidth()) {
                ThemedProgressBar(
                    progress = 20 * 60 * 60 - expendition.remainingTime,
                    max = 20 * 60 * 60,
                )
            }
        }
    }
}
