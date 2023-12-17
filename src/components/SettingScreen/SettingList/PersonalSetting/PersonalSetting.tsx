import { View, Text } from "react-native";
import React, { useState } from "react";
import SettingGroup from "../../SettingGroup/SettingGroup";
import SettingItem from "../../SettingGroup/SettingItem/SettingItem";
import useLocalState from "../../../../hooks/useLocalState";

const fonts = [
  { name: "開", value: true },
  { name: "關", value: false },
];

export default function PersonalSetting() {
  const [font, setFont] = useLocalState("use-custom-font", fonts[0].value);

  return (
    <SettingGroup title="個性化">
      <SettingItem
        type="list"
        title="使用游戏字体"
        list={fonts}
        value={font}
        onChange={setFont}
      />
      <SettingItem type="navigation" title="更换壁纸" content="壁紙一" />
    </SettingGroup>
  );
}
