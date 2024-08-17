package screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import com.dokar.sonner.ToastType
import com.dokar.sonner.Toaster
import com.dokar.sonner.rememberToasterState
import components.BackIcon
import components.HeaderData
import components.PAGE_HEADER_HEIGHT
import components.PageHeader
import components.PomPomPopup
import components.UISearchBar
import components.defaultHeaderData
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import files.PlayerLevel
import files.Res
import files.UIDFormatError
import files.UIDNoData
import files.UIDSearchRecord
import files.UIDSearchRecordClear
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import moe.tlaster.precompose.navigation.Navigator
import types.Constants
import types.UserAccount.Companion.UIDSEARCH
import types.UserAccountLite
import utils.FontSizeNormal12
import utils.FontSizeNormal14
import utils.FontSizeNormal16
import utils.LongStringXML
import utils.UtilTools
import utils.hoyolab.HoyolabConst
import utils.hoyolab.MihomoRequest
import utils.navigation.Screen
import utils.navigation.navigateLimited
import utils.navigation.navigatorInstance
import utils.navigation.pomPomPopupInstance
import utils.starbase.StarbaseAPI

@Composable
fun UIDSearchPageScreen(
    modifier: Modifier = Modifier,
    navigator: Navigator,
    headerData: HeaderData = defaultHeaderData,
){
    val hazeState = remember { HazeState() }
    val listState = rememberLazyListState()
    val searchWords = remember { mutableStateOf("") }
    val demoList = arrayListOf(
        UserAccountLite("900033852", "Vocaloid2048",70, "202014", HoyolabConst.SERVER.TW_HK_MO, ),
        UserAccountLite("800051234", "Ascent",15, "202011", HoyolabConst.SERVER.ASIA, ),
        UserAccountLite("601058310", "Corin",44, "1210", HoyolabConst.SERVER.AMERICA, ),
        UserAccountLite("900033853", "Homomo",54, "1310", HoyolabConst.SERVER.TW_HK_MO, ),
        UserAccountLite("900033852", "Bronya",70, "1101", HoyolabConst.SERVER.TW_HK_MO, ),
    )
    val searchRecordList = remember { mutableStateOf(UserAccountLite.getSearchRecordList()) }

    /*
                itemOnClickAction = {
                    UIDSEARCH = MihomoRequest("800333171").getUserAccountByMiHomo()
                    navControllerInstance.navigateLimited("${Screen.UserInfoPageScreen.route}?uid=800333171")
                },
     */

    Box{
        val toaster = rememberToasterState()
        val noDataStr = UtilTools().removeStringResDoubleQuotes(Res.string.UIDNoData)
        val wrongFormatStr = UtilTools().removeStringResDoubleQuotes(Res.string.UIDFormatError)
        val isQuerying = remember { mutableStateOf(false) }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.haze(hazeState).align(Alignment.Center).padding(start = Constants.SCREEN_SAVE_PADDING, end = Constants.SCREEN_SAVE_PADDING)
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

                    //Fix : UID查詢未輸入時點擊搜尋會閃退
                    //Preventing user enter empty value / non-number value
                    searchWords.value = searchWords.value.trim()

                    if(arrayListOf(null, "").contains(searchWords.value) || searchWords.value.toLongOrNull() == null) {
                        toaster.show(
                            message = wrongFormatStr,
                            type = ToastType.Warning,
                        )
                        return@launch
                    }else{
                        val mihomoRequest = MihomoRequest(searchWords.value).getUserAccountByMiHomo()
                        UIDSEARCH = if(mihomoRequest.uid == "000000000") {
                            StarbaseAPI().getUserAccountInfo(searchWords.value, true)
                        }else{
                            mihomoRequest
                        }

                        if(UIDSEARCH.uid != "000000000") {
                            if(searchRecordList.value.none { it.uid == UIDSEARCH.uid }) {
                                searchRecordList.value.add(UserAccountLite(UIDSEARCH.uid, UIDSEARCH.username, UIDSEARCH.level, UIDSEARCH.icon, UIDSEARCH.server))
                                UserAccountLite.saveSearchRecordList(searchRecordList.value)
                            }
                            navigatorInstance.navigateLimited("${Screen.UserInfoPageScreen.route}?uid=${searchWords.value}")
                        }else{
                            toaster.show(
                                message = noDataStr,
                                type = ToastType.Warning,
                            )
                        }
                    }

                    withContext(Dispatchers.Main){
                        pomPomPopupInstance.value = PomPomPopup(isDisplay = false)
                    }
                }
            })

            Text(
                text = LongStringXML().UIDOnlySupportFullUID(),
                style = FontSizeNormal12(),
                modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
                    .align(Alignment.CenterHorizontally).wrapContentSize()
            )

            Row(Modifier.padding(top = 8.dp, bottom = 8.dp).fillMaxWidth()) {
                Text(
                    text = UtilTools().removeStringResDoubleQuotes(Res.string.UIDSearchRecord),
                    style = FontSizeNormal14(),
                    color = Color.White
                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = UtilTools().removeStringResDoubleQuotes(Res.string.UIDSearchRecordClear),
                    style = FontSizeNormal14(),
                    color = Color.White,
                    modifier = Modifier.clickable {
                        searchRecordList.value.clear()
                    }
                )
            }

            LazyColumn {
                items(
                    count = searchRecordList.value.size,
                    key = { index -> searchRecordList.value[index].hashCode() }
                ) { index ->
                    val item = searchRecordList.value[index]
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

                                    UIDSEARCH = MihomoRequest(item.uid).getUserAccountByMiHomo()
                                    navigatorInstance.navigateLimited("${Screen.UserInfoPageScreen.route}?uid=${item.uid}")

                                    withContext(Dispatchers.Main) {
                                        pomPomPopupInstance.value = PomPomPopup(isDisplay = false)
                                        isQuerying.value = false
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
                                    model = UtilTools().newImageRequest(
                                        context,
                                        UtilTools().getIconByUserAccountIconValue(item.icon)
                                    ),
                                    imageLoader = UtilTools().newImageLoader(context),
                                    contentDescription = "",
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
                                    text = "${item.uid}·${UtilTools().removeStringResDoubleQuotes(item.server.localeName)}",
                                    style = FontSizeNormal14(),
                                    color = Color.White
                                )
                            }

                            Spacer(Modifier.width(8.dp))

                            Row {
                                Text(
                                    text = "${UtilTools().removeStringResDoubleQuotes(Res.string.PlayerLevel)} ${item.level}",
                                    style = FontSizeNormal12(),
                                )

                            }
                        }
                    }
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
                                text = "${item.uid}·${UtilTools().removeStringResDoubleQuotes(item.server.localeName)}",
                                style = FontSizeNormal14(),
                                modifier = Modifier.weight(1f)
                            )
                        }

                        Spacer(Modifier.width(8.dp))

                        Row {
                            Text(
                                text = "${UtilTools().removeStringResDoubleQuotes(Res.string.PlayerLevel)} ${item.level}",
                                style = FontSizeNormal12(),
                            )

                        }
                    }
                }
            }
             */

        }

        PageHeader(
            navigator = navigator,
            headerData = headerData,
            hazeState = hazeState,
            backIconId = BackIcon.CANCEL,
        )

        Toaster(state = toaster, alignment = Alignment.BottomCenter, richColors = true)
    }
}