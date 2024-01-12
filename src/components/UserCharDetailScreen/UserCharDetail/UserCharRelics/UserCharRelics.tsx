import { View, Text } from "react-native";
import React from "react";
import { CardColors } from "../../../../constant/card";
import { LinearGradient } from "expo-linear-gradient";
import { Image } from "expo-image";
import Relic from "../../../../../assets/images/images_map/relic";
import useProfileHsrInGameInfo from "../../../../context/UserCharDetailData/hooks/useProfileHsrInGameInfo";
import RelicItem from "./RelicItem/RelicItem";
import getRelicScore from "../../../../utils/calculator/relicScoreCalculator/getRelicScore";

export default function UserCharRelics() {
  const { inGameCharData } = useProfileHsrInGameInfo();

  const userRelicsData: any[] = inGameCharData.relics;
  const { totalScore, eachScore } = getRelicScore(
    inGameCharData.id,
    userRelicsData
  );

  return (
    <View
      className=""
      style={{
        flexDirection: "row",
        flexWrap: "wrap",
        gap: 20,
        justifyContent: "center",
      }}
    >
      {userRelicsData.map((userRelicData, i) => (
        <RelicItem
          key={userRelicData.id}
          userRelicData={userRelicData}
          score={eachScore ? Object.values(eachScore?.[i])?.[0] : 0}
        ></RelicItem>
      ))}
    </View>
  );
}
