import { View, Text } from "react-native";
import React from "react";
import { LOCALES } from "../../../../../locales";
import useAppLanguage from "../../../../language/AppLanguage/useAppLanguage";
import useProfileCharId from "../../../../context/UserCharDetailData/hooks/useProfileCharId";
import useProfileUUID from "../../../../context/UserCharDetailData/hooks/useProfileUUID";
import { useQuery } from "react-query";
import db from "../../../../firebase/db";
import useProfileHsrInGameInfo from "../../../../context/UserCharDetailData/hooks/useProfileHsrInGameInfo";
import getCharScore from "../../../../utils/calculator/charScoreCalculator/getCharScore";
import formatLocale from "../../../../utils/format/formatLocale";

export default function UserCharStats() {
  const { language: appLanguage } = useAppLanguage();

  const profileUUID = useProfileUUID();
  const { inGameCharData } = useProfileHsrInGameInfo();
  const charId = inGameCharData?.id;

  // 角色總分
  const charTotalScore = inGameCharData
    ? getCharScore(charId, inGameCharData)
    : 0;

  const { data: overStat } = useQuery(
    ["char-score-over-stat", charId, charTotalScore],
    async () => {
      const data = await db
        .UserCharacterScores(charId)
        .where("score", "<=", charTotalScore)
        .get();
      return data.size || 0;
    },
    { staleTime: 1000 * 30 }
  );

  const { data: totalStat } = useQuery(
    ["char-score-total-stat", charId],
    async () => {
      const data = await db.UserCharacterScores(charId).get();
      return data.size || 0;
    }
  );

  return (
    <>
      <View style={{ alignItems: "center", gap: 2 }}>
        <Text className="text-text font-[HY65] text-[18px] leading-5">
          {formatLocale(LOCALES[appLanguage].OverWholeServerUser, [
            // @ts-ignore
            ((overStat / totalStat) * 100 || 0).toFixed(1),
          ])}
        </Text>
        <Text className="text-[#FFFFFF60] font-[HY65] text-[12px] leading-4">
          {LOCALES[appLanguage].LeaderboardDataFrom}
        </Text>
      </View>
    </>
  );
}
