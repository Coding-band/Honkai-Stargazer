import React from "react";
import SettingGroup from "../../SettingGroup/SettingGroup";
import SettingItem from "../../SettingGroup/SettingItem/SettingItem";
import { LOCALES } from "../../../../../locales";
import useAppLanguage from "../../../../language/AppLanguage/useAppLanguage";
import { VERSION } from "../../../../../app.config";
import * as Device from "expo-device";

export default function DevelopmentSetting() {
  const { language } = useAppLanguage();

  return (
    <SettingGroup title={LOCALES[language].DevOptions}>
      <SettingItem
        type="navigation"
        title={LOCALES[language].AppVersion}
        content={VERSION.production}
      />
      <SettingItem
        type="navigation"
        title={LOCALES[language].OsVersion}
        content={`${Device.osName} ${Device.osVersion}`}
      />
    </SettingGroup>
  );
}
