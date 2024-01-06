import React from "react";
import SettingGroup from "../../SettingGroup/SettingGroup";
import SettingItem from "../../SettingGroup/SettingItem/SettingItem";
import Toast from "../../../../utils/toast/Toast";
import useAppLanguage from "../../../../language/AppLanguage/useAppLanguage";
import { LOCALES } from "../../../../../locales";

export default function NotificationSetting() {
  const { language } = useAppLanguage();
  return (
    <SettingGroup title="通知">
      <SettingItem
        type="navigation"
        title={LOCALES[language].NotifiAll}
        content={LOCALES[language].SwitchOff}
        onNavigate={() => {
          Toast.StillDevelopingToast();
        }}
      />
      <SettingItem
        type="navigation"
        title={LOCALES[language].Stamina}
        content={LOCALES[language].SwitchOff}
        onNavigate={() => {
          Toast.StillDevelopingToast();
        }}
      />
      <SettingItem
        type="navigation"
        title={LOCALES[language].Stamina}
        content={LOCALES[language].SwitchOff}
        onNavigate={() => {
          Toast.StillDevelopingToast();
        }}
      />
      <SettingItem
        type="navigation"
        title={LOCALES[language].NotiMission}
        content={LOCALES[language].SwitchOff}
        onNavigate={() => {
          Toast.StillDevelopingToast();
        }}
      />
      <SettingItem
        type="navigation"
        title={LOCALES[language].SimulatedUniverse}
        content={LOCALES[language].SwitchOff}
        onNavigate={() => {
          Toast.StillDevelopingToast();
        }}
      />
    </SettingGroup>
  );
}
