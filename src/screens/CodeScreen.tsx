import { View } from "react-native";
import React from "react";
import { StatusBar } from "expo-status-bar";
import { LinearGradient } from "expo-linear-gradient";
import Header from "../components/global/Header/Header";
import { SCREENS } from "../constant/screens";
import WallPaper from "../components/global/WallPaper/WallPaper";
import useAppLanguage from "../context/AppLanguage/useAppLanguage";
import CodeList from "../components/CodeScreen/CodeList/CodeList";

export default function CodeScreen() {
  const { language } = useAppLanguage();

  return (
    <View style={{ flex: 1, backgroundColor: "white" }}>
      <StatusBar style="dark" />
      <WallPaper isBlur />
      <LinearGradient
        className="absolute w-full h-full"
        colors={["#00000080", "#00000020"]}
      />

      <Header leftBtn="back" Icon={SCREENS.CodePage.icon}>
        {SCREENS.CodePage.getName(language)}
      </Header>
      <CodeList />
      <LinearGradient
        className="w-full h-[600px] absolute bottom-0"
        colors={["#00000000", "#000000"]}
      />
    </View>
  );
}
