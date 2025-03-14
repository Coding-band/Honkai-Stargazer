package ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import dev.chrisbanes.haze.hazeSource
import files.GetCharAndUnLock
import files.Res
import files.SetWallPaper
import files.phorphos_lock_regular
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import org.jetbrains.compose.resources.painterResource
import types.Character
import types.UserAccount
import types.Wallpaper
import types.isUnlockSpecials
import ui.components.BackIcon
import ui.components.HeaderData
import ui.components.PAGE_HEADER_HEIGHT
import ui.components.PageHeader
import ui.components.UIButton
import ui.components.defaultHeaderData
import ui.navigation.hazeStateRoot
import ui.navigation.popBackStackLimited
import utils.app.Constants
import utils.app.DefaultZIndex
import utils.app.FontSizeNormal16
import utils.app.Language
import utils.app.newImageRequest
import utils.app.removeStrQuote
import utils.app.toLocaleMap

//All the background image can find in /commonMain/composeResources/files/images/bgs
//U can use the function UtilTools().getAssetsWebpByFileName to get the image

@Composable
fun BackgroundSettingScreen(modifier: Modifier = Modifier, navigator: NavHostController, headerData: HeaderData = defaultHeaderData){
    val context = LocalPlatformContext.current
    val currentWallpaper = Wallpaper.getPreferenceWallpaper()
    val wallpaperIndex = remember { mutableStateOf(kotlin.math.max(0, Wallpaper.wallpaperList.indexOf(currentWallpaper))) }

    Box(modifier = Modifier.navigationBarsPadding()){
        //Waterfall-type Background Image List
        LazyVerticalGrid(
            columns = GridCells.Adaptive(160.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxSize().padding(start = Constants.SCREEN_SAVE_PADDING, end = Constants.SCREEN_SAVE_PADDING).hazeSource(state = hazeStateRoot, zIndex = DefaultZIndex),
        ){
            item(span = { GridItemSpan(maxCurrentLineSpan) }) {
                Spacer(
                    modifier = Modifier
                        .statusBarsPadding()
                        .height(PAGE_HEADER_HEIGHT)
                )
            }
            items(Wallpaper.wallpaperList){ item ->
                val isMatchRequirememt = if(!item.requireOwnChar || UserAccount.INSTANCE.isUnlockSpecials() ) true else (item.requireOwnChar && !UserAccount.INSTANCE.characterList.none { it.officialId.toString() == item.id })
                val aspectRation = rememberSaveable { mutableStateOf(720/1642f) }
                Column(modifier.wrapContentSize()){

                    Box{
                        //Background Image
                        AsyncImage(
                            model = newImageRequest(data = Wallpaper.getWallpaperURLById(item),context = context),
                            contentDescription = "Wallpaper",
                            contentScale = ContentScale.FillBounds,
                            modifier = Modifier
                                .wrapContentHeight()
                                .aspectRatio(aspectRation.value)
                                .clickable {
                                    wallpaperIndex.value = Wallpaper.wallpaperList.indexOf(item)
                                }.let {
                                    if(item.id == Wallpaper.wallpaperList[wallpaperIndex.value].id) it.border(width = 2.dp, color = Color(0xFFDBC291)) else it
                                },
                            onSuccess = { imageResult ->
                                aspectRation.value = imageResult.result.image.width / imageResult.result.image.height.toFloat()
                            }
                        )

                        //Overlay - if not matching the unlocked requirement
                        if(!isMatchRequirememt){
                            Box(
                                modifier = Modifier
                                    .matchParentSize()
                                    .align(Alignment.Center)
                                    .background(Color.Black.copy(alpha = 0.6f))
                                    .clickable {  }
                            ){
                                //Text - Get that character to unlock
                                Column(modifier = Modifier.fillMaxWidth().wrapContentHeight().align(Alignment.Center)) {
                                    Image(
                                        painter = painterResource(Res.drawable.phorphos_lock_regular),
                                        contentDescription = "Lock Icon",
                                        modifier = Modifier
                                            .align(Alignment.CenterHorizontally)
                                            .size(32.dp),
                                        colorFilter = ColorFilter.tint(Color.White)
                                    )
                                    Text(
                                        text = removeStrQuote(Res.string.GetCharAndUnLock),
                                        style = FontSizeNormal16(),
                                        color = Color.White,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                }
                            }
                        }

                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = item.locale?.get(Language.TextLanguageInstance) ?: item.locale?.get(Language.TextLanguage.EN) ?:
                        Character.getCharacterFromExtListJson(item.id)?.jsonObject?.get("localeName")?.jsonObject?.get(Language.TextLanguageInstance.folderName)?.jsonPrimitive?.content ?:
                        item.id,
                        style = FontSizeNormal16(),
                        color = if(item.id == Wallpaper.wallpaperList[wallpaperIndex.value].id) Color(0xFFDBC291) else Color.White,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.navigationBarsPadding().size(64.dp))
            }
        }

        //"Confirm" UI Button
        Row (modifier = Modifier
            .widthIn(Constants.INFO_MIN_WIDTH, Constants.INFO_MAX_WIDTH)
            .padding(start = Constants.SCREEN_SAVE_PADDING, end = Constants.SCREEN_SAVE_PADDING)
            .align(Alignment.BottomCenter)
            .navigationBarsPadding()
        ){
            UIButton(Modifier.weight(0.5f).wrapContentHeight(), text = removeStrQuote(Res.string.SetWallPaper), onClick = {
                Wallpaper.setPreferenceWallpaper(Wallpaper.wallpaperList[wallpaperIndex.value].id)
                Wallpaper.setPreferenceWallpaperLocaleName(
                    Wallpaper.wallpaperList[wallpaperIndex.value].locale ?:
                    Character.getCharacterFromExtListJson(Wallpaper.wallpaperList[wallpaperIndex.value].id)?.jsonObject?.get("localeName")?.jsonObject?.toLocaleMap() ?:
                    mapOf()
                )
                bgModified.value = true
                navigator.popBackStackLimited()
            })
        }

        PageHeader(navigator = navigator, headerData = headerData, hazeState = hazeStateRoot, backIconId = BackIcon.BACK)
    }
}