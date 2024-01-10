import { View, Text } from "react-native";
import React from "react";
import useProfileHsrInGameInfo from "../../../../context/UserCharDetailData/hooks/useProfileHsrInGameInfo";
import UserCharDetailStars from "../UserCharDetailStars/UserCharDetailStars";
import { Image } from "expo-image";
import officalLightconeId from "../../../../../map/lightcone_offical_id_map";
import Lightcone from "../../../../../assets/images/images_map/lightcone";
import { LightconeName } from "../../../../types/lightcone";
import useTextLanguage from "../../../../language/TextLanguage/useTextLanguage";
import {
  getLcFullData,
  getLcJsonData,
} from "../../../../utils/dataMap/getDataFromMap";
import LightconePath from "./LightconePath/LightconePath";
import LightconeAttribute from "./LightconeAttribute/LightconeAttribute";
import LightconeLevel from "./LightconeLevel/LightconeLevel";

export default function UserCharLightcone() {
  const { language } = useTextLanguage();

  const { inGameCharData } = useProfileHsrInGameInfo();

  const lightconeId = officalLightconeId[
    inGameCharData.light_cone.id
  ] as LightconeName;
  const lcJsonData = getLcJsonData(lightconeId);
  const lcFullData = getLcFullData(lightconeId, language);
  const lcInGameData = inGameCharData.light_cone;

  return (
    <View style={{ alignItems: "center" }}>
      <View className="w-[135px] h-[1px] bg-[#F3F9FF40]"></View>
      <View
        className="py-[18px]"
        style={{
          flexDirection: "row",
          alignItems: "center",

          gap: 40,
        }}
      >
        <Image
          className="w-[72px] h-[106px] border-4 border-[#FFF]"
          style={{ transform: [{ rotate: "5deg" }] }}
          source={Lightcone[lightconeId].imageFull}
          contentFit="contain"
        />
        <View style={{ gap: 2, alignItems: "flex-start" }}>
          <Text className="text-[20px] font-[HY65] text-text">
            {lcFullData.name}
          </Text>
          <LightconeLevel
            lcId={lightconeId}
            lcFullData={lcFullData}
            lcInGameData={lcInGameData}
          />
          <UserCharDetailStars count={lcInGameData.rarity} />
          <LightconePath lcId={lightconeId} lcJsonData={lcJsonData} />
          <LightconeAttribute
            lcId={lightconeId}
            lcFullData={lcFullData}
            lcInGameData={lcInGameData}
          />
        </View>
      </View>
      <View className="w-[135px] h-[1px] bg-[#F3F9FF40]"></View>
    </View>
  );
}
