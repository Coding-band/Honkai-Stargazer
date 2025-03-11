package com.voc.idle.irc.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import files.Res
import files.element_fire
import files.ic_fire
import files.pom_pom
import org.jetbrains.compose.resources.painterResource
import utils.app.FontShadow
import utils.app.FontSizeNormal12
import utils.app.FontSizeNormal14
import utils.app.FontSizeNormal16

@Composable
fun ItemGridView(currHave : Int = 0, ableToBuy : Int = 0){
    Box(
        modifier = Modifier
            .background(Color.Black, RoundedCornerShape(12.dp))
            .border(
                1.dp,
                if (ableToBuy > 0) Color.White else Color.Transparent,
                RoundedCornerShape(12.dp)
            )
            .clip(RoundedCornerShape(12.dp))
            .aspectRatio(1f)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(),
                onClick = {}
            )
    ){
        //Image of that item 物品圖片
        Image(
            painter = painterResource(resource = Res.drawable.pom_pom),
            contentDescription = "Item Image",
            modifier = Modifier
                .fillMaxSize()
        )

        //Column with item info 物品資訊
        Column (modifier = Modifier
            .fillMaxSize()
            .padding(4.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            //Item name 物品名稱
            Text(
                "鐵礦",
                color = Color.White,
                modifier = Modifier.fillMaxWidth(),
                style = FontSizeNormal14() ,
                textAlign = TextAlign.Center,

                )
            // Item amount 物品數量
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    "x${currHave}",
                    color = Color.White,
                    style = FontSizeNormal12() ,
                    textAlign = TextAlign.Center,
                )

                if(ableToBuy > 0){
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                        "+${ableToBuy}",
                        color = Color.Green,
                        style = FontSizeNormal12() ,
                        textAlign = TextAlign.Start
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            //Item provided currency count 物品提供的貨幣數量
            Row(modifier = Modifier.padding(start = 4.dp, end = 4.dp)) {
                Image(
                    painter = painterResource(resource = Res.drawable.element_fire),
                    contentDescription = "Currency Icon",
                    modifier = Modifier
                        .height(16.dp)
                        .align(Alignment.CenterVertically)
                )
                Spacer(modifier = Modifier.width(2.dp))
                Text(
                    "+123.45K/s",
                    color = Color.White,
                    modifier = Modifier.wrapContentWidth(),
                    style = FontSizeNormal12() ,
                )
            }
        }
    }
}

@Composable
fun ItemUpgradeView(){
    Row(
        modifier = Modifier.fillMaxWidth()
            .wrapContentHeight()
            .fillMaxWidth()
            .clickable {  }
    ) {
        //Image of that item 物品圖片
        Box(
            modifier = Modifier
                .height(64.dp)
                .aspectRatio(1f)
                .padding(8.dp)
                .align(Alignment.CenterVertically)
                .clip(RoundedCornerShape(8.dp))
        ) {
            //Image of that item 物品圖片
            Image(
                painter = painterResource(resource = Res.drawable.pom_pom),
                contentDescription = "Item Image",
                modifier = Modifier
                    .height(64.dp)
                    .aspectRatio(1f)
            )

            //Image of the currency icon 貨幣圖標
            Image(
                painter = painterResource(resource = Res.drawable.element_fire),
                contentDescription = "Upgrade Icon",
                modifier = Modifier
                    .height(24.dp)
                    .aspectRatio(1f)
                    .align(Alignment.BottomEnd)
            )
        }

        Spacer(modifier = Modifier.width(4.dp))

        //Column with item info 物品資訊
        Row(modifier = Modifier.fillMaxWidth().wrapContentHeight().padding(top = 4.dp, bottom = 4.dp)) {
            Column(Modifier.wrapContentWidth().wrapContentHeight().padding(start = 8.dp).align(Alignment.CenterVertically)) {
                Text(
                    text = "鐵礦",
                    color = Color.White,
                    modifier = Modifier.wrapContentWidth().wrapContentHeight(),
                    style = FontSizeNormal16() ,
                    textAlign = TextAlign.Start,
                )
                Text(
                    text = "物品收益 +25%",
                    color = Color.Green,
                    modifier = Modifier.wrapContentWidth().wrapContentHeight().padding(start = 8.dp),
                    style = FontSizeNormal14() ,
                    textAlign = TextAlign.Start,
                )
                Text(
                    text = "當前等級: 10 (+250%)",
                    color = Color.LightGray,
                    modifier = Modifier.wrapContentWidth().wrapContentHeight().padding(start = 8.dp),
                    style = FontSizeNormal14() ,
                    textAlign = TextAlign.Start,
                )
            }

            Spacer(modifier = Modifier.height(1.dp).fillMaxWidth().weight(1f))

            //Upgrade Requirement 升級需求
            Column(modifier = Modifier.wrapContentWidth().wrapContentHeight().padding(start = 8.dp).align(Alignment.CenterVertically)) {
                Row(modifier = Modifier.wrapContentWidth().wrapContentHeight()) {
                    Image(
                        painter = painterResource(resource = Res.drawable.ic_fire),
                        contentDescription = "Currency Icon",
                        modifier = Modifier
                            .height(16.dp)
                            .align(Alignment.CenterVertically)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "100.00M",
                        color = Color.White,
                        modifier = Modifier.wrapContentWidth().wrapContentHeight(),
                        style = FontSizeNormal14() ,
                        textAlign = TextAlign.Start,
                    )
                }
            }

            Spacer(modifier = Modifier.width(8.dp))
        }
    }
}


@Composable
fun ItemResearchView(){
    Row(
        modifier = Modifier.fillMaxWidth()
            .wrapContentHeight()
            .fillMaxWidth()
            .clickable {  }
    ) {
        //Image of that item 物品圖片
        Box(
            modifier = Modifier
                .height(64.dp)
                .aspectRatio(1f)
                .padding(8.dp)
                .align(Alignment.CenterVertically)
                .clip(RoundedCornerShape(8.dp))
        ) {
            //Image of that item 物品圖片
            Image(
                painter = painterResource(resource = Res.drawable.pom_pom),
                contentDescription = "Item Image",
                modifier = Modifier
                    .height(64.dp)
                    .aspectRatio(1f)
            )

            //Image of the currency icon 貨幣圖標
            Image(
                painter = painterResource(resource = Res.drawable.ic_fire),
                contentDescription = "Upgrade Icon",
                modifier = Modifier
                    .height(24.dp)
                    .aspectRatio(1f)
                    .align(Alignment.BottomEnd)
            )
        }

        Spacer(modifier = Modifier.width(4.dp))

        //Requirement of Unlocking That item 解鎖物品的需求
        Column (modifier = Modifier.fillMaxWidth().wrapContentHeight().padding(top = 4.dp, bottom = 4.dp)) {
            Text(
                text = "解鎖條件：",
                color = Color.White,
                modifier = Modifier.fillMaxWidth(),
                style = FontSizeNormal16() ,
                textAlign = TextAlign.Start,
            )

            //Row of requirements 需求行
            LazyRow(modifier = Modifier.fillMaxHeight().fillMaxWidth()) {
                items(4){
                    Column(modifier = Modifier.wrapContentWidth().defaultMinSize(48.dp,48.dp)) {
                        //Image of that item 物品圖片
                        Image(
                            painter = painterResource(resource = Res.drawable.pom_pom),
                            contentDescription = "Item Image",
                            modifier = Modifier
                                .size(32.dp)
                                .aspectRatio(1f)
                                .align(Alignment.CenterHorizontally)
                        )

                        Text(
                            text = "100",
                            color = Color.Gray,
                            modifier = Modifier.wrapContentSize().align(Alignment.CenterHorizontally),
                            style = FontSizeNormal12() ,
                            textAlign = TextAlign.Center,
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.width(8.dp))
        }
    }
}

