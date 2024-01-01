import { View, ScrollView } from "react-native";
import React, { useState } from "react";
import SettingGroup from "../SettingGroup/SettingGroup";
import SettingItem from "../SettingGroup/SettingItem/SettingItem";
import AccountSetting from "./AccountSetting/AccountSetting";
import LanguageSetting from "./LanguageSetting/LanguageSetting";
import PersonalSetting from "./PersonalSetting/PersonalSetting";
import NotificationSetting from "./NotificationSetting/NotificationSetting";
import SupportSetting from "./SupportSetting/SupportSetting";
import useAppLanguage from "../../../context/AppLanguage/useAppLanguage";
import { LOCALES } from "../../../../locales";

export default function SettingList() {
  const {language} = useAppLanguage();
  return (
    <ScrollView
      style={{
        paddingVertical: 127,
        paddingHorizontal: 17,
        paddingBottom: 0,
      }}
      className="z-30 h-screen"
    >
      <View style={{ gap: 20 }} className="pb-48">
        <AccountSetting />
        <LanguageSetting />
        <PersonalSetting />
        <NotificationSetting />
        <SupportSetting />
        <SettingGroup title={LOCALES[language].DevOptions}>
          <SettingItem type="navigation" title={LOCALES[language].DevUseCookieLogin} />
        </SettingGroup>
        <SettingGroup title={LOCALES[language].SpecialThanks}>
          <SettingItem type="navigation" title={LOCALES[language].SpecialThanksDevs} />
          <SettingItem type="navigation" title={LOCALES[language].SpecialThanksOpenSource} />
        </SettingGroup>
      </View>
    </ScrollView>
  );
}
