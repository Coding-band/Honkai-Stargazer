import React from "react";
import SettingGroup from "../../SettingGroup/SettingGroup";
import SettingItem from "../../SettingGroup/SettingItem/SettingItem";
import Toast from "react-native-root-toast";

export default function NotificationSetting() {
  return (
    <SettingGroup title="通知">
      <SettingItem
        type="navigation"
        title="所有通知"
        content="關"
        onNavigate={() => {
          Toast.show("此功能還在開發中", {
            duration: 3000,
            position: Toast.positions.CENTER,
            shadow: true,
            animation: true,
            hideOnPress: true,
            delay: 0,
            opacity: 1,
            containerStyle: {
              paddingHorizontal: 10,
              paddingVertical: 8,
              backgroundColor: "#F3F9FF",
            },
            textStyle: {
              fontFamily: "HY65",
              color: "#222",
            },
          });
        }}
      />
      <SettingItem type="navigation" title="开拓力" content="關" />
      <SettingItem type="navigation" title="派遣委托" content="關" />
      <SettingItem type="navigation" title="每日实训" content="關" />
      <SettingItem type="navigation" title="模拟宇宙" content="關" />
    </SettingGroup>
  );
}
