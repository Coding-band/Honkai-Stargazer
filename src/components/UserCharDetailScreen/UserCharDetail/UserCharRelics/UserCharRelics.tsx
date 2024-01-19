import { View } from "react-native";
import React from "react";
import useProfileHsrInGameInfo from "../../../../context/UserCharDetailData/hooks/useProfileHsrInGameInfo";
import RelicItem from "./RelicItem/RelicItem";
import getRelicScore from "../../../../utils/calculator/relicScoreCalculator/getRelicScore";

export default function UserCharRelics() {
  const { inGameCharData } = useProfileHsrInGameInfo();
  const userRelicsData: any[] = inGameCharData?.relics;

  const { eachScore } = getRelicScore(inGameCharData?.id, userRelicsData);

  return (
    inGameCharData && (
      <View style={{ gap: 20 }}>
        <View
          className="px-4"
          style={{
            flexDirection: "row",
            flexWrap: "wrap",
            columnGap: 24,
            rowGap: 0,
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
      </View>
    )
  );
}
