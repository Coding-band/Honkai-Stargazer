import { View, ScrollView } from "react-native";
import React, { useState } from "react";
import SettingGroup from "../SettingGroup/SettingGroup";
import SettingItem from "../SettingGroup/SettingItem/SettingItem";
import AccountSetting from "./AccountSetting/AccountSetting";
import LanguageSetting from "./LanguageSetting/LanguageSetting";
import PersonalSetting from "./PersonalSetting/PersonalSetting";
import NotificationSetting from "./NotificationSetting/NotificationSetting";

export default function SettingList() {
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
        <SettingGroup title="支持我们">
          <SettingItem type="navigation" title="捐赠" />
          <SettingItem type="navigation" title="邀请他人" />
        </SettingGroup>
        <SettingGroup title="开发者选项">
          <SettingItem type="navigation" title="使用Cookies登录" />
        </SettingGroup>
        <SettingGroup title="特別感謝">
          <SettingItem type="navigation" title="開發人員" />
          <SettingItem type="navigation" title="開源感謝" />
        </SettingGroup>
      </View>
    </ScrollView>
  );
}
