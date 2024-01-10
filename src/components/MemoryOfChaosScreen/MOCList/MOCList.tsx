import { View, Text, ScrollView } from "react-native";
import React from "react";
import { Image } from "expo-image";
import MOCFloor from "./MOCFloor/MOCFloor";
import useMemoryOfChaos from "../../../hooks/hoyolab/useMemoryOfChaos";
import { capitalize } from "lodash";
import useHsrPlayerData from "../../../hooks/hoyolab/useHsrPlayerData";
import { LOCALES } from "../../../../locales";
import useAppLanguage from "../../../language/AppLanguage/useAppLanguage";
import { animated, useTrail } from "@react-spring/native";
import NotFound from "../../global/Loading/NotFound";

export default function MOCList() {
  // data
  const { data: moc } = useMemoryOfChaos();
  const playerData = useHsrPlayerData();
  const { language } = useAppLanguage();

  const floors = moc?.all_floor_detail?.map((floor: any) => ({
    title: floor?.name,
    stars: floor?.star_num,
    round: floor?.round_num,
    roundAverage: floor?.round_num,
    roundRemaining: 30 - floor?.round_num,
    isFast: floor?.is_fast,
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

  // animation
  const [trails] = useTrail(
    10,
    () => ({
      from: { opacity: 0 },
      to: { opacity: 1 },
    }),
    []
  );

  return moc ? (
    <ScrollView className="z-30 pt-[127px] px-[17px] pb-0">
      <View
        style={{
          flexDirection: "row",
          justifyContent: "center",
          flexWrap: "wrap",
          gap: 12,
        }}
        className="pb-36"
      >
        {/* Top */}
        <View
          className="w-full"
          style={{ flexDirection: "row", justifyContent: "space-between" }}
        >
          {
            <Text className="text-white font-[HY65]">
              {moc.begin_time.month.toLocaleString("en-US", {
                minimumIntegerDigits: 2,
                useGrouping: false,
              })}
              /
              {moc.begin_time.day.toLocaleString("en-US", {
                minimumIntegerDigits: 2,
                useGrouping: false,
              })}{" "}
              -{" "}
              {moc.end_time.month.toLocaleString("en-US", {
                minimumIntegerDigits: 2,
                useGrouping: false,
              })}
              /
              {moc.end_time.day.toLocaleString("en-US", {
                minimumIntegerDigits: 2,
                useGrouping: false,
              })}
            </Text>
          }
          <Text className="text-white font-[HY65]">
            {LOCALES[language].PlayersBattleReport.replace(
              "${1}",
              playerData?.nickname
            )}
          </Text>
        </View>
        {floors.length ? (
          floors?.map((floor: any, index: number) => (
            <AnimatedView
              key={floor?.title}
              // style={trails[index]}
            >
              <MOCFloor {...floor} />
            </AnimatedView>
          ))
        ) : (
          <Text className="text-text">{LOCALES[language].NoDataYet}</Text>
        )}
      </View>
    </ScrollView>
  ) : (
    <NotFound />
  );
}

const AnimatedView = animated(View);
