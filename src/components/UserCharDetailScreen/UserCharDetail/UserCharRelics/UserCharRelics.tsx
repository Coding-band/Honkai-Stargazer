import { View, Text } from "react-native";
import React from "react";
import { CardColors } from "../../../../constant/card";
import { LinearGradient } from "expo-linear-gradient";
import { Image } from "expo-image";
import Relic from "../../../../../assets/images/images_map/relic";
import useProfileHsrInGameInfo from "../../../../context/UserCharDetailData/hooks/useProfileHsrInGameInfo";
import RelicItem from "./RelicItem/RelicItem";
import getRelicScore from "../../../../utils/calculator/relicScoreCalculator/getRelicScore";
import getTotalScoreRange from "./utils/getTotalScoreRange";
import ScoreRangeFont from "../../../../../assets/images/images_map/scoreRangeFont";

export default function UserCharRelics() {
  const { inGameCharData } = useProfileHsrInGameInfo();

  const userRelicsData: any[] = inGameCharData.relics;
  const { totalScore, eachScore } = getRelicScore(
    inGameCharData.id,
    userRelicsData
  );

  return (
    <View style={{ gap: 20 }}>
      <View
        style={{
          flexDirection: "row",
          flexWrap: "wrap",
          columnGap: 24,
          rowGap: 0,
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
      <View
        style={{
          flexDirection: "row",
          justifyContent: "center",
          gap: 16,
          flexWrap: "wrap",
        }}
      >
        {/* <View style={{ gap: 8, alignItems: "center" }}>
          <Text className="text-text font-[HY65] text-[24px]">B</Text>
          <Text className="text-text font-[HY65] text-[12px]">角色评价</Text>
        </View>
        <View style={{ gap: 8, alignItems: "center" }}>
          <Text className="text-text font-[HY65] text-[24px]">101%</Text>
          <Text className="text-text font-[HY65] text-[12px]">角色毕业率</Text>
        </View>
        <View style={{ gap: 8, alignItems: "center" }}>
          <Text className="text-text font-[HY65] text-[24px]">101%</Text>
          <Text className="text-text font-[HY65] text-[12px]">角色评分</Text>
        </View> */}
        <View
          className="h-[55px]"
          style={{ justifyContent: "space-between", alignItems: "center" }}
        >
          <Text className="text-text font-[HY65] text-[24px]">
            {totalScore.toFixed(1)}
          </Text>
          <Text className="text-text font-[HY65] text-[12px]">遺器評分</Text>
        </View>
        <View
          className="h-[55px]"
          style={{ justifyContent: "space-between", alignItems: "center" }}
        >
          <Image
            className="w-12 h-8"
            source={ScoreRangeFont[getTotalScoreRange(totalScore)]}
            contentFit="contain"
          ></Image>
          <Text className="text-text font-[HY65] text-[12px]">遺器評價</Text>
        </View>
      </View>
      <View style={{ alignItems: "center", gap: 2 }}>
        <Text className="text-text font-[HY65] text-[18px] leading-5">
          用戶量不足 暫無排行
        </Text>
        <Text className="text-[#FFFFFF60] font-[HY65] text-[12px] leading-4">
          （數據來源於已登入用戶）
        </Text>
      </View>
    </View>
  );
}
