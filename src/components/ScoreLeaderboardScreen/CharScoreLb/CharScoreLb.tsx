import {
  View,
  Text,
  ScrollView,
  Dimensions,
  TouchableOpacity,
  Platform,
} from "react-native";
import React, { MutableRefObject, useEffect, useRef, useState } from "react";
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
import useLocalState from "../../../hooks/useLocalState";
import { useNavigation } from "@react-navigation/native";
import { SCREENS } from "../../../constant/screens";
import { getCharRange } from "../../../utils/calculator/charScoreCalculator/getCharScore";
import useAppLanguage from "../../../language/AppLanguage/useAppLanguage";
import { LOCALES } from "../../../../locales";
import { Image } from "expo-image";
import formatLocale from "../../../utils/format/formatLocale";
import Lightcone from "../../../../assets/images/images_map/lightcone";
import officalLightconeId from "../../../../map/lightcone_offical_id_map";
import { CharacterName } from "../../../types/character";
import { FlatList } from "react-native";
import Animated from "react-native-reanimated";

export default function CharScoreLb(props: {
  selectedCharOption: any;
  scrollViewRef : MutableRefObject<ScrollView | Animated.ScrollView | undefined | null>;
  onChange: (v: any) => void;
}) {
  const { language: textLanguage } = useTextLanguage();
  const flatListRef = useRef(null);
  
  useEffect(() => {
    props.scrollViewRef = flatListRef;
    props.scrollViewRef.current = flatListRef.current;
    //@ts-ignore
    props.scrollViewRef.isFlatList = true;
  })

  // 所有角色選項
  const charOptions: { id: string; name: string }[] = map(
    officalCharId,
    (v, k) => ({
      id: k,
      name: getCharFullData(v, textLanguage).name,
    })
  );
  // 當前選擇角色
  const [selectedCharOption, setSelectedCharOption] = useState(
    props.selectedCharOption
  );
  // <-> 父組件同步更新
  useEffect(() => {
    props.onChange(selectedCharOption);
  }, [selectedCharOption]);

  useEffect(() => {
    setSelectedCharOption(props.selectedCharOption);
  }, [props.selectedCharOption]);

  // 評分數據
  const { data: charScores } = useQuery(
    ["char-score-leaderboard", selectedCharOption?.id],
    async () =>
      (
        await db
          .UserCharacterScores(selectedCharOption?.id)
          .orderBy("score", "desc")
          .limit(99)
          .get()
      ).docs?.map((doc) => ({ id: doc?.id, ...doc.data() })),
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
        <FlatList
          //@ts-ignore
          ref={flatListRef}
          data={charScores} // 数据源
          keyExtractor={(item, index) => item.id || index.toString()} // 指定key
          renderItem={(
            { item, index } // 如何渲染每项
          ) => (
            <CharScoreLbItem
              rank={index + 1}
              userId={item.id}
              score={item.score}
              charRank={item.rank}
              charId={selectedCharOption?.id}
              lightconeId={item.lightcone_id}
            />
          )}
          contentContainerStyle={{
            gap: 12,
            paddingBottom: 60,
            paddingHorizontal: 2,
          }} // 设置内容容器的样式
          style={{ height: Dimensions.get("screen").height - 220 }} // 设置FlatList的样式
        />
      </View>
    </>
  );
}

const CharScoreLbItem = React.memo(
  (props: {
    rank: number;
    userId: string;
    score: number;
    charRank: number;
    charId: string;
    lightconeId: number;
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
      <View className="flex-row items-center justify-between h-6">
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
          {/* 光錐 */}
          <View>
            <Image cachePolicy="none"
              className="w-7 h-7"
              source={Lightcone[officalLightconeId[props.lightconeId]]?.icon}
            />
          </View>
          {/* 星魂 */}
          <View>
            <Text className="font-[HY65] text-[18px] leading-5 text-text2">
              {formatLocale(LOCALES[appLanguage].CharSoulShort, [
                props?.charRank || 0,
              ])}
            </Text>
          </View>
          {/* 分數 */}
          <View className="items-end w-[56px]">
            <Text
              style={{ color: ScoreColors[getCharRange(props?.score)] }}
              className="font-[HY65] text-[18px] leading-5"
            >
              {props?.score.toFixed(1)}
            </Text>
          </View>
        </View>
      </View>
    );
  }
);
