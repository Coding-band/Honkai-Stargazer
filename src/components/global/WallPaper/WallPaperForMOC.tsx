import { View, Text } from "react-native";
import React from "react";
import { ImageBackground } from "expo-image";

const Bg = require("../../../../assets/images/bgs/memory_of_chaos_bg.webp");

export default function WallPaperForMOC() {
  return (
    <ImageBackground
      className="absolute w-full h-full"
      source={Bg}
      cachePolicy="memory"
      contentFit="cover"
      placeholder={"L11M7[fkayj[+DaxfkjtxyayfQfQ"}
    />
  );
}
