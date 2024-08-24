package utils

import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import files.Res
import files.misans_regular
import org.jetbrains.compose.resources.Font
import screens.gradientBottom
import setKeyboardDarkMode

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

val Black = Color(0xFF000000)
val White = Color(0xFFFFFFFF)
val BlackAlpha30 = Color(0x4D000000)
val BlackAlpha80 = Color(0xCC000000)
val BlackAlpha20 = Color(0x33000000)
val TextColorNormal = Color(0xFFFFFFFF)
val TextColorNormalDim = Color(0xFFDDDDDD)
val TextColorNormalDimCC = Color(0xCCDDDDDD)
val TextColorLevel = Color(0xFFDBC291)
val Transparent = Color(0x00000000)
val ProgressLevelBackground = Color(0xFF666666)
val ProgressLevelPrimary = Color(0xFFDBC291)
val AdditionalGreen = Color(0xFF43A047)
val GradReachYellow = Color(0xFFFFD070)

val BezierEasing2O48 = CubicBezierEasing(0.3f, 0f, 0.3f, 1f)

private val DarkColorScheme = darkColors(
    primary = Purple80,
    secondary = PurpleGrey80
)

private val LightColorScheme = lightColors(
    primary = Purple40,
    secondary = PurpleGrey40,
    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun Stargazer3Theme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    if(darkTheme) setKeyboardDarkMode()
    MaterialTheme(
        colors = colorScheme,
        typography = AppTypography(),
        content = content
    )
}

@Composable
fun AppFont() = FontFamily(
    Font(Res.font.misans_regular, weight = FontWeight.Medium)
)


@Composable
fun AppTypography() = androidx.compose.material.Typography(
    defaultFontFamily = AppFont()
)

@Composable
fun FontSizeNormal8() = TextStyle(
    fontFamily = AppFont(),
    fontWeight = FontWeight.Medium,
    fontSize = 8.sp
)
@Composable
fun FontSizeNormalSmall() = TextStyle(
    fontFamily = AppFont(),
    fontWeight = FontWeight.Medium,
    fontSize = 10.sp
)
@Composable
fun FontSizeNormal12() = TextStyle(
    fontFamily = AppFont(),
    fontWeight = FontWeight.Medium,
    fontSize = 12.sp
)
@Composable
fun FontSizeNormal14() = TextStyle(
    fontFamily = AppFont(),
    fontWeight = FontWeight.Medium,
    fontSize = 14.sp
)
@Composable
fun FontSizeNormal16() = TextStyle(
    fontFamily = AppFont(),
    fontWeight = FontWeight.Medium,
    fontSize = 16.sp
)
@Composable
fun FontSizeNormal20() = TextStyle(
    fontFamily = AppFont(),
    fontWeight = FontWeight.Medium,
    fontSize = 20.sp
)
@Composable
fun FontSizeNormalLarge24() = TextStyle(
    fontFamily = AppFont(),
    fontWeight = FontWeight.Medium,
    fontSize = 24.sp
)
@Composable
fun FontSizeNormalLarge32() = TextStyle(
    fontFamily = AppFont(),
    fontWeight = FontWeight.Medium,
    fontSize = 32.sp
)

@Composable
fun PageBottomMask(modifier: Modifier = Modifier) {
    Box(Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier.fillMaxWidth().fillMaxHeight(0.2f).background(gradientBottom).align(Alignment.BottomCenter)
        )
    }
}