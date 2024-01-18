import { View, Text } from "react-native";
import React from "react";
import { ImageBackground } from "expo-image";

export default function WallPaperForMOC() {
  return (
    <ImageBackground
      cachePolicy="memory"
      className="absolute w-full h-full"
      // 把背景關掉
      source={require("../../../../assets/images/bgs/memory_of_chaos_bg.webp")}
      // placeholder={blurhash}
      contentFit="cover"
    />
  );
}
