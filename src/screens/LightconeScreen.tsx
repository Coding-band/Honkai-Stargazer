import { View } from "react-native";
import React, { useEffect, useState } from "react";
import { StatusBar } from "expo-status-bar";
import { ImageBackground } from "expo-image";
import { LinearGradient } from "expo-linear-gradient";
import Header from "../components/global/Header/Header";
import { SCREENS } from "../constant/screens";
import { RouteProp, useRoute } from "@react-navigation/native";
import { ParamList } from "../types/navigation";
import LightconeMain from "../components/LightconeScreen/Lightcone";
import { filter } from "lodash";
import { Lightcone, LightconeName } from "../types/lightcone";
import LightconeContext from "../context/LightconeContext";
import lcList from "../../data/lightcone_data/lightcone_list.json";
import * as imagesMap from "../../assets/images/@images_map/images_map";
import { getLcFullData } from "../utils/dataMap/getDataFromMap";

export default function LightconeScreen() {
  const route = useRoute<RouteProp<ParamList, "Lightcone">>();
  const lcId = route.params.id as LightconeName;
  const lcName = route.params.name;

  const [lcData, setLcData] = useState<Lightcone>({});
  const [showMain, setShowMain] = useState(false);

  useEffect(() => {
    const lcDataJson = filter(lcList, (lc) => lc?.name === lcId)[0];
    const lcFullData = getLcFullData(lcId);
    setLcData({
      id: lcId,
      name: lcFullData?.name,
      rare: lcDataJson?.rare,
      path: lcFullData?.baseType?.name,
      imageFull: imagesMap.Lightcone[lcId]?.imageFull,
      description: lcFullData?.descHash,
    });
    setShowMain(true);
  }, []);

  return (
    <LightconeContext.Provider value={lcData}>
      <View style={{ flex: 1, backgroundColor: "white" }}>
        <StatusBar style="dark" />
        <ImageBackground
          className="absolute w-full h-full"
          // 把背景關掉
          source={require("../../assets/images/test-bg.png")}
          // placeholder={blurhash}
          contentFit="cover"
          blurRadius={10}
        />
        <LinearGradient
          className="absolute w-full h-full"
          colors={["#00000080", "#00000020"]}
        />

        <Header leftBtn="back" Icon={SCREENS.LightconeListPage.icon}>
          {lcName}
        </Header>
        {showMain && <LightconeMain />}
        <LinearGradient
          className="w-full h-[600px] absolute bottom-0"
          colors={["#00000000", "#000000"]}
        />
      </View>
    </LightconeContext.Provider>
  );
}
