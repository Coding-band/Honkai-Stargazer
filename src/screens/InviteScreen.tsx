import { View } from "react-native";
import React, { useRef } from "react";
import { StatusBar } from "expo-status-bar";
import { LinearGradient } from "expo-linear-gradient";
import Header from "../components/global/Header/Header";
import { SCREENS } from "../constant/screens";
import WallPaper from "../components/global/WallPaper/WallPaper";
import SettingList from "../components/SettingScreen/SettingList/SettingList";
import useAppLanguage from "../language/AppLanguage/useAppLanguage";
import InviteList from "../components/InviteScreen/InviteList/InviteList";

export default function InviteScreen() {
  const { language } = useAppLanguage();
  const scrollViewRef = useRef();

  return (
    <View style={{ flex: 1 }} className="overflow-hidden">
      <StatusBar style="dark" />
      <WallPaper isBlur />
      <LinearGradient
        className="absolute w-full h-full"
        colors={["#00000080", "#00000020"]}
      />

      <Header leftBtn="back" Icon={SCREENS.InvitePage.icon} scrollViewRef={scrollViewRef}>
        {SCREENS.InvitePage.getName(language)}
      </Header>
      <InviteList scrollViewRef={scrollViewRef}/>
      <LinearGradient
        className="w-full h-[600px] absolute bottom-0"
        colors={["#00000000", "#000000"]}
      />
    </View>
  );
}
