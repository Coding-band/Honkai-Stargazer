package components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Slider
import androidx.compose.material.SliderDefaults
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ThemedSlider(sliderValue : Float, sliderValueIt: (Float) -> Unit, valueRange: ClosedFloatingPointRange<Float> = 1f..80f, steps: Int = 7) {


    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
            .height(20.dp)
    ) {
        val sliderHeight = 20.dp

        CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(sliderHeight)
                    .background(color = Color(0x33FFFFFF), shape = RoundedCornerShape(20.dp))
            )

            Slider(
                value = sliderValue,
                onValueChange = sliderValueIt,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(sliderHeight)
                    .padding(vertical = 2.dp),
                colors = SliderDefaults.colors(
                    thumbColor = Color.White,
                    activeTrackColor = Color.Transparent,
                    inactiveTrackColor = Color.Transparent,

                    ),
                valueRange = valueRange,
                steps = steps,
                )
        }
    }
}

private object NoRippleTheme : RippleTheme {
    @Composable
    override fun defaultColor() = Color.Unspecified

    @Composable
    override fun rippleAlpha(): RippleAlpha = RippleAlpha(0.0f,0.0f,0.0f,0.0f)
}