package ui.function.homePage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.unit.dp
import com.voc.honkaistargazer.BuildKonfig
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import files.Res
import files.donate_ad_bg
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.painterResource
import ui.components.HeaderData
import ui.components.defaultHeaderData
import ui.function.homePage.components.HomePageBlock1x1
import ui.function.homePage.components.HomePageBlock2x1
import ui.function.homePage.components.HomePageBlocks
import ui.function.homePage.components.HomePageHeader
import ui.function.homePage.components.ThreeDotsDialog
import utils.annotation.DoItLater
import utils.app.FontSizeNormal12
import utils.app.FontSizeNormal16
import utils.app.Language
import utils.app.TextColorNormalDim

@Composable
fun HomePage(
    modifier: Modifier = Modifier,
    navigator: Navigator,
    headerData: HeaderData = defaultHeaderData
) {
    val viewModel = HomePageViewModel(navigator = navigator)
    val state by viewModel.state.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.handleIntent(HomePageIntent.Initialize)
        viewModel.handleIntent(HomePageIntent.RefreshData)
    }

    if(!arrayListOf("PRODUCTION", "RELEASE").contains(BuildKonfig.appProfile) ){
        BetaVersionBox()
    }

    key(Language.AppLanguageInstance){
        println("LANGUAGE CHANGED !")
        Box(modifier = Modifier
            .statusBarsPadding()
            .haze(state = state.hazeState)
        ) {
            Column {
                HomePageHeader(
                    navigator = navigator,
                    threeDotDialogPos = state.threeDotDialogPos,
                    threeDotDialogDisplay = state.threeDotDialogDisplay
                )
                HomePageMenuScrollView(
                    navigator = navigator,
                    hazeState = state.hazeState,
                    homeMenuBlockList = state.homeMenuBlockList.value
                )
            }
        }

        ThreeDotsDialog(
            navigator = navigator,
            threeDotDialogPos = state.threeDotDialogPos,
            hazeState = state.hazeState,
            threeDotDialogDisplay = state.threeDotDialogDisplay,
        )

    }
}

@Composable
fun HomePageMenuScrollView(
    modifier: Modifier = Modifier,
    navigator: Navigator,
    hazeState: HazeState,
    homeMenuBlockList: MutableList<HomePageBlocks.HomePageBlockItem>
) {
    Column {
        LazyVerticalGrid(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp)
                .weight(1f),
            columns = GridCells.Adaptive(80.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            items(count = homeMenuBlockList.size, span = { index ->

                when (homeMenuBlockList[index].itemType) {
                    HomePageBlocks.HomePageBlockItem.HomePageBlockItemType.W1H1 -> GridItemSpan(1)
                    HomePageBlocks.HomePageBlockItem.HomePageBlockItemType.W2H1 -> GridItemSpan(2)
                }

            }) { index ->
                val blockData: HomePageBlocks.HomePageBlockItem = homeMenuBlockList[index];
                Box(Modifier.layoutId("HomePageItemBox")){
                    when (blockData.itemType) {
                        HomePageBlocks.HomePageBlockItem.HomePageBlockItemType.W1H1 -> HomePageBlock1x1(
                            blockData,
                            navigator = navigator
                        )
                        HomePageBlocks.HomePageBlockItem.HomePageBlockItemType.W2H1 -> HomePageBlock2x1(
                            blockData,
                            navigator = navigator
                        )
                    }
                }
            }
        }
        BottomView()
    }
}

@Composable
@DoItLater("Ads function")
fun BottomView(modifier: Modifier = Modifier){
    Box(modifier = Modifier
        .heightIn(64.dp, 100.dp)){
        Image(
            modifier = Modifier.fillMaxWidth(),
            painter = painterResource(resource = Res.drawable.donate_ad_bg),
            contentDescription = "Donate Us",
            contentScale = ContentScale.Crop,
            colorFilter = ColorFilter.tint(Color(0xCCFFFFFF), BlendMode.Lighten)
        )

        Text(
            "恭喜您，看到了一條我發呆寫的廣告",
            color = Color.LightGray,
            style = FontSizeNormal16(),
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun BetaVersionBox(){
    Box(modifier = Modifier.fillMaxSize()){
        Box(modifier = Modifier
            .wrapContentSize()
            .background(Color.Black)
            .padding(start = 2.dp, end = 2.dp, top = 12.dp, bottom = 12.dp)
            .rotate(-90f)
            .align(Alignment.TopEnd)
        ){
            Text(
                text = if(BuildKonfig.appProfile == "DEV") {"DEV"} else BuildKonfig.appVersionName,
                style = FontSizeNormal12(),
                color = TextColorNormalDim,
                modifier = Modifier
                    .align(Alignment.Center
                )
            )
        }
    }
}