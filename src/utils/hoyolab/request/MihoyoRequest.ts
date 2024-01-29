import { hsrServerId } from "../servers/hsrServer.types";
import { LanguageEnum } from "../language/language.interface";
import Request from "./Request";
import { hsrServer } from "../servers/hsrServer";
import axios from "axios";

export default class MihoyoRequest {
  private request: Request;
  private lang: LanguageEnum;

  constructor(
    cookies: string | null = null,
    lang: LanguageEnum = LanguageEnum.SIMPLIFIED_CHINESE
  ) {
    this.request = new Request(cookies, lang, "v2").setHeaders({
      Host: "api-takumi-record.mihoyo.com",
      "x-rpc-app_version": "2.65.2",
    });
    this.lang = lang;
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

  //* 獲取用戶角色
  public getHsrCharactersData(uuid: string, server: hsrServerId = "asia") {
    const getUrl = (uuid: string, server: hsrServerId) =>
      `https://api-takumi-record.mihoyo.com/game_record/app/hkrpg/api/avatar/info?server=${hsrServer[server]}&role_id=${uuid}`;

    return this.request.send(getUrl(uuid, server));
  }

  //* 獲取崩鐵角色便籤
  public getHsrNote(uuid: string, server: hsrServerId = "asia") {
    const getUrl = (uuid: string, server: hsrServerId) =>
      `https://api-takumi-record.mihoyo.com/game_record/app/hkrpg/api/note?server=${hsrServer[server]}&role_id=${uuid}`;

    return this.request.send(getUrl(uuid, server));
  }

  //* 獲取混沌回憶資料
  public getHsrMemoryOfChaos(
    uuid: string,
    server: hsrServerId = "asia",
    scheduleType: 1 | 2
  ) {
    const getUrl = (uuid: string, server: hsrServerId) =>
      `https://api-takumi-record.mihoyo.com/game_record/app/hkrpg/api/challenge?server=${hsrServer[server]}&role_id=${uuid}&schedule_type=${scheduleType}&need_all=true`;

    return this.request.send(getUrl(uuid, server));
  }

  //* 獲取虛構敘事資料
  public getHsrPureFiction(
    uuid: string,
    server: hsrServerId = "asia",
    scheduleType: 1 | 2
  ) {
    const getUrl = (uuid: string, server: hsrServerId) =>
      `https://api-takumi-record.mihoyo.com/game_record/app/hkrpg/api/challenge_story?server=${hsrServer[server]}&role_id=${uuid}&schedule_type=${scheduleType}&need_all=true`;

    return this.request.send(getUrl(uuid, server));
  }

  //* 獲取活動列表
  public getHsrEventList(uuid: string, server: hsrServerId = "asia") {
    return axios.get(
      `https://sg-hkrpg-api.hoyoverse.com/common/hkrpg_global/announcement/api/getAnnList?game=hkrpg&game_biz=hkrpg_global&lang=${this.lang}&bundle_id=hkrpg_global&level=55&platform=pc&region=prod_official_cht&uid=900000000`
    );
  }

  //* 獲取活動
  public getHsrEvent(uuid: string, server: hsrServerId = "asia") {
    return axios.get(
      `https://sg-hkrpg-api.hoyoverse.com/common/hkrpg_global/announcement/api/getAnnContent?game=hkrpg&game_biz=hkrpg_global&lang=${this.lang}&bundle_id=hkrpg_global&level=55&platform=pc&region=prod_official_cht&uid=900000000`
    );
  }
}
