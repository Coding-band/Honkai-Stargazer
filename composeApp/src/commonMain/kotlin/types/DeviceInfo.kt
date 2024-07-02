package types

import kotlinx.serialization.Serializable


@Serializable
data class DeviceInfo(
    val deviceModel: String = "Unknown",  //DUMMY-A1000
    val deviceOSName: String = "Unknown", //Android
    val deviceOSVersion: String = "Unknown", //34
)