package types

import kotlinx.serialization.Serializable


@Serializable
data class AppInfo(
    val appVersionName: String = "Beta 2.3.9 (2999)",
    val appVersionCode: Int = 2999,
    val appPlatformOS: String = "DummyOS",
)