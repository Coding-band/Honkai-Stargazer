import { View } from "react-native";
import React, { useRef } from "react";
import { StatusBar } from "expo-status-bar";
import { LinearGradient } from "expo-linear-gradient";
import Header from "../components/global/Header/Header";
import { SCREENS } from "../constant/screens";
import WallPaper from "../components/global/WallPaper/WallPaper";
import useAppLanguage from "../language/AppLanguage/useAppLanguage";
import ScoreLbList from "../components/ScoreLeaderboardScreen/ScoreLbList";

export default function ScoreLeaderboardScreen() {
  const { language } = useAppLanguage();
  const scrollViewRef = useRef();

  return (
    <View style={{ flex: 1 }} className="overflow-hidden">
      <StatusBar style="dark" />

      <Header leftBtn="back" Icon={SCREENS.ScoreLeaderboardPage.icon} scrollViewRef={scrollViewRef}>
        {SCREENS.ScoreLeaderboardPage.getName(language)}
      </Header>
      <ScoreLbList scrollViewRef={scrollViewRef}/>
    </View>
  );
}
