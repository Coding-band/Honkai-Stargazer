import { View } from "react-native";
import React, { useState } from "react";
import { ImageBackground } from "expo-image";
import { LinearGradient } from "expo-linear-gradient";
import Header from "../components/global/Header/Header";
import { StatusBar } from "expo-status-bar";
import { SCREENS } from "../constant/screens";
import LcList from "../components/LightconeListScreen/LcList/LcList";
import LcAction from "../components/LightconeListScreen/LcAction/LcAction";
import useWallPaper from "../redux/wallPaper/useWallPaper";
import WallPaper from "../components/global/WallPaper/WallPaper";

export default function LightconeListScreen() {
  const { wallPaper } = useWallPaper();
  const [installOrder, setInstallOrder] = useState(true);

  return (
    <View style={{ flex: 1, backgroundColor: "white" }}>
      <StatusBar style="dark" />
      <WallPaper />
      <LinearGradient
        className="absolute w-full h-full"
        colors={["#00000080", "#00000020"]}
      />

      <Header Icon={SCREENS.LightconeListPage.icon}>
        {SCREENS.LightconeListPage.name}
      </Header>
      <>
        <LcList reverse={!installOrder} />
        <LcAction
          onInstallOrderChange={(o) => {
            setInstallOrder(o);
          }}
        />
      </>
      <LinearGradient
        pointerEvents="none"
        className="w-full h-[177px] absolute bottom-0 z-40"
        colors={["#00000000", "#000000"]}
      />
      <LinearGradient
        className="w-full h-[600px] absolute bottom-0"
        colors={["#00000000", "#000000"]}
      />
    </View>
  );
}
