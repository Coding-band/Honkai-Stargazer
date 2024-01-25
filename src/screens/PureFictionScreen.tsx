import { View } from "react-native";
import React from "react";
import { StatusBar } from "expo-status-bar";
import PureFiction from "../components/PureFictionScreen/PureFiction/PureFiction";
import WallPaperForPF from "../components/global/WallPaper/WallPaperForPF";

export default function PureFictionScreen() {
  return (
    <View style={{ flex: 1 }} className="overflow-hidden">
      <StatusBar style="dark" />
      <WallPaperForPF />
      <PureFiction />
    </View>
  );
}
