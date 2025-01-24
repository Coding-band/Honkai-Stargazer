package ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.LocalRippleConfiguration
import androidx.compose.material.RippleConfiguration
import androidx.compose.material.Slider
import androidx.compose.material.SliderDefaults
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlin.math.min

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ThemedProgressBar(progress: Number, max: Number, barColor: Color = Color(0xCCF3F9FF), background: Color = Color(0x40000000)) {

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
            .height(10.dp)
            .background(background, shape = RoundedCornerShape(20.dp))
    ) {
        Box(
            modifier = Modifier
                .height(6.dp)
                .padding(start = 2.dp, end = 2.dp)
                .align(Alignment.CenterStart)
                .fillMaxWidth(min(progress.toFloat() / max.toFloat(), 1f))
                .background(barColor, shape = RoundedCornerShape(20.dp))
        )
    }
}

