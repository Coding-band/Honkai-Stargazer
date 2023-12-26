import { hsrServerId } from "../servers/hsrServer.types";
import { LanguageEnum } from "../language/language.interface";
import Request from "./Request";
import { hsrServer } from "../servers/hsrServer";

export default class MihoyoRequest {
  private request: Request;

  constructor(
    cookies: string | null = null,
    lang: LanguageEnum = LanguageEnum.SIMPLIFIED_CHINESE
  ) {
    this.request = new Request(cookies, "mihoyo", lang);
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
}
