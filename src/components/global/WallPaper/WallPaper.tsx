import { View, Text } from "react-native";
import React from "react";
import { ImageBackground } from "expo-image";
import useWallPaper from "../../../redux/wallPaper/useWallPaper";

type Props = {
  isBlur?: boolean;
};

export default function WallPaper(props: Props) {
  const { wallPaper } = useWallPaper();
  return (
    <ImageBackground
      className="absolute w-full h-full -mt-1 scale-105"
      // 把背景關掉
      source={ wallPaper?.url }
      // placeholder={blurhash}
      contentFit="cover"
      blurRadius={props.isBlur ? 5 : 0}
    />
  );
}
