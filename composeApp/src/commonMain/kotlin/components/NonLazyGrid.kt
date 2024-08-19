package components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

//https://dev.to/maaxgr/jetpack-compose-grid-without-lazy-5gb8
@Composable
fun NonLazyGrid(
    columns: Int,
    itemCount: Int,
    modifier: Modifier = Modifier,
    horizontalSpaceBetween: Dp = 0.dp,
    verticalSpaceBetween: Dp = 0.dp,
    content: @Composable() (Int) -> Unit,
) {
    Column(modifier = modifier) {
        var rows = (itemCount / columns)
        if (itemCount.mod(columns) > 0) {
            rows += 1
        }

        for (rowId in 0 until rows) {
            val firstIndex = rowId * columns

            Row {
                for (columnId in 0 until columns) {
                    val index = firstIndex + columnId
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    ) {
                        if (index < itemCount) {
                            content(index)
                        }
                    }
                    Spacer(modifier = Modifier.width(if (columnId < columns - 1) horizontalSpaceBetween else 0.dp))
                }
            }
            Spacer(modifier = Modifier.height(if (rowId < rows - 1) verticalSpaceBetween else 0.dp))
        }

    }
}