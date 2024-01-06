import { View } from "react-native";
import React from "react";
import { LinearGradient } from "expo-linear-gradient";
import Header from "../components/global/Header/Header";
import { StatusBar } from "expo-status-bar";
import { SCREENS } from "../constant/screens";
import LcList from "../components/LightconeListScreen/LcList/LcList";
import LcAction from "../components/LightconeListScreen/LcAction/LcAction";
import WallPaper from "../components/global/WallPaper/WallPaper";
import useAppLanguage from "../language/AppLanguage/useAppLanguage";

export default function LightconeListScreen() {
  const { language } = useAppLanguage();

  return (
    <View style={{ flex: 1, backgroundColor: "white" }}>
      <StatusBar style="dark" />
      <WallPaper isBlur />
      <LinearGradient
        className="absolute w-full h-full"
        colors={["#00000080", "#00000020"]}
      />

      <Header Icon={SCREENS.LightconeListPage.icon}>
        {SCREENS.LightconeListPage.getName(language)}
      </Header>
      <>
        <LcList />
        <LcAction />
      </>
      <LinearGradient
        pointerEvents="none"
        className="w-full h-[177px] absolute bottom-0 z-40"
        colors={["#00000000", "#000000"]}
      />
      <LinearGradient
        className="w-full h-[600px] absolute bottom-0"
        colors={["#00000000", "#000000"]}
      />
    </View>
  );
}
