import { View, Text, TouchableOpacity } from "react-native";
import React from "react";
import { BlurView } from "expo-blur";
import { Image } from "expo-image";

const ArrowRight = require("./icons/ArrowRight.svg");

export default function SettingItem() {
  return (
    <BlurView tint="dark" intensity={30} className="w-full">
      <View
        className="w-full h-[41px] bg-[#F3F9FF80] border-b border-[#979797]"
        style={{ flexDirection: "row" }}
      >
        <View
          className="flex-1 h-full px-3"
          style={{ justifyContent: "center" }}
        >
          <Text className="text-[14px] font-[HY65]">使用邀请码</Text>
        </View>
        <TouchableOpacity activeOpacity={0.35}>
          <View
            className="w-40 h-full bg-[#F3F9FF40]"
            style={{ justifyContent: "center", alignItems: "center" }}
          >
            <Text className="text-[14px] font-[HY65]">已使用</Text>
            <Image
              source={ArrowRight}
              className="w-3 h-[14px] absolute right-[14px]"
            />
          </View>
        </TouchableOpacity>
      </View>
    </BlurView>
  );
}
