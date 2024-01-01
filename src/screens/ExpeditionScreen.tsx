import { View } from "react-native";
import React from "react";
import { StatusBar } from "expo-status-bar";
import { LinearGradient } from "expo-linear-gradient";
import Header from "../components/global/Header/Header";
import { SCREENS } from "../constant/screens";
import EpdtList from "../components/ExpeditionScreen/EpdtList/EpdtList";
import WallPaper from "../components/global/WallPaper/WallPaper";
import useAppLanguage from "../context/AppLanguage/useAppLanguage";

export default function ExpeditionScreen() {

  const {language} = useAppLanguage();

  return (
    <View style={{ flex: 1, backgroundColor: "white" }}>
      <StatusBar style="dark" />
      <WallPaper isBlur />
      <LinearGradient
        className="absolute w-full h-full"
        colors={["#00000080", "#00000020"]}
      />

      <Header leftBtn="back" Icon={SCREENS.ExpeditionPage.icon}>
        {SCREENS.ExpeditionPage.getName(language)}
      </Header>
      <EpdtList />
      <LinearGradient
        className="w-full h-[600px] absolute bottom-0"
        colors={["#00000000", "#000000"]}
      />
    </View>
  );
}
