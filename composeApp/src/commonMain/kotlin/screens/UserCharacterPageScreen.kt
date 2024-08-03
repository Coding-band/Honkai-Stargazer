package screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import com.mohamedrejeb.richeditor.model.rememberRichTextState
import com.mohamedrejeb.richeditor.ui.material3.RichText
import components.HeaderData
import components.PAGE_HEADER_ALPHA_HEIGHT
import components.PageHeaderAlpha
import components.defaultHeaderData
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import files.Eidolon
import files.Res
import files.Superimpose
import files.TraceNormalATK
import files.TraceSkill
import files.TraceTalent
import files.TraceTechnique
import files.TraceUltimate
import files.ic_atk
import files.ic_def
import files.ic_hp
import files.ui_icon_share
import files.ui_icon_star
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.float
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import org.jetbrains.compose.resources.painterResource
import types.Character
import types.Constants
import types.HsrProperties
import types.Lightcone
import types.UserAccount
import utils.AdditionalGreen
import utils.FontSizeNormal12
import utils.FontSizeNormal14
import utils.FontSizeNormal16
import utils.FontSizeNormal20
import utils.FontSizeNormalLarge32
import utils.UtilTools
import utils.annotation.DoItLater
import utils.calculator.getLcAttrData

@DoItLater("Get User Data from Database / API")
@Composable
fun UserCharacterPageScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    headerData: HeaderData = defaultHeaderData,
    backStackEntry: NavBackStackEntry? = null,
) {
    val uid = backStackEntry!!.arguments?.getString("uid")!!
    val userAccount by remember { mutableStateOf(
        if (UserAccount.INSTANCE.uid == uid) {
            UserAccount.INSTANCE
        } else {
            UserAccount.INSTANCE
        }
    ) }
    val characterId = backStackEntry.arguments?.getInt("charId")!!
    val characterFilter = userAccount.characterList.filter { it.officialId == characterId }
    val hazeState = remember { HazeState() }
    val listState = rememberLazyListState()
    val isScrolling by remember {
        derivedStateOf {
            // whatever logic you need
            listState.canScrollBackward
        }
    }
    var character = if(characterFilter.isEmpty()) null else characterFilter[0]

    if(character == null){ navController.popBackStack() }else{
        Box(modifier = modifier.fillMaxSize()){
            CharacterInfoFadeImg(
                fileName = character.registName!!,
                isVisible = !isScrolling //alpha = scrollToAlpha
            )

            LazyColumn(
                state = listState,
                modifier = Modifier.padding(
                    start = Constants.SCREEN_SAVE_PADDING,
                    end = Constants.SCREEN_SAVE_PADDING
                ).haze(hazeState).navigationBarsPadding()
            ) {
                item { Spacer(Modifier.statusBarsPadding().height(PAGE_HEADER_ALPHA_HEIGHT + 240.dp)) }
                item { CharBioSkillInfo(character) }
                item { UserCharPageDivider() }
                item { LightconeInfo(character) }
                item { UserCharPageDivider() }
            }


            PageHeaderAlpha(
                navController = navController,
                onForward = {
                    //TODO : Remember to add the Share Function
                },
                forwardIconId = Res.drawable.ui_icon_share
            ) {
                Column(Modifier.fillMaxSize()) {
                    Text(
                        userAccount.username,
                        modifier = Modifier.align(Alignment.CenterHorizontally).padding(2.dp),
                        style = FontSizeNormal16(),
                        color = Color.White
                    )

                    Text(
                        text = "${userAccount.uid}·${
                            UtilTools().removeStringResDoubleQuotes(
                                userAccount.server.localeName)}",
                        modifier = Modifier.align(Alignment.CenterHorizontally).padding(2.dp)
                            .background(Color(0x4D000000), RoundedCornerShape(49.dp))
                            .clip(RoundedCornerShape(49.dp)).padding(8.dp),
                        style = FontSizeNormal14(),
                        color = Color.White
                    )
                }
            }
        }
    }


}

@Composable
fun CharBioSkillInfo(character: Character) {
    val charStatus = character.characterStatus!!
    Column(Modifier.fillMaxWidth(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = Character.getCharacterItemFromJSON(character.officialId.toString()).displayName!!,
            style = FontSizeNormalLarge32(),
            color = Color.White
        )

        Spacer(modifier = Modifier.size(6.dp))

        Row {
            repeat(character.rarity) {
                Image(
                    modifier = Modifier.size(12.dp, 12.dp),
                    painter = painterResource(Res.drawable.ui_icon_star),
                    contentScale = ContentScale.FillHeight,
                    contentDescription = "Stars to represent Rarity"
                )
            }
        }

        Spacer(modifier = Modifier.size(6.dp))

        Text(
            "Lv ${charStatus.characterLevel} · ${
                UtilTools().removeStringResDoubleQuotes(
                    Res.string.Eidolon
                )
            } ${charStatus.eidolon}",
            modifier = Modifier.align(Alignment.CenterHorizontally).padding(2.dp)
                .background(Color(0x4D000000), RoundedCornerShape(49.dp))
                .clip(RoundedCornerShape(49.dp))
                .padding(top = 4.dp, bottom = 4.dp, start = 8.dp, end = 8.dp),
            style = FontSizeNormal12(),
            color = Color.White
        )

        //CombatType and Path
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(character.path.iconWhite),
                modifier = Modifier.size(24.dp).padding(end = 6.dp)
                    .align(Alignment.CenterVertically),
                contentDescription = "CombatType Icon"
            )
            Text(
                text = UtilTools().removeStringResDoubleQuotes(character.path.resName),
                style = FontSizeNormal16(),
                color = Color.White,
                textAlign = TextAlign.End,
                modifier = Modifier.align(Alignment.CenterVertically)
            )

            Box(modifier = Modifier.width(24.dp))

            Image(
                painter = painterResource(character.combatType.iconColor),
                modifier = Modifier.size(24.dp).padding(end = 6.dp)
                    .align(Alignment.CenterVertically),
                contentDescription = "CombatType Icon"
            )
            Text(
                text = UtilTools().removeStringResDoubleQuotes(character.combatType.resName),
                style = FontSizeNormal16(),
                color = Color.White,
                textAlign = TextAlign.End,
                modifier = Modifier.align(Alignment.CenterVertically)
            )

        }

        Spacer(Modifier.height(8.dp))

        //Skill Level
        val skillLvlList = arrayListOf(
            Res.string.TraceNormalATK to (charStatus.traceBasicAtkLevel),
            Res.string.TraceSkill to (charStatus.traceSkillLevel),
            Res.string.TraceUltimate to (charStatus.traceUltimateLevel),
            Res.string.TraceTalent to (charStatus.traceTalentLevel),
            Res.string.TraceTechnique to (1),
        )

        //Skill Icons
        Row {
            for (skill in skillLvlList) {
                Column(Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                    Image(
                        bitmap = UtilTools().getAssetsWebpByFileName(
                            UtilTools.ImageFolderType.CHAR_SKILL,
                            "${UtilTools().getImageNameByRegistName(character.registName!!)}_skill" + when(skill.first) {
                                Res.string.TraceNormalATK -> "1"
                                Res.string.TraceSkill -> "2"
                                Res.string.TraceUltimate -> "3"
                                Res.string.TraceTalent -> "4"
                                Res.string.TraceTechnique -> "6"
                                else -> "1"
                            }),
                        contentDescription = "Skill Icon",
                        modifier = Modifier.size(36.dp)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        modifier = Modifier.padding(top = 2.dp),
                        text = UtilTools().removeStringResDoubleQuotes(skill.first),
                        style = FontSizeNormal14(),
                        color = Color.White
                    )
                    Text(
                        text = "Lv ${skill.second}",
                        style = FontSizeNormal14(),
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }

        //Status Info
        val showFullStatus = remember { mutableStateOf(false) }
        if(charStatus.characterProperties != null){
            val statusList = charStatus.characterProperties!!.filter { it.valueFinal > 0 }
            Column(Modifier.clickable {
                showFullStatus.value = !showFullStatus.value
            }) {
                for ((index, status) in statusList.withIndex()){
                    if(showFullStatus.value){
                        StatusFullUI(status)
                    }else if (index % 5 == 0) {
                        Row(
                            Modifier.fillMaxWidth().wrapContentHeight(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            for (i in 0 until 5) {
                                if (index + i < statusList.size) {
                                    StatusShortUI(statusList[index + i])
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun LightconeInfo(character: Character){
    val charStatus = character.characterStatus!!
    val isLightconeShowDesc = remember { mutableStateOf(false) }
    if (charStatus.equippingLightcone != null && charStatus.equippingLightcone!!.registName != null) {
        val lightcone = charStatus.equippingLightcone!!
        val lcInfoJson = Lightcone.getLightconeDataFromJSON(lightcone.fileName!!)
        Row(Modifier.fillMaxWidth().wrapContentHeight().clickable { isLightconeShowDesc.value = !isLightconeShowDesc.value }) {
            Box(Modifier.requiredHeight(120.dp).weight(0.4f), contentAlignment = Alignment.Center) {
                Box(Modifier.rotate(if (isLightconeShowDesc.value) 0f else 5f).padding(start = 8.dp , end = 8.dp)) {
                    val context = LocalPlatformContext.current
                    val imageRequest = remember { UtilTools().newImageRequest(
                        context = context,
                        data = UtilTools().getAssetsWebpByteArrayByFileName(
                            UtilTools.ImageFolderType.LC_ARTWORK,
                            UtilTools().getImageNameByRegistName(lightcone.registName!!)
                        ))
                    }
                    val imageLoader = remember { UtilTools().newImageLoader(context) }
                    AsyncImage(
                        model = imageRequest,
                        contentDescription = "Lightcone Image",
                        modifier = Modifier.height(if (isLightconeShowDesc.value) 100.dp else 112.dp).requiredWidth(72.dp).wrapContentWidth()
                            .border(4.dp, Color.White, RectangleShape).align(
                            Alignment.Center
                        ),
                        contentScale = ContentScale.Fit,
                        imageLoader = imageLoader
                    )
                }
            }

            Spacer(Modifier.width(16.dp))

            Column (Modifier.weight(0.6f)){
                Text(
                    text = lightcone.displayName!!,
                    modifier = Modifier.padding(2.dp),
                    style = FontSizeNormal20(),
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                )
                Text(
                    text = "Lv ${lightcone.level} · ${
                        UtilTools().removeStringResDoubleQuotes(
                            Res.string.Superimpose
                        ).replace("$"+"{1}",lightcone.superimposition.toString())
                    }",
                    modifier = Modifier.padding(2.dp)
                        .background(Color(0x4D000000), RoundedCornerShape(49.dp))
                        .clip(RoundedCornerShape(49.dp)).padding(4.dp),
                    style = FontSizeNormal12(),
                    color = Color.White
                )

                if(isLightconeShowDesc.value && lcInfoJson.jsonObject["skill"] != null){
                    val richTextState = rememberRichTextState()
                    richTextState.setHtml(getLightconeMetaInfo(lightcone, lcInfoJson, lightcone.superimposition))

                    RichText(richTextState, color = Color(0xFFFFFFFF), style = FontSizeNormal14())
                }else{
                    Row {
                        repeat(lightcone.rarity) {
                            Image(
                                modifier = Modifier.size(12.dp, 12.dp),
                                painter = painterResource(Res.drawable.ui_icon_star),
                                contentScale = ContentScale.FillHeight,
                                contentDescription = "Stars to represent Rarity"
                            )
                        }
                    }

                    Row{
                        Image(
                            painter = painterResource(character.path.iconWhite),
                            modifier = Modifier.size(24.dp).padding(end = 6.dp)
                                .align(Alignment.CenterVertically),
                            contentDescription = "CombatType Icon"
                        )
                        Text(
                            text = UtilTools().removeStringResDoubleQuotes(character.path.resName),
                            style = FontSizeNormal16(),
                            color = Color.White,
                            textAlign = TextAlign.End,
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                    }

                    //Get Lightcone Status
                    val lcAttrData = getLcAttrData(lcInfoJson, lightcone.level)
                    val lcAttrDataArr = arrayOf(Res.drawable.ic_hp to lcAttrData.hp, Res.drawable.ic_atk to lcAttrData.atk, Res.drawable.ic_def to lcAttrData.def)
                    Row {
                        for ((index, attr) in lcAttrDataArr.withIndex()){
                            Row(
                                Modifier.wrapContentWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Image(
                                    painter = painterResource(attr.first),
                                    contentDescription = "Attribute Icon",
                                    modifier = Modifier.size(24.dp)
                                )
                                Text(
                                    text = UtilTools().formatDecimal(attr.second, 0),
                                    style = FontSizeNormal14(),
                                    color = Color.White,
                                    maxLines = 1
                                )
                                if(index < lcAttrDataArr.size - 1){
                                    Spacer(Modifier.width(8.dp))
                                }
                            }
                        }
                    }
                }
            }

        }
    }
}

fun getLightconeMetaInfo(lightcone: Lightcone, lcInfoJson: JsonElement, metaLv: Int): String {
    val paramsList : ArrayList<Float> = arrayListOf()
    val levelData = lcInfoJson.jsonObject["skill"]!!.jsonObject["levelData"]
    if (levelData != null && levelData.jsonArray.size >= (metaLv)){
        for(param in levelData.jsonArray[metaLv -1].jsonObject["params"]!!.jsonArray){
            paramsList.add(param.jsonPrimitive.float)
        }
    }

    return UtilTools().htmlDescApplier(
        lcInfoJson.jsonObject["skill"]!!.jsonObject["descHash"]!!.jsonPrimitive.content,
        paramsList
    )
}

@Composable
fun UserCharPageDivider() {
    Box(Modifier.fillMaxWidth().padding(top = 12.dp, bottom = 12.dp), contentAlignment = Alignment.Center) {
        Box(modifier = Modifier.fillMaxWidth(0.33f).height(2.dp).background(Color(0x66F3F9FF)),)
    }
}

@Composable
fun StatusShortUI(status : HsrProperties){
    Row(
        Modifier.wrapContentWidth().padding(4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(status.attributeExchange.attribute.iconWhite),
            contentDescription = "Attribute Icon",
            modifier = Modifier.size(24.dp)
        )
        Text(
            text = UtilTools().formatDecimal(
                status.valueFinal * if (status.attributeExchange.isPercent) 100 else 1,
                if (status.attributeExchange.key == "spd") 1 else if (status.attributeExchange.isPercent) 1 else 0
            ) + if (status.attributeExchange.isPercent) "%" else "",
            style = FontSizeNormal14(),
            color = Color.White,
            maxLines = 1
        )
    }
}
@Composable
fun StatusFullUI(status : HsrProperties){
    Row(Modifier.fillMaxWidth().wrapContentHeight().padding(top = 4.dp, bottom = 4.dp)) {
        Image(
            painter = painterResource(status.attributeExchange.attribute.iconWhite),
            contentDescription = "Attribute Icon",
            modifier = Modifier.size(24.dp)
        )
        Spacer(Modifier.width(8.dp))
        Text(
            text = UtilTools().removeStringResDoubleQuotes(status.attributeExchange.attribute.resName),
            style = FontSizeNormal14(),
            color = Color.White,
            maxLines = 1
        )

        Spacer(Modifier.weight(1f))

        if(status.valueBase > 0f){
            Text(
                text = UtilTools().formatDecimal(
                    status.valueBase * if (status.attributeExchange.isPercent) 100 else 1,
                    if (status.attributeExchange.key == "spd") 1 else if (status.attributeExchange.isPercent) 1 else 0
                ) + if (status.attributeExchange.isPercent) "%" else "",
                style = FontSizeNormal14(),
                color = Color.White,
                maxLines = 1
            )
        }

        if(status.valueAdd > 0f && status.valueBase > 0f){
            Text(
                text = "+",
                style = FontSizeNormal14(),
                color = Color.White,
                maxLines = 1,
                modifier = Modifier.padding(end = 4.dp, start = 4.dp)
            )
        }

        if(status.valueAdd > 0f){
            Text(
                text = UtilTools().formatDecimal(
                    status.valueAdd * if (status.attributeExchange.isPercent) 100 else 1,
                    if (status.attributeExchange.key == "spd") 1 else if (status.attributeExchange.isPercent) 1 else 0
                ) + if (status.attributeExchange.isPercent) "%" else "",
                style = FontSizeNormal14(),
                color = AdditionalGreen,
                maxLines = 1
            )
        }
    }
}

@Composable
fun CharacterInfoFadeImg(
    modifier: Modifier = Modifier,
    fileName: String,
    isVisible: Boolean = true
) {
    Box(modifier = Modifier.fillMaxSize()) {
        AnimatedVisibility(
            visible = isVisible,
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier.fillMaxWidth().wrapContentHeight().align(Alignment.TopCenter)
        ) {
            AsyncImage(
                model = UtilTools().newImageRequest(context = LocalPlatformContext.current, data = Character.getCharacterImageByteArrayFromFileName(
                    UtilTools.ImageFolderType.CHAR_FADE, fileName
                )),
                contentDescription = "Character Full Image",
                contentScale = ContentScale.Fit,
                imageLoader = UtilTools().newImageLoader(LocalPlatformContext.current)
            )
        }
        Box(
            modifier = Modifier.fillMaxWidth().fillMaxHeight(0.5f).background(
                Brush.verticalGradient(
                    colors = listOf(Color(0x00000000), Color(0xCC000000))
                )
            ).align(Alignment.BottomCenter),
        )

    }
}
