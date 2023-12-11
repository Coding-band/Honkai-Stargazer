import { View, Text, Pressable } from "react-native";
import React, { useState } from "react";
import genRanHex from "../../../../utils/fun/genRanHex";
import { Image } from "expo-image";
import useHsrCharList from "../../../../hooks/hoyolab/useHsrCharList";

export default function PlayerCharacter() {
  
  const hsrCharList = useHsrCharList();

  return (
    <View className="flex flex-row gap-1">
      {hsrCharList?.slice(0, 3)?.map((char: any, i: number) => (
        <Pressable
          key={i}
          className="w-[30px] h-[30px] bg-[#D9D9D9] rounded-full overflow-hidden"
          style={{ justifyContent: "center", alignItems: "center" }}
        >
          <Image className="w-[26px] h-[26px]" source={{ uri: char.icon }} />
        </Pressable>
      ))}
    </View>
  );
}
