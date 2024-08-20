package components

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.voc.honkai_stargazer.component.CharacterCard
import dev.chrisbanes.haze.HazeState
import files.MOCSkipped
import files.PlayersRounds
import files.Res
import files.ic_moc_star
import org.jetbrains.compose.resources.painterResource
import types.AbyssInfoType
import types.Character
import types.CharacterStatus
import types.UserAbyssRecordData
import utils.FontSizeNormal12
import utils.FontSizeNormal14
import utils.FontSizeNormal16
import utils.UtilTools
import utils.replaceStrRes

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
                        text = "${title}·${UtilTools().getMocPhaseStrByIndex((data[0].floor) - 1)}",
                        color = Color.White,
                        style = FontSizeNormal16()
                    )

                    //Remaining Rounds
                    Text(
                        text = if(data[0].isFastPass) {
                            UtilTools().removeStringResDoubleQuotes(Res.string.MOCSkipped)
                        } else {
                            UtilTools().removeStringResDoubleQuotes(Res.string.PlayersRounds).replaceStrRes(
                                data[0].roundUsed.toString(),
                            )
                        },
                        color = Color(0xCCFFFFFF),
                        style = FontSizeNormal12()
                    )
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
                Text(
                    text = data[0].recordTime,
                    color = Color.White,
                    style = FontSizeNormal12()
                )
                Spacer(Modifier.weight(1f))

                val score1 = if (data.isNotEmpty()) data[0].score else 0
                val score2 = if(data.size > 1) data[1].score else 0
                if(score1 != -1 || score2 != -1) {
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
                val charList = data[0].charList

                Column(Modifier.fillMaxWidth().wrapContentHeight()) {
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

                    if(it == 0 && !data[0].isFastPass) {
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