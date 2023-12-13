import { View, Text } from "react-native";
import React from "react";
import { ImageBackground } from "expo-image";
import useWallPaper from "../../../redux/wallPaper/useWallPaper";

export default function WallPaper() {
  const { wallPaper } = useWallPaper();
  return (
    <ImageBackground
      className="absolute w-full h-full -mt-1"
      // 把背景關掉
      source={{ uri: wallPaper }}
      // placeholder={blurhash}
      contentFit="cover"
    />
  );
}
