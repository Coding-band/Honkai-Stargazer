package com.voc.honkaistargazer

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
@Deprecated("I'm lazy, and it can be simplified to use XML")
fun TreeDiagram() {
    Box(modifier = Modifier.size(325.dp, 404.dp)) {
        val scaleFactor = 2f
        Canvas(modifier = Modifier.matchParentSize()) {
            val path = Path().apply {
                // 繪製樹狀路徑 - 中間直線
                moveTo(166f,16.5f)
                lineTo(166f,388.5f)

                // 繪製樹狀路徑 - 頭頂彎向下n
                moveTo(97f, 34f)
                quadraticBezierTo(120f,16f,166.25f,16f)
                quadraticBezierTo(212.5f,16f,237f,34f)

                // 繪製樹狀路徑 - 左右彎向上u
                moveTo(15f, 134f)
                quadraticBezierTo(102f,240f,166f,240f)
                quadraticBezierTo(230f,240f,309f,134f)

                // 繪製樹狀路徑 - 左下角斜向中間偏左直線
                moveTo(16f,225f)
                lineTo(83.69f,339.5f)

                // 繪製樹狀路徑 - 右下角斜向中間偏左直線
                moveTo(309f,225f)
                lineTo(247.5f,342.5f)

                // 繪製樹狀路徑 - 下方彎向下n
                moveTo(83.69f,339.5f)
                quadraticBezierTo(123.95f,326.5f,166.5f,326.5f)
                quadraticBezierTo(209.05f,326.5f,247.5f,342.5f)
            }
            drawPath(path, color = Color.Gray, style = Stroke(width = 2f))
            //drawPath(drawCurve(97f,34f,166f,16f,237f,34f), color = Color.Gray, style = Stroke(width = 2f))

            // 繪製圓點 - 頭頂彎向下n
            drawCircle(Color(0xFFDDDDDD), radius = 16f , center = Offset(100f,30f))
            drawCircle(Color(0xFFDDDDDD), radius = 16f , center = Offset(166f,16f))
            drawCircle(Color(0xFFDDDDDD), radius = 16f , center = Offset(232f,30f))

            // 繪製圓點 -中間直線
            drawCircle(Color(0xFFDDDDDD), radius = 32f , center = Offset(166f,84f))
            drawCircle(Color(0xFFDDDDDD), radius = 28f , center = Offset(166f,162f))

            // 繪製圓點 -左右彎向上u
            drawCircle(Color(0xFFDDDDDD), radius = 28f , center = Offset(84f,208f))
            drawCircle(Color(0xFFDDDDDD), radius = 28f , center = Offset(166f,236f))


        }
    }
}

@Preview(showBackground = true, showSystemUi = false)
@Composable
fun TreePointPreview(){
    Box(modifier = Modifier.fillMaxSize().padding(16.dp)){
        TreeDiagram()
    }
}

fun drawCurve(xStart : Float, yStart : Float, xMid: Float, yMid: Float, xEnd:Float, yEnd:Float): Path {
    return Path().apply {
        moveTo(xStart, yStart)
        val midX1 = (xStart + xMid)/2 ;
        val midY1 = (yStart + yMid)/2
        quadraticBezierTo((midX1), (midY1), (xMid) , (yMid))


        moveTo(xMid, yMid)
        val midX2 = (xEnd + xMid)/2 ;
        val midY2 = (yEnd + yMid)/2
        quadraticBezierTo((midX2), (midY2), xEnd , yEnd)
    }
}