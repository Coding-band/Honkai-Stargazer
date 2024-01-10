import { View, Text } from "react-native";
import React from "react";
import { Image } from "expo-image";

export default function NotFound() {
  return (
    <View
      className="w-screen h-screen"
      style={{ justifyContent: "center", alignItems: "center" }}
    >
      <View style={{ gap: 21, alignItems: "center" }}>
        <Image
          className="w-[144px] h-[144px]"
          source={require("./images/02.png")}
        />
        <Text className="text-text font-[HY65] text-[16px]">
          和列車失去聯繫了...
        </Text>
      </View>
    </View>
  );
}
