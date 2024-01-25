import { View, Text } from "react-native";
import React from "react";
import { ImageBackground } from "expo-image";

const Bg = require("../../../../assets/images/bgs/pure_fiction_bg.png");

export default function WallPaperForPF() {
  return (
    <ImageBackground
      className="absolute w-full h-full"
      source={Bg}
      contentFit="cover"
      placeholder={"L03vN~kCfQkC:NfPfQfPoNfQfQfQ"}
    />
  );
}
