import { StyleSheet, Text, View } from "react-native";
import React from "react";
import { StatusBar } from "expo-status-bar";
import { LinearGradient } from "expo-linear-gradient";
import Header from "../components/global/Header/Header";
import { SCREENS } from "../constant/screens";
import WallPaper from "../components/global/WallPaper/WallPaper";
import WallPaperChanger from "../components/WallPaperScreen/WallPaperChanger";

export default function WallPaperScreen() {
  return (
    <View style={{ flex: 1, backgroundColor: "white" }}>
      <StatusBar style="dark" />

      <LinearGradient
        className="absolute w-full h-full"
        colors={["#020510", "#001C40"]}
      />

      <Header leftBtn="back" Icon={SCREENS.WallPaperPage.icon}>
        {SCREENS.WallPaperPage.name}
      </Header>
      <WallPaperChanger />
      <LinearGradient
        className="w-full h-[600px] absolute bottom-0"
        colors={["#00000000", "#000000"]}
      />
    </View>
  );
}
