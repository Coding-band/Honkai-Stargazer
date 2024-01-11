import { View, Text } from "react-native";
import React from "react";
import { Image } from "expo-image";

export default function NoDataYet() {
  return (
    <View
      className="w-screen h-screen"
      style={{ justifyContent: "center", alignItems: "center" }}
    >
      <View style={{ gap: 21, alignItems: "center" }}>
        <Image
          className="w-[144px] h-[144px]"
          source={require("./images/03.png")}
        />
        <Text className="text-text font-[HY65] text-[16px]">
          暫時沒找到數據...
        </Text>
      </View>
    </View>
  );
}
