import { View, Text, ScrollView } from "react-native";
import React, { useState } from "react";
import { Image } from "expo-image";
import MOCFloor from "./MOCFloor/MOCFloor";
import useMemoryOfChaos from "../../../hooks/hoyolab/useMemoryOfChaos";
import { capitalize } from "lodash";
import useHsrPlayerData from "../../../hooks/hoyolab/useHsrPlayerData";
import { LOCALES } from "../../../../locales";
import useAppLanguage from "../../../language/AppLanguage/useAppLanguage";
import { animated, useTrail } from "@react-spring/native";
import NotFound from "../../global/Loading/NotFound";
import NoDataYet from "../../global/Loading/NoDataYet";
import { globalStyles } from "../../../../styles/global";
import Button from "../../global/Button/Button";

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
        date: `${floor?.node_1?.challenge_time.year}.${(
          "0" + floor?.node_1?.challenge_time.month
        ).slice(-2)}.${("0" + floor?.node_1?.challenge_time.day).slice(-2)} ${(
          "0" + floor?.node_1?.challenge_time.hour
        ).slice(-2)}:${("0" + floor?.node_1?.challenge_time.minute).slice(-2)}`,
        characters: floor?.node_1?.avatars?.map((char: any) => ({
          officalId: char.id,
          level: char?.level,
          image: char?.icon,
          rare: char?.rarity,
          rank: char?.rank,
          combatType: capitalize(char?.element),
        })),
      },
      {
        date: `${floor?.node_2?.challenge_time.year}.${(
          "0" + floor?.node_2?.challenge_time.month
        ).slice(-2)}.${("0" + floor?.node_2?.challenge_time.day).slice(-2)} ${(
          "0" + floor?.node_2?.challenge_time.hour
        ).slice(-2)}:${("0" + floor?.node_2?.challenge_time.minute).slice(-2)}`,
        characters: floor?.node_2?.avatars?.map((char: any) => ({
          officalId: char.id,
          level: char?.level,
          image: char?.icon,
          rare: char?.rarity,
          rank: char?.rank,
          combatType: capitalize(char?.element),
        })),
      },
    ],
  }));

  // state
  const [timeMode, setTimeMode] = useState<"text" | "time">("text");

  if (!floors?.length) return <NoDataYet />;
  if (!moc) return <NotFound />;

  return (
    <ScrollView className="z-30 pt-[127px] pb-0">
      <View
        style={{ ...globalStyles.rJCenterFWrap, gap: 12 }}
        className="pb-48"
      >
        {/* Top */}
        <View
          className="w-full px-4 "
          style={{ gap: 20, alignItems: "center" }}
        >
          <Button
            width={250}
            height={46}
            onPress={() => {
              if (timeMode === "time") setTimeMode("text");
              else setTimeMode("time");
            }}
          >
            {timeMode === "time" ? (
              <Text className="text-[#222] font-[HY65]">
                {moc.begin_time.month.toLocaleString("en-US", {
                  minimumIntegerDigits: 2,
                  useGrouping: false,
                })}
                月
                {moc.begin_time.day.toLocaleString("en-US", {
                  minimumIntegerDigits: 2,
                  useGrouping: false,
                })}
                日 -{" "}
                {moc.end_time.month.toLocaleString("en-US", {
                  minimumIntegerDigits: 2,
                  useGrouping: false,
                })}
                月
                {moc.end_time.day.toLocaleString("en-US", {
                  minimumIntegerDigits: 2,
                  useGrouping: false,
                })}
                日
              </Text>
            ) : (
              "1.5 下半 - 2 藏於深空之秘"
            )}
          </Button>
          <View
            className="w-full"
            style={{ flexDirection: "row", justifyContent: "space-between" }}
          >
            <Text className="text-white font-[HY65]">
              {LOCALES[language].PlayersBattleReport.replace(
                "${1}",
                playerData?.nickname
              )}
            </Text>
            <Text className="text-white font-[HY65]">正向排序</Text>
          </View>
        </View>
        {floors?.map((floor: any, index: number) => (
          <MOCFloor key={floor?.title} {...floor} />
        ))}
      </View>
    </ScrollView>
  );
}
