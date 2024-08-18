package components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.toRect
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.draw
import androidx.compose.ui.graphics.isSpecified
import androidx.compose.ui.layout.onPlaced
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.IntSize

/**
 * https://github.com/kubode/compose-shadow-alternative/blob/main/compose-dropshadow/src/main/kotlin/io/github/kubode/compose/dropshadow/DropShadow.kt
 *
 * Composable to apply drop shadow effect.
 *
 * Unlike the standard shadow modifier, the shadow is rendered according to the transparency of the content.
 *
 * ## Caution
 * This composable performs very poorly because it draws the content twice.
 * It also changes the content rendering.
 * For example, button indicators and over-scrolling in the content will not work correctly.
 *
 * @param color The color of the shadow.
 * @param offset The offset of the shadow.
 * @param radius The blur radius of the shadow.
 * @param edgeTreatment The edge treatment of the shadow.
 * @param modifier The modifier to apply to this layout node.
 * @param drawInvalidationTrigger The trigger to invalidate the drawing.
 * If you want to redraw the content due to a state change, describe that state in this lambda.
 * ```
 * val rotate by animateAsFloat(...)
 * DropShadow(
 *     ...
 *     drawInvalidationTrigger = { rotate }
 * ) {
 *     Text(text = "Hello", modifier = Modifier.rotate(rotate))
 * }
 * ```
 * @param content The content to draw.
 */
@Composable
public fun DropShadow(
    color: Color,
    offset: DpOffset,
    radius: Dp,
    modifier: Modifier = Modifier,
    edgeTreatment: BlurredEdgeTreatment = BlurredEdgeTreatment.Unbounded,
    drawInvalidationTrigger: () -> Any? = {},
    content: @Composable () -> Unit,
) {
    Box(
        modifier = modifier,
    ) {
        val state by remember { mutableStateOf(DropShadowState()) }
        DropShadowEffect(
            state = state,
            color = color,
            offset = offset,
            radius = radius,
            edgeTreatment = edgeTreatment,
            drawInvalidationTrigger = drawInvalidationTrigger,
        )
        DropShadowContent(
            state = state,
            drawInvalidationTrigger = drawInvalidationTrigger,
            content = content,
        )
    }
}

private class DropShadowState {
    var size: IntSize by mutableStateOf(IntSize.Zero)
        private set
    var bitmapAndCanvas: Pair<ImageBitmap, Canvas>? by mutableStateOf(null)
        private set

    fun updateSize(size: IntSize) {
        this.size = size
        val bitmap = ImageBitmap(width = size.width, height = size.height)
        this.bitmapAndCanvas = bitmap to Canvas(bitmap)
    }
}

private val clearPaint = Paint().apply { blendMode = BlendMode.Clear }

@Composable
private fun DropShadowContent(
    state: DropShadowState,
    drawInvalidationTrigger: () -> Any?,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Box(
        modifier = modifier
            .onPlaced { state.updateSize(it.size) }
            .drawWithContent {
                debug { "Draw - content" }
                drawInvalidationTrigger()
                state.bitmapAndCanvas?.let { (bitmap, canvas) ->
                    canvas.drawRect(size.toRect(), clearPaint)
                    draw(this, layoutDirection, canvas, size) {
                        this@drawWithContent.drawContent()
                    }
                    drawImage(bitmap)
                }
            },
    ) {
        content()
    }
}

@Composable
private fun DropShadowEffect(
    state: DropShadowState,
    color: Color,
    offset: DpOffset,
    radius: Dp,
    edgeTreatment: BlurredEdgeTreatment,
    drawInvalidationTrigger: () -> Any?,
    modifier: Modifier = Modifier,
) {
    val bitmap = state.bitmapAndCanvas?.first ?: return
    val colorFilter =
        remember(color) { color.takeIf { it.isSpecified }?.let { ColorFilter.tint(it) } }
    val density = LocalDensity.current
    androidx.compose.foundation.Canvas(
        modifier = modifier
            .size(
                width = with(density) { bitmap.width.toDp() },
                height = with(density) { bitmap.height.toDp() },
            )
            .offset(
                x = offset.x,
                y = offset.y,
            )
            .blur(
                radius = radius,
                edgeTreatment = edgeTreatment,
            ),
    ) {
        debug { "Draw - shadow" }
        drawInvalidationTrigger()
        drawImage(
            image = bitmap,
            colorFilter = colorFilter,
        )
    }
}

private const val DEBUG = false
private inline fun debug(message: () -> String) {
    if (DEBUG) {
        println(message())
    }
}