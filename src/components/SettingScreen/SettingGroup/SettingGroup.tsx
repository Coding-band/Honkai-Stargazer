import { View, Text } from "react-native";
import React from "react";

type Props = {
  title: any;
  children: any;
};

export default function SettingGroup(props: Props) {
  return (
    <>
      <View className="w-full mb-[-16px] mt-[20px]">
        <Text className="text-text text-[16px] font-[HY55] leading-5">
          {props.title}
        </Text>
      </View>
      {props.children}
    </>
  );
}
