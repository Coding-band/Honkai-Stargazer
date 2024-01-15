import { View, Text } from "react-native";
import React, { useEffect, useState } from "react";
import { StatusBar } from "expo-status-bar";
import MOCList from "../components/MemoryOfChaosStatsScreen/MOCList/MOCList";
import WallPaperForMOC from "../components/global/WallPaper/WallPaperForMOC";

export default function MemoryOfChaosStatsScreen() {
  return (
    <View style={{ flex: 1, backgroundColor: "white" }}>
      <StatusBar style="dark" />
      <WallPaperForMOC />
      <MOCList />
    </View>
  );
}
