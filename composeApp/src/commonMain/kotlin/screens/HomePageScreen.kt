/*
 * Project Honkai Stargazer and app Stargazer (星穹觀星者) were
 * Organized & Develop by Coding Band.
 * Copyright © 2024 Coding Band 版權所有
 */

package screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.IconButton
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import components.HeaderData
import components.HomePageBlock1x1
import components.HomePageBlock2x1
import components.HomePageBlocks
import components.defaultHeaderData
import files.Res
import files.donate_ad_bg
import files.ic_rounded_option_btn
import files.test_avatar
import files.test_char_1
import files.test_char_2
import files.test_char_3
import files.test_char_4
import files.test_char_5
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import types.Constants.Companion.HOME_PAGE_ITEMS
import utils.BlackAlpha30
import utils.FontSizeNormal
import utils.FontSizeNormalLarge
import utils.ProgressLevelBackground
import utils.ProgressLevelPrimary
import utils.TextColorLevel
import utils.TextColorNormal
import utils.checkHasErrorLogFromLastCrash


@Composable
fun HomePage(modifier: Modifier = Modifier, navController: NavController, headerData: HeaderData = defaultHeaderData) {
    Box(modifier = Modifier
        .statusBarsPadding()
    ) {
        checkHasErrorLogFromLastCrash()

        Column {
            HomePageHeader(navController = navController)
            HomePageMenuScrollView(navController = navController)
        }
    }
}

@Composable
fun UserHelpTeamIcon(icon: Painter, modifier: Modifier = Modifier,navController: NavController) {
    Image(
        painter = icon, contentDescription = "", contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(30.dp)
            .background(Color(0xFFD9D9D9), CircleShape)
            .clip(CircleShape)
            .border(1.5.dp, Color(0xFFD3D3D3), CircleShape)
            .padding()
    )
    Spacer(modifier = Modifier.width(4.dp))
}

@Composable
fun HomePageHeader(modifier: Modifier = Modifier, navController: NavController) {
    Box(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, top = 4.dp)
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Column {
            Text(
                text = "900033852",
                modifier = Modifier
                    .background(BlackAlpha30, CircleShape)
                    .padding(all = 8.dp)
                    .wrapContentSize(),
                color = TextColorNormal,
                style = FontSizeNormal(),
            )
            Spacer(modifier = Modifier.height(12.dp))
            Box {
                Row(Modifier.height(72.dp)) {
                    // User Avatar
                    Image(
                        painterResource(Res.drawable.test_avatar),
                        contentDescription = "",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(72.dp)
                            .background(Color(0xFFCAB89E), CircleShape)
                            .clip(CircleShape)
                            .border(1.dp, Color(0x66907C54), CircleShape)
                    )
                    // User Name & Helping Team
                    Column(
                        modifier = Modifier
                            .padding(start = 8.dp, end = 8.dp)
                            .weight(1f)
                    ) {
                        //User Name - Hmm interesting Kt
                        Text(
                            text = "Vocaloid2048",
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxSize()
                                .wrapContentHeight(align = Alignment.CenterVertically),
                            color = TextColorNormal,
                            style = FontSizeNormalLarge(),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                        //Helping Team
                        Row(
                            modifier = Modifier
                                .height(30.dp)
                                .fillMaxWidth()
                        ) {
                            UserHelpTeamIcon(painterResource(resource = Res.drawable.test_char_1), navController = navController)
                            UserHelpTeamIcon(painterResource(resource = Res.drawable.test_char_2), navController = navController)
                            UserHelpTeamIcon(painterResource(resource = Res.drawable.test_char_3), navController = navController)
                            UserHelpTeamIcon(painterResource(resource = Res.drawable.test_char_4), navController = navController)
                            UserHelpTeamIcon(painterResource(resource = Res.drawable.test_char_5), navController = navController)
                        }
                    }
                    Column(
                        Modifier
                            .padding(top = 12.dp)
                            .fillMaxHeight(), horizontalAlignment = Alignment.End
                    ) {
                        IconButton(
                            modifier = Modifier
                                .height(23.dp)
                                .width(45.dp),
                            onClick = {

                            }
                        ) {
                            Image(painterResource(resource = Res.drawable.ic_rounded_option_btn), "")
                        }

                        Spacer(Modifier.weight(1f))

                        Text(
                            text = "開拓等級 58",
                            color = TextColorLevel,
                            style = FontSizeNormal(),
                            //fontWeight = FontWeight.Bold
                        )
                    }
                }

            }
            LinearProgressIndicator(
                progress = (58f / 60f),
                Modifier
                    .padding(top = 12.dp, bottom = 12.dp)
                    .fillMaxWidth(),
                backgroundColor = ProgressLevelBackground,
                color = ProgressLevelPrimary,
            )
        }
    }
}


@Composable
fun HomePageMenuScrollView(modifier: Modifier = Modifier, navController: NavController) {
    Column {
        LazyVerticalGrid(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp)
                .weight(1f),
            columns = GridCells.Adaptive(80.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            var finalBlockData = HOME_PAGE_ITEMS;
            items(count = HOME_PAGE_ITEMS.size, span = { index ->
                if(finalBlockData[index].itemType.width > (maxCurrentLineSpan - ((index+1) % maxCurrentLineSpan))){
                    val tmpExchange = finalBlockData[index]
                    finalBlockData[index] = finalBlockData[index+1]
                    finalBlockData[index+1] = tmpExchange
                }
                when (finalBlockData[index].itemType) {
                    HomePageBlocks.HomePageBlockItem.HomePageBlockItemType.W1H1 -> GridItemSpan(1)
                    HomePageBlocks.HomePageBlockItem.HomePageBlockItemType.W2H1 -> GridItemSpan(2)
                }
            }) { index ->
                var blockData: HomePageBlocks.HomePageBlockItem = finalBlockData[index];
                Box(Modifier.layoutId("HomePageItemBox")){
                    when (blockData.itemType) {
                        HomePageBlocks.HomePageBlockItem.HomePageBlockItemType.W1H1 -> HomePageBlock1x1(blockData,navController = navController)
                        HomePageBlocks.HomePageBlockItem.HomePageBlockItemType.W2H1 -> HomePageBlock2x1(blockData,navController = navController)
                    }
                }
            }
        }
        BottomView()
    }
}

@Composable
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
    }
}

@Preview
@Composable
fun HomePagePreview() {
    //RootContent(screen = Screen.HomePage, navController = rememberNavController(), page = { HomePage(navController = rememberNavController())})
}