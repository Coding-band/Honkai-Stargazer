import { View } from "react-native";
import React, { useEffect, useState } from "react";
import { StatusBar } from "expo-status-bar";
import { LinearGradient } from "expo-linear-gradient";
import Header from "../components/global/Header/Header";
import { SCREENS } from "../constant/screens";
import { RouteProp, useRoute } from "@react-navigation/native";
import { ParamList } from "../types/navigation";
import Fixed from "../components/global/Fixed/Fixed";
import WallPaper from "../components/global/WallPaper/WallPaper";
import { RelicName } from "../types/relic";
import RelicProvider from "../context/RelicData/RelicProvider";
import RelicMain from "../components/RelicScreen/Relic/Relic";

export default function RelicScreen() {
  const route = useRoute<RouteProp<ParamList, "Relic">>();
  const relicId = route.params.id as RelicName;
  const relicName = route.params.name;

  const [showMain, setShowMain] = useState(false);
  useEffect(() => {
    setTimeout(() => {
      setShowMain(true);
    });
  }, []);

  return (
    <RelicProvider relicId={relicId}>
      <View style={{ flex: 1 }} className="overflow-hidden">
        <StatusBar style="dark" />
        <WallPaper isBlur />
        <LinearGradient
          className="absolute w-full h-full"
          colors={["#00000080", "#00000020"]}
        />
        <Header leftBtn="back" Icon={SCREENS.RelicPage.icon}>
          {relicName}
        </Header>
        {showMain && <RelicMain />}
        <LinearGradient
          className="w-full h-[600px] absolute bottom-0"
          colors={["#00000000", "#000000"]}
        />
        <Fixed />
      </View>
    </RelicProvider>
  );
}
