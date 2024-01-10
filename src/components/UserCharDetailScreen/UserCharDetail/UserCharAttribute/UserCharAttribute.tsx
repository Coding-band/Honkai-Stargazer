import { View, Text } from "react-native";
import React from "react";
import { Image } from "expo-image";
import useProfileHsrInGameInfo from "../../../../context/UserCharDetailData/hooks/useProfileHsrInGameInfo";
import useProfileCharFullData from "../../../../context/UserCharDetailData/hooks/useProfileCharFullData";
import AttributeImage from "../../../../../assets/images/images_map/attributeImage";

export default function UserCharAttribute() {
  const charFullData = useProfileCharFullData();
  const { inGameCharData } = useProfileHsrInGameInfo();

  const attributes = [
    {
      key: "hp",
      icon: AttributeImage.hp,
      value: (
        (inGameCharData?.attributes.filter(
          (attr: any) => attr.field === "hp"
        )[0]?.value || 0) +
        (inGameCharData?.additions.filter((attr: any) => attr.field === "hp")[0]
          ?.value || 0)
      ).toFixed(),
    },
    {
      key: "atk",
      icon: AttributeImage.atk,
      value: (
        (inGameCharData?.attributes.filter(
          (attr: any) => attr.field === "atk"
        )[0]?.value || 0) +
        (inGameCharData?.additions.filter(
          (attr: any) => attr.field === "atk"
        )[0]?.value || 0)
      ).toFixed(),
    },
    {
      key: "def",
      icon: AttributeImage.def,
      value: (
        (inGameCharData?.attributes.filter(
          (attr: any) => attr.field === "spd"
        )[0]?.value || 0) +
        (inGameCharData?.additions.filter(
          (attr: any) => attr.field === "spd"
        )[0]?.value || 0)
      ).toFixed(),
    },
    {
      key: "speed",
      icon: AttributeImage.spd,
      value: inGameCharData?.attributes.filter(
        (attr: any) => attr.field === "spd"
      )[0]?.display,
    },
    // {
    //   key: "energy",
    //   icon: AttributeImage.energy,
    //   value: charFullData?.spRequirement,
    // },
    {
      key: "crit_rate",
      icon: AttributeImage.crit_rate,
      value:
        (
          ((inGameCharData?.attributes.filter(
            (attr: any) => attr.field === "crit_rate"
          )[0]?.value || 0) +
            (inGameCharData?.additions.filter(
              (attr: any) => attr.field === "crit_rate"
            )[0]?.value || 0)) *
          100
        ).toFixed(0) + "%",
    },
    {
      key: "crit_dmg",
      icon: AttributeImage.crit_dmg,
      value:
        (
          ((inGameCharData?.attributes.filter(
            (attr: any) => attr.field === "crit_dmg"
          )[0]?.value || 0) +
            (inGameCharData?.additions.filter(
              (attr: any) => attr.field === "crit_dmg"
            )[0]?.value || 0)) *
          100
        ).toFixed(0) + "%",
    },
    {
      key: "effect_hit",
      icon: AttributeImage.crit_rate,
      value: inGameCharData?.properties.filter(
        (attr: any) => attr.field === "effect_hit"
      )[0]?.display,
    },
    {
      key: "effect_res",
      icon: AttributeImage.effect_res,
      value: inGameCharData?.properties.filter(
        (attr: any) => attr.field === "effect_res"
      )[0]?.display,
    },
    {
      key: "heal_rate",
      icon: AttributeImage.heal_rate,
      value: inGameCharData?.properties.filter(
        (attr: any) => attr.field === "heal_rate"
      )[0]?.display,
    },
    {
      key: "break_dmg",
      icon: AttributeImage.break_dmg,
      value: inGameCharData?.properties.filter(
        (attr: any) => attr.field === "break_dmg"
      )[0]?.display,
    },
    {
      key: "lightning_dmg",
      icon: AttributeImage.lightning_dmg,
      value: inGameCharData?.properties.filter(
        (attr: any) => attr.field === "lightning_dmg"
      )[0]?.display,
    },
    {
      key: "quantum_dmg",
      icon: AttributeImage.quantum_dmg,
      value: inGameCharData?.properties.filter(
        (attr: any) => attr.field === "quantum_dmg"
      )[0]?.display,
    },
    {
      key: "ice_dmg",
      icon: AttributeImage.ice_dmg,
      value: inGameCharData?.properties.filter(
        (attr: any) => attr.field === "ice_dmg"
      )[0]?.display,
    },
    {
      key: "fire_dmg",
      icon: AttributeImage.fire_dmg,
      value: inGameCharData?.properties.filter(
        (attr: any) => attr.field === "fire_dmg"
      )[0]?.display,
    },
    {
      key: "imaginary_dmg",
      icon: AttributeImage.imaginary_dmg,
      value: inGameCharData?.properties.filter(
        (attr: any) => attr.field === "imaginary_dmg"
      )[0]?.display,
    },
    {
      key: "physical_dmg",
      icon: AttributeImage.physical_dmg,
      value: inGameCharData?.properties.filter(
        (attr: any) => attr.field === "physical_dmg"
      )[0]?.display,
    },
    {
      key: "wind_dmg",
      icon: AttributeImage.wind_dmg,
      value: inGameCharData?.properties.filter(
        (attr: any) => attr.field === "wind_dmg"
      )[0]?.display,
    },
  ];

  return (
    <View
      className="px-3"
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
