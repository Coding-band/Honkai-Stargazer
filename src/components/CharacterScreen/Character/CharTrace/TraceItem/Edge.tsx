import { View } from "react-native";
import React from "react";
import { ExpoImage } from "../../../../../types/image";
import { Image } from "expo-image";

export default ({
  left,
  top,
  icon,
}: {
  left: number;
  top: number;
  icon: ExpoImage;
}) => (
  <View
    style={{
      position: "absolute",
      left,
      top,
      justifyContent: "center",
      alignItems: "center",
    }}
    className="w-8 h-8 bg-[#666] rounded-full"
  >
    <Image source={icon} className="w-6 h-6" />
  </View>
);
