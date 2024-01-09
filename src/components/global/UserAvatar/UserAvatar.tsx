import { View, Text } from "react-native";
import React from "react";
import { TouchableOpacity } from "react-native";
import { Image } from "expo-image";
import { ExpoImage } from "../../../types/image";

export default function UserAvatar({
  image,
  onPress,
}: {
  image: ExpoImage;
  onPress: () => void;
}) {
  return (
    <TouchableOpacity
      className="z-50 bg-white rounded-full border border-[#907C54]"
      onPress={onPress}
      activeOpacity={0.35}
    >
      <Image
        source={image}
        className="w-[73px] h-[73px] rounded-full"
        style={{
          backgroundColor: "rgba(144, 124, 84, 0.4)",
        }}
      />
    </TouchableOpacity>
  );
}
