import { View } from "react-native";
import React from "react";
import { StatusBar } from "expo-status-bar";
import WallPaperForMOC from "../components/global/WallPaper/WallPaperForMOC";
import MOC from "../components/MemoryOfChaosScreen/MOC/MOC";

export default function MemoryOfChaosScreen() {
  return (
    <View style={{ flex: 1 }} className="overflow-hidden">
      <StatusBar style="dark" />
      <WallPaperForMOC />
      <MOC />
    </View>
  );
}
