import { View, Text } from "react-native";
import React from "react";
import SettingGroup from "../../SettingGroup/SettingGroup";
import SettingItem from "../../SettingGroup/SettingItem/SettingItem";
import Toast from "../../../../utils/toast/Toast";
import useAppLanguage from "../../../../context/AppLanguage/useAppLanguage";
import { LOCALES } from "../../../../../locales";

export default function AccountSetting() {
  const {language} = useAppLanguage();
  return (
    <SettingGroup title="账号设置(108289390)">
      <SettingItem
        type="navigation"
        title={LOCALES[language].UseInviteCode}
        content={LOCALES[language].HaveNotUsed}
        onNavigate={() => {
          Toast.StillDevelopingToast();
        }}
      />
    </SettingGroup>
  );
}
