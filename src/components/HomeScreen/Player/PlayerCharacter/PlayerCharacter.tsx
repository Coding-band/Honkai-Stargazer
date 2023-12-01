import { View, Text, Pressable } from "react-native";
import React, { useState } from "react";
import genRanHex from "../../../../utils/genRanHex";
import { Image } from "expo-image";

export default function PlayerCharacter() {
  const [ranHexs, setRanHexs] = useState([
    [genRanHex(6), genRanHex(6)],
    [genRanHex(6), genRanHex(6)],
    [genRanHex(6), genRanHex(6)],
  ]);

  return (
    <View className="flex flex-row gap-1">
      <Pressable
        className="w-[30px] h-[30px] rounded-full"
        // style={{
        //   backgroundColor: "#" + ranHexs[0][0],
        //   borderColor: "#" + ranHexs[0][1],
        // }}
      >
        <Image
          className="w-full h-full"
          source={require("../../../../../assets/vocchi.png")}
        />
      </Pressable>
      <Pressable
        className="w-[30px] h-[30px] rounded-full"
        // style={{
        //   backgroundColor: "#" + ranHexs[1][0],
        //   borderColor: "#" + ranHexs[1][1],
        // }}
      >
        <Image
          className="w-full h-full"
          source={require("../../../../../assets/vocchi.png")}
        />
      </Pressable>
      <Pressable
        className="w-[30px] h-[30px] rounded-full"
        // style={{
        //   backgroundColor: "#" + ranHexs[2][0],
        //   borderColor: "#" + ranHexs[2][1],
        // }}
      >
        <Image
          className="w-full h-full"
          source={require("../../../../../assets/vocchi.png")}
        />
      </Pressable>
    </View>
  );
}
