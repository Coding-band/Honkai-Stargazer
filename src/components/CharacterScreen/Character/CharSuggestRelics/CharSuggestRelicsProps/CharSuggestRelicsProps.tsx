import { View, Text } from "react-native";
import React from "react";
import useCharData from "../../../../../context/CharacterData/hooks/useCharData";
import useAppLanguage from "../../../../../language/AppLanguage/useAppLanguage";
import { LOCALES } from "../../../../../../locales";
import { AppLanguage } from "../../../../../language/language";

export default function CharSuggestRelicsProps() {
  const { language: appLanguage } = useAppLanguage();
  const { charFullData } = useCharData();

  const mainRelicPropNames = [
    LOCALES[appLanguage].RelicPropBodyShort,
    LOCALES[appLanguage].RelicPropFeetShort,
    LOCALES[appLanguage].RelicPropPlanarSphereShort,
    LOCALES[appLanguage].RelicPropLinkRopeShort,
  ];

  return (
    <View className="w-full mt-4">
      <View className="w-full">
        <Text className="font-[HY75] text-white text-[16px]">
          {LOCALES[appLanguage].MainAffix}
        </Text>
        <View
          className="w-full pt-3 pb-4"
          style={{
            flexDirection: "row",
            flexWrap: "wrap",
            rowGap: 9,
            columnGap: 12,
          }}
        >
          {charFullData.relicRecommend.props.map((t, i) => (
            <View
              key={i}
              className="w-[48%]"
              style={{
                flexDirection: "row",
                justifyContent: "space-between",
                alignItems: "center",
              }}
            >
              <Text className="text-[13px] text-white font-[HY75]">
                {mainRelicPropNames[i]}
              </Text>
              <Text
                numberOfLines={1}
                className="text-[13px] text-[#DDD] opacity-80 font-[HY65] w-24 text-right"
              >
                {t.propertyName}
              </Text>
            </View>
          ))}
        </View>
      </View>
      <View
        className="w-full"
        style={{
          flexDirection: "row",
          justifyContent: "space-between",
          alignItems: "center",
        }}
      >
        <Text className="font-[HY75] text-white text-[16px]">
          {LOCALES[appLanguage].SubAffix}
        </Text>
        <Text className="text-[13px] text-[#DDD] opacity-80 font-[HY65]">
          {LOCALES[appLanguage].NoDataYet}
        </Text>
      </View>
    </View>
  );
}
