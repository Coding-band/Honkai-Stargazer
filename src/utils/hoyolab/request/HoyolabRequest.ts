import { hsrServerId } from "../servers/hsrServer.types";
import { LanguageEnum } from "../language/language.interface";
import Request from "./Request";
import { hsrServer } from "../servers/hsrServer";
import axios from "axios";

export default class HoyolabRequest {
  private request: Request;

  constructor(
    cookies: string | null = null,
    lang: LanguageEnum = LanguageEnum.TRADIIONAL_CHINESE
  ) {
    this.request = new Request(cookies, lang, "v1").setHeaders({
      Host: "bbs-api-os.hoyolab.com",
      "x-rpc-app_version": "1.5.0",
    });
  }

  //* 獲取 Hoyolab 遊戲紀錄
  public getGameRecord(hoyolabId: string) {
    const getUrl = (hoyolabId: string) =>
      `https://bbs-api-os.hoyolab.com/game_record/card/wapi/getGameRecordCard?uid=${hoyolabId}`;

    return this.request.send(getUrl(hoyolabId));
  }

  //* 獲取崩鐵完整用戶資料
  public getHsrFullData(uuid: string, server: hsrServerId = "asia") {
    const getUrl = (uuid: string, server: hsrServerId) =>
      `https://bbs-api-os.hoyolab.com/game_record/hkrpg/api/index?server=${hsrServer[server]}&role_id=${uuid}`;

    return this.request.send(getUrl(uuid, server));
  }

  //* 獲取用戶角色
  public getHsrCharactersData(uuid: string, server: hsrServerId = "asia") {
    const getUrl = (uuid: string, server: hsrServerId) =>
      `https://bbs-api-os.hoyolab.com/game_record/hkrpg/api/avatar/info?server=${hsrServer[server]}&role_id=${uuid}`;

    return this.request.send(getUrl(uuid, server));
  }

  //* 獲取崩鐵角色便籤
  public getHsrNote(uuid: string, server: hsrServerId = "asia") {
    const getUrl = (uuid: string, server: hsrServerId) =>
      `https://bbs-api-os.hoyolab.com/game_record/hkrpg/api/note?server=${hsrServer[server]}&role_id=${uuid}`;

    return this.request.send(getUrl(uuid, server));
  }

  //* 獲取混沌回憶資料
  public getHsrMemoryOfChaos(uuid: string, server: hsrServerId = "asia") {
    const getUrl = (uuid: string, server: hsrServerId) =>
      `https://bbs-api-os.hoyolab.com/game_record/hkrpg/api/challenge?server=${hsrServer[server]}&role_id=${uuid}&schedule_type=1&need_all=true`;

    return this.request.send(getUrl(uuid, server));
  }

  //* 獲取活動列表
  public getHsrEventList(uuid: string, server: hsrServerId = "asia") {
    return axios.get(
      `https://sg-hkrpg-api.hoyoverse.com/common/hkrpg_global/announcement/api/getAnnList?game=hkrpg&game_biz=hkrpg_global&lang=zh-tw&bundle_id=hkrpg_global&level=55&platform=pc&region=prod_official_cht&uid=900000000`
    );
  }

  //* 獲取活動
  public getHsrEvent(uuid: string, server: hsrServerId = "asia") {
    return axios.get(
      `https://sg-hkrpg-api.hoyoverse.com/common/hkrpg_global/announcement/api/getAnnContent?game=hkrpg&game_biz=hkrpg_global&lang=zh-tw&bundle_id=hkrpg_global&level=55&platform=pc&region=prod_official_cht&uid=900000000`
    );
  }
}
