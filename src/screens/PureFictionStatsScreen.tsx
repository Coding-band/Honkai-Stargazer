import { View } from "react-native";
import React from "react";
import { StatusBar } from "expo-status-bar";
import WallPaperForPF from "../components/global/WallPaper/WallPaperForPF";
import PFList from "../components/PureFictionStatsScreen/PFList/PFList";

export default function PureFictionStatsScreen() {
  return (
    <View style={{ flex: 1 }} className="overflow-hidden">
      <StatusBar style="dark" />
      <WallPaperForPF />
      <PFList />
    </View>
  );
}
