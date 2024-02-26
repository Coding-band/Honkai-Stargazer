import { View, Text, Pressable } from "react-native";
import React from "react";
import { Image } from "expo-image";
import { TouchableOpacity } from "react-native-gesture-handler";

const LeftIcon = require("../../../../../../assets/icons/Left.svg");

export default function LeftBtn({ onPress }: { onPress?: () => void }) {
  return (
    <TouchableOpacity
      className="translate-y-8"
      style={{
        width: 22,
        height: 16,
        justifyContent: "center",
        alignItems: "center",
      }}
      onPress={onPress}
    >
      <Image cachePolicy="none" style={{ width: 7.5, height: 15 }} source={LeftIcon} />
    </TouchableOpacity>
  );
}
