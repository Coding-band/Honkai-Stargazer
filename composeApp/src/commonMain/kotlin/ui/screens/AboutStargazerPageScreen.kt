package ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Chip
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeSource
import files.AboutTheApp
import files.Res
import files._2O48
import files.phorphos_discord_logo_regular
import files.phorphos_film_slate_fill
import files.phorphos_github_logo_regular
import files.vocaloid2048
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import ui.components.BackIcon
import ui.components.HeaderData
import ui.components.PAGE_HEADER_HEIGHT
import ui.components.PageHeader
import ui.navigation.urlHandler
import utils.app.Constants
import utils.app.DefaultZIndex
import utils.app.FontSizeNormal12
import utils.app.FontSizeNormal14
import utils.app.FontSizeNormal16
import utils.app.FontSizeNormal20
import utils.app.GradReachYellow
import utils.app.LongStringXML
import utils.app.newImageRequest
import utils.app.removeStrQuote
import utils.starbase.StarbaseAPI

@Composable
fun AboutStargazerPageScreen(
    navigator: NavHostController,
    hazeState: HazeState,
    pageHeader: MutableState<@Composable () -> Unit>,
) {

    val listState = rememberLazyListState()

    pageHeader.value = {
        PageHeader(
            navigator = navigator,
            headerData = HeaderData(title = removeStrQuote(Res.string.AboutTheApp), titleIconId = Res.drawable.phorphos_film_slate_fill),
            hazeState = hazeState,
            backIconId = BackIcon.BACK,
        )
    }

    Box(modifier = Modifier.fillMaxSize()){
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            state = listState,
            modifier = Modifier
                .hazeSource(hazeState, zIndex = DefaultZIndex)
                //.widthIn(Constants.INFO_MIN_WIDTH, Constants.INFO_MAX_WIDTH)
                .padding(start = Constants.SCREEN_SAVE_PADDING, end = Constants.SCREEN_SAVE_PADDING),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ){
            item { Spacer(modifier = Modifier.statusBarsPadding().height(PAGE_HEADER_HEIGHT)) }
            item { AboutSG3() }
            item { AboutCodingBand() }
            item { SpecialThanks() }
            item { Declaration() }
            item { Spacer(modifier = Modifier.navigationBarsPadding()) }
        }
    }
}

@Composable
fun AboutSG3(){
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Stargazer 3",
            style = FontSizeNormal20(),
            color = GradReachYellow
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = LongStringXML().AboutSG3Desc(),
            style = FontSizeNormal16(),
            color = Color.White
        )
    }
}

private data class CodingBandTeammate(
    val name: String,
    val role: String,
    val avatar: DrawableResource,
    val representColor: Color,
    val discordLink: String,
    val gitHubLink: String,
)

@Composable
private fun CodingBandTeammateCard(modifier: Modifier = Modifier, teammate: CodingBandTeammate){
    Column(modifier = modifier
        .background(Color(0xFF404040), RoundedCornerShape(12.dp))
        .wrapContentSize()
        .padding(16.dp)
    ){
        Image(
            painter = painterResource(teammate.avatar),
            contentDescription = null,
            modifier = Modifier.sizeIn(64.dp,96.dp).clip(CircleShape),
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = teammate.name,
            style = FontSizeNormal16(),
            color = Color.White,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = teammate.role,
            style = FontSizeNormal12(),
            color = Color(0xFFCCCCCC),
            modifier = Modifier.background(teammate.representColor, RoundedCornerShape(4.dp)).align(Alignment.CenterHorizontally).padding(4.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Image(
                painter = painterResource(Res.drawable.phorphos_discord_logo_regular),
                contentDescription = null,
                modifier = Modifier.width(24.dp).clickable { urlHandler.openUri(teammate.discordLink) }.clip(RoundedCornerShape(4.dp)),
                colorFilter = ColorFilter.tint(Color.White)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Image(
                painter = painterResource(Res.drawable.phorphos_github_logo_regular),
                contentDescription = null,
                modifier = Modifier.width(24.dp).clickable { urlHandler.openUri(teammate.gitHubLink) }.clip(RoundedCornerShape(4.dp)),
                colorFilter = ColorFilter.tint(Color.White)
            )
        }
    }
}

@Composable
fun AboutCodingBand(){
    val teamInfoList = listOf<CodingBandTeammate>(
        CodingBandTeammate("Voc-夜芷冰", "App Developer", Res.drawable.vocaloid2048, Color(0xFFff5c94), "https://discord.com/users/417665898548166678", "https://github.com/Vocaloid2048"),
        CodingBandTeammate("2O48", "UI/UX Designer", Res.drawable._2O48, Color(0xFFD46C5F), "https://discord.com/users/746212954504167447", "https://github.com/2O48"),
    )
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Coding Band",
            style = FontSizeNormal20(),
            color = GradReachYellow
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = LongStringXML().AboutSG3CodingBand(),
            style = FontSizeNormal16(),
            color = Color.White
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.wrapContentSize(), horizontalArrangement = Arrangement.Center) {
            teamInfoList.forEachIndexed { index, teamInfo ->
                CodingBandTeammateCard(teammate = teamInfo, modifier = Modifier.weight(1f))
                if(index < teamInfoList.size - 1){
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }

        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SpecialThanks(){
    val cbetaTesters = listOf("smcl_tw","sakura_snow.0w0","alex90103","mrfatworm","whitykun","haoye_mikufan","aucekess","1200_00","mashujiu","_ericchou","coffeecat2006","foxfire_0425","ykram1006","lavriel_owo","doubletouch","hlep0_987","leftypencil","chickensouptastesgood6163","oolong_chawanmushi","yokaze0","wanhuaholmes","yukinayume","foxshadow2008",".snrvshallowdream","professionalwindowsexpert","butterfly_0109","nytethedevil","xiao_guagua0079","martin05016_martin","shawnlo.","potatochip1114","mktsay123","fat0187","hag0_","l1ght_owo","benzun","lawrencez_1996","cici.0711")
    val context = LocalPlatformContext.current

    Column {
        Text(
            text = "Special Thanks",
            style = FontSizeNormal20(),
            color = GradReachYellow
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = LongStringXML().AboutSG3SpecialThanks(),
            style = FontSizeNormal16(),
            color = Color.White
        )
        Spacer(modifier = Modifier.height(8.dp))
        FlowRow {
            cbetaTesters.mapIndexed { index, name ->
                Chip(
                    leadingIcon = {
                        Row {
                            Spacer(modifier = Modifier.width(2.dp))
                            AsyncImage(
                                model = newImageRequest(context, StarbaseAPI().getGitHubStaticAssetURL()+"/images/cbeta_testers/${name}.webp"),
                                modifier = Modifier.size(24.dp).aspectRatio(1f).clip(CircleShape),
                                contentDescription = "C.BETA Tester $name",
                            )
                        }
                    },
                    onClick = {},
                    content = {
                        Text(
                            text = name,
                            style = FontSizeNormal14(),
                            color = Color.White
                        )
                    }
                )
                if(index < cbetaTesters.size - 1) {
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }
        }
    }
}

@Composable
fun Declaration(){
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Declaration",
            style = FontSizeNormal20(),
            color = GradReachYellow
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = LongStringXML().AboutSG3Disclaimer(),
            style = FontSizeNormal16(),
            color = Color.White
        )
    }
}