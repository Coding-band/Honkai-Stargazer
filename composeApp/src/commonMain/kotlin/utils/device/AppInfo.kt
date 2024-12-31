package utils.device

import com.voc.stargazer3.BuildKonfig
import kotlinx.serialization.Serializable


@Serializable
data class AppInfo(
    val appProfile: String = "BETA",
    val appVersionName: String = "2.3.9",
    val appVersionCode: Int = 2999,
){
    companion object{
        val AppInfoInstance = AppInfo(
            BuildKonfig.appProfile,
            BuildKonfig.appVersionName, BuildKonfig.appVersionCode)
    }
}