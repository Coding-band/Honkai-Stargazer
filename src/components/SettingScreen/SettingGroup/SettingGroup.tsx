import { View, Text } from "react-native";
import React from "react";
import { BlurView } from "expo-blur";
import { Image } from "expo-image";
import SettingItem from "../SettingItem/SettingItem";

type Props = {
  children: any;
};

export default function SettingGroup(props: Props) {
  return (
    <View className="w-full" style={{ gap: 6 }}>
      <Text className="text-text text-[16px] font-[HY55]">帳號設置</Text>
      {props.children}
    </View>
  );
}
