package utils.hoyolab

import files.HaveNotUsed
import files.Res
import files.america
import files.asia
import files.cn1
import files.cn2
import files.europe
import files.twhkmo
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.StringResource
import utils.annotation.DoItLater

class HoyolabConst {
    /**
     *
     * @param devName 方便我對照的...
     * @param localeName hoyolabGameRecord() 獲取 JSON data => data.list[x].region
     * @param serverId 伺服器的ID
     * @param platform 分辨 國服 (CN) 和 國際服 (OS)
     */
    enum class SERVER(val devName: String, val localeName: StringResource, val serverId: String, val platform: HoyolabRequest.PLATFORM) {
        MIHOYO("天空島服", Res.string.cn1, "prod_gf_cn", HoyolabRequest.PLATFORM.MIYOUSHE),
        BILIBILI("世界樹服", Res.string.cn2, "prod_qd_cn", HoyolabRequest.PLATFORM.MIYOUSHE),
        ASIA("Asia", Res.string.asia, "prod_official_asia", HoyolabRequest.PLATFORM.HOYOLAB),
        EUROPE("Europe", Res.string.europe, "prod_official_eur", HoyolabRequest.PLATFORM.HOYOLAB),
        AMERICA("America", Res.string.america, "prod_official_usa", HoyolabRequest.PLATFORM.HOYOLAB),
        TW_HK_MO("TW,HK,MO", Res.string.twhkmo, "prod_official_cht", HoyolabRequest.PLATFORM.HOYOLAB),
        UNKNOWN("UNKNOWN", Res.string.HaveNotUsed, "UNKNOWN", HoyolabRequest.PLATFORM.HOYOLAB);
    }
    fun getServerById(serverId : String): SERVER {
        return SERVER.entries.filter { server -> server.serverId == serverId }.firstOrNull() ?: SERVER.UNKNOWN
    }
    fun getServerByUID(uid : String): SERVER {
        return when(uid.first()){
            '1' -> SERVER.MIHOYO
            '2' -> SERVER.MIHOYO
            '5' -> SERVER.BILIBILI
            '6' -> SERVER.AMERICA
            '7' -> SERVER.EUROPE
            '8' -> SERVER.ASIA
            '9' -> SERVER.TW_HK_MO
            else -> SERVER.UNKNOWN
        }

    }

    /**
     *
     * @param devName 方便我對照的...
     * @param gameId 在 hoyolabGameRecord() 獲取 JSON data => data.list[x].game_id
     */
    enum class GAME(val devName: String, val gameId: Int) {
        HONKAI_3RD("崩壞3rd", 1),
        GENSHIN_IMPACT("原神", 2),
        HONKAI_2ND("崩壞學院2", 3),
        TEARS_OF_THEMIS("未定事件簿", 4),
        DA_BIE_YE("大别野", 5),
        HONKAI_STAR_RAIL("崩壞．星穹鐵道", 6),
        ZZZ("絕區零", 8);
    }

    @Serializable
    data class HoyolabTime(
        val year: Int = 1970,
        val month: Int = 1,
        val day: Int = 1,
        val hour: Int = 0,
        val minute: Int = 0,
        val second: Int = 0
    ) {
        fun getDateTimeFromHoyolabTime(hoyolabTime: HoyolabTime, showSeconds: Boolean = true): String {
            val year = hoyolabTime.year
            val month = if (hoyolabTime.month < 10) "0${hoyolabTime.month}" else hoyolabTime.month.toString()
            val day = if (hoyolabTime.day < 10) "0${hoyolabTime.day}" else hoyolabTime.day.toString()
            val hour = if (hoyolabTime.hour < 10) "0${hoyolabTime.hour}" else hoyolabTime.hour.toString()
            val minute = if (hoyolabTime.minute < 10) "0${hoyolabTime.minute}" else hoyolabTime.minute.toString()
            val second = if (hoyolabTime.second < 10) "0${hoyolabTime.second}" else hoyolabTime.second.toString()

            return if (showSeconds) {
                "$year-$month-$day $hour:$minute:$second"
            } else {
                "$year-$month-$day $hour:$minute"
            }
        }
    }

    /**
     * 登入URL
     */
    fun getLoginURL(serverSelected : HoyolabConst.SERVER): String {
        return when (serverSelected.platform) {
            HoyolabRequest.PLATFORM.MIYOUSHE -> "https://user.miyoushe.com/login-platform/mobile.html?app_id=bll8iq97cem8&theme=&token_type=4&game_biz=bbs_cn&redirect_url=https%253A%252F%252Fuser.miyoushe.com%252Fsingle-page%252Fcommunity-init.html%253Fapp_id%253Dbll8iq97cem8%2526ux_mode%253Dredirect%2526st%253Dhttps%25253A%25252F%25252Fm.miyoushe.com%25252Fsr%25252F%252523%25252Fhome%25252F0%2526dest%253Dhttps%25253A%25252F%25252Fpassport-api.miyoushe.com%25252Faccount%25252Fma-cn-session%25252Fweb%25252FcrossLoginStart%25253Fdest%25253Dhttps%2525253A%2525252F%2525252Fm.miyoushe.com%2525252Fsr%2525252F%25252523%2525252Fhome%2525252F0&st=https%253A%252F%252Fm.miyoushe.com%252Fsr%252F%2523%252Fhome%252F0&succ_back_type=redirect&fail_back_type=&ux_mode=redirect#/login/captcha"
            else -> "https://act.hoyolab.com/app/community-game-records-sea/rpg/index.html"
        }
    }
    fun getLoginDomain(serverSelected : HoyolabConst.SERVER): String {
        return when (serverSelected.platform) {
            HoyolabRequest.PLATFORM.MIYOUSHE -> "https://user.miyoushe.com"
            else -> "https://act.hoyolab.com"
        }
    }

    /**
     * 地圖
     */
    @DoItLater("Check whether is MIYOUSHE's URL correct")
    fun getHsrMapURL(serverSelected : HoyolabConst.SERVER) : String {
        return when(serverSelected.platform){
            HoyolabRequest.PLATFORM.HOYOLAB -> "https://act.hoyolab.com/sr/app/interactive-map/index.html"
            HoyolabRequest.PLATFORM.MIYOUSHE -> "https://act.hoyolab.com/sr/app/interactive-map/index.html"
        }

    }



    val HOYOLAB_V2_KEY_GROUP = arrayOf(
        "cookie_token_v2",
        "account_mid_v2",
        "account_id_v2",
        "ltoken_v2",
        "ltmid_v2",
        "ltuid_v2"
    )
}