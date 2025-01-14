package ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import com.russhwolf.settings.Settings
import ui.components.BackIcon
import ui.components.HeaderData
import ui.components.PAGE_HEADER_HEIGHT
import ui.components.PageHeader
import ui.components.UIButton
import ui.components.defaultHeaderData
import dev.chrisbanes.haze.HazeState
import files.Res
import files.SaveWallPaper
import files.SetWallPaper
import files.SwitchOff
import files.SwitchOn
import files.UseBlurEffect
import kotlinx.coroutines.launch
import moe.tlaster.precompose.navigation.Navigator
import types.Character
import types.ImageFolder
import utils.app.Constants
import types.Wallpaper
import utils.app.DpToPx
import utils.app.FontSizeNormal16
import utils.app.getAssetsURLByFileName
import utils.app.newImageRequest
import utils.app.removeStrQuote
import utils.app.showFunctionIsDevelopingToast

//All the background image can find in /commonMain/composeResources/files/images/bgs
//U can use the function UtilTools().getAssetsWebpByFileName to get the image

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
fun BackgroundSettingScreen(modifier: Modifier = Modifier, navigator: Navigator, headerData: HeaderData = defaultHeaderData){

    val hazeState = remember { HazeState() }
    val coroutineScope = rememberCoroutineScope()
    val density = LocalDensity.current.density
    //val isBlur = remember { mutableStateOf(Settings().getBoolean("useBlurEffect", true)) }

    val extendedItems = listOf(Wallpaper.wallpaperList[Wallpaper.wallpaperList.size - 2], Wallpaper.wallpaperList.last()) + Wallpaper.wallpaperList + listOf(Wallpaper.wallpaperList.first() , Wallpaper.wallpaperList[1])
    val currentWallpaper = extendedItems.find { wallpaper: Wallpaper ->
        wallpaper.id == Settings().getString("backgroundImage", "221000")
    }

    val wallpaperIndex = remember {
        mutableStateOf(
            when (extendedItems.indexOf(currentWallpaper)) {
                -1 -> { 2 }
                0 -> { extendedItems.lastIndexOf(currentWallpaper) }
                else -> { extendedItems.indexOf(currentWallpaper) }
            }
        )
    }

    val listState = rememberLazyListState(wallpaperIndex.value, -DpToPx(32.dp, density))

    Box(modifier = Modifier.navigationBarsPadding()){
        Column(modifier = Modifier.fillMaxSize()) {
            Spacer(modifier = Modifier.statusBarsPadding().height(PAGE_HEADER_HEIGHT))

            Spacer(modifier = Modifier.height(14.dp))

            //Remove : Not adapted to the new UI
            /*
            Row (Modifier.widthIn(Constants.INFO_MIN_WIDTH, Constants.INFO_MAX_WIDTH).padding(start = Constants.SCREEN_SAVE_PADDING, end = Constants.SCREEN_SAVE_PADDING).align(Alignment.CenterHorizontally)){
                UIButton(Modifier.weight(1f).height(64.dp), text = "Test 1")
                Spacer(modifier = Modifier.width(10.dp))
                UIButton(Modifier.weight(1f).height(64.dp),
                    text = "${removeStrQuote(Res.string.UseBlurEffect)}: ${removeStrQuote(if(isBlur.value) Res.string.SwitchOn else Res.string.SwitchOff)}",
                    onClick = {
                        isBlur.value = !isBlur.value
                        Settings().putBoolean("useBlurEffect", isBlur.value)
                })
            }
            Spacer(modifier = Modifier.height(34.dp))
             */

            Text(
                text = "SHIFT + Mouse_Wheel to Change Background",
                style = FontSizeNormal16(),
                color = Color.White,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(14.dp))

            //在這裡，我們透過使用LazyRow來實現一個，透過橫向滑動來選擇背景的功能
            //當中目前選取的背景將會在正中央完整展示，其前、後的背景則只會展示右方/左方部分的背景
            //必須確保三張背景高度一致，Scale一致，且背景間有20.dp width 的間隔


            LaunchedEffect(listState.isScrollInProgress){
                if(!listState.isScrollInProgress){
                    wallpaperIndex.value = listState.firstVisibleItemIndex + 1
                    if(wallpaperIndex.value >= extendedItems.size - 2 && !listState.isScrollInProgress){
                        coroutineScope.launch {
                            listState.scrollToItem(2,-DpToPx(32.dp, density))
                        }
                    }else if(wallpaperIndex.value <= 1 && !listState.isScrollInProgress){
                        coroutineScope.launch {
                            listState.scrollToItem(extendedItems.size - 3 ,-DpToPx(32.dp, density))
                        }
                    }
                }
            }

            BoxWithConstraints(modifier = Modifier.fillMaxSize().weight(1f)) {
                val screenWidth = mutableStateOf(maxWidth)
                val flingBehavior = rememberSnapFlingBehavior(listState)

                LazyRow(
                    state = listState,
                    modifier = Modifier.fillMaxWidth(),
                    flingBehavior = flingBehavior,

                ){
                    itemsIndexed(extendedItems) { index, wallpaper ->
                        Row {
                            Spacer(Modifier.width(10.dp))
                            Box(
                                modifier = Modifier
                            ) {
                                // 背景圖片
                                AsyncImage(
                                    model = newImageRequest(context = LocalPlatformContext.current, getAssetsURLByFileName(ImageFolder.BGS, wallpaper.fileName)),
                                    contentScale = ContentScale.Crop,
                                    contentDescription = "Background Image",
                                    modifier = Modifier
                                        .width(screenWidth.value - 80.dp)
                                        .fillParentMaxHeight(),
                                )
                            }
                            Spacer(Modifier.width(10.dp))
                        }
                    }
                }
            }


            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = extendedItems[wallpaperIndex.value].localeName
                    ?: Character.getCharacterItemFromJSON(
                        extendedItems[wallpaperIndex.value].fileName.split(
                            "-"
                        )[0]
                    ).displayName ?: "Unknown",
                style = FontSizeNormal16(),
                color = Color.White,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(34.dp))

            Row (Modifier.widthIn(Constants.INFO_MIN_WIDTH, Constants.INFO_MAX_WIDTH).padding(start = Constants.SCREEN_SAVE_PADDING, end = Constants.SCREEN_SAVE_PADDING).align(Alignment.CenterHorizontally)){
                /*
                UIButton(Modifier.weight(1f).height(64.dp), text = removeStrQuote(Res.string.SaveWallPaper), onClick = {
                    showFunctionIsDevelopingToast()
                })

                Spacer(modifier = Modifier.width(10.dp))
                 */

                UIButton(Modifier .weight(1f).height(64.dp), text = removeStrQuote(Res.string.SetWallPaper), onClick = {
                    Settings().putString("backgroundImage", extendedItems[wallpaperIndex.value].id)
                    bgModified.value = true
                    navigator.popBackStack()
                })
            }
        }

        PageHeader(navigator = navigator, headerData = headerData, hazeState = hazeState, backIconId = BackIcon.BACK)
    }

}