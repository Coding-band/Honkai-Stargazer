import { View, Text } from "react-native";
import React from "react";
import { Image } from "expo-image";

export default function CardBg({ rare, width }: { rare: number, width:number }) {
  return (
    <Image cachePolicy="none"
      source={Bgs[rare]}
      className="absolute -left-5 -top-1"
      style={{width: width, height:width}}
      contentFit="contain"
    />
  );
}

const Bgs: any = {
  5: require("./bg/5StarBg.png"),
  4: require("./bg/4StarBg.png"),
  3: require("./bg/3StarBg.png"),
  2: require("./bg/2StarBg.png"),
  1: require("./bg/1StarBg.png"),
};
