import AsyncStorage from "@react-native-async-storage/async-storage";
import GachaRequest, { IResponse } from "./GachaRequest";
import { LanguageEnum } from "../hoyolab/language/language.interface";
import { hsrServer } from "../hoyolab/servers/hsrServer.types";
import { ENV, PACKAGE_NAME, VERSION } from "../../../app.config";
import { NativeModules } from "react-native";
import Toast from "../toast/Toast";
import { AppLanguage } from "../../language/language.types";
import { LOCALES } from "../../../locales";

export const GACHA_KEY = "user-analysis-gacha-data";
export const GACHA_END_ID_KEY = "user-analysis-gacha-end-id";
export const GACHA_SUMMARY_KEY = "user-analysis-gacha-summary";

const TIMEZONE_NA = -5;
const TIMEZONE_EU = 1;
const TIMEZONE_ASIA = 8;
const TIMEZONE_HKTWMO = 8;
const TIMEZONE_CN = 8;

export type uidServerInfo = {
  uid: string,
  serverTZ: number,
  serverName: hsrServer,
  regionType: string,
}

export type GachaInfo = {
  uid: string,
  gacha_id: number,
  gacha_type: 1 | 2 | 11 | 12,
  item_id: number,
  count: 1,
  time: string,
  name: string,
  lang: LanguageEnum,
  item_type: string,
  rank_type: 3 | 4 | 5,
  id: string,
  isPity: boolean,
  afterPulled: number
}

export type GachaSummary = {
  regionTimezone: number,
  rare5Gacha: GachaInfo[],
  rare4Gacha: GachaInfo[],
  rare5GachaAverage: number,
  rare5HavePityPercent: number,
  totalPulls: number,
  luckyRanking: number,
  isInit: boolean,
}

export const GachaPoolArray = [1, 2, 11, 12];

/**
 * 看不明是十分正常 感到混亂也是十分正常
 * 因爲我在開發這個功能的時候 思緒十分散
 * 先説一句Sorry ...
 */
export default class GachaHandler {
  //勿刪，Class實體化
  private gachaRequest = new GachaRequest();

  /**
   * 合拼抽卡紀錄（基本上只在透過authkey獲取抽卡紀錄會調用）
   * @param authkey authkey
   * @param arr 臨時存放的陣列
   * @param lang 語言
   * @param lastPageId 上一頁躍遷頁數 
   * @param gachaId 卡池種類ID
   * @param endId 停止的ID，用於合拼
   * @param lastId 上一個請求最後一個ID，用於接續請求
   * @param size 本頁請求躍遷項目數
   * @returns 官方躍遷紀錄JSON (GachaInfo封裝)
   */
  public async gachaCombineHandler(
    authkey: string | null = null,
    arr: GachaInfo[],
    lang?: LanguageEnum,
    lastPageId?: number,
    gachaId?: -1 | 1 | 2 | 11 | 12,
    lastId?: string,
    size?: number,
    language?: AppLanguage,
    regionType?: "GLOBAL" | "CN"
  ): Promise<GachaInfo[]> {
    size = (size && size > 20 ? 20 : size);
    if (gachaId === -1) {
      for (let x = 0; x < GachaPoolArray.length; x++) {
        //@ts-ignore
        let tmpArr: GachaInfo[] = []
        let tmpLastPageId = 0
        let tmpLastId = 0

        if (x === 0) {
          (await this.getGachaRecordByAuthKey(authkey, lang, 1, 11, "0", 1, regionType).then((getRecordArr: GachaInfo[]) => {
            if (getRecordArr.length > 0) {
              regionType = this.getServerInfoByUID(getRecordArr[0].uid).regionType
            }
          }))
        }

        await this.sleep(300).then(async () => {
          arr = arr.concat(await this.gachaCombineHandler(authkey, tmpArr, lang, tmpLastPageId, GachaPoolArray[x], tmpLastId, size, language, regionType) as GachaInfo[])
        });
      }
      return arr as GachaInfo[]
    }
    return await this.getGachaRecordByAuthKey(authkey, lang, lastPageId, gachaId, lastId, size, regionType).then(async (getRecordArr) => {
      const GachaPoolArrayLocale = [
        LOCALES[language].WrapStaticPool,
        LOCALES[language].WrapNewbiePool,
        LOCALES[language].WrapCharPool,
        LOCALES[language].WrapLcPool
      ]
      if (getRecordArr !== undefined && getRecordArr.length > 0) {
        lastId = getRecordArr[getRecordArr.length - 1].id;
        lastPageId = (lastPageId ? lastPageId : 0) + 1;
        arr = arr.concat(getRecordArr)
        Toast(
          LOCALES[language].WrapPopUpURLProgress
            .replace("${1}", (GachaPoolArrayLocale[GachaPoolArray.indexOf(gachaId)]))
            .replace("${2}", lastPageId),
          1,
          false
        )
        return await this.sleep(300).then(() => { return this.gachaCombineHandler(authkey, arr, lang, lastPageId, gachaId, lastId, size, language, regionType) }) as GachaInfo[]
      } else {
        return arr as GachaInfo[]
      }
    });


  }

  /**
   * 
   * @param ms 睡覺的毫秒
   * @returns 一個Promise，但你不需要讀它
   */
  public sleep(ms: number) {
    return new Promise(resolve => setTimeout(resolve, ms || 1000));
  }

  /**
   * 匯入躍遷紀錄
   * @param gachaData 躍遷紀錄
   */
  public importGachaRecord(gachaData: string) {
    AsyncStorage.setItem(GACHA_KEY, gachaData);
  }
  /**
 * 獲取躍遷紀錄 (GachaInfo包裝)
 */
  public async getGachaRecord() {
    return await AsyncStorage.getItem(GACHA_KEY).then((data) => {
      if (data) {
        return JSON.parse(data) as Array<GachaInfo>
      } else {
        return [];
      }
    })
  }

  /**
   * 設定躍遷總結資料
   * @param gachaSummary 躍遷總結
   */
  public setGachaSummary(gachaSummary: GachaSummary[]) {
    AsyncStorage.setItem(GACHA_SUMMARY_KEY, JSON.stringify(gachaSummary));
  }
  /**
   * 獲取躍遷總結資料 (GachaSummary包裝)
   */
  public async getGachaSummary() {
    return await AsyncStorage.getItem(GACHA_SUMMARY_KEY).then((data) => {
      if (data) {
        return JSON.parse(data) as GachaSummary[]
      } else {
        return [];
      }
    })
  }
  /**
 * 設定躍遷最後一筆ID (避免覆蓋)
 * @param gachaSummary 躍遷總結
 */
  public setGachaEndId(gachaEndId: string) {
    AsyncStorage.setItem(GACHA_END_ID_KEY, gachaEndId);
  }
  /**
   * 獲取躍遷最後一筆ID (避免覆蓋)
   */
  public async getGachaEndId() {
    return await AsyncStorage.getItem(GACHA_END_ID_KEY).then((data) => {
      return data === null ? "0" : data
    })
  }

  /**
   * 匯出躍遷紀錄
   * @param exportType 按哪個標準匯出 
   * @param lang 語言
   * @returns 按標準匯出的JSON 
   */
  public async exportGachaRecord(exportType: "SRGF", lang: LanguageEnum) {
    return AsyncStorage.getItem(GACHA_KEY).then((data) => {
      if (exportType === "SRGF") {
        const gachaList = (data === null ? { list: [] } : { list: JSON.parse(data) });
        const uid = gachaList.list[0]?.uid;
        const srgfHeader = {
          "info": {
            "uid": uid,
            "lang": lang,
            "region_time_zone": this.getServerInfoByUID(uid).serverTZ,
            "export_timestamp": Date.now() / 1000,
            "export_app": "Stargazer 2 (" + NativeModules.RNDeviceInfo?.bundleId + ")",
            "export_app_version": VERSION[ENV],
            "srgf_version": "v1.0"
          }
        };
        return { ...srgfHeader, ...gachaList };
      }
    });
  }

  /**
   * 從躍遷AuthKey獲取躍遷紀錄
   * @param authkey authkey
   * @param pageId 躍遷頁數
   * @param gachaType 躍遷卡池ID
   * @param endId 上一個請求最後一個ID，用於接續請求
   * @param size 請求躍遷紀錄項數
   * @returns 官方躍遷紀錄JSON (GachaInfo封裝)
   */
  public async getGachaRecordByAuthKey(
    authkey: string | null = null,
    lang?: LanguageEnum,
    pageId?: number,
    gachaType?: 1 | 2 | 11 | 12,
    endId?: string,
    size?: number,
    regionType?: "GLOBAL" | "CN"
  ): Promise<Array<GachaInfo>> {
    return this.gachaRequest
      ?.send(
        "https://" + (regionType === "CN" ? "api-takumi.mihoyo.com" : "api-os-takumi.hoyoverse.com") + "/common/gacha_record/api/getGachaLog?authkey_ver=1&sign_type=2" +
        "&game_biz=hkrpg_cn" +
        (lang ? "&lang=" + lang : "&lang=zh-tw") +
        (gachaType ? "&gacha_type=" + gachaType : "&gacha_type=1") +
        (pageId ? "&page=" + pageId : "") +
        (endId ? "&end_id=" + endId : "") +
        (size ? "&size=" + size : "") +
        (authkey ? "&authkey=" + authkey : "")
      )
      .then((dataJSON) => {
        if (dataJSON.retcode !== 0) {
          Toast(dataJSON.message, 3, false)
          console.log(dataJSON.message)
        }
        return (dataJSON.data === null || dataJSON.data == undefined ? [] : dataJSON.data.list) as GachaInfo[];
      })
      .catch((error) => {
        Toast(error, 3);
      });
  }

  /**
   * 
   * @param uid 遊戲UID (E.g. 900033852)
   * @returns "{ uid , serverTZ , serverName, regionType}"
   */
  public getServerInfoByUID(uid: string): uidServerInfo {
    switch (uid[0]) {
      case "1":
      case "2":
      case "3":
      case "4": return { uid, serverTZ: TIMEZONE_CN, serverName: "prod_gf_cn", regionType: "CN" }
      case "5": return { uid, serverTZ: TIMEZONE_CN, serverName: "prod_qd_cn", regionType: "CN" }
      case "6": return { uid, serverTZ: TIMEZONE_NA, serverName: "prod_official_usa", regionType: "GLOBAL" }
      case "7": return { uid, serverTZ: TIMEZONE_EU, serverName: "prod_official_eur", regionType: "GLOBAL" }
      case "8": return { uid, serverTZ: TIMEZONE_ASIA, serverName: "prod_official_asia", regionType: "GLOBAL" }
      case "9": return { uid, serverTZ: TIMEZONE_HKTWMO, serverName: "prod_official_cht", regionType: "GLOBAL" }
      default: return { uid, serverTZ: TIMEZONE_HKTWMO, serverName: "prod_official_usa", regionType: "GLOBAL" } //故意的，方便找到ERROR
    }
  }

  /**
   * 
   * @param wrapPityRate 抽卡歪了的機率
   * @param wrapAvgGetUP 平均出貨機率（當期限定UP）
   * @returns 介乎0-5的數值
   */
  public getWrapLuckyScore(wrapPityRate : number, wrapAvgGetUP : number) : number {
    let pityRateScore = 0;
    let avgGetUpScore = 0;

    pityRateScore = (wrapPityRate === -1 ? 0 : 5 - wrapPityRate * 5);
    avgGetUpScore = (wrapAvgGetUP !== undefined && wrapAvgGetUP > 0 && wrapAvgGetUP !== -1 ?
    (
      wrapAvgGetUP > 80 ? 0 : 
      wrapAvgGetUP > 70 ? 1 : 
      wrapAvgGetUP > 60 ? 2 : 
      wrapAvgGetUP > 50 ? 3 : 
      wrapAvgGetUP > 40 ? 4 : 5
    ) : -1)

    const divCheck = (2 - (wrapPityRate === -1 ? 1 : 0) - (wrapAvgGetUP === -1 ? 1 : 0))
    return divCheck > 0 ? Math.round((pityRateScore + avgGetUpScore) / divCheck) : 0
  }
}