import { View, Text } from "react-native";
import React from "react";
import Button from "../../../global/ui/Button/Button";
import { Image } from "expo-image";

export default function CharAction() {
  return (
    <View
      className="w-full h-[46px] absolute bottom-0 z-50 mb-[37px]"
      style={{
        justifyContent: "center",
        alignItems: "center",
        flexDirection: "row",
        gap: 27,
      }}
    >
      <Button width={140} height={46}>
        <Text className="font-[HY65] text-[16px]">推荐装备</Text>
      </Button>
      <Button width={140} height={46}>
        <Text className="font-[HY65] text-[16px]">推荐配队</Text>
      </Button>
    </View>
  );
}
