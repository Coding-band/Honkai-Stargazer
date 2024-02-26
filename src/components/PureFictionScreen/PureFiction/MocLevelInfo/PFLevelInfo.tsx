import { View, Text, TouchableOpacity, Dimensions } from "react-native";
import React, { useEffect, useState } from "react";
import { Image } from "expo-image";
import CombatType from "../../../../../assets/images/images_map/combatType";
import useTextLanguage from "../../../../language/TextLanguage/useTextLanguage";
import { LinearGradient } from "expo-linear-gradient";
import MonsterImage from "../../../../../assets/images/images_map/monsterImage";
import MOCDataMap from "../../../../../map/memory_of_chao_data_map";
import { LOCALES } from "../../../../../locales";
import useAppLanguage from "../../../../language/AppLanguage/useAppLanguage";
import PFDataMap from "../../../../../map/pure_fiction_data_map";

export default function PFLevelInfo({
  versionNumber,
}: {
  versionNumber: number;
}) {
  // @ts-ignore
  const mocData = PFDataMap[versionNumber];
  const [floor, setFloor] = useState(1);
  const { language } = useAppLanguage();

  return (
    <View
      className="border border-[#DDDDDD20] rounded-[4px] p-2.5 w-[360px]"
      style={{ gap: 16 }}
    >
      {/* Top */}
      <View className="z-50 flex-row justify-between">
        <Text className="text-text text-[16px] font-[HY65]">
          {LOCALES[language].MOCMissionInfoTitle}
        </Text>
        <FloorOptions
          length={mocData.info.length}
          onChange={(f) => {
            setFloor(f);
          }}
        />
      </View>
      <View>
        <View style={{ gap: 8 }}>
          {/* 1 */}
          <Layer floor={floor} data={mocData?.info?.[floor - 1]} part={1} />
          <View className="w-full h-[1px] bg-[#FFFFFF20]" />
          {/* 2 */}
          <Layer floor={floor} data={mocData?.info?.[floor - 1]} part={2} />
        </View>
      </View>
      <Text className="text-text2 font-[HY65] text-[16px] text-center">
        {new Date(mocData.time.begin).toLocaleDateString()} -{" "}
        {new Date(mocData.time.end).toLocaleDateString()}
      </Text>
    </View>
  );
}

const Layer = ({
  floor,
  data,
  part,
}: {
  floor: number;
  data: any;
  part: number;
}) => {
  return (
    data && (
      <View
        className="h-[210px]"
        style={{
          flexDirection: "row",
          alignItems: "center",
        }}
      >
        {/* 關卡名稱 */}
        <View
          className="w-[100px]"
          style={{ alignItems: "center", justifyContent: "center" }}
        >
          <Text className="text-text text-[16px] font-[HY65]">
            {floor}-{part}
          </Text>
          <View style={{ flexDirection: "row" }}>
            <Image cachePolicy="none"
              className="w-4 h-4"
              source={
                // @ts-ignore
                CombatType[data["part" + part].weakness_suggest[0]]?.icon
              }
            />
            <Image cachePolicy="none"
              className="w-4 h-4"
              source={
                // @ts-ignore
                CombatType[data["part" + part].weakness_suggest[1]]?.icon
              }
            />
          </View>
        </View>
        {/* 怪物 */}
        <View style={{ gap: 12 }}>
          <View style={{ flex: 1, flexDirection: "row", gap: 12 }}>
            {data["part" + part]["wave1"].map((monster: any, i: number) => (
              <Mob key={i}>{monster}</Mob>
            ))}
          </View>
          <View style={{ flex: 1, flexDirection: "row", gap: 12 }}>
            {data["part" + part]["wave2"].map((monster: any, i: number) => (
              <Mob key={i}>{monster}</Mob>
            ))}
          </View>
          <View style={{ flex: 1, flexDirection: "row", gap: 12 }}>
            {data["part" + part]["wave3"].map((monster: any, i: number) => (
              <Mob key={i}>{monster}</Mob>
            ))}
          </View>
        </View>
      </View>
    )
  );
};

const Mob = ({ children }: { children: any }) => (
  <>
    <View style={{ gap: 2 }}>
      <TouchableOpacity activeOpacity={0.65}>
        <LinearGradient
          className="w-12 h-12 rounded-[4px]"
          style={{ justifyContent: "center", alignItems: "center" }}
          colors={["#78767D", "#9F9FAA"]}
        >
          <Image cachePolicy="none"
            cachePolicy="none"
            transition={200}
            // @ts-ignore
            source={MonsterImage[children.monster_name]?.icon}
            className="w-9 h-9"
          />
        </LinearGradient>
      </TouchableOpacity>
      <View style={{ flexDirection: "row" }}>
        {children.monster_weakness.map((w: any, i: number) => (
          // @ts-ignore
          <Image cachePolicy="none" key={i} className="w-4 h-4" source={CombatType[w]?.icon} />
        ))}
      </View>
    </View>
  </>
);

const FloorOptions = ({
  onChange,
  length,
}: {
  onChange: (floor: number) => void;
  length: number;
}) => {
  const { language } = useAppLanguage();
  const floors = [
    LOCALES[language].MOCMissionPart1,
    LOCALES[language].MOCMissionPart2,
    LOCALES[language].MOCMissionPart3,
    LOCALES[language].MOCMissionPart4,
    LOCALES[language].MOCMissionPart5,
    LOCALES[language].MOCMissionPart6,
    LOCALES[language].MOCMissionPart7,
    LOCALES[language].MOCMissionPart8,
    LOCALES[language].MOCMissionPart9,
    LOCALES[language].MOCMissionPart10,
    LOCALES[language].MOCMissionPart11,
    LOCALES[language].MOCMissionPart12,
  ].slice(0, length);

  const [currentFloor, setCurrentFloor] = useState(0);
  const [open, setOpen] = useState(false);

  useEffect(() => {
    onChange(currentFloor + 1);
  }, [currentFloor]);

  return (
    <View className="w-[90px]" style={{ alignItems: "flex-end" }}>
      <TouchableOpacity
        onPress={() => {
          setOpen(!open);
        }}
        activeOpacity={0.35}
        style={{ flexDirection: "row", alignItems: "center", gap: 8 }}
      >
        <Text className="text-text text-[16px] font-[HY65]">
          {floors[currentFloor]}
        </Text>
        <Image cachePolicy="none" source={require("./icons/More.svg")} className="w-3 h-1.5" />
      </TouchableOpacity>
      <View
        style={{
          display: open ? "flex" : "none",
          gap: 8,
          alignItems: "center",
        }}
        className="absolute top-[24px] right-0 bg-[#3E3E47] px-3 py-2 rounded-[4px]"
      >
        {floors.map((floor, i) => (
          <TouchableOpacity
            key={i}
            activeOpacity={0.35}
            onPress={() => {
              setOpen(false);
              setCurrentFloor(i);
            }}
            style={{ alignItems: "flex-end" }}
          >
            <Text className="text-text text-[16px] font-[HY65]">{floor}</Text>
          </TouchableOpacity>
        ))}
      </View>
    </View>
  );
};
