import { View } from "react-native";
import React, { useEffect, useState } from "react";
import { StatusBar } from "expo-status-bar";
import Header from "../components/global/Header/Header";
import { SCREENS } from "../constant/screens";
import { ImageBackground } from "expo-image";
import MOCList from "../components/MemoryOfChaosScreen/MOCList/MOCList";
import useAppLanguage from "../language/AppLanguage/useAppLanguage";
import WallPaperForMOC from "../components/global/WallPaper/WallPaperForMOC";

export default function MemoryOfChaosScreen() {
  const { language } = useAppLanguage();

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
      <Header leftBtn="back" Icon={SCREENS.MemoryOfChaosPage.icon}>
        {SCREENS.MemoryOfChaosPage.getName(language)}
      </Header>
      {showMain && <MOCList />}
    </View>
  );
}
