import {
  View,
  Text,
  ScrollView,
  Dimensions,
  TouchableOpacity,
  Platform,
} from "react-native";
import React, { useEffect, useState } from "react";
import Options from "../../global/Options/Options";
import { map } from "lodash";
import { getCharFullData } from "../../../utils/data/getDataFromMap";
import useTextLanguage from "../../../language/TextLanguage/useTextLanguage";
import officalCharId from "../../../../map/character_offical_id_map";
import getRankColor from "../../../utils/getRankColor";
import { useQuery } from "react-query";
import db from "../../../firebase/db";
import useUser from "../../../firebase/hooks/User/useUser";
import { ScoreColors } from "../../../constant/score";
import { useNavigation } from "@react-navigation/native";
import { SCREENS } from "../../../constant/screens";
import { getCharRange } from "../../../utils/calculator/charScoreCalculator/getCharScore";
import useAppLanguage from "../../../language/AppLanguage/useAppLanguage";
import { Image } from "expo-image";
import Relic from "../../../../assets/images/images_map/relic";
import officalRelicId from "../../../../map/relic_offical_id_map";

export default function RelicScoreLb(props: {
  selectedCharOption: any;
  onChange: (v: any) => void;
}) {
  const { language: textLanguage } = useTextLanguage();

  // 所有角色選項
  const charOptions: { id: string; name: string }[] = map(
    officalCharId,
    (v, k) => ({
      id: k,
      name: getCharFullData(v, textLanguage).name,
    })
  );
  // 當前選擇角色
  const [selectedCharOption, setSelectedCharOption] = useState(props.selectedCharOption);
  // <-> 父組件同步更新
  useEffect(() => {
    props.onChange(selectedCharOption);
  }, [selectedCharOption]);
  useEffect(() => {
    setSelectedCharOption(props.selectedCharOption);
  }, [props.selectedCharOption]);

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
  ) as any;

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
            <CharRelicScoreLbItem
              key={i}
              rank={i + 1}
              userId={item.id}
              charId={selectedCharOption.id}
              relicTotalScore={item.relic_score}
              relicHeadScore={item.relic_head_score}
              relicHandsScore={item.relic_hands_score}
              relicBodyScore={item.relic_body_score}
              relicShoesScore={item.relic_shoes_score}
              relicBallScore={item.relic_ball_score}
              relicLinkScore={item.relic_link_score}
              relicHeadSetId={item.relic_head_set_id}
              relicHandsSetId={item.relic_hands_set_id}
              relicBodySetId={item.relic_body_set_id}
              relicShoesSetId={item.relic_shoes_set_id}
              relicBallSetId={item.relic_ball_set_id}
              relicLinkSetId={item.relic_link_set_id}
            />
          ))}
        </ScrollView>
      </View>
    </>
  );
}

const CharRelicScoreLbItem = (props: {
  rank: number;
  userId: string;
  relicTotalScore: number;
  relicHeadScore: number;
  relicHandsScore: number;
  relicBodyScore: number;
  relicShoesScore: number;
  relicBallScore: number;
  relicLinkScore: number;
  relicHeadSetId: number;
  relicHandsSetId: number;
  relicBodySetId: number;
  relicShoesSetId: number;
  relicBallSetId: number;
  relicLinkSetId: number;
  charId: string;
}) => {
  const navigation = useNavigation();
  const { language: appLanguage } = useAppLanguage();

  const { data: user } = useUser(props.userId);

  const handleNavigateToUserCharaPage = () => {
    // @ts-ignore
    navigation.push(SCREENS.UserCharDetailPage.id, {
      uuid: user?.uuid,
      charId: officalCharId?.[props.charId],
    });
  };

  return (
    !!props?.relicTotalScore && (
      <View className="flex-row items-center justify-between">
        <View className="flex-row" style={{ gap: 10 }}>
          <Text
            style={{ color: getRankColor(props.rank) }}
            className="font-[HY65] text-[20px] leading-5"
          >
            {props.rank}
          </Text>
          <TouchableOpacity
            activeOpacity={0.35}
            onPress={handleNavigateToUserCharaPage}
          >
            <Text className="text-text font-[HY65] text-[20px] leading-6">
              {user?.name}
            </Text>
          </TouchableOpacity>
        </View>
        <View className="flex-row items-center" style={{ gap: 12 }}>
          {/* 遺器圖標 */}
          <View className="flex-row" style={{ gap: 4 }}>
            <Image
              className="w-[20px] h-[20px]"
              // @ts-ignore
              source={Relic[officalRelicId[props.relicHeadSetId]]?.["icon" + 1]}
            />
            <Image
              className="w-[20px] h-[20px]"
              // @ts-ignore
              source={
                Relic[officalRelicId[props.relicHandsSetId]]?.["icon" + 2]
              }
            />
            <Image
              className="w-[20px] h-[20px]"
              // @ts-ignore
              source={Relic[officalRelicId[props.relicBodySetId]]?.["icon" + 3]}
            />
            <Image
              className="w-[20px] h-[20px]"
              // @ts-ignore
              source={
                Relic[officalRelicId[props.relicShoesSetId]]?.["icon" + 4]
              }
            />
          </View>
          {/* 分數 */}
          <View
            style={{ width: Platform.OS === "android" ? 56 : 48 }}
            className="items-end"
          >
            <Text
              style={{
                color: ScoreColors[getCharRange(props?.relicTotalScore)],
              }}
              className="font-[HY65] text-[18px] leading-5"
            >
              {props?.relicTotalScore?.toFixed(1)}
            </Text>
          </View>
        </View>
      </View>
    )
  );
};
