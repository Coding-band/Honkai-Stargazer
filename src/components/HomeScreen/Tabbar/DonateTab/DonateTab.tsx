import { View, Text, TouchableOpacity } from "react-native";
import React from "react";
import { Image, ImageBackground } from "expo-image";

export default function DonateTab() {
  return (
    <ImageBackground
      source={require("../../../../../assets/ads/donate_ad_bg.png")}
      className="w-full h-full"
    >
      <View className="absolute w-full h-full opacity-80 bg-[#FFF]" />
      <View className="pt-[6px]" style={{ alignItems: "center", gap: 8 }}>
        <Text className="text-black font-[HY65]">
          请考虑捐赠我们的工作，老子天天爆肝寫
        </Text>
        <View style={{ flexDirection: "row", gap: 14 }}>
          <DonateBtn>$2</DonateBtn>
          <DonateBtn>$5</DonateBtn>
          <DonateBtn>$10</DonateBtn>
          <DonateBtn>隨機</DonateBtn>
        </View>
      </View>
    </ImageBackground>
  );
}

const DonateBtn = (props: { children: string }) => {
  return (
    <TouchableOpacity
      activeOpacity={0.65}
      className="w-[77px] h-9 bg-[#404165] rounded-[49px] border-2 border-[#FFFFFF30]"
      style={{ alignItems: "center", justifyContent: "center" }}
    >
      <Text className="font-[HY65] text-text">{props.children}</Text>
    </TouchableOpacity>
  );
};
