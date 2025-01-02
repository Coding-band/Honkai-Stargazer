package ui.function.lightconeListPage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cheonjaeung.compose.grid.SimpleGridCells
import com.cheonjaeung.compose.grid.VerticalGrid
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import moe.tlaster.precompose.navigation.Navigator
import ui.components.BackIcon
import ui.components.LightconeCard
import ui.components.HeaderData
import ui.components.LIST_FILTER_TOOL_HEIGHT
import ui.components.PAGE_HEADER_HEIGHT
import ui.components.PageHeader
import ui.components.defaultHeaderData
import utils.app.Constants.Companion.CHAR_CARD_WIDTH
import utils.app.PageBottomMask

@Composable
fun LightconeListPage(
    modifier: Modifier = Modifier,
    navigator: Navigator,
    headerData: HeaderData = defaultHeaderData
) {
    val viewModel = LightconeListPageViewModel(navigator = navigator)
    val state by viewModel.state.collectAsState()
    val hazeState = remember { HazeState() }


    LaunchedEffect(Unit){
        viewModel.handleIntent(LightconeListPageIntent.Initialize)
    }

    Box {
        LazyColumn {
            item{
                Spacer(modifier = Modifier
                    .statusBarsPadding()
                    .height(PAGE_HEADER_HEIGHT+12.dp)
                )
            }

            item{
                VerticalGrid(
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp)
                        .haze(state = hazeState),
                    columns = SimpleGridCells.Adaptive(CHAR_CARD_WIDTH),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ){
                    for(lightcone in state.lightconeListFilted.value){
                        LightconeCard(lightcone = lightcone)
                    }

                }
            }
            item{
                Spacer(
                    modifier = Modifier
                        .navigationBarsPadding()
                        .height(LIST_FILTER_TOOL_HEIGHT+12.dp)
                )
            }
        }


        PageBottomMask()

        /*
        ListFilterTool(
            filterList = charList.value,
            filterType = ListFilterType.CHARACTER,
            onFilterApplied = { filteredList ->
                charListSortable.value = filteredList
            }
        )
         */

        PageHeader(
            navigator = navigator,
            headerData = headerData,
            hazeState = hazeState,
            backIconId = BackIcon.CANCEL
        )
    }
}