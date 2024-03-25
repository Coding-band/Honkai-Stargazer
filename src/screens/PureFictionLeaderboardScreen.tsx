import { View } from "react-native";
import React, { useRef } from "react";
import { StatusBar } from "expo-status-bar";
import { LinearGradient } from "expo-linear-gradient";
import Header from "../components/global/Header/Header";
import { SCREENS } from "../constant/screens";
import WallPaper from "../components/global/WallPaper/WallPaper";
import useAppLanguage from "../language/AppLanguage/useAppLanguage";
import CodeList from "../components/CodeScreen/CodeList/CodeList";
import WallPaperForMOC from "../components/global/WallPaper/WallPaperForMOC";
import useDelayLoad from "../hooks/useDelayLoad";
import Loading from "../components/global/Loading/Loading";
import WallPaperForPF from "../components/global/WallPaper/WallPaperForPF";
import PFLbList from "../components/PureFictionLeaderboardScreen/MOCLbList/PFLbList";

export default function PureFictionLeaderboardScreen() {
  const { language } = useAppLanguage();
  const scrollViewRef = useRef();

  const loaded = useDelayLoad(1000);

  return (
    <View style={{ flex: 1 }} className="overflow-hidden">
      <StatusBar style="dark" />
      <WallPaperForPF />
      <Header leftBtn="back" Icon={SCREENS.PureFictionLeaderboardPage.icon} scrollViewRef={scrollViewRef}>
        {SCREENS.PureFictionLeaderboardPage.getName(language)}
      </Header>
      {loaded ? <PFLbList scrollViewRef={scrollViewRef} /> : <Loading />}
    </View>
  );
}
