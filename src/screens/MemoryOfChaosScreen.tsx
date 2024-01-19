import { View } from "react-native";
import React, { useEffect, useState } from "react";
import { StatusBar } from "expo-status-bar";
import Header from "../components/global/Header/Header";
import { SCREENS } from "../constant/screens";
import { ImageBackground } from "expo-image";
import useAppLanguage from "../language/AppLanguage/useAppLanguage";
import WallPaperForMOC from "../components/global/WallPaper/WallPaperForMOC";
import MOC from "../components/MemoryOfChaosScreen/MOC/MOC";

export default function MemoryOfChaosScreen() {
  const [showMain, setShowMain] = useState(false);
  useEffect(() => {
    setTimeout(() => {
      setShowMain(true);
    });
  }, []);

  return (
    <View style={{ flex: 1, backgroundColor: "white" }}>
      <StatusBar style="dark" />
      <WallPaperForMOC />
      <MOC />
    </View>
  );
}
