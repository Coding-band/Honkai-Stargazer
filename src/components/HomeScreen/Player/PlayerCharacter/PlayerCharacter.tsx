import { View, Text } from "react-native";
import React from "react";

export default function PlayerCharacter() {
  return (
    <View className="flex flex-row gap-1">
      <View className="w-[30px] h-[30px] bg-[#D9D9D9] border-2 border-[#D3D3D3] rounded-full" />
      <View
        className="w-[30px] h-[30px] bg-[#D9D9D9] border-2 border-[#D3D3D3] rounded-full"
      />
      <View className="w-[30px] h-[30px] bg-[#D9D9D9] border-2 border-[#D3D3D3] rounded-full" />
    </View>
  );
}
