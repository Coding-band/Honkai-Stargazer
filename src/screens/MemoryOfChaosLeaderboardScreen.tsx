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
      <Header leftBtn="back" Icon={SCREENS.MemoryOfChaosLeaderboardPage.icon}>
        {SCREENS.MemoryOfChaosLeaderboardPage.getName(language)}
      </Header>
      <MOCLbList />
    </View>
  );
}
