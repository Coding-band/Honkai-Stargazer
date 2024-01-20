import { View } from "react-native";
import React, { useEffect, useState } from "react";
import { StatusBar } from "expo-status-bar";
import Header from "../components/global/Header/Header";
import { SCREENS } from "../constant/screens";
import { ImageBackground } from "expo-image";
import useAppLanguage from "../language/AppLanguage/useAppLanguage";
import WallPaperForMOC from "../components/global/WallPaper/WallPaperForMOC";
import MOC from "../components/MemoryOfChaosScreen/MOC/MOC";
import { LinearGradient } from "expo-linear-gradient";

export default function MemoryOfChaosScreen() {
  return (
    <View style={{ flex: 1 }} className="overflow-hidden">
      <StatusBar style="dark" />

      <WallPaperForMOC />
      <MOC />
    </View>
  );
}
