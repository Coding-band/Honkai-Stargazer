import AsyncStorage from "@react-native-async-storage/async-storage";
import GachaRequest, { IResponse } from "./GachaRequest";
import { LanguageEnum } from "../hoyolab/language/language.interface";
import { hsrServer } from "../hoyolab/servers/hsrServer.types";
import { ENV, PACKAGE_NAME, VERSION } from "../../../app.config";
import { NativeModules } from "react-native";

export const GACHA_KEY = "user-gacha-data";

const TIMEZONE_NA = -5;
const TIMEZONE_EU = 1;
const TIMEZONE_ASIA = 8;
const TIMEZONE_HKTWMO = 8;
const TIMEZONE_CN = 8;

export type uidServerInfo = {
  uid : string,
  serverTZ : number,
  serverName : hsrServer
}

export type GachaInfo = {
  uid : string,
  gacha_id : string,
  gacha_type : 1 | 2 | 11 | 12,
  item_id : number,
  count : 1,
  time : string,
  name : string,
  lang : LanguageEnum,
  item_type : string,
  rank_type : 3 | 4 | 5,
  id : string,
  isPity : boolean,
  afterPulled : number
}

export const GachaPoolArray = [1,2,11,12];

export default class GachaHandler {
  private gachaRequest = new GachaRequest();

  /**
   * 獲取躍遷紀錄 (GachaInfo包裝)
   */
  public getGachaRecord(){
    return AsyncStorage.getItem(GACHA_KEY).then((data) => {
      if(data){
        return JSON.parse(data) as Array<GachaInfo>
      }else{
        return [];
      }
    })
  }

  public async gachaCombineHandler(
    authkey: string | null = null,
    arr : GachaInfo[],
    lang?: LanguageEnum,
    lastPageId?: number,
    gachaId? : -1 | 1 | 2 | 11 | 12,
    lastId? : string,
    size? : number
  ): Promise<GachaInfo[]> {
    size = (size && size > 20 ? 20 : size);
    if(gachaId === -1){
      for(let x = 0 ; x < GachaPoolArray.length ; x ++){
        //@ts-ignore
        let tmpArr : GachaInfo[] = [] 
        let tmpLastPageId = 0
        let tmpLastId = 0
        await this.sleep(300).then(async() => {
          arr = arr.concat(await this.gachaCombineHandler(authkey,tmpArr,lang,tmpLastPageId,GachaPoolArray[x],tmpLastId,size))
        });
      }
      return arr
    }
    return await this.getGachaRecordByAuthKey(authkey,lang,lastPageId,gachaId,lastId,size).then(async (getRecordArr) => {
      if(getRecordArr !== undefined && getRecordArr.length > 0){
        lastId = getRecordArr[getRecordArr.length - 1].id;
        lastPageId = (lastPageId ? lastPageId : 0) + 1;
        arr = arr.concat(getRecordArr)
        console.log(gachaId)
        return await this.sleep(300).then(() => {return this.gachaCombineHandler(authkey,arr,lang,lastPageId,gachaId,lastId,size)})
      }else{
        return arr
      }
    });
    
    
  }

  public sleep(ms: number) {
    return new Promise(resolve => setTimeout(resolve, ms || 1000));
  }

  /**
   * 匯入躍遷紀錄
   * @param gachaData 躍遷紀錄
   */
  public importGachaRecord(gachaData : string){
    AsyncStorage.setItem(GACHA_KEY, gachaData);
  }

  /**
   * 匯出躍遷紀錄
   * @param exportType 按哪個標準匯出 
   * @param lang 語言
   * @returns 按標準匯出的JSON 
   */
  public async exportGachaRecord(exportType: "SRGF", lang : LanguageEnum){
    return AsyncStorage.getItem(GACHA_KEY).then((data) => {
      if (exportType === "SRGF") {
        const gachaList = (data === null ? {list:[]} : {list : JSON.parse(data)});
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
    size?: number
  ): Promise<Array<GachaInfo>> {
    return this.gachaRequest
      ?.send(
        "https://api-os-takumi.hoyoverse.com/common/gacha_record/api/getGachaLog?authkey_ver=1&sign_type=2" +
          "&game_biz=hkrpg_cn" +
          (lang ? "&lang="+lang : "&lang=zh-tw")+
          (gachaType ? "&gacha_type=" + gachaType :  "&gacha_type=1")+
          (pageId ? "&page=" + pageId : "")+
          (endId ? "&end_id=" + endId : "")+
          (size ? "&size=" + size : "")+
          (authkey ? "&authkey="+authkey : "")
      )
      .then((dataJSON) => {
        console.log(dataJSON.message)
        return dataJSON.data.list;
      })
      .catch((error) => {
        console.log(error);
      });
  }

  public getServerInfoByUID(uid : string) : uidServerInfo {
    switch(uid[0]){
        case "1" :
        case "2" :
        case "3" :
        case "4" : return {uid, serverTZ : TIMEZONE_CN, serverName : "prod_gf_cn"}
        case "5" : return {uid, serverTZ : TIMEZONE_CN, serverName : "prod_qd_cn"}
        case "6" : return {uid, serverTZ : TIMEZONE_NA, serverName : "prod_official_usa"}
        case "7" : return {uid, serverTZ : TIMEZONE_EU, serverName : "prod_official_eur"}
        case "8" : return {uid, serverTZ : TIMEZONE_ASIA, serverName : "prod_official_asia"}
        case "9" : return {uid, serverTZ : TIMEZONE_HKTWMO, serverName : "prod_official_cht"}
        default : return {uid, serverTZ: TIMEZONE_HKTWMO, serverName : "prod_official_usa"} //故意的，方便找到ERROR
    }
  }
}
