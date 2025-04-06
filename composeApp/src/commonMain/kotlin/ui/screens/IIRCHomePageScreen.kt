package ui.screens

import NavigationBar
import NavigationItemData
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.voc.idle.irc.components.ItemGridView
import com.voc.idle.irc.components.ItemResearchView
import com.voc.idle.irc.components.ItemUpgradeView
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import dev.chrisbanes.haze.hazeSource
import files.Res
import files.ui_icon_close
import files.ui_icon_grid
import files.ui_icon_missing
import files.ui_icon_research
import files.ui_icon_setting
import files.ui_icon_transfer
import files.ui_icon_upgrade
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.vectorResource
import types.CombatType
import types.IIRC
import types.IIRCCurrecyType
import types.IIRCWorld
import ui.components.iirc.HoverButtonBar
import ui.components.iirc.MultiplyEnum
import ui.navigation.navigatorInstance
import ui.navigation.popBackStackLimited
import utils.app.DefaultZIndex
import utils.app.FontShadow
import utils.app.FontSizeNormal14
import utils.app.formatDecimalSci
import utils.app.hazeEffectSG3
import utils.app.toInt

lateinit var iircWorldInfo: MutableState<ArrayList<IIRCWorld>>

@Composable
fun initIIRC(){
    iircWorldInfo = rememberSaveable(stateSaver = IIRC.WorldListSaver) { mutableStateOf(arrayListOf()) }
}

@Composable
fun IIRCHomePageScreen(
    navigator: NavController = rememberNavController(),
    hazeState: HazeState
) {
    val selectedItem = remember { mutableStateOf(0) }

    //Root Frame of the HomePage
    Box(modifier = Modifier.fillMaxSize().navigationBarsPadding().statusBarsPadding()) {

        //The UIs of the HomePage
        Column(modifier = Modifier.fillMaxSize()) {
            //Currency Row, Gift Button, HAVE TO REPACK AS COMPONENT
            CurrencyUI()

            Spacer(modifier = Modifier.height(8.dp))

            //Main Content 主要內容
            Box(modifier = Modifier
                .fillMaxSize()
                .weight(1f)) {
                when(selectedItem.value){
                    0 ->  RedeemPage(hazeState = hazeState)
                    1 ->  UpgradePage(hazeState = hazeState)
                    2 ->  ResearchPage(hazeState = hazeState)
                }
            }

            //Bottom Navigation Bar 導航欄, HAVE TO REPACK AS COMPONENT
            BottomNavigationBar(selectedItem)


        }

    }
}

@Composable
fun RedeemPage(hazeState : HazeState) {

    Box{
        val minItemSize = 96.dp
        val currentDisplayPage = remember { mutableStateOf("WORLD") }
        val currList = remember { mutableStateOf(arrayListOf<IIRC>()) }

        LazyVerticalGrid(
            columns = GridCells.Adaptive(minItemSize),
            contentPadding = PaddingValues(start = 8.dp, end = 8.dp, top = 8.dp, bottom = 64.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxSize()
                .hazeSource(hazeState)

        ) {
            items(charList.value.filter { it.officialId!! in 1000 ..< 1100}.sortedBy { it.officialId }) {it ->
                ItemGridView(it, (it.officialId!! < 1005).toInt(), (it.officialId!! < 1005).toInt())
            }
        }

        Box(modifier = Modifier.align(Alignment.BottomCenter).padding(8.dp)){
            HoverButtonBar(hazeState = hazeState, choiceList = MultiplyEnum.entries){ item, choosedIndex ->
                Text(
                    text = item.text,
                    color = Color(if (MultiplyEnum.entries[choosedIndex] == item) 0xFFFFFFFF else 0x99FFFFFF),
                    modifier = Modifier.height(24.dp).requiredWidth(48.dp).wrapContentWidth().align(
                        Alignment.Center)
                )
            }
        }
    }
}

@Composable
fun UpgradePage(hazeState : HazeState){
    val choiceList = arrayListOf<ImageVector>(
        vectorResource(Res.drawable.ui_icon_missing),
        vectorResource(Res.drawable.ui_icon_missing),
        vectorResource(Res.drawable.ui_icon_missing),
        vectorResource(Res.drawable.ui_icon_missing)
    )

    //Root of Upgrade Page 升級頁面
    Column(modifier = Modifier.fillMaxSize()) {
        HoverButtonBar(choiceList = choiceList){ item, choosedIndex ->
            Image(
                imageVector = item,
                contentDescription = "Upgrade Filter Icon",
                colorFilter = ColorFilter.tint(Color(if (choiceList[choosedIndex] == item) 0xFFFFFFFF else 0x99FFFFFF)),
                modifier = Modifier.size(30.dp).aspectRatio(1f).align(Alignment.CenterHorizontally)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(1),
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer { alpha = 0.99f }
                .haze(hazeState),

            ) {
            items(20) {it ->
                Column {
                    ItemUpgradeView()
                    Divider()
                }
            }
        }
    }
}

@Composable
fun ResearchPage(hazeState : HazeState){
    val choiceList = arrayListOf<ImageVector>(vectorResource(Res.drawable.ui_icon_missing))

    //Root of Research Page 研究頁面
    Column(modifier = Modifier.fillMaxSize()) {

        LazyVerticalGrid(
            columns = GridCells.Fixed(1),
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer { alpha = 0.99f }
                .hazeSource(hazeState, DefaultZIndex)
        ) {
            items(20) {it ->
                Column {
                    ItemResearchView()
                    Divider()
                }
            }
        }
    }
}

@Composable
fun CurrencyUI(){
    Row(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
    ) {
        //Currency Row 貨幣列
        Row(
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp)
                .wrapContentHeight()
                .fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(shape = CircleShape)
                    .background(Color(0x33FFFFFF))
                    .clickable {
                        navigatorInstance.popBackStackLimited()
                    }
            ) {
                Image(
                    painter = painterResource(Res.drawable.ui_icon_close),
                    contentDescription = "Exit Without Saving",
                    modifier = Modifier.size(24.dp).align(Alignment.Center),
                    colorFilter = ColorFilter.tint(Color.White),
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                verticalArrangement = Arrangement.spacedBy(2.dp),
                horizontalArrangement = Arrangement.spacedBy(2.dp),
                modifier = Modifier.fillMaxWidth().wrapContentHeight().align(Alignment.CenterVertically)
            ) {
                items(IIRCCurrecyType.entries) { it ->
                    //Currency UI 貨幣UI
                    Row{
                        //Currency Icon 貨幣圖標
                        Image(
                            painter = painterResource(resource = it.icon),
                            contentDescription = "Currency Icon",
                            modifier = Modifier.height(32.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            formatDecimalSci(
                                if(it == IIRCCurrecyType.CREDIT) 401000 else 0,
                                decimalPlaces = 2
                            ),
                            modifier = Modifier.align(Alignment.CenterVertically).wrapContentWidth(),
                            style = FontSizeNormal14()+ FontShadow(),
                            color = Color.White ,
                            maxLines = 1,
                        )
                    }
                }
            }
        }
        //Spacer Dash Line 間隔線
        //SpacerDashLine(modifier = Modifier.fillMaxHeight().width(0.5.dp), orientation = LineOrientation.VERTICAL)

        /*//Gift Icon 禮物圖標
        Image(
            painter = painterResource(resource = Res.drawable.ui_icon_gift),
            contentDescription = "Gift Icon",
            modifier = Modifier
                .height(64.dp)
                .aspectRatio(1f)
                .align(Alignment.CenterVertically)
                .padding(8.dp),
            colorFilter = ColorFilter.tint(Color.White),
        )

         */
    }
}

@Composable
fun BottomNavigationBar(selectedItem : MutableState<Int>) {
    val items = listOf(
        NavigationItemData("兌換", vectorResource(resource = Res.drawable.ui_icon_grid)),
        NavigationItemData("升級", vectorResource(resource = Res.drawable.ui_icon_upgrade)),
        NavigationItemData("研究", vectorResource(resource = Res.drawable.ui_icon_research)),
        NavigationItemData("轉讓", vectorResource(resource = Res.drawable.ui_icon_transfer)),
        NavigationItemData("設定", vectorResource(resource = Res.drawable.ui_icon_setting)),
    )

    NavigationBar(
        items = items,
        selectedIndex = selectedItem.value,
        onItemSelected = { selectedItem.value = it }
    )

}
