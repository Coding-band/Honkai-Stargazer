package utils.hoyolab

import files.HaveNotUsed
import files.Res
import files.america
import files.asia
import files.cn1
import files.cn2
import files.europe
import files.twhkmo
import org.jetbrains.compose.resources.StringResource

class HoyolabConst {
    /**
     *
     * @param devName 方便我對照的...
     * @param localeName hoyolabGameRecord() 獲取 JSON data => data.list[x].region
     * @param serverId 伺服器的ID
     * @param serverLocation 分辨 國服 (CN) 和 國際服 (OS)
     */
    enum class SERVER(val devName: String, val localeName: StringResource, val serverId: String, val serverLocation: String) {
        MIHOYO("天空島服", Res.string.cn1, "prod_gf_cn", "CN"),
        BILIBILI("世界樹服", Res.string.cn2, "prod_qd_cn", "CN"),
        ASIA("Asia", Res.string.asia, "prod_official_asia", "OS"),
        EUROPE("Europe", Res.string.europe, "prod_official_eur", "OS"),
        AMERICA("America", Res.string.america, "prod_official_usa", "OS"),
        TW_HK_MO("TW,HK,MO", Res.string.twhkmo, "prod_official_cht", "OS"),
        UNKNOWN("UNKNOWN", Res.string.HaveNotUsed, "UNKNOWN", "UNKNOWN");
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

    val HOYOLAB_V2_KEY_GROUP = arrayOf(
        "cookie_token_v2",
        "account_mid_v2",
        "account_id_v2",
        "ltoken_v2",
        "ltmid_v2",
        "ltuid_v2"
    )
}