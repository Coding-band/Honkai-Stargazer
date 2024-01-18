import { View, Text } from "react-native";
import React from "react";
import CharCard from "../../../global/CharCard/CharCard";
import { Image } from "expo-image";
import { useNavigation } from "@react-navigation/native";
import { SCREENS } from "../../../../constant/screens";
import officalCharId from "../../../../../map/character_offical_id_map";
import { CombatType } from "../../../../types/combatType";
import {
  getCharFullData,
  getCharJsonData,
} from "../../../../utils/dataMap/getDataFromMap";
import useAppLanguage from "../../../../language/AppLanguage/useAppLanguage";
import { LOCALES } from "../../../../../locales";
import formatLocale from "../../../../utils/format/formatLocale";
import MocStar from "./MocStarIcon/MocStar";
import { Path } from "../../../../types/path";

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
  const { language } = useAppLanguage();

  return (
    <View className="border border-[#DDDDDD20] rounded-[4px] p-2.5 w-[360px]">
      <View style={{ flexDirection: "row", justifyContent: "space-between" }}>
        <View>
          {/* 標題 */}
          <Text className="text-[#FFF] text-[16px] font-[HY65]">
            {/* 20230118 UNSUITABLE TO EDIT*/}
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
            <MocStar key={i} />
          ))}
        </View>
      </View>
      {!props.isFast ? (
        <>
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
              </View>
              <Text className="text-[#FFF] text-[12px] font-[HY65] leading-5">
                {formatLocale(LOCALES[language].PlayersRounds, [props.round])}
              </Text>
            </View>
          </View>
          <View style={{ alignItems: "center" }}>
            {/* Layer 1 */}
            <MocFloorLayer team={props.teams[0]} />
            {/* Layer 2 */}
            <MocFloorLayer team={props.teams[1]} />
          </View>
        </>
      ) : (
        <Text className="text-[#DD8200] text-[14px] font-[HY65]">快速通關</Text>
      )}
    </View>
  );
}

const MocFloorLayer = ({ team }: { team: any }) => {
  const navigation = useNavigation();
  return (
    <View>
      <View className="mt-2" style={{ flexDirection: "row", gap: 6 }}>
        {team?.characters?.map((char: any, i: number) => {
          const charId = officalCharId[char.officalId];
          const charJsonData = getCharJsonData(charId);

          return (
            <CharCard
              key={i}
              // @ts-ignore
              id={charId}
              name={`Lv ${char.level}`}
              image={char.image}
              rare={char.rare}
              // @ts-ignore
              combatType={char.combatType}
              path={charJsonData.path as Path}
              rank={char.rank}
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
          );
        })}
      </View>
    </View>
  );
};
