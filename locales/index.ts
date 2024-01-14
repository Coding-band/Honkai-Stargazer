import { strings as EN } from "./en/strings";
import { strings as ZH_CN } from "./zh-CN/strings";
import { strings as ZH_HK } from "./zh-HK/strings";
import { strings as VOCCHINESE } from "./yue/strings";
import { strings as JP } from "./ja/strings";
/*
import { strings as ES } from "./es-ES/strings";
import { strings as DE } from "./de/strings";
import { strings as FR } from "./fr/strings";
import { strings as ID } from "./id/strings";
import { strings as KR } from "./ko/strings";
import { strings as PT } from "./pt-PT/strings";
import { strings as RU } from "./ru/strings";
import { strings as TH } from "./th/strings";
import { strings as VI } from "./vi/strings";

 NOT IN USED NOW
*/

export const LOCALES = {
  en: fallbackLang(EN),
  zh_cn: fallbackLang(ZH_CN),
  zh_hk: fallbackLang(ZH_HK),
  vocchinese: fallbackLang(VOCCHINESE),
  jp: fallbackLang(JP),
};

function fallbackLang(lang: any) {
  return { ...ZH_HK, ...lang };
}
