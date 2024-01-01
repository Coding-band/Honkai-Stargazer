import { View } from "react-native";
import React, { useEffect, useState } from "react";
import { StatusBar } from "expo-status-bar";
import Header from "../components/global/Header/Header";
import { SCREENS } from "../constant/screens";
import { ImageBackground } from "expo-image";
import MOCList from "../components/MemoryOfChaosScreen/MOCList/MOCList";
import useAppLanguage from "../context/AppLanguage/useAppLanguage";

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
      <ImageBackground
        className="absolute w-full h-full"
        // 把背景關掉
        source={require("../../assets/images/bgs/memory_of_chaos_bg.png")}
        // placeholder={blurhash}
        contentFit="cover"
      />
      <Header leftBtn="back" Icon={SCREENS.MemoryOfChaosPage.icon}>
        {SCREENS.MemoryOfChaosPage.getName(language)}
      </Header>
      {showMain && <MOCList />}
    </View>
  );
}
