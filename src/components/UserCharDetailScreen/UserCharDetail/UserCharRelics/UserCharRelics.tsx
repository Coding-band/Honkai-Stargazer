import { View } from "react-native";
import React, { useState } from "react";
import useProfileHsrInGameInfo from "../../../../context/UserCharDetailData/hooks/useProfileHsrInGameInfo";
import RelicItem from "./RelicItem/RelicItem";
import getRelicScore from "../../../../utils/calculator/relicScoreCalculator/getRelicScore";

export default function UserCharRelics() {
  const { inGameCharData } = useProfileHsrInGameInfo();
  const userRelicsData: any[] = inGameCharData?.relics;
  const { eachScore } = getRelicScore(inGameCharData?.id, userRelicsData);

  const [selectedRelic, setSelectedRelic] = useState(-1);

  return (
    !!userRelicsData?.length && (
      <View style={{ gap: 20, alignItems: "center" }}>
        <View
          className="px-4"
          style={{
            flexDirection: "row",
            flexWrap: "wrap",
            columnGap: 24,
            rowGap: 0,
          }}
        >
          {userRelicsData.map((userRelicData, i) =>
            selectedRelic !== -1 ? (
              selectedRelic === i && (
                <RelicItem
                  key={userRelicData.id}
                  userRelicData={userRelicData}
                  score={eachScore ? Object.values(eachScore?.[i])?.[0] : 0}
                  selected={selectedRelic === i}
                  onSlectedChange={(selected) => {
                    if (selected) setSelectedRelic(i);
                    else setSelectedRelic(-1);
                  }}
                />
              )
            ) : (
              <RelicItem
                key={userRelicData.id}
                userRelicData={userRelicData}
                score={eachScore ? Object.values(eachScore?.[i])?.[0] : 0}
                selected={selectedRelic === i}
                onSlectedChange={(selected) => {
                  if (selected) setSelectedRelic(i);
                  else setSelectedRelic(-1);
                }}
              />
            )
          )}
        </View>
        <View className="w-[135px] h-[1px] bg-[#F3F9FF40]" />
      </View>
    )
  );
}
