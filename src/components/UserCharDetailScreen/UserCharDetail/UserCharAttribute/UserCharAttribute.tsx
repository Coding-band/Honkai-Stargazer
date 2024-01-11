import { View, Text, TouchableOpacity } from "react-native";
import React, { useState } from "react";
import { Image } from "expo-image";
import useProfileHsrInGameInfo from "../../../../context/UserCharDetailData/hooks/useProfileHsrInGameInfo";
import AttributeImage from "../../../../../assets/images/images_map/attributeImage";
import { LOCALES } from "../../../../../locales";
import useAppLanguage from "../../../../language/AppLanguage/useAppLanguage";

export default function UserCharAttribute() {
  const { language } = useAppLanguage();

  const [displayMode, setDisplayMode] = useState<"light" | "normal">("light");

  const { inGameCharData } = useProfileHsrInGameInfo();

  const attributes = [
    {
      key: "hp",
      icon: AttributeImage.hp,
      attr: inGameCharData?.attributes.filter(
        (attr: any) => attr.field === "hp"
      )[0]?.display,
      addi: inGameCharData?.additions.filter(
        (attr: any) => attr.field === "hp"
      )[0]?.display,
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
      attr: inGameCharData?.attributes.filter(
        (attr: any) => attr.field === "atk"
      )[0]?.display,
      addi: inGameCharData?.additions.filter(
        (attr: any) => attr.field === "atk"
      )[0]?.display,
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
      attr: inGameCharData?.attributes.filter(
        (attr: any) => attr.field === "hp"
      )[0]?.display,
      addi: inGameCharData?.additions.filter(
        (attr: any) => attr.field === "hp"
      )[0]?.display,
      value: (
        (inGameCharData?.attributes.filter(
          (attr: any) => attr.field === "def"
        )[0]?.value || 0) +
        (inGameCharData?.additions.filter(
          (attr: any) => attr.field === "def"
        )[0]?.value || 0)
      ).toFixed(),
    },
    {
      key: "spd",
      icon: AttributeImage.spd,
      attr: inGameCharData?.attributes.filter(
        (attr: any) => attr.field === "spd"
      )[0]?.display,
      addi: inGameCharData?.additions.filter(
        (attr: any) => attr.field === "spd"
      )[0]?.display,
      value: (
        (inGameCharData?.attributes.filter(
          (attr: any) => attr.field === "spd"
        )[0]?.value || 0) +
        (inGameCharData?.additions.filter(
          (attr: any) => attr.field === "spd"
        )[0]?.value || 0)
      ).toFixed(),
    },
    // {
    //   key: "energy",
    //   icon: AttributeImage.energy,
    //   value: charFullData?.spRequirement,
    // },
    {
      key: "crit_rate",
      icon: AttributeImage.crit_rate,
      attr: inGameCharData?.attributes.filter(
        (attr: any) => attr.field === "crit_rate"
      )[0]?.display,
      addi: inGameCharData?.additions.filter(
        (attr: any) => attr.field === "crit_rate"
      )[0]?.display,
      value:
        (
          ((inGameCharData?.attributes.filter(
            (attr: any) => attr.field === "crit_rate"
          )[0]?.value || 0) +
            (inGameCharData?.additions.filter(
              (attr: any) => attr.field === "crit_rate"
            )[0]?.value || 0)) *
          100
        ).toFixed(1) + "%",
    },
    {
      key: "crit_dmg",
      icon: AttributeImage.crit_dmg,
      attr: inGameCharData?.attributes.filter(
        (attr: any) => attr.field === "crit_dmg"
      )[0]?.display,
      addi: inGameCharData?.additions.filter(
        (attr: any) => attr.field === "crit_dmg"
      )[0]?.display,
      value:
        (
          ((inGameCharData?.attributes.filter(
            (attr: any) => attr.field === "crit_dmg"
          )[0]?.value || 0) +
            (inGameCharData?.additions.filter(
              (attr: any) => attr.field === "crit_dmg"
            )[0]?.value || 0)) *
          100
        ).toFixed(1) + "%",
    },
    {
      key: "break_dmg",
      icon: AttributeImage.break_dmg,
      attr: inGameCharData?.attributes.filter(
        (attr: any) => attr.field === "break_dmg"
      )[0]?.display,
      addi: inGameCharData?.additions.filter(
        (attr: any) => attr.field === "break_dmg"
      )[0]?.display,
      value: inGameCharData?.properties.filter(
        (attr: any) => attr.field === "break_dmg"
      )[0]?.display,
    },
    {
      key: "effect_hit",
      icon: AttributeImage.effect_hit,
      attr: inGameCharData?.attributes.filter(
        (attr: any) => attr.field === "effect_hit"
      )[0]?.display,
      addi: inGameCharData?.additions.filter(
        (attr: any) => attr.field === "effect_hit"
      )[0]?.display,
      value: inGameCharData?.properties.filter(
        (attr: any) => attr.field === "effect_hit"
      )[0]?.display,
    },
    {
      key: "effect_res",
      icon: AttributeImage.effect_res,
      attr: inGameCharData?.attributes.filter(
        (attr: any) => attr.field === "effect_res"
      )[0]?.display,
      addi: inGameCharData?.additions.filter(
        (attr: any) => attr.field === "effect_res"
      )[0]?.display,
      value: inGameCharData?.properties.filter(
        (attr: any) => attr.field === "effect_res"
      )[0]?.display,
    },
    {
      key: "heal_rate",
      icon: AttributeImage.heal_rate,
      attr: inGameCharData?.attributes.filter(
        (attr: any) => attr.field === "heal_rate"
      )[0]?.display,
      addi: inGameCharData?.additions.filter(
        (attr: any) => attr.field === "heal_rate"
      )[0]?.display,
      value: inGameCharData?.properties.filter(
        (attr: any) => attr.field === "heal_rate"
      )[0]?.display,
    },

    {
      key: "lightning_dmg",
      icon: AttributeImage.lightning_dmg,
      attr: inGameCharData?.attributes.filter(
        (attr: any) => attr.field === "lightning_dmg"
      )[0]?.display,
      addi: inGameCharData?.additions.filter(
        (attr: any) => attr.field === "lightning_dmg"
      )[0]?.display,
      value: inGameCharData?.properties.filter(
        (attr: any) => attr.field === "lightning_dmg"
      )[0]?.display,
    },
    {
      key: "quantum_dmg",
      icon: AttributeImage.quantum_dmg,
      attr: inGameCharData?.attributes.filter(
        (attr: any) => attr.field === "quantum_dmg"
      )[0]?.display,
      addi: inGameCharData?.additions.filter(
        (attr: any) => attr.field === "quantum_dmg"
      )[0]?.display,
      value: inGameCharData?.properties.filter(
        (attr: any) => attr.field === "quantum_dmg"
      )[0]?.display,
    },
    {
      key: "ice_dmg",
      icon: AttributeImage.ice_dmg,
      attr: inGameCharData?.attributes.filter(
        (attr: any) => attr.field === "ice_dmg"
      )[0]?.display,
      addi: inGameCharData?.additions.filter(
        (attr: any) => attr.field === "ice_dmg"
      )[0]?.display,
      value: inGameCharData?.properties.filter(
        (attr: any) => attr.field === "ice_dmg"
      )[0]?.display,
    },
    {
      key: "fire_dmg",
      icon: AttributeImage.fire_dmg,
      attr: inGameCharData?.attributes.filter(
        (attr: any) => attr.field === "fire_dmg"
      )[0]?.display,
      addi: inGameCharData?.additions.filter(
        (attr: any) => attr.field === "fire_dmg"
      )[0]?.display,
      value: inGameCharData?.properties.filter(
        (attr: any) => attr.field === "fire_dmg"
      )[0]?.display,
    },
    {
      key: "imaginary_dmg",
      icon: AttributeImage.imaginary_dmg,
      attr: inGameCharData?.attributes.filter(
        (attr: any) => attr.field === "imaginary_dmg"
      )[0]?.display,
      addi: inGameCharData?.additions.filter(
        (attr: any) => attr.field === "imaginary_dmg"
      )[0]?.display,
      value: inGameCharData?.properties.filter(
        (attr: any) => attr.field === "imaginary_dmg"
      )[0]?.display,
    },
    {
      key: "physical_dmg",
      icon: AttributeImage.physical_dmg,
      attr: inGameCharData?.attributes.filter(
        (attr: any) => attr.field === "physical_dmg"
      )[0]?.display,
      addi: inGameCharData?.additions.filter(
        (attr: any) => attr.field === "physical_dmg"
      )[0]?.display,
      value: inGameCharData?.properties.filter(
        (attr: any) => attr.field === "physical_dmg"
      )[0]?.display,
    },
    {
      key: "wind_dmg",
      icon: AttributeImage.wind_dmg,
      attr: inGameCharData?.attributes.filter(
        (attr: any) => attr.field === "wind_dmg"
      )[0]?.display,
      addi: inGameCharData?.additions.filter(
        (attr: any) => attr.field === "wind_dmg"
      )[0]?.display,
      value: inGameCharData?.properties.filter(
        (attr: any) => attr.field === "wind_dmg"
      )[0]?.display,
    },
  ];

  return (
    <TouchableOpacity
      onPress={() => {
        setDisplayMode(displayMode === "light" ? "normal" : "light");
      }}
      activeOpacity={0.35}
    >
      {displayMode === "light" ? (
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
                <View
                  style={{
                    flexDirection: "row",
                    alignItems: "center",
                    gap: 6,
                  }}
                >
                  <Image source={attr.icon} className="w-6 h-6" />
                  <Text className="text-text text-[14px] font-[HY65]">
                    {attr.value}
                  </Text>
                </View>
              )
          )}
        </View>
      ) : (
        <View
          className="px-3"
          style={{
            gap: 10,
          }}
        >
          {attributes.map(
            (attr) =>
              attr.value && (
                <View
                  className="w-[320px]"
                  style={{
                    flexDirection: "row",
                    alignItems: "center",
                    justifyContent: "space-between",
                  }}
                >
                  <View
                    style={{
                      flexDirection: "row",
                      alignItems: "center",
                      gap: 6,
                    }}
                  >
                    <Image source={attr.icon} className="w-6 h-6" />
                    <Text className="text-text text-[14px] font-[HY65]">
                      {/* @ts-ignore */}
                      {LOCALES[language]["RelicAffix_" + attr.key]}
                    </Text>
                  </View>
                  <View
                    style={{
                      flexDirection: "row",
                      alignItems: "center",
                      gap: 6,
                    }}
                  >
                    <Text className="text-text text-[14px] font-[HY65]">
                      {attr.attr}
                      {attr.addi && attr.attr ? <Text> + </Text> : null}
                      <Text className="text-green-600">{attr.addi}</Text>
                    </Text>
                  </View>
                </View>
              )
          )}
        </View>
      )}
    </TouchableOpacity>
  );
}
