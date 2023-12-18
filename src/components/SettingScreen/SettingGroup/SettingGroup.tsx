import { View, Text } from "react-native";
import React from "react";

type Props = {
  title: string;
  children: any;
};

export default function SettingGroup(props: Props) {
  return (
    <View className="w-full">
      <Text className="text-text text-[16px] font-[HY55] mb-1.5">
        {props.title}
      </Text>
      {props.children}
    </View>
  );
}
