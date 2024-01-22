import {
  View,
  Text,
  ScrollView,
  Dimensions,
  TouchableOpacity,
} from "react-native";
import React, { useEffect, useState } from "react";
import Options from "../../global/Options/Options";
import { map } from "lodash";
import { getCharFullData } from "../../../utils/dataMap/getDataFromMap";
import useTextLanguage from "../../../language/TextLanguage/useTextLanguage";
import WallPaper from "../../global/WallPaper/WallPaper";
import officalCharId from "../../../../map/character_offical_id_map";
import getRankColor from "../../../utils/getRankColor";
import { useQuery } from "react-query";
import db from "../../../firebase/db";
import useUser from "../../../firebase/hooks/User/useUser";
import { getRelicScoreRange } from "../../../utils/calculator/relicScoreCalculator/getRelicScore";
import { ScoreColors } from "../../../constant/score";
import useLocalState from "../../../hooks/useLocalState";
import { useNavigation } from "@react-navigation/native";
import { SCREENS } from "../../../constant/screens";
import { getCharRange } from "../../../utils/calculator/charScoreCalculator/getCharScore";

export default function CharScoreLb({
  onChange,
}: {
  onChange: (v: any) => void;
}) {
  const { language: textLanguage } = useTextLanguage();

  // 所有角色選項
  const charOptions = map(officalCharId, (v, k) => ({
    id: k,
    name: getCharFullData(v, textLanguage).name,
  })).slice(0, map(officalCharId).length - 3);
  // 當前選擇角色 <-> 父組件同步更新 (更新壁紙)
  const [selectedCharOption, setSelectedCharOption] = useLocalState(
    "char-score-leaderboard-selected-char",
    charOptions[0]
  );
  useEffect(() => {
    onChange(selectedCharOption);
  }, [selectedCharOption]);

  // 評分數據

  const { data: charScores } = useQuery(
    ["char-score-leaderboard", selectedCharOption.id],
    async () =>
      (
        await db
          .UserCharacterScores(selectedCharOption.id)
          .orderBy("score", "desc")
          .get()
      ).docs?.map((doc) => ({ id: doc.id, ...doc.data() })),
    { staleTime: 1000 * 60 }
  );

  return (
    <>
      <View style={{ gap: 12 }}>
        <Options
          values={charOptions}
          value={selectedCharOption}
          onChange={(c) => {
            setSelectedCharOption(c);
          }}
        />
        <ScrollView
          className="px-2"
          contentContainerStyle={{ gap: 12 }}
          style={{ height: Dimensions.get("screen").height - 220 }}
        >
          {charScores?.map((item, i) => (
            <CharScoreLbItem
              rank={i + 1}
              userId={item.id}
              score={item.score}
              charId={selectedCharOption.id}
            />
          ))}
        </ScrollView>
      </View>
    </>
  );
}

const CharScoreLbItem = (props: {
  rank: number;
  userId: string;
  score: number;
  charId: string;
}) => {
  const navigation = useNavigation();

  const { data: user } = useUser(props.userId);

  const handleNavigateToUserCharaPage = () => {
    navigation.push(SCREENS.UserCharDetailPage.id, {
      uuid: user?.uuid,
      charId: officalCharId?.[props.charId],
    });
  };

  return (
    <View className="flex-row items-center justify-between">
      <View className="flex-row" style={{ gap: 10 }}>
        <Text
          style={{ color: getRankColor(props.rank) }}
          className="font-[HY65] text-[20px]"
        >
          {props.rank}
        </Text>
        <TouchableOpacity
          activeOpacity={0.35}
          onPress={handleNavigateToUserCharaPage}
        >
          <Text className="text-text font-[HY65] text-[20px]">
            {user?.name}
          </Text>
        </TouchableOpacity>
      </View>
      <View className="flex-row">
        <Text></Text>
        <Text></Text>
        <Text
          style={{ color: ScoreColors[getCharRange(props?.score)] }}
          className="font-[HY65] text-[20px]"
        >
          {props?.score.toFixed(1)}
        </Text>
      </View>
    </View>
  );
};
