package utils.hoyolab

import androidx.annotation.IntRange
import utils.Language
import utils.Preferences
import utils.annotation.DoItLater

class HoyolabAPI(platform: HoyolabRequest.PLATFORM = HoyolabRequest.PLATFORM.HOYOLAB, cookies : String) {
    private val hoyolabRequest = HoyolabRequest(platform, cookies)
    private val platformLocale = platform
    @DoItLater("TextLanguage need to make a instance!")
    private val language = Language.TextLanguageInstance

    companion object Instance{

    }

    /**
     * 獲取Hoyolab / 米游社遊戲紀錄
     */
    fun getGameRecordCard(userHoyoId : String) : HoyolabResponse {
        return hoyolabRequest.send(
            when(platformLocale){
                HoyolabRequest.PLATFORM.HOYOLAB -> "https://bbs-api-os.hoyolab.com/game_record/card/wapi/getGameRecordCard?uid=${userHoyoId}"
                HoyolabRequest.PLATFORM.MIYOUSHE -> "https://api-takumi-record.mihoyo.com/game_record/app/card/wapi/getGameRecordCard?uid=${userHoyoId}"
            }
        )
    }

    /**
     * 獲取星鐵完整用戶資料
     */
    fun getHsrFullData(uid: String, server: HoyolabConst.SERVER = HoyolabConst.SERVER.ASIA) : HoyolabResponse {
        return hoyolabRequest.send(
            when(platformLocale){
                HoyolabRequest.PLATFORM.HOYOLAB -> "https://bbs-api-os.hoyolab.com/game_record/hkrpg/api/avatar/info?server=${server.serverId}&role_id=${uid}"
                HoyolabRequest.PLATFORM.MIYOUSHE -> "https://api-takumi-record.mihoyo.com/game_record/app/hkrpg/api/avatar/info?server=${server.serverId}&role_id=${uid}"
            }
        )
    }

    /**
     * 獲取星鐵用戶基礎資料
     */
    fun getHsrIndexData(uid: String, server: HoyolabConst.SERVER = HoyolabConst.SERVER.ASIA) : HoyolabResponse {
        return hoyolabRequest.send(
            when(platformLocale){
                HoyolabRequest.PLATFORM.HOYOLAB -> "https://bbs-api-os.hoyolab.com/game_record/hkrpg/api/index?server=${server.serverId}&role_id=${uid}"
                HoyolabRequest.PLATFORM.MIYOUSHE -> "https://api-takumi-record.mihoyo.com/game_record/app/hkrpg/api/index?server=${server.serverId}&role_id=${uid}"
            }
        )
    }

    /**
     * 獲取星鐵角色便籤
     */
    fun getHsrNote(uid: String, server: HoyolabConst.SERVER = HoyolabConst.SERVER.ASIA) : HoyolabResponse {
        return hoyolabRequest.send(
            when(platformLocale){
                HoyolabRequest.PLATFORM.HOYOLAB -> "https://bbs-api-os.hoyolab.com/game_record/hkrpg/api/note?server=${server.serverId}&role_id=${uid}"
                HoyolabRequest.PLATFORM.MIYOUSHE -> "https://api-takumi-record.mihoyo.com/game_record/app/hkrpg/api/note?server=${server.serverId}&role_id=${uid}"
            }
        )
    }

    /**
     * 獲取混沌回憶資料
     */
    fun getHsrMemoryOfChaos(uid: String, server: HoyolabConst.SERVER = HoyolabConst.SERVER.ASIA, @IntRange(1,2) scheduleType: Long = 1) : HoyolabResponse {
        return hoyolabRequest.send(
            when(platformLocale){
                HoyolabRequest.PLATFORM.HOYOLAB -> "https://bbs-api-os.hoyolab.com/game_record/hkrpg/api/challenge?server=${server.serverId}&role_id=${uid}&schedule_type=${scheduleType}&need_all=true"
                HoyolabRequest.PLATFORM.MIYOUSHE -> "https://api-takumi-record.mihoyo.com/game_record/app/hkrpg/api/challenge?server=${server.serverId}&role_id=${uid}&schedule_type=${scheduleType}&need_all=true"
            }
        )
    }

    /**
     * 獲取虛構敘事資料
     */
    fun getHsrPureFiction(uid: String, server: HoyolabConst.SERVER = HoyolabConst.SERVER.ASIA, @IntRange(1,2) scheduleType: Long = 1) : HoyolabResponse {
        return hoyolabRequest.send(
            when(platformLocale){
                HoyolabRequest.PLATFORM.HOYOLAB -> "https://bbs-api-os.hoyolab.com/game_record/hkrpg/api/challenge_story?server=${server.serverId}&role_id=${uid}&schedule_type=${scheduleType}&need_all=true"
                HoyolabRequest.PLATFORM.MIYOUSHE -> "https://api-takumi-record.mihoyo.com/game_record/app/hkrpg/api/challenge_story?server=${server.serverId}&role_id=${uid}&schedule_type=${scheduleType}&need_all=true"
            }
        )
    }

    /**
     * 獲取末日幻影資料
     */
    fun getHsrApocalypticShadow(uid: String, server: HoyolabConst.SERVER = HoyolabConst.SERVER.ASIA, @IntRange(1,2) scheduleType: Long = 1) : HoyolabResponse {
        return hoyolabRequest.send(
            when(platformLocale){
                HoyolabRequest.PLATFORM.HOYOLAB -> "https://bbs-api-os.hoyolab.com/game_record/hkrpg/api/challenge_boss?server=${server.serverId}&role_id=${uid}&schedule_type=${scheduleType}&need_all=true"
                HoyolabRequest.PLATFORM.MIYOUSHE -> "https://api-takumi-record.mihoyo.com/game_record/app/hkrpg/api/challenge_boss?server=${server.serverId}&role_id=${uid}&schedule_type=${scheduleType}&need_all=true"
            }
        )
    }

    /**
     * 獲取活動列表
     */
    @DoItLater("Check whether is MIYOUSHE's URL correct")
    fun getHsrEventList() : HoyolabResponse {
        return hoyolabRequest.getPlainTxt(
            when(platformLocale){
                HoyolabRequest.PLATFORM.HOYOLAB -> "https://sg-hkrpg-api.hoyoverse.com/common/hkrpg_global/announcement/api/getAnnList?game=hkrpg&game_biz=hkrpg_global&lang=${language.hoyolabName}&bundle_id=hkrpg_global&level=55&platform=pc&region=prod_official_cht&uid=900000000"
                HoyolabRequest.PLATFORM.MIYOUSHE -> "https://sg-hkrpg-api.hoyoverse.com/common/hkrpg_global/announcement/api/getAnnList?game=hkrpg&game_biz=hkrpg_global&lang=${language.hoyolabName}&bundle_id=hkrpg_global&level=55&platform=pc&region=prod_official_cht&uid=900000000"
            }
        )
    }


    /**
     * 獲取活動内容
     */
    @DoItLater("Check whether is MIYOUSHE's URL correct")
    fun getHsrEventContent() : HoyolabResponse {
        return hoyolabRequest.getPlainTxt(
            when(platformLocale){
                HoyolabRequest.PLATFORM.HOYOLAB -> "https://sg-hkrpg-api.hoyoverse.com/common/hkrpg_global/announcement/api/getAnnContent?game=hkrpg&game_biz=hkrpg_global&lang=${language.hoyolabName}&bundle_id=hkrpg_global&level=55&platform=pc&region=prod_official_cht&uid=900000000"
                HoyolabRequest.PLATFORM.MIYOUSHE -> "https://sg-hkrpg-api.hoyoverse.com/common/hkrpg_global/announcement/api/getAnnList?game=hkrpg&game_biz=hkrpg_global&lang=${language.hoyolabName}&bundle_id=hkrpg_global&level=55&platform=pc&region=prod_official_cht&uid=900000000"
            }

        )
    }
}