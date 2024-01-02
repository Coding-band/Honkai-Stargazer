import { hsrServerId } from "../servers/hsrServer.types";
import { LanguageEnum } from "../language/language.interface";
import Request from "./Request";
import { hsrServer } from "../servers/hsrServer";
import generateDsV2 from "../ds/generateDsV2";

export default class MihoyoRequest {
  private request: Request;

  constructor(
    cookies: string | null = null,
    lang: LanguageEnum = LanguageEnum.SIMPLIFIED_CHINESE
  ) {
    this.request = new Request(cookies, lang, "v2").setHeaders({
      Host: "api-takumi-record.mihoyo.com",
      "x-rpc-app_version": "2.65.2",
    });
  }

  //* 獲取米游社遊戲紀錄
  public getGameRecord(mihoyoId: string) {
    const getUrl = (mihoyoId: string) =>
      `https://api-takumi-record.mihoyo.com/game_record/app/card/wapi/getGameRecordCard?uid=${mihoyoId}`;

    return this.request.send(getUrl(mihoyoId));
  }

  //* 獲取崩鐵完整用戶資料
  public getHsrFullData(uuid: string, server: hsrServerId = "asia") {
    const getUrl = (uuid: string, server: hsrServerId) =>
      `https://api-takumi-record.mihoyo.com/game_record/app/hkrpg/api/index?server=${hsrServer[server]}&role_id=${uuid}`;

    return this.request.send(getUrl(uuid, server));
  }

  //* 獲取崩鐵角色便籤
  public getHsrNote(uuid: string, server: hsrServerId = "asia") {
    const getUrl = (uuid: string, server: hsrServerId) =>
      `https://api-takumi-record.mihoyo.com/game_record/app/hkrpg/api/note?server=${hsrServer[server]}&role_id=${uuid}`;

    return this.request.send(getUrl(uuid, server));
  }

  //* 獲取混沌回憶資料
  public getHsrMemoryOfChaos(uuid: string, server: hsrServerId = "asia") {
    const getUrl = (uuid: string, server: hsrServerId) =>
      `https://api-takumi-record.mihoyo.com/game_record/app/hkrpg/api/challenge?server=${hsrServer[server]}&role_id=${uuid}&schedule_type=1&need_all=true`;

    return this.request.send(getUrl(uuid, server));
  }
}
