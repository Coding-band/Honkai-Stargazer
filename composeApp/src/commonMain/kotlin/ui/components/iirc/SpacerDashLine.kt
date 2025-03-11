package com.voc.idle.irc.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

enum class LineOrientation {
    HORIZONTAL, VERTICAL
}

@Composable
fun SpacerDashLine(
    modifier: Modifier = Modifier,
    orientation: LineOrientation = LineOrientation.HORIZONTAL
) {
    Canvas(modifier = modifier.fillMaxSize()) {
        val pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
        if (orientation == LineOrientation.HORIZONTAL) {
            drawLine(
                color = Color.Gray,
                start = Offset(0f, size.height / 2),
                end = Offset(size.width, size.height / 2),
                strokeWidth = size.width.dp.toPx(),
                pathEffect = pathEffect
            )
        } else {
            drawLine(
                color = Color.Gray,
                start = Offset(size.width / 2, 0f),
                end = Offset(size.width / 2, size.height),
                strokeWidth = size.width.dp.toPx(),
                pathEffect = pathEffect
            )
        }
    }
}