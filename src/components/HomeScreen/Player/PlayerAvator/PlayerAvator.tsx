import { Image } from "expo-image";
import React from "react";
import { View } from "react-native";

const testAvatorImage = require("../../../../../assets/images/test-avator.png");

export default function PlayerAvator() {
  return (
    <View className="w-[73px] h-[73px] rounded-full mr-2 bg-white">
      <Image
        source={testAvatorImage}
        className="w-[73px] h-[73px] rounded-full"
        style={{
          backgroundColor: "rgba(144, 124, 84, 0.4)",
        }}
      />
    </View>
  );
}
