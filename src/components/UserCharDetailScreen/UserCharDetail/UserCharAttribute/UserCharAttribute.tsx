import { View, Text } from "react-native";
import React from "react";
import { Image } from "expo-image";
import useProfileHsrInGameInfo from "../../../../context/UserCharDetailData/hooks/useProfileHsrInGameInfo";
import useProfileCharFullData from "../../../../context/UserCharDetailData/hooks/useProfileCharFullData";

export default function UserCharAttribute() {
  const charFullData = useProfileCharFullData();
  const { inGameCharData } = useProfileHsrInGameInfo();

  const attributes = [
    {
      key: "hp",
      icon: require("../../../../../assets/images/ui_icon/ic_hp.webp"),
      value: (
        inGameCharData?.attributes.filter((attr: any) => attr.field === "hp")[0]
          ?.value +
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
    {
      key: "speed",
      icon: require("../../../../../assets/images/ui_icon/ic_speed.webp"),
      value: inGameCharData?.attributes.filter(
        (attr: any) => attr.field === "spd"
      )[0]?.display,
    },

    {
      key: "energy",
      icon: require("../../../../../assets/images/ui_icon/ic_energy.webp"),
      value: charFullData?.spRequirement,
    },
    {
      key: "crit_rate",
      icon: require("../../../../../assets/images/ui_icon/ic_crit_rate.webp"),
      value: inGameCharData?.attributes.filter(
        (attr: any) => attr.field === "crit_rate"
      )[0]?.display,
    },
    {
      key: "crit_dmg",
      icon: require("../../../../../assets/images/ui_icon/ic_crit_dmg.webp"),
      value: inGameCharData?.attributes.filter(
        (attr: any) => attr.field === "crit_dmg"
      )[0]?.display,
    },
    {
      key: "effect_hit_rate",
      icon: require("../../../../../assets/images/ui_icon/ic_effect_hit_rate.webp"),
      value: inGameCharData?.properties.filter(
        (attr: any) => attr.field === "effect_hit"
      )[0]?.display,
    },
    {
      key: "effect_res",
      icon: require("../../../../../assets/images/ui_icon/ic_effect_res.webp"),
      value: inGameCharData?.properties.filter(
        (attr: any) => attr.field === "effect_res"
      )[0]?.display,
    },
  ];

  return (
    <View
      className="px-4"
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
