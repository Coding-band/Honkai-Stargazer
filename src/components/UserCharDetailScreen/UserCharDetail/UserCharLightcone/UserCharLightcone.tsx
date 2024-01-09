import { View, Text } from "react-native";
import React from "react";
import useProfileHsrInGameInfo from "../../../../context/UserCharDetailData/hooks/useProfileHsrInGameInfo";
import UserCharLevel from "../UserCharLevel/UserCharLevel";
import UserCharDetailStars from "../UserCharDetailStars/UserCharDetailStars";
import { Image } from "expo-image";
import Path from "../../../../../assets/images/images_map/path";
import officalLightconeId from "../../../../../map/lightcone_offical_id_map";
import Lightcone from "../../../../../assets/images/images_map/lightcone";
import { LightconeName } from "../../../../types/lightcone";
import useTextLanguage from "../../../../language/TextLanguage/useTextLanguage";
import { getLcFullData } from "../../../../utils/dataMap/getDataFromMap";
import LightconeLevel from "./LightconeLevel/LightconeLevel";
import LightconeAttribute from "./LightconeAttribute/LightconeAttribute";

export default function UserCharLightcone() {
  const { language } = useTextLanguage();

  const { inGameCharData } = useProfileHsrInGameInfo();

  const lightconeId = officalLightconeId[
    inGameCharData.light_cone.id
  ] as LightconeName;

  const lcFullData = getLcFullData(lightconeId, language);

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
          <View
            className="mb-3 bg-[#222222] rounded-[49px] px-[12px] py-[4px]"
            style={{ alignItems: "center" }}
          >
            <Text className="text-[12px] text-[#FFFFFF] font-[HY65]">
              Lv 80 · 1星魂
            </Text>
          </View>
          <UserCharDetailStars count={5} />
          <LightconeLevel lcId={lightconeId} />
          <LightconeAttribute lcId={lightconeId} />
        </View>
      </View>
      <View className="w-[135px] h-[1px] bg-[#F3F9FF40]"></View>
    </View>
  );
}
