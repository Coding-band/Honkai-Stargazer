import { View, Text } from "react-native";
import React from "react";
import { Image } from "expo-image";

export default function NoPublicData() {
  return (
    <View
      className="h-[250px]"
      style={{ justifyContent: "center", alignItems: "center" }}
    >
      <View style={{ gap: 21, alignItems: "center" }}>
        <Image
          className="w-[144px] h-[144px]"
          source={require("./images/04.png")}
        />
        <Text className="text-text font-[HY65] text-[16px] leading-5">
          玩家没有公開信息哦
        </Text>
      </View>
    </View>
  );
}
