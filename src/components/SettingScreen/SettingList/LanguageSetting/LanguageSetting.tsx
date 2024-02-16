import React, { useState } from "react";
import SettingGroup from "../../SettingGroup/SettingGroup";
import SettingItem from "../../SettingGroup/SettingItem/SettingItem";
import useTextLanguage from "../../../../language/TextLanguage/useTextLanguage";
import useAppLanguage from "../../../../language/AppLanguage/useAppLanguage";
import {
  AppLanguage,
  Language,
  TextLanguage,
  isGptTranslate,
} from "../../../../language/language";
import _ from "lodash";
import { LOCALES } from "../../../../../locales";
import Toast from "../../../../utils/toast/Toast";
import useLocalState from "../../../../hooks/useLocalState";

const textLanguages = TextLanguage.map((lan) => ({
  name: Language[lan],
  value: lan,
}));

//不是不知道怎樣做，而是感覺這個彩蛋不應該被隱藏，
//已經和2O48妥協，凡是選擇繁體中文，在設定頁語言部分會展示一個按鈕
//用戶按下後就會解鎖Vocchinese & 注音
//因為是繁體中文特例，暫時不會安排翻譯。
const appLanguages = AppLanguage.map((lan) => ({
  name: Language[lan]+ (isGptTranslate[lan] ? "（GPT）" : ""),
  value: lan,
}));

export default function LanguageSetting() {
  const { language: textLanguage, setLanguage: setTextLanguage } =
    useTextLanguage();

  const { language: appLanguage, setLanguage: setAppLanguage } =
    useAppLanguage();

  const [easterEggAppear, setIsEasterEggAppear] = useLocalState<boolean>(
    "text-easteregg-appear",
    false
  );
  
  
  return (
    <SettingGroup title={LOCALES[appLanguage].LanguageSetup}>
      <SettingItem
        type="list"
        title={LOCALES[appLanguage].DocumentLanguage}
        list={textLanguages}
        value={textLanguage}
        onChange={setTextLanguage}
      />
      <SettingItem
        type="list"
        title={LOCALES[appLanguage].AppLanguage}
        list={appLanguages.filter((lang) => 
          easterEggAppear ? true : (lang.value !== "vocchinese" && lang.value !== "jyu_yam")
        )}
        value={appLanguage}
        onChange={setAppLanguage}
      />

      {
        (appLanguage === 'zh_hk' && !easterEggAppear) && 
        <SettingItem
          type="none"
          title={"甚麼？語言也有彩蛋？"}
          content={"解鎖"}
          onNavigate={() => {
            Toast("已經解鎖Vocchinese 和 ㄓㄨˋ ㄧㄣ！")
            setIsEasterEggAppear(true)
          }}
        />
      }
    </SettingGroup>
  );
}
