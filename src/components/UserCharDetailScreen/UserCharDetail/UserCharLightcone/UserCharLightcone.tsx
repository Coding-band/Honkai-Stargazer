import { View, Text } from "react-native";
import React from "react";
import useProfileHsrInGameInfo from "../../../../context/UserCharDetailData/hooks/useProfileHsrInGameInfo";
import UserCharLevel from "../UserCharLevel/UserCharLevel";
import UserCharDetailStars from "../UserCharDetailStars/UserCharDetailStars";
import { Image } from "expo-image";
import Path from "../../../../../assets/images/images_map/path";

export default function UserCharLightcone() {
  const { inGameCharData } = useProfileHsrInGameInfo();

  return (
    <View>
      <View className="w-[135px] h-[1px] bg-[#F3F9FF40]"></View>
      <View className="py-[18px]" style={{ flexDirection: "row" }}>
        <View></View>
        <View style={{ gap: 2 }}>
          <Text className="text-[20px] font-[HY65] text-text">在藍天下</Text>
          <View
            className="mb-3 bg-[#222222] rounded-[49px] px-[12px] py-[4px]"
            style={{ alignItems: "center" }}
          >
            <Text className="text-[12px] text-[#FFFFFF] font-[HY65]">
              Lv 80 · 1星魂
            </Text>
          </View>
          <UserCharDetailStars count={5} />
          <View className="mt-2" style={{ flexDirection: "row", gap: 4 }}>
            <Image
              // @ts-ignore
              source={Path["Hunt"].icon}
              style={{ width: 24, height: 24 }}
            />
            <Text className="text-text text-[16px] font-[HY65]">巡猎</Text>
          </View>
          <View
            style={{
              flexDirection: "row",
              gap: 10,
              flexWrap: "wrap",
              justifyContent: "center",
            }}
          >
            {[
              {
                key: "hp",
                icon: require("../../../../../assets/images/ui_icon/ic_hp.webp"),
                value: (
                  inGameCharData?.attributes.filter(
                    (attr: any) => attr.field === "hp"
                  )[0]?.value +
                    inGameCharData?.additions.filter(
                      (attr: any) => attr.field === "hp"
                    )[0]?.value || 0
                ).toFixed(),
              },
              {
                key: "atk",
                icon: require("../../../../../assets/images/ui_icon/ic_atk.webp"),
                value: (
                  inGameCharData?.attributes.filter(
                    (attr: any) => attr.field === "atk"
                  )[0]?.value +
                    inGameCharData?.additions.filter(
                      (attr: any) => attr.field === "atk"
                    )[0]?.value || 0
                ).toFixed(),
              },
              {
                key: "def",
                icon: require("../../../../../assets/images/ui_icon/ic_def.webp"),
                value: (
                  inGameCharData?.attributes.filter(
                    (attr: any) => attr.field === "spd"
                  )[0]?.value +
                    inGameCharData?.additions.filter(
                      (attr: any) => attr.field === "spd"
                    )[0]?.value || 0
                ).toFixed(),
              },
            ].map(
              (attr) =>
                attr.value && (
                  <View style={{ flexDirection: "row", alignItems: "center" }}>
                    <Image source={attr.icon} className="w-6 h-6" />
                    <Text className="text-text text-[14px]"> {attr.value}</Text>
                  </View>
                )
            )}
          </View>
        </View>
      </View>
      <View className="w-[135px] h-[1px] bg-[#F3F9FF40]"></View>
    </View>
  );
}
