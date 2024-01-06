import { View, Text } from "react-native";
import React from "react";
import useAppLanguage from "../../../../language/AppLanguage/useAppLanguage";
import { LOCALES } from "../../../../../locales";
import SettingGroup from "../../SettingGroup/SettingGroup";
import SettingItem from "../../SettingGroup/SettingItem/SettingItem";
import Toast from "../../../../utils/toast/Toast";

export default function SpecialThanksSetting() {
  const { language } = useAppLanguage();
  return (
    <SettingGroup title={LOCALES[language].SpecialThanks}>
      <SettingItem
        type="navigation"
        title={LOCALES[language].SpecialThanksDevs}
        onNavigate={() => {
          Toast.StillDevelopingToast();
        }}
      />
      <SettingItem
        type="navigation"
        title={LOCALES[language].SpecialThanksOpenSource}
        onNavigate={() => {
          Toast.StillDevelopingToast();
        }}
      />
    </SettingGroup>
  );
}
