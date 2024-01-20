import { View, Text } from "react-native";
import React from "react";
import { Image } from "expo-image";
import ScoreRangeFontMap from "./ScoreRangeFontMap";

export default function ScoreRangeFont({ scoreRange }: { scoreRange: string }) {
  return (
    <Image
      className="w-12 h-8"
      source={ScoreRangeFontMap[scoreRange]}
      contentFit="contain"
    />
  );
}
