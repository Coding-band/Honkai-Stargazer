import { View, Text, Platform } from "react-native";
import React from "react";
import { ImageBackground } from "expo-image";
import useWallPaper from "../../../redux/wallPaper/useWallPaper";
import { wallPapers } from "../../../redux/wallPaper/wallpapers";

type Props = {
  isBlur?: boolean;
  wallPaperId?: number;
};

export default function WallPaper(props: Props) {
  const { wallPaper } = useWallPaper();

  return (
    <ImageBackground
      className="absolute w-full h-full -mt-1 scale-105"
      // 把背景關掉
      source={
        wallPapers?.filter((w) => w?.id === props?.wallPaperId)[0]?.url ||
        wallPaper?.url
      }
      // placeholder={blurhash}
      cachePolicy="memory"
      contentFit="cover"
      blurRadius={props.isBlur ? (Platform.OS === "android" ? 2 : 5) : 0}
    />
  );
}
