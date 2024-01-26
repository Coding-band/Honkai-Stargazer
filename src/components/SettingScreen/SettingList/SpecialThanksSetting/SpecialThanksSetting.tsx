import { View, Text } from "react-native";
import React from "react";
import useAppLanguage from "../../../../language/AppLanguage/useAppLanguage";
import { LOCALES } from "../../../../../locales";
import SettingGroup from "../../SettingGroup/SettingGroup";
import SettingItem from "../../SettingGroup/SettingItem/SettingItem";
import Toast from "../../../../utils/toast/Toast";
import { useNavigation } from "@react-navigation/native";
import { SCREENS } from "../../../../constant/screens";
import { Star } from "phosphor-react-native";
import AboutTheApp from "./AboutTheApp1/AboutTheApp";

export default function SpecialThanksSetting() {
  const navigation = useNavigation();
  const { language } = useAppLanguage();

  return (
    <SettingGroup title={LOCALES[language].About}>
      <SettingItem
        type="navigation"
        title={LOCALES[language].AboutTheApp}
        onNavigate={() => {
          // @ts-ignore
          navigation.navigate(SCREENS.DescriptionPage.id, {
            title: LOCALES[language].AboutTheApp,
            icon: Star,
            content: <AboutTheApp />,
          });
        }}
      />
      {/* <SettingItem
        type="navigation"
        title={LOCALES[language].SpecialThanksDevs}
        onNavigate={() => {
          Toast.StillDevelopingToast();
        }}
      /> */}
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
