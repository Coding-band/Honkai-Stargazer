import {
  TextLanguage as TextLanguageType,
  AppLanguage as AppLanguageType,
} from "../types/language";

export const Language = {
  en: "English",
  zh_cn: "简体中文",
  zh_hk: "繁體中文",
  jp: "日本語",
};
export const TextLanguage: TextLanguageType[] = ["en", "zh_cn", "zh_hk", "jp"];
export const AppLanguage: AppLanguageType[] = ["zh_cn", "zh_hk"];
