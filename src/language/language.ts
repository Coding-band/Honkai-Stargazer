import {
  TextLanguage as TextLanguageType,
  AppLanguage as AppLanguageType,
} from "./language.types";

export const Language = {
  vocchinese: "Vocchinese", //Easter Egg
  en: "English",
  zh_cn: "简体中文",
  zh_hk: "繁體中文",
  jp: "日本語",
  fr: "Français",
  ru: "Русский",
  de: "Deutsch",
  pt: "Português",
  vi: "tiếng Việt",
  es: "Español",
  kr: "한국어",
  th: "ภาษาไทย",
  jyu_yam: "ㄓㄨˋ ㄧㄣ",
};
export const TextLanguage: TextLanguageType[] = [
  "en",
  "zh_cn",
  "zh_hk",
  "jp",
  "fr",
  "ru",
  "de",
  "pt",
  "vi",
  "es",
  "kr",
  "th",
];
export const AppLanguage: AppLanguageType[] = [
  "en",
  "zh_cn",
  "zh_hk",
  "vocchinese",
  "jp",
  "jyu_yam",
  "es",
  "ru",
];
