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

export default function PersonalSetting() {
  const navigation = useNavigation();
  const { language } = useAppLanguage();

  const fonts = [
    { name: LOCALES[language].SwitchOn, value: true },
    { name: LOCALES[language].SwitchOff, value: false },
  ];
  const { doUseCustomFont, setDoUseCustomFont } = useDoUseCustomFont();

  const { wallPaper } = useWallPaper();

  return (
    <SettingGroup title={LOCALES[language].Customize}>
      {/* <SettingItem
        type="list"
        title={LOCALES[language].UseHSRFont}
        list={fonts}
        value={doUseCustomFont}
        onChange={setDoUseCustomFont}
      /> */}
      <SettingItem
        type="navigation"
        title={LOCALES[language].ChangeWallPaper}
        content={wallPaper?.name}
        onNavigate={() => {
          // @ts-ignore
          navigation.navigate(SCREENS.WallPaperPage.id);
        }}
      />
    </SettingGroup>
  );
}
