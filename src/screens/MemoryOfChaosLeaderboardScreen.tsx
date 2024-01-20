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
import useDelayLoad from "../hooks/useDelayLoad";
import Loading from "../components/global/Loading/Loading";

export default function MemoryOfChaosLeaderboardScreen() {
  const { language } = useAppLanguage();

  const loaded = useDelayLoad(1000);

  return (
    <View style={{ flex: 1 }} className="overflow-hidden">
      <StatusBar style="dark" />
      <WallPaperForMOC />
      <Header leftBtn="back" Icon={SCREENS.MemoryOfChaosLeaderboardPage.icon}>
        {SCREENS.MemoryOfChaosLeaderboardPage.getName(language)}
      </Header>
      {loaded ? <MOCLbList /> : <Loading />}
    </View>
  );
}
