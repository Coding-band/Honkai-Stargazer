import { View, Text } from "react-native";
import React from "react";
import getRelicScore, {
  getRelicTotalScoreRange,
} from "../../../../utils/calculator/relicScoreCalculator/getRelicScore";
import useProfileHsrInGameInfo from "../../../../context/UserCharDetailData/hooks/useProfileHsrInGameInfo";
import { LOCALES } from "../../../../../locales";
import useAppLanguage from "../../../../language/AppLanguage/useAppLanguage";
import { Image } from "expo-image";
import getCharScore, {
  getCharRange,
  getCurrAndGradScore,
} from "../../../../utils/calculator/charScoreCalculator/getCharScore";
import UserCharScoreItem from "./UserCharScoreItem/UserCharScoreItem";
import ScoreRangeFont from "../../../global/ScoreRangeFont/ScoreRangeFont";
import UserCharScoreBar from "./UserCharScoreBar/UserCharScoreBar";

export default function UserCharScore() {
  const { language: appLanguage } = useAppLanguage();

  const { inGameCharData } = useProfileHsrInGameInfo();
  const userRelicsData: any[] = inGameCharData?.relics;
  const charId = inGameCharData?.id;

  // 遺器總分
  const relicTotalScore = getRelicScore(
    inGameCharData?.id,
    userRelicsData
  ).totalScore;
  // 角色總分
  const charTotalScore = inGameCharData
    ? getCharScore(charId, inGameCharData)
    : 0;
  // 各屬性畢業度
  const currAndGrad = getCurrAndGradScore(charId, inGameCharData)[0];

  console.log(currAndGrad);

  return (
    inGameCharData && (
      <>
        <View
          style={{
            flexDirection: "row",
            justifyContent: "center",
            gap: 16,
            flexWrap: "wrap",
          }}
        >
          {/* <View style={{ gap: 8, alignItems: "center" }}>
          <Text className="text-text font-[HY65] text-[24px]">101%</Text>
          <Text className="text-text font-[HY65] text-[12px]">角色毕业率</Text>
        </View> */}
          <UserCharScoreItem
            title={LOCALES[appLanguage].CharScore}
            value={charTotalScore.toFixed(1)}
          />
          <UserCharScoreItem
            title={LOCALES[appLanguage].CharRank}
            value={<ScoreRangeFont scoreRange={getCharRange(charTotalScore)} />}
          />
          <UserCharScoreItem
            title={LOCALES[appLanguage].RelicScore}
            value={relicTotalScore.toFixed(1)}
          />
          <UserCharScoreItem
            title={LOCALES[appLanguage].RelicRank}
            value={
              <ScoreRangeFont
                scoreRange={getRelicTotalScoreRange(relicTotalScore)}
              />
            }
          />
        </View>
        <View>
          <View style={{ gap: 10 }}>
            {currAndGrad.map((attr, i) => (
              <UserCharScoreBar
                field={Object.keys(attr)[0]}
                currScore={Object.values(attr)?.[0]?.[0] as number}
                gradScore={Object.values(attr)?.[0]?.[1] as number}
                type={Object.values(attr)?.[0]?.[2] as number}
              />
            ))}
          </View>
        </View>
        <View style={{ alignItems: "center", gap: 2 }}>
          <Text className="text-text font-[HY65] text-[18px] leading-5">
            {LOCALES[appLanguage].LackOfUserData}
          </Text>
          <Text className="text-[#FFFFFF60] font-[HY65] text-[12px] leading-4">
            {LOCALES[appLanguage].LeaderboardDataFrom}
          </Text>
        </View>
      </>
    )
  );
}
