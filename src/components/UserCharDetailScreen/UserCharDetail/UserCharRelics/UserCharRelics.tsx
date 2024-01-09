import { View, Text } from "react-native";
import React from "react";
import { CardColors } from "../../../../constant/card";
import { LinearGradient } from "expo-linear-gradient";
import { Image } from "expo-image";
import Relic from "../../../../../assets/images/images_map/relic";
import useProfileHsrInGameInfo from "../../../../context/UserCharDetailData/hooks/useProfileHsrInGameInfo";
import RelicItem from "./RelicItem/RelicItem";

export default function UserCharRelics() {
  const { inGameCharData } = useProfileHsrInGameInfo();

  const userRelicsData: any[] = inGameCharData.relics;

  return (
    <View
      className="px-4"
      style={{ flexDirection: "row", flexWrap: "wrap", gap: 20 }}
    >
      {userRelicsData.map((userRelicData) => (
        <RelicItem userRelicData={userRelicData}></RelicItem>
      ))}
    </View>
  );
}
