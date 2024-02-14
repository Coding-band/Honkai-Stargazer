import { View, Text } from "react-native";
import React from "react";
import { ImageBackground } from "expo-image";
import { LinearGradient } from "expo-linear-gradient";

export default function WallPaperForPF() {
  return (
    <LinearGradient
      className="absolute w-full h-full"
      colors={["#262E3D", "#1A3555"]}
    />
  );
}
