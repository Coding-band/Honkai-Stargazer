import { View, Text } from "react-native";
import React from "react";
import CharCard from "../../../global/CharCard/CharCard";
import { Image } from "expo-image";
import { useNavigation } from "@react-navigation/native";
import { SCREENS } from "../../../../constant/screens";
import officalCharId from "../../../../../map/character_offical_id_map";
import { CombatType } from "../../../../types/combatType";
import { getCharFullData } from "../../../../utils/dataMap/getDataFromMap";
import useAppLanguage from "../../../../context/AppLanguage/useAppLanguage";
import { LOCALES } from "../../../../../locales";
import formatLocale from "../../../../utils/format/formatLocale";

type Props = {
  title: string;
  stars: number;
  round: number;
  roundAverage: number;
  roundRemaining: number;
  isFast: boolean;
  teams: {
    date: string;
    characters: {
      officalId: number;
      level: number;
      image: string;
      rare: number;
      combatType: CombatType;
    }[];
  }[];
};

export default function MOCFloor(props: Props) {
  const navigation = useNavigation();
  const { language } = useAppLanguage();

  return (
    props.isFast || (
      <View className="w-full  border border-[#DDDDDD20] rounded-[4px] p-2.5">
        <View style={{ flexDirection: "row", justifyContent: "space-between" }}>
          <View>
            {/* 標題 */}
            <Text className="text-[#FFF] text-[16px] font-[HY65]">
              {props.title}
            </Text>
            {/* 剩餘 ... 輪 */}
            <Text className="text-[#FFFFFF90] text-[12px] font-[HY65] leading-5">
              {formatLocale(LOCALES[language].PlayersRemainRounds, [
                props.roundRemaining,
              ])}
            </Text>
          </View>
          {/* 星星數 */}
          <View style={{ flexDirection: "row", alignItems: "center", gap: 8 }}>
            {Array.from({ length: props.stars }, (_, i) => (
              <Star key={i} />
            ))}
          </View>
        </View>
        {/* Layer 1 */}
        <View>
          <View className="mt-2">
            <View
              style={{
                flexDirection: "row",
                justifyContent: "space-between",
              }}
            >
              <View style={{ flexDirection: "row", gap: 4 }}>
                <Text className="text-[#FFF] text-[12px] font-[HY65] leading-5">
                  {props.teams[0].date}
                </Text>
                <Text className="text-[#FFF] text-[12px] font-[HY65] leading-5">
                  {formatLocale(LOCALES[language].PlayersRounds, [props.round])}
                </Text>
              </View>
              <Text className="text-[#FFF] text-[12px] font-[HY65] leading-5">
                {LOCALES[language].PlayersAverageRounds.replace(
                  "${1}",
                  props.roundAverage.toString()
                )}
              </Text>
            </View>
          </View>
          <View className="mt-2" style={{ flexDirection: "row", gap: 6 }}>
            {props.teams[0].characters?.map((char, i) => (
              <CharCard
                key={i}
                // @ts-ignore
                id={officalCharId[char.officalId]}
                name={`Lv ${char.level}`}
                image={char.image}
                rare={char.rare}
                combatType={char.combatType}
                onPress={() => {
                  // @ts-ignore
                  navigation.navigate(SCREENS.CharacterPage.id, {
                    // @ts-ignore
                    id: officalCharId[char.officalId],
                    // @ts-ignore
                    name: getCharFullData(officalCharId[char.officalId]).name,
                  });
                }}
              />
            ))}
          </View>
          <View className="w-full h-[1px] bg-[#FFFFFF20] mt-3"></View>
        </View>
        {/* Layer 2 */}
        <View>
          <View className="mt-2" style={{ flexDirection: "row", gap: 6 }}>
            {props.teams[1].characters?.map((char, i) => (
              <CharCard
                key={i}
                // @ts-ignore
                id={officalCharId[char.officalId]}
                name={`Lv ${char.level}`}
                image={char.image}
                rare={char.rare}
                // @ts-ignore
                combatType={char.combatType}
                onPress={() => {
                  // @ts-ignore
                  navigation.navigate(SCREENS.CharacterPage.id, {
                    // @ts-ignore
                    id: officalCharId[char.officalId],
                    // @ts-ignore
                    name: getCharFullData(officalCharId[char.officalId]).name,
                  });
                }}
              />
            ))}
          </View>
        </View>
      </View>
    )
  );
}

const StarIcon = require("./icons/Star.svg");
const Star = () => <Image source={StarIcon} className="w-6 h-6" />;
