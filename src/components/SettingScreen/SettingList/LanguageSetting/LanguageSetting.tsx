import React from "react";
import SettingGroup from "../../SettingGroup/SettingGroup";
import SettingItem from "../../SettingGroup/SettingItem/SettingItem";
import useTextLanguage from "../../../../context/TextLanguage/useTextLanguage";
import useAppLanguage from "../../../../context/AppLanguage/useAppLanguage";
import {
  AppLanguage,
  Language,
  TextLanguage,
} from "../../../../language/language";
import _ from "lodash";
import { LOCALES } from "../../../../../locales";

const textLanguages = TextLanguage.map((lan) => ({
  name: Language[lan],
  value: lan,
}));

const appLanguages = AppLanguage.map((lan) => ({
  name: Language[lan],
  value: lan,
}));

export default function LanguageSetting() {
  const { language: textLanguage, setLanguage: setTextLanguage } =
    useTextLanguage();

  const { language: appLanguage, setLanguage: setAppLanguage } =
    useAppLanguage();

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
        list={appLanguages}
        value={appLanguage}
        onChange={setAppLanguage}
      />
    </SettingGroup>
  );
}
