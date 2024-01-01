import { View, Text, ScrollView } from "react-native";
import React from "react";
import { Image } from "expo-image";
import MOCFloor from "./MOCFloor/MOCFloor";
import useMemoryOfChaos from "../../../hooks/hoyolab/useMemoryOfChaos";
import { capitalize } from "lodash";
import useHsrPlayerData from "../../../hooks/hoyolab/useHsrPlayerData";
import { LOCALES } from "../../../../locales";
import useAppLanguage from "../../../context/AppLanguage/useAppLanguage";

export default function MOCList() {
  const { data: moc } = useMemoryOfChaos();
  const playerData = useHsrPlayerData();
  const {language} = useAppLanguage();

  const floors = moc?.all_floor_detail?.map((floor: any) => ({
    title: floor?.name,
    stars: floor?.star_num,
    round: floor?.round_num,
    roundAverage: floor?.round_num,
    roundRemaining: 30 - floor?.round_num,
    teams: [
      {
        date: `${floor?.node_1?.challenge_time.year}.${floor?.node_1?.challenge_time.month}.${floor?.node_1?.challenge_time.day} ${floor?.node_1?.challenge_time.hour}:${floor?.node_1?.challenge_time.minute}`,
        characters: floor?.node_1?.avatars?.map((char: any) => ({
          officalId: char.id,
          level: char?.level,
          image: char?.icon,
          rare: char?.rarity,
          combatType: capitalize(char?.element),
        })),
      },
      {
        date: `${floor?.node_2?.challenge_time.year}.${floor?.node_2?.challenge_time.month}.${floor?.node_2?.challenge_time.day} ${floor?.node_2?.challenge_time.hour}:${floor?.node_2?.challenge_time.minute}`,
        characters: floor?.node_2?.avatars?.map((char: any) => ({
          officalId: char.id,
          level: char?.level,
          image: char?.icon,
          rare: char?.rarity,
          combatType: capitalize(char?.element),
        })),
      },
    ],
  }));

  return (
    <ScrollView className="z-30 py-[127px] px-[17px] pb-0">
      <View
        style={{
          flexDirection: "row",
          flexWrap: "wrap",
          gap: 12,
        }}
        className="pb-60"
      >
        {/* Top */}
        <View
          className="w-full"
          style={{ flexDirection: "row", justifyContent: "space-between" }}
        >
          <Text className="text-white font-[HY65]">排序方式：正序</Text>
          <Text className="text-white font-[HY65]">
            {LOCALES[language].PlayersBattleReport.replace("${1}",playerData?.nickname)}
          </Text>
        </View>
        {floors?.map((floor: any) => (
          <MOCFloor key={floor?.title} {...floor} />
        ))}
      </View>
    </ScrollView>
  );
}
