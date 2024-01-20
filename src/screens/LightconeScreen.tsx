import { View } from "react-native";
import React, { useEffect, useState } from "react";
import { StatusBar } from "expo-status-bar";
import { LinearGradient } from "expo-linear-gradient";
import Header from "../components/global/Header/Header";
import { SCREENS } from "../constant/screens";
import { RouteProp, useRoute } from "@react-navigation/native";
import { ParamList } from "../types/navigation";
import LightconeMain from "../components/LightconeScreen/Lightcone/Lightcone";
import { LightconeName } from "../types/lightcone";
import WallPaper from "../components/global/WallPaper/WallPaper";
import LightconeProvider from "../context/LightconeData/LightconeProvider";

export default function LightconeScreen() {
  const route = useRoute<RouteProp<ParamList, "Lightcone">>();
  const lcId = route.params.id as LightconeName;
  const lcName = route.params.name;

  const [showMain, setShowMain] = useState(false);
  useEffect(() => {
    setTimeout(() => {
      setShowMain(true);
    });
  }, []);

  return (
    <LightconeProvider lcId={lcId}>
      <View style={{ flex: 1 }} className="overflow-hidden">
        <StatusBar style="dark" />
        <WallPaper isBlur />
        <LinearGradient
          className="absolute w-full h-full"
          colors={["#00000080", "#00000020"]}
        />

        <Header leftBtn="back" Icon={SCREENS.LightconePage.icon}>
          {lcName}
        </Header>
        {showMain && <LightconeMain />}
        <LinearGradient
          className="w-full h-[600px] absolute bottom-0"
          colors={["#00000000", "#000000"]}
        />
      </View>
    </LightconeProvider>
  );
}
