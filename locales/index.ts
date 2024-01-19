import { strings as EN } from "./en/strings";
import { strings as ZH_CN } from "./zh-CN/strings";
import { strings as ZH_HK } from "./zh-HK/strings";
import { strings as VOCCHINESE } from "./yue/strings";
import { strings as JP } from "./ja/strings";
import { strings as JyuYam } from "./zh/strings"; //注音
import { strings as ES } from "./es-ES/strings";
import { strings as PT } from "./pt-PT/strings";
import { strings as RU } from "./ru/strings";

import { longStrings as EN_LONG } from "./en/long_strings";
import { longStrings as ZH_CN_LONG } from "./zh-CN/long_strings";
import { longStrings as ZH_HK_LONG } from "./zh-HK/long_strings";
import { longStrings as VOCCHINESE_LONG } from "./yue/long_strings";
import { longStrings as JP_LONG } from "./ja/long_strings";
import { longStrings as PT_LONG } from "./pt-PT/long_strings";
import { longStrings as JyuYam_LONG } from "./zh/long_strings"; //注音
import { longStrings as ES_LONG } from "./es-ES/long_strings";
import { longStrings as RU_LONG } from "./ru/long_strings";

/*
import { strings as DE } from "./de/strings";
import { strings as FR } from "./fr/strings";
import { strings as ID } from "./id/strings";
import { strings as KR } from "./ko/strings";
import { strings as PT } from "./pt-PT/strings";
import { strings as TH } from "./th/strings";
import { strings as VI } from "./vi/strings";

 NOT IN USED NOW
*/

export const LOCALES = {
  en: fallbackLang(EN,EN_LONG),
  zh_cn: fallbackLang(ZH_CN,ZH_CN_LONG),
  zh_hk: fallbackLang(ZH_HK,ZH_HK_LONG),
  vocchinese: fallbackLang(VOCCHINESE,VOCCHINESE_LONG),
  jp: fallbackLang(JP,JP_LONG),
  jyu_yam: fallbackLang(JyuYam,JyuYam_LONG), //注音
  es: fallbackLang(ES,ES_LONG),
  ru: fallbackLang(RU,RU_LONG),
  pt: fallbackLang(PT,PT_LONG),
};

function fallbackLang(lang: any, langLong: any) {
  return { ...ZH_HK, ...langLong, ...lang };
}
