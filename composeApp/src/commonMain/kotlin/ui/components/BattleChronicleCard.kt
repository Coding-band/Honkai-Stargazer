package ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import dev.chrisbanes.haze.HazeState
import files.MOCSkipped
import files.PlayersRounds
import files.Res
import files.ic_moc_star
import org.jetbrains.compose.resources.painterResource
import types.AbyssInfo
import types.AbyssInfoList.Companion.getAbyssInfoFileNameById
import types.AbyssInfoType
import types.Character
import types.CharacterStatus
import types.UserAbyssRecordData
import utils.app.FontSizeNormal12
import utils.app.FontSizeNormal14
import utils.app.FontSizeNormal16
import utils.app.Language.Companion.TextLanguageInstance
import utils.app.getAAPhaseStrByIndex
import utils.app.getMocPhaseStrByIndex
import utils.app.newImageRequest
import utils.app.removeStrQuote
import utils.app.replaceStrRes
import utils.starbase.StarbaseAPI

@Composable
fun BattleChronicleCard(
    modifier: Modifier = Modifier,
    hazeState: HazeState = remember { HazeState() },
    data: List<UserAbyssRecordData>,
    type: AbyssInfoType,
    title: String? = null,
    isSelected : Boolean? = null,
    //asData: UserASRecord? = null,
) {
    val context = LocalPlatformContext.current
    Column(
        modifier
            .border(1.dp, Color(0x66DDDDDD), shape = RoundedCornerShape(4.dp))
            .clip(RoundedCornerShape(4.dp))
            .background(Brush.linearGradient(
                colors = listOf(Color(0xFF000000), Color(0x00000000)),
                start = Offset(50.0f, 0.0f),
                end = Offset(50.0f, Float.POSITIVE_INFINITY))
            )
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Column(Modifier.padding(12.dp)) {
            // Header : Phase, Remaining Rounds, Star
            Row {
                Column {
                    //Phase
                    Text(
                        text = when(type){
                                AbyssInfoType.AnomalyArbitration -> "${title}·${getAAPhaseStrByIndex((data[0].floor) - 1)}"
                                else -> "${title}·${getMocPhaseStrByIndex((data[0].floor) - 1)}"
                            },
                        color = Color.White,
                        style = FontSizeNormal16()
                    )

                    //Remaining Rounds
                    if(data[0].roundUsed != -1 || data[0].isFastPass) {
                        Text(
                            text = if(data[0].isFastPass) {
                                removeStrQuote(Res.string.MOCSkipped)
                            } else {
                                removeStrQuote(Res.string.PlayersRounds).replaceStrRes(
                                    data[0].roundUsed.toString(),
                                )
                            },
                            color = Color(0xCCFFFFFF),
                            style = FontSizeNormal12()
                        )
                    }
                }

                Spacer(Modifier.weight(1f))

                //Star
                Row {
                    repeat(data[0].star) {
                        Image(
                            painter = painterResource(Res.drawable.ic_moc_star),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp).aspectRatio(1f)
                        )
                        if(it < data[0].star - 1) {
                            Spacer(Modifier.width(8.dp))
                        }
                    }
                }


            }

            Spacer(Modifier.height(4.dp))
            //DateTime & Score
            Row {
                if(data[0].recordTime != null){
                    Text(
                        text = data[0].recordTime!!,
                        color = Color.White,
                        style = FontSizeNormal12()
                    )
                }
                Spacer(Modifier.weight(1f))

                val score1 = if (data.isNotEmpty()) data[0].score else 0
                val score2 = if(data.size > 1) data[1].score else 0
                if(score1 != -1 || score2 != -1 && data.size > 1){
                    Text(
                        text = (score1 + score2).toString(),
                        color = Color(0xFFDD8200),
                        style = FontSizeNormal14()
                    )
                }
            }
            Spacer(Modifier.height(6.dp))

            //node 1 & node 2
            repeat(2){
                //fix: Display the node 1 twice
                if(it >= data.size) return@repeat
                val charList = data[it].charList
                Column(Modifier.fillMaxWidth().wrapContentHeight()) {

                    val buffIcon = remember { mutableStateOf("---") }
                    val buffName = remember { mutableStateOf("---") }
                    val buffDesc = remember { mutableStateOf("---") }

                    val buffData = remember(data[it].id) {
                        mutableStateOf<AbyssInfo?>(null)
                    }

                    LaunchedEffect(data[it].id) {
                        buffData.value = AbyssInfo.getAbyssItemById(data[it].id, type, getAbyssInfoFileNameById(data[it].id, type))
                    }

                    buffData.value?.let { bdata ->
                        val buffItem = bdata.buffList.find { buff -> buff.buffId == data[it].buffId.toString() }
                        buffIcon.value = (StarbaseAPI().getGitHubStaticAssetURL() + "/images/buff_icons/${buffItem?.buffIcon}.webp")
                        buffName.value = buffItem?.nameList?.get(TextLanguageInstance) ?: "???"
                        buffDesc.value = buffItem?.descList?.get(TextLanguageInstance) ?: "???"
                    }

                    // Buff Icon & Description
                    if(buffName.value != "---" && buffName.value != "???"){
                        Row {
                            // Buff Icon
                            AsyncImage(
                                model = newImageRequest(context = context, buffIcon.value),
                                contentDescription = null,
                                modifier = Modifier.size(36.dp).padding(4.dp).background(Color(0xCC000000), CircleShape).border(1.dp, Color.White, CircleShape)
                            )

                            // Buff Name & Description
                            Column {
                                Text(
                                    text = buffName.value,
                                    color = Color.White,
                                    style = FontSizeNormal14(),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Text(
                                    text = buffDesc.value,
                                    color = Color(0xCCFFFFFF),
                                    style = FontSizeNormal12(),
                                    maxLines = 3,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }
                        Spacer(Modifier.height(8.dp))
                    }

                    // Character Display
                    NonLazyGrid(
                        columns = 4,
                        itemCount = charList.size ?: 0,
                        horizontalSpaceBetween = 6.dp
                    ){charIndex ->
                        val character = Character.getCharacterItemFromJSON((charList[charIndex].charId.toString()))
                        character.characterStatus = CharacterStatus(
                            eidolon = charList[charIndex].charEidolon,
                            characterLevel = charList[charIndex].charLevel,
                        )
                        CharacterCard(
                            character = character,
                            isDisplayLevel = true,
                            isDisplayName = false
                        )
                    }

                    if(it == 0 && !data[0].isFastPass && data.size >1 ) {
                        Spacer(Modifier.height(4.dp))
                        //Divider
                        Box(Modifier.fillMaxWidth().padding(top = 12.dp, bottom = 12.dp), contentAlignment = Alignment.Center) {
                            Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(Color(0x66F3F9FF)))
                        }
                        Spacer(Modifier.height(4.dp))
                    }
                }
            }
        }
    }
}