import { View, Text } from "react-native";
import React from "react";

type Props = {
  title: string;
  children: any;
};

export default function SettingGroup(props: Props) {
  return (
    <View className="w-full" style={{ gap: 6 }}>
      <Text className="text-text text-[16px] font-[HY55]">{props.title}</Text>
      {props.children}
    </View>
  );
}
