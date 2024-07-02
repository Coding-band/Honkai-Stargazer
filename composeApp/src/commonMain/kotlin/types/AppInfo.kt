package types

import kotlinx.serialization.Serializable


@Serializable
data class AppInfo(
    val appProfile: String = "BETA",
    val appVersionName: String = "2.3.9",
    val appVersionCode: Int = 2999,
)