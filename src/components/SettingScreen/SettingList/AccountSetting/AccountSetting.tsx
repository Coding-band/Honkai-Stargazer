import { View, Text } from "react-native";
import React from "react";
import SettingGroup from "../../SettingGroup/SettingGroup";
import SettingItem from "../../SettingGroup/SettingItem/SettingItem";

export default function AccountSetting() {
  return (
    <SettingGroup title="账号设置(108289390)">
      <SettingItem type="navigation" title="使用邀請碼" content="已使用" />
    </SettingGroup>
  );
}
