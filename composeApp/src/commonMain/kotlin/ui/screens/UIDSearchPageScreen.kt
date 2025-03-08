package ui.screens

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
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import dev.chrisbanes.haze.hazeSource
import files.PlayerLevel
import files.Res
import files.UIDFormatError
import files.UIDNoData
import files.UIDSearchRecord
import files.UIDSearchRecordClear
import files.pom_pom_failed_issue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jetbrains.compose.resources.painterResource
import types.UserAccount
import types.UserAccount.Companion.UIDSEARCH
import types.UserAccountLite
import ui.components.BackIcon
import ui.components.HeaderData
import ui.components.PAGE_HEADER_HEIGHT
import ui.components.PageHeader
import ui.components.PomPomPopup
import ui.components.UISearchBar
import ui.components.defaultHeaderData
import ui.components.pomPomPopupInstance
import ui.navigation.Screen
import ui.navigation.UserInfoRoute
import ui.navigation.hazeStateRoot
import ui.navigation.navigateLimited
import utils.app.Constants
import utils.app.FontSizeNormal12
import utils.app.FontSizeNormal14
import utils.app.FontSizeNormal16
import utils.app.LongStringXML
import utils.app.PageBottomMask
import utils.app.getIconByUserAccountIconValue
import utils.app.newImageRequest
import utils.app.removeStrQuote
import utils.app.showWarningToast
import utils.hoyolab.MihomoRequest
import utils.starbase.StarbaseAPI

@Composable
fun UIDSearchPageScreen(
    modifier: Modifier = Modifier,
    navigator: NavHostController,
    headerData: HeaderData = defaultHeaderData,
){
    val listState = rememberLazyListState()
    val searchWords = remember { mutableStateOf("") }
    val searchRecordList = remember { mutableStateListOf<UserAccountLite>().apply { addAll(UserAccountLite.getSearchRecordList()) } }

    /*
                itemOnClickAction = {
                    UIDSEARCH = MihomoRequest("800333171").getUserAccountByMiHomo()
                    navControllerInstance.navigateLimited("${Screen.UserInfoPageScreen.route}?uid=800333171")
                },
     */

    Box{
        val noDataStr = removeStrQuote(Res.string.UIDNoData)
        val wrongFormatStr = removeStrQuote(Res.string.UIDFormatError)
        val isQuerying = remember { mutableStateOf(false) }
        Column(
            modifier = Modifier.fillMaxSize().hazeSource(hazeStateRoot).padding(start = Constants.SCREEN_SAVE_PADDING, end = Constants.SCREEN_SAVE_PADDING)
        ) {
            Spacer(Modifier.padding(top = PAGE_HEADER_HEIGHT + 8.dp).statusBarsPadding())

            UISearchBar(inputString = searchWords, onClick = {
                CoroutineScope(Dispatchers.Default).launch{
                    if(isQuerying.value) {
                        return@launch
                    }
                    withContext(Dispatchers.Main){
                        pomPomPopupInstance.value = PomPomPopup(isDisplay = true)
                        isQuerying.value = true
                    }

                    //Preventing user enter empty value / non-number value
                    searchWords.value = searchWords.value.trim()

                    if(arrayListOf(null, "").contains(searchWords.value) || searchWords.value.length < 9 ||searchWords.value.toLongOrNull() == null) {
                        //Not matching request format, show error and stop searching progress
                        showWarningToast(
                            message = wrongFormatStr,
                        )
                        isQuerying.value = false
                        pomPomPopupInstance.value = PomPomPopup(isDisplay = false)
                        return@launch
                    }else{
                        //Else, Search UID from Starbase API first, then search UID from Mihomo API
                        if(UserAccount.INSTANCE.uid == searchWords.value) {
                            UIDSEARCH = UserAccount.INSTANCE
                        }else{
                            val starbaseResult = StarbaseAPI().getUserAccountInfo(searchWords.value, true)
                            UIDSEARCH = if (starbaseResult.uid != "000000000") {
                                starbaseResult
                            } else {
                                MihomoRequest(searchWords.value).getUserAccountByMiHomo()
                            }
                        }

                        if(UIDSEARCH.uid != "000000000") {
                            if(searchRecordList.none { it.uid == UIDSEARCH.uid }) {
                                searchRecordList.add(UserAccountLite(UIDSEARCH.uid, UIDSEARCH.username, UIDSEARCH.level, UIDSEARCH.icon, UIDSEARCH.server))
                                UserAccountLite.saveSearchRecordList(arrayListOf<UserAccountLite>().apply { addAll(searchRecordList) })
                            }
                            withContext(Dispatchers.Main){
                                isQuerying.value = false
                                pomPomPopupInstance.value = PomPomPopup(isDisplay = false)
                                navigator.navigateLimited(UserInfoRoute(searchWords.value))
                            }
                        }else{
                            isQuerying.value = false
                            pomPomPopupInstance.value = PomPomPopup(isDisplay = false)
                            showWarningToast(
                                message = noDataStr,
                            )
                        }
                    }
                }
            }, isFocus = false)

            Text(
                text = LongStringXML().UIDOnlySupportFullUID(),
                style = FontSizeNormal12(),
                modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
                    .align(Alignment.CenterHorizontally).wrapContentSize()
            )

            Row(Modifier.padding(top = 8.dp, bottom = 8.dp).fillMaxWidth()) {
                Text(
                    text = removeStrQuote(Res.string.UIDSearchRecord),
                    style = FontSizeNormal14(),
                    color = Color.White
                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = removeStrQuote(Res.string.UIDSearchRecordClear),
                    style = FontSizeNormal14(),
                    color = Color.White,
                    modifier = Modifier.clickable {
                        searchRecordList.clear()
                        UserAccountLite.saveSearchRecordList(arrayListOf<UserAccountLite>().apply { addAll(searchRecordList) })
                    }
                )
            }

            LazyColumn {
                items(
                    count = searchRecordList.size,
                    key = { index -> searchRecordList[index].hashCode() }
                ) { index ->
                    val item = searchRecordList[index]
                    Box(
                        Modifier.fillMaxWidth().padding(top = 4.dp, bottom = 4.dp)
                            .background(Color((0x66F3F9FF)), RoundedCornerShape(10.dp))
                            .clip(RoundedCornerShape(10.dp)).clickable {
                                CoroutineScope(Dispatchers.Default).launch {
                                    if (isQuerying.value) {
                                        return@launch
                                    }

                                    withContext(Dispatchers.Main) {
                                        pomPomPopupInstance.value = PomPomPopup(isDisplay = true)
                                        isQuerying.value = true
                                    }

                                    if(UserAccount.INSTANCE.uid == item.uid) {
                                        UIDSEARCH = UserAccount.INSTANCE
                                    }else{
                                        val starbaseResult = StarbaseAPI().getUserAccountInfo(item.uid, true)
                                        UIDSEARCH = if (starbaseResult.uid != "000000000") {
                                            starbaseResult
                                        } else {
                                            MihomoRequest(item.uid).getUserAccountByMiHomo()
                                        }
                                    }

                                    withContext(Dispatchers.Main) {
                                        pomPomPopupInstance.value = PomPomPopup(isDisplay = false)
                                        isQuerying.value = false
                                        navigator.navigateLimited(UserInfoRoute(item.uid))
                                    }
                                }
                            }
                    ) {
                        Row(Modifier.padding(10.dp).fillMaxWidth().wrapContentHeight()) {
                            val context = LocalPlatformContext.current
                            val scale = if (item.icon.startsWith("http")) 1.142857f else 1f
                            Box(Modifier.requiredSize(40.dp)) {
                                // User Avatar
                                AsyncImage(
                                    modifier = Modifier.size(40.dp * scale),
                                    model = newImageRequest(
                                        context,
                                        getIconByUserAccountIconValue(item.icon)
                                    ),
                                    contentDescription = "",
                                    error = painterResource(Res.drawable.pom_pom_failed_issue)
                                )
                            }

                            Spacer(Modifier.width(8.dp))

                            Column(Modifier.fillMaxSize().weight(1f)) {
                                Text(
                                    text = item.username,
                                    style = FontSizeNormal16(),
                                    color = Color.White
                                )
                                Spacer(Modifier.height(4.dp).weight(1f))
                                Text(
                                    text = "${item.uid}·${removeStrQuote(item.server.localeName)}",
                                    style = FontSizeNormal14(),
                                    color = Color.White
                                )
                            }

                            Spacer(Modifier.width(8.dp))

                            Row {
                                Text(
                                    text = "${removeStrQuote(Res.string.PlayerLevel)} ${item.level}",
                                    style = FontSizeNormal12(),
                                )

                            }
                        }
                    }
                }

                item{
                    Box(modifier = Modifier.navigationBarsPadding().height(64.dp))
                }

            }

            /*
            DragDropColumn(
                modifier = Modifier.weight(1f).fillMaxSize(),
                items = searchRecordList.value,
                onSwap = { from, to ->
                    searchRecordList.update {
                        val newList = it.toMutableList()
                        val fromItem = it[from].copy()
                        val toItem = it[to].copy()
                        newList[from] = toItem
                        newList[to] = fromItem

                        println("it: $it, newList: $newList")

                        newList as ArrayList<UserAccountLite>
                    }
                },
            ) { item ->
                Box(
                    Modifier.fillMaxWidth().padding(top = 4.dp, bottom = 4.dp)
                        .background(Color((0x66F3F9FF)), RoundedCornerShape(10.dp))
                ) {
                    Row(Modifier.padding(10.dp).fillMaxWidth().wrapContentHeight()) {
                        val context = LocalPlatformContext.current
                        val scale = if (item.icon.startsWith("http")) 1.142857f else 1f
                        Box(Modifier.requiredSize(40.dp)) {
                            // User Avatar
                            AsyncImage(
                                modifier = Modifier.size(40.dp * scale),
                                model = UtilTools().newImageRequest(
                                    context,
                                    if (item.icon == "") {
                                        UtilTools().getAssetsWebpByteArrayByFileName(
                                            folderType = UtilTools.ImageFolderType.AVATAR_ICON,
                                            "Anonymous"
                                        )
                                    } else if (item.icon.startsWith("http")) {
                                        item.icon
                                    } else {
                                        UtilTools().getAssetsWebpByteArrayByFileName(
                                            folderType = UtilTools.ImageFolderType.AVATAR_ICON,
                                            item.icon
                                        )
                                    }
                                ),
                                imageLoader = UtilTools().newImageLoader(context),
                                contentDescription = "",
                            )
                        }

                        Spacer(Modifier.width(8.dp))

                        Column(Modifier.weight(1f)) {
                            Text(
                                text = item.username,
                                style = FontSizeNormal16(),
                                modifier = Modifier.weight(1f)
                            )
                            Spacer(Modifier.width(4.dp))
                            Text(
                                text = "${item.uid}·${removeStrQuote(item.server.localeName)}",
                                style = FontSizeNormal14(),
                                modifier = Modifier.weight(1f)
                            )
                        }

                        Spacer(Modifier.width(8.dp))

                        Row {
                            Text(
                                text = "${removeStrQuote(Res.string.PlayerLevel)} ${item.level}",
                                style = FontSizeNormal12(),
                            )

                        }
                    }
                }
            }
             */

        }


        PageBottomMask()

        PageHeader(
            navigator = navigator,
            headerData = headerData,
            hazeState = hazeStateRoot,
            backIconId = BackIcon.CANCEL,
        )
    }
}