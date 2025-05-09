package utils.app

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavBackStackEntry
import dev.chrisbanes.haze.HazeEffectScope
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.hazeEffect
import files.Res
import files.misans_regular
import io.github.oikvpqya.compose.fastscroller.ScrollbarStyle
import io.github.oikvpqya.compose.fastscroller.ThumbStyle
import io.github.oikvpqya.compose.fastscroller.TrackStyle
import io.github.oikvpqya.compose.fastscroller.VerticalScrollbar
import io.github.oikvpqya.compose.fastscroller.rememberScrollbarAdapter
import org.jetbrains.compose.resources.Font
import setKeyboardDarkMode
import ui.components.PAGE_HEADER_HEIGHT
import ui.screens.globalHazeBlur
import ui.screens.gradientBottom
import ui.screens.gradientTop

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
val WhiteAlpha10 = Color(0x1AFFFFFF)
val TextColorNormal = Color(0xFFFFFFFF)
val TextColorNormalDim = Color(0xFFDDDDDD)
val TextColorNormalDimCC = Color(0xCCDDDDDD)
val TextColorLevel = Color(0xFFDBC291)
val TextDonorColorBrush = Brush.verticalGradient(
    colors = listOf(Color(0xFF57C1FF), Color(0xFFFFFFFF)),
)
val Transparent = Color(0x00000000)
val ProgressLevelBackground = Color(0xFF666666)
val ProgressLevelPrimary = Color(0xFFDBC291)
val AdditionalGreen = Color(0xFF43A047)
val GradReachYellow = Color(0xFFFFD070)
val GradientHomeButton = Brush.verticalGradient(
    colors = listOf(
        Color(0x99222222), //0.6f of FF222222
        Color(0x00222222),
    )
)

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

val RamdomLinearGradientGroup = listOf(
    // 調整後的 8 組較深色柔和漸變（原淺色系改進）
    Brush.linearGradient(colors = listOf(Color(0xFF8CA8D3), Color(0xFF6687B8))), // 晨霧藍（加深）
    Brush.linearGradient(colors = listOf(Color(0xFFFFA8B8), Color(0xFFFF6F85))), // 桃花粉（加深）
    Brush.linearGradient(colors = listOf(Color(0xFF8FDBB6), Color(0xFF5EB38A))), // 薄荷綠（加深）
    Brush.linearGradient(colors = listOf(Color(0xFFB89FE6), Color(0xFF8E6FC7))), // 薰衣草紫（加深）
    Brush.linearGradient(colors = listOf(Color(0xFFFFB07A), Color(0xFFFF8C4B))), // 日落橙（加深）
    Brush.linearGradient(colors = listOf(Color(0xFFFF8A80), Color(0xFFFF5C4D))), // 蜜桃珊瑚（加深）
    Brush.linearGradient(colors = listOf(Color(0xFFB3BCD1), Color(0xFF8795B3))), // 雲朵灰（加深）
    Brush.linearGradient(colors = listOf(Color(0xFF80D8E8), Color(0xFF4FB2C7))), // 海沫綠（加深）
    // 原新增的 8 組較深色柔和漸變
    Brush.linearGradient(colors = listOf(Color(0xFF2A3B5A), Color(0xFF4B6587))), // 深夜藍
    Brush.linearGradient(colors = listOf(Color(0xFF3F2E56), Color(0xFF6B4E8A))), // 暮色紫
    Brush.linearGradient(colors = listOf(Color(0xFF2E4B3F), Color(0xFF4A7A5E))), // 森林綠
    Brush.linearGradient(colors = listOf(Color(0xFF4A2B3C), Color(0xFF7A4E5F))), // 酒紅魅影
    Brush.linearGradient(colors = listOf(Color(0xFF2F2F2F), Color(0xFF4C5A65))), // 炭灰黑
    Brush.linearGradient(colors = listOf(Color(0xFF1E4D5C), Color(0xFF3B7A8A))), // 深海青
    Brush.linearGradient(colors = listOf(Color(0xFF3C2F2F), Color(0xFF6A4E4E))), // 咖啡棕
    Brush.linearGradient(colors = listOf(Color(0xFF283A5E), Color(0xFF4A6191)))  // 靛藍夜
)

@Composable
fun Modifier.notchPaddingLeft (): Modifier {
    val density = LocalDensity.current
    return this.padding(start = pxToDp(WindowInsets.displayCutout.getLeft(density, LayoutDirection.Ltr), density.density))
}

@Composable
fun Modifier.notchPaddingRight (): Modifier {
    val density = LocalDensity.current
    return this.padding(end = pxToDp(WindowInsets.displayCutout.getRight(density, LayoutDirection.Ltr), density.density))
}

object SG3NavTransitions {
    val enterTransition:
            AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition = {
        slideIntoContainer(
            towards = AnimatedContentTransitionScope.SlideDirection.Start,
            animationSpec = tween(
                durationMillis = 250,
                easing = if(isIosPlatform()) LinearEasing else BezierEasing2O48
            )
        )
    }
    val exitTransition:
            AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition = {
        slideOutOfContainer(
            towards = AnimatedContentTransitionScope.SlideDirection.Start,
            animationSpec = tween(
                durationMillis = 250,
                easing = if(isIosPlatform()) LinearEasing else BezierEasing2O48
            ),
            targetOffset = { fullOffset -> (fullOffset * 0.3f).toInt() }
        )
    }
    val popEnterTransition:
            AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition = {
        slideIntoContainer(
            towards = AnimatedContentTransitionScope.SlideDirection.End,
            animationSpec = tween(
                durationMillis = 250,
                easing = if(isIosPlatform()) LinearEasing else BezierEasing2O48
            ),
            initialOffset = { fullOffset -> (fullOffset * 0.3f).toInt() }
        )
    }
    val popExitTransition:
            AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition = {
        slideOutOfContainer(
            towards = AnimatedContentTransitionScope.SlideDirection.End,
            animationSpec = tween(
                durationMillis = 250,
                easing = if(isIosPlatform()) LinearEasing else BezierEasing2O48
            )
        )
    }
    val sizeTransform:
            (AnimatedContentTransitionScope<NavBackStackEntry>.() -> SizeTransform?)? = null
}

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
@Composable
fun PageTopMask(modifier: Modifier = Modifier) {
    Box(Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier.fillMaxWidth().height(PAGE_HEADER_HEIGHT).background(gradientTop).align(Alignment.TopCenter)
        )
    }
}

@Composable
fun FontShadow() = TextStyle(shadow = Shadow(color = Color.Black, offset = Offset(2.0f, 2.0f)))

val HazeBlurDp10 = HazeStyle(Color.Black, null, 10.dp, 0f)
val HazeBlurDp10Alpha = HazeStyle(Color.Transparent, null, 10.dp, 0f)
val HazeBlurDp20Alpha = HazeStyle(Color.Transparent, null, 20.dp, 0f)
val HazeBlurDp20 = HazeStyle(Color.Black, null, 20.dp, 0f)
const val DialogPopUpZIndex = 200f
const val PageHeaderZIndex = 100f
const val HomePageBtnZIndex = 10f
const val DefaultZIndex = 5f
const val BackgroundZIndex = 2f

fun Modifier.hazeEffectSG3(
    state: HazeState,
    isBlur: MutableState<Boolean> = globalHazeBlur,
    style: HazeStyle = HazeBlurDp10,
    block: (HazeEffectScope.() -> Unit)? = {
        blurEnabled = isBlur.value
    },
): Modifier = this.hazeEffect(
    state = state,
    style = style,
    block = block
)

@Composable
fun SG3ScrollbarStyle(): ScrollbarStyle {
    return ScrollbarStyle(
        minimalHeight = 4.dp,
        thickness = 4.dp,
        hoverDurationMillis = 300,
        thumbStyle = ThumbStyle(
            shape = RoundedCornerShape(4.dp),
            unhoverColor = Color.White.copy(0.5f),
            hoverColor = Color.White.copy(0.5f),
        ),
        trackStyle = TrackStyle(
            shape = RoundedCornerShape(4.dp),
            unhoverColor = Color.Transparent,
            hoverColor = Color.Transparent,
        ),
    )
}

@Composable
fun SG3VerticalScrollbar(scrollState : ScrollState){
    Box(modifier = Modifier.fillMaxSize()){
        VerticalScrollbar(
            modifier = Modifier.align(Alignment.TopEnd).padding(top = PAGE_HEADER_HEIGHT, bottom = 16.dp).fillMaxHeight(),
            adapter = rememberScrollbarAdapter(scrollState = scrollState),
            style = SG3ScrollbarStyle(),
            enablePressToScroll = false,
        )
    }
}

@Composable
fun SG3VerticalScrollbar(listState: LazyListState){
    Box(modifier = Modifier.fillMaxSize()){
        VerticalScrollbar(
            modifier = Modifier.align(Alignment.TopEnd).padding(top = PAGE_HEADER_HEIGHT, bottom = 16.dp).fillMaxHeight(),
            adapter = rememberScrollbarAdapter(scrollState = listState),
            style = SG3ScrollbarStyle(),
            enablePressToScroll = false,
        )
    }
}

@Composable
fun SG3VerticalScrollbar(gridState: LazyGridState){
    Box(modifier = Modifier.fillMaxSize()){
        VerticalScrollbar(
            modifier = Modifier.align(Alignment.TopEnd).padding(top = PAGE_HEADER_HEIGHT, bottom = 16.dp).fillMaxHeight(),
            adapter = rememberScrollbarAdapter(scrollState = gridState),
            style = SG3ScrollbarStyle(),
            enablePressToScroll = false,
        )
    }
}