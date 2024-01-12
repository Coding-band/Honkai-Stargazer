import { View, Text } from "react-native";
import React from "react";
import { LightconeName } from "../../../../../types/lightcone";

export default function LightconeLevel({
  lcId,
  lcFullData,
  lcInGameData,
}: {
  lcId: LightconeName;
  lcFullData: any;
  lcInGameData: any;
}) {
  return (
    <View
      className="mb-2 bg-[#222222] rounded-[49px] px-[12px] py-[4px]"
      style={{ alignItems: "center" }}
    >
      <Text className="text-[12px] text-[#FFFFFF] font-[HY65] leading-4">
        Lv {lcInGameData.level} · {lcInGameData.rank}疊影
      </Text>
    </View>
  );
}
