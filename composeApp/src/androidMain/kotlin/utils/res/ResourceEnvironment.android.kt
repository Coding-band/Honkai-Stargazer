package utils.res

import android.content.res.Configuration
import android.content.res.Resources
import org.jetbrains.compose.resources.DensityQualifier
import org.jetbrains.compose.resources.InternalResourceApi
import org.jetbrains.compose.resources.LanguageQualifier
import org.jetbrains.compose.resources.RegionQualifier
import org.jetbrains.compose.resources.ThemeQualifier
import java.util.Locale

@OptIn(InternalResourceApi::class)
internal actual fun getSystemEnvironment(): ResourceEnvironment {
    val locale = Locale.getDefault()
    val configuration = Resources.getSystem().configuration
    val isDarkTheme = configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
    val dpi = configuration.densityDpi
    return ResourceEnvironment(
        language = LanguageQualifier(locale.language),
        region = RegionQualifier(locale.country),
        theme = ThemeQualifier.selectByValue(isDarkTheme),
        density = DensityQualifier.selectByValue(dpi)
    )
}