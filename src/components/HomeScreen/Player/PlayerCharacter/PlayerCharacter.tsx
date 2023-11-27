import { View, Text } from "react-native";
import React from "react";
import genRanHex from "../../../../utils/genRanHex";

const ranHexs = [
  [genRanHex(6), genRanHex(6)],
  [genRanHex(6), genRanHex(6)],
  [genRanHex(6), genRanHex(6)],
];

export default function PlayerCharacter() {
  return (
    <View className="flex flex-row gap-1">
      {ranHexs.map(([bg, bd], i) => (
        <View
          key={i}
          className="w-[30px] h-[30px] border-2 rounded-full"
          style={{ backgroundColor: "#" + bg, borderColor: "#" + bd }}
        />
      ))}
    </View>
  );
}
