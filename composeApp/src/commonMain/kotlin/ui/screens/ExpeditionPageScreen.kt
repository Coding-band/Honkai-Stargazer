package ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.multiplatform.webview.web.WebView
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeChild
import files.IsDone
import files.NoDataYet
import files.Res
import files.StatusDays
import files.StatusHours
import files.StatusMinutes
import moe.tlaster.precompose.navigation.Navigator
import types.UserAccount
import types.UserAccount.Companion.INSTANCE
import types.UserExpedition
import ui.components.BackIcon
import ui.components.HeaderData
import ui.components.PAGE_HEADER_HEIGHT
import ui.components.PageHeader
import ui.components.ThemedProgressBar
import ui.components.defaultHeaderData
import utils.app.FontSizeNormal14
import utils.app.FontSizeNormal16
import utils.app.getFinishTimeStr
import utils.app.getRemainingTimeStr
import utils.app.removeStrQuote

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ExpeditionPage(
    modifier: Modifier = Modifier,
    navigator: Navigator,
    headerData: HeaderData = defaultHeaderData
) {
    val hazeState = remember { HazeState() }
    /*
    val pullRefreshState = rememberPullRefreshState(
        onRefresh = {
            UserAccount.refreshNoteData()
        },
        refreshing = false
    )

     */
    Box(modifier = Modifier.fillMaxSize()) {

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = PAGE_HEADER_HEIGHT)
                .statusBarsPadding()
                .navigationBarsPadding(),
            contentPadding = PaddingValues(12.dp),

        ) {
            INSTANCE.userNote.expedition.forEach { expendition ->
                item {
                    ExpenditionItem(expendition, hazeState)
                }
            }
        }

        PageHeader(
            navigator = navigator,
            headerData = headerData,
            hazeState = hazeState,
            backIconId = BackIcon.BACK,
        )
    }
}

@Composable
fun ExpenditionItem(expendition: UserExpedition, hazeState: HazeState) {
    //Frame & Background
    Box(modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight()
        .background(Color(0x66F3F9FF))
        .clip(shape = RoundedCornerShape(4.dp, 20.dp, 4.dp, 4.dp))
        .hazeChild(hazeState)
    ) {
        //Content
        Column {
            //ExpendInfo
            Row(modifier = Modifier
                .padding(12.dp)
            ) {
                //Expendition's Material Target's Icon
                AsyncImage(
                    model = expendition.materialUrl,
                    modifier = Modifier
                        .padding(4.dp)
                        .background(Color(0x66FFFFFF))
                        .clip(shape = CircleShape)
                        .aspectRatio(1f)
                        .widthIn(40.dp, 80.dp),
                    contentDescription = null,
                )

                Spacer(modifier = Modifier.width(8.dp))

                //Expendition's Material Target's Name & Time Remaining
                Column(modifier = Modifier.weight(1f)) {
                    //Expendition's Target's Name
                    Text(
                        text = expendition.materialName,
                        color = Color(0xFF222222),
                        style = FontSizeNormal14(),
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    //Expendition's Time Remaining
                    Text(
                        text = if(expendition.remainingTime > 0){ getRemainingTimeStr(expendition.remainingTime) } else {
                            if (expendition.expeditionCharacterIcon.isEmpty()){
                                removeStrQuote(Res.string.NoDataYet)
                            } else {
                                removeStrQuote(Res.string.IsDone)
                            }
                        },
                        color = Color(0xFF222222),
                        style = FontSizeNormal16(),
                    )
                }

                //Character(s)
                Column {
                    expendition.expeditionCharacterIcon.forEach { characterIcon ->
                        AsyncImage(
                            model = characterIcon,
                            modifier = Modifier
                                .padding(4.dp)
                                .clip(shape = CircleShape)
                                .aspectRatio(1f)
                                .widthIn(40.dp, 80.dp),
                            contentDescription = null,
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        //Time When Ends
                        Text(
                            text = if(expendition.remainingTime > 0){ getFinishTimeStr(expendition.remainingTime) } else {
                                ""
                            },
                            color = Color(0xFF222222),
                            style = FontSizeNormal14(),
                        )

                    }
                }

                //ProgressBar
                ThemedProgressBar(
                    progress = expendition.remainingTime,
                    max = 20 * 60 * 60,
                )
            }
        }
    }
}
