import { View, Text } from "react-native";
import React, { useState } from "react";
import SettingGroup from "../../SettingGroup/SettingGroup";
import SettingItem from "../../SettingGroup/SettingItem/SettingItem";
import useLocalState from "../../../../hooks/useLocalState";
import { useNavigation } from "@react-navigation/native";
import { SCREENS } from "../../../../constant/screens";
import useWallPaper from "../../../../redux/wallPaper/useWallPaper";
import useAppLanguage from "../../../../language/AppLanguage/useAppLanguage";
import { LOCALES } from "../../../../../locales";
import useDoUseCustomFont from "../../../../redux/doUseCustomFont/useDoUseCustomFont";
import useDoUseBlurEffect from "../../../../redux/doUseBlurEffect/useDoUseBlurEffect";

export default function PersonalSetting() {
  const navigation = useNavigation();
  const { language } = useAppLanguage();

  // 字體
  const switchs = [
    { name: LOCALES[language].SwitchOn, value: true },
    { name: LOCALES[language].SwitchOff, value: false },
  ];
  const { doUseCustomFont, setDoUseCustomFont } = useDoUseCustomFont();
  const { doUseBlurEffect, setDoUseBlurEffect } = useDoUseBlurEffect();

  // 壁紙
  const { wallPaper } = useWallPaper();

  // 模糊效果

  return (
    <SettingGroup title={LOCALES[language].Customize}>
      {/* 字體 */}
      {/* <SettingItem
        type="list"
        title={LOCALES[language].UseHSRFont}
        list={switchs}
        value={doUseCustomFont}
        onChange={setDoUseCustomFont}
      /> */}
      {/* 壁紙 */}
      <SettingItem
        type="navigation"
        title={LOCALES[language].ChangeWallPaper}
        content={wallPaper?.name}
        onNavigate={() => {
          // @ts-ignore
          navigation.navigate(SCREENS.WallPaperPage.id);
        }}
      />
      {/* 模糊效果 */}
      <SettingItem
        type="list"
        title={LOCALES[language].UseBlurEffect}
        list={switchs}
        value={doUseBlurEffect}
        onChange={setDoUseBlurEffect}
      />
    </SettingGroup>
  );
}
