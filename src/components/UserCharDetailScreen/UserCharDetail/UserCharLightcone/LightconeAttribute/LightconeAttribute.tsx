import { View, Text } from "react-native";
import React from "react";
import { LightconeName } from "../../../../../types/lightcone";
import useProfileCharFullData from "../../../../../context/UserCharDetailData/hooks/useProfileCharFullData";
import useProfileHsrInGameInfo from "../../../../../context/UserCharDetailData/hooks/useProfileHsrInGameInfo";
import { Image } from "expo-image";

export default function LightconeAttribute({ lcId }: { lcId: LightconeName }) {
  const { inGameCharData } = useProfileHsrInGameInfo();
  const attributes = [
    {
      key: "hp",
      icon: require("../../../../../../assets/images/ui_icon/ic_hp.webp"),
      value: (
        inGameCharData.light_cone.attributes.filter(
          (attr: any) => attr.field === "hp"
        )[0]?.value +
          inGameCharData.light_cone.attributes.filter(
            (attr: any) => attr.field === "hp"
          )[0]?.value || 0
      ).toFixed(),
    },
    {
      key: "atk",
      icon: require("../../../../../../assets/images/ui_icon/ic_atk.webp"),
      value: (
        inGameCharData.light_cone.attributes.filter(
          (attr: any) => attr.field === "atk"
        )[0]?.value +
          inGameCharData.light_cone.attributes.filter(
            (attr: any) => attr.field === "atk"
          )[0]?.value || 0
      ).toFixed(),
    },
    {
      key: "def",
      icon: require("../../../../../../assets/images/ui_icon/ic_def.webp"),
      value: (
        inGameCharData.light_cone.attributes.filter(
          (attr: any) => attr.field === "def"
        )[0]?.value +
          inGameCharData.light_cone.attributes.filter(
            (attr: any) => attr.field === "def"
          )[0]?.value || 0
      ).toFixed(),
    },
  ];

  return (
    <View
      style={{
        flexDirection: "row",
        gap: 10,
        flexWrap: "wrap",
        justifyContent: "center",
      }}
    >
      {attributes.map(
        (attr) =>
          attr.value && (
            <View style={{ flexDirection: "row", alignItems: "center" }}>
              <Image source={attr.icon} className="w-6 h-6" />
              <Text className="text-text text-[14px]"> {attr.value}</Text>
            </View>
          )
      )}
    </View>
  );
}
