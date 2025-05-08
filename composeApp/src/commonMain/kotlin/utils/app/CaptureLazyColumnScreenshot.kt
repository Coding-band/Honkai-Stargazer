package utils.app

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.material.Text
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.layer.drawLayer
import androidx.compose.ui.graphics.rememberGraphicsLayer
import androidx.compose.ui.unit.Dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import platformContext
import shareImageToOther
import utils.annotation.TranslationPls

@Composable
fun CaptureLazyColumnScreenshot(
    imageName: String,
    items: List<@Composable () -> Unit>,
    modifier: Modifier = Modifier,
    isCapture: MutableState<Boolean>,
) {
    val coroutineScope = rememberCoroutineScope()
    val graphicsLayer = rememberGraphicsLayer()

    if(isCapture.value){
        coroutineScope.launch {
            delay(1000)
            val bitmap = graphicsLayer.toImageBitmap()
            //writeToFileImageBitmap(imageName, content = bitmap)

            @TranslationPls
            shareImageToOther(
                shareTitle = "分享您的角色長圖給...",
                bitmap,
                imageName,
                context = platformContext
            )
            isCapture.value = false
        }
    }

    SubcomposeLayout(modifier
        .drawWithContent {
            // call record to capture the content in the graphics layer
            graphicsLayer.record {
                // draw the contents of the composable into the graphics layer
                this@drawWithContent.drawContent()
            }
            // draw the graphics layer on the visible canvas
            drawLayer(graphicsLayer)
    }) { constraints ->
        // 測量所有項目的完整高度
        val contentPlaceable = subcompose("content") {
            Column(modifier = Modifier.wrapContentWidth()) {
                items.map {
                    it()
                }
            }
        }.map { it.measure(constraints.copy(minHeight = 0, maxHeight = Int.MAX_VALUE)) }

        // 計算總高度和最大寬度
        val totalHeight = contentPlaceable.sumOf { it.height }
        val maxWidth = contentPlaceable.maxOfOrNull { it.width } ?: constraints.maxWidth

        // 佈局顯示（可選擇不顯示）
        layout(maxWidth, totalHeight) {
            contentPlaceable.forEachIndexed { index, placeable ->
                placeable.placeRelative(0, placeable.height * index)
            }
        }
    }
}