import { View } from "react-native";
import React from "react";
import { StatusBar } from "expo-status-bar";
import { LinearGradient } from "expo-linear-gradient";
import Header from "../components/global/Header/Header";
import { SCREENS } from "../constant/screens";
import WallPaper from "../components/global/WallPaper/WallPaper";
import useAppLanguage from "../language/AppLanguage/useAppLanguage";
import CodeList from "../components/CodeScreen/CodeList/CodeList";
import WallPaperForMOC from "../components/global/WallPaper/WallPaperForMOC";
import MOCLbList from "../components/MemoryOfChaosLeaderboardScreen/MOCLbList/MOCLbList";

export default function MemoryOfChaosLeaderboardScreen() {
  const { language } = useAppLanguage();

  return (
    <View style={{ flex: 1, backgroundColor: "white" }}>
      <StatusBar style="dark" />
      <WallPaperForMOC />
      <LinearGradient
        className="absolute w-full h-full"
        colors={["#00000080", "#00000020"]}
      />
      <Header leftBtn="back" Icon={SCREENS.MemoryOfChaosLeaderboardPage.icon}>
        {SCREENS.MemoryOfChaosLeaderboardPage.getName(language)}
      </Header>
      <MOCLbList />
      <LinearGradient
        className="w-full h-[600px] absolute bottom-0"
        colors={["#00000000", "#000000"]}
      />
    </View>
  );
}
