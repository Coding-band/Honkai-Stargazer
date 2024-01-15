import { View, Text } from "react-native";
import React from "react";
import useCharData from "../../../../../context/CharacterData/hooks/useCharData";
import useAppLanguage from "../../../../../language/AppLanguage/useAppLanguage";
import { LOCALES } from "../../../../../../locales";
import charAdviceRelicMap from "../../../../../../map/character_advice_relic_map";

export default function CharSuggestRelicsProps() {
  const { language: appLanguage } = useAppLanguage();
  const { charId } = useCharData();

  const mainRelicPropNames = [
    LOCALES[appLanguage].RelicPropBodyShort,
    LOCALES[appLanguage].RelicPropFeetShort,
    LOCALES[appLanguage].RelicPropPlanarSphereShort,
    LOCALES[appLanguage].RelicPropLinkRopeShort,
  ];

  const adviceRelics = charAdviceRelicMap?.[charId];


  return (
    <View className="w-full mt-4">
      <View className="w-full">
        <Text className="font-[HY75] text-white text-[16px]">
          {LOCALES[appLanguage].MainAffix}
        </Text>
        {/* 主詞條 */}
        <View
          className="w-full pt-3 pb-4 "
          style={{
            flexDirection: "row",
            flexWrap: "wrap",
            rowGap: 9,
            columnGap: 12,
          }}
        >
          {adviceRelics?.slice(0, 4)?.map((r: any, i: number) => (
            <View
              key={i}
              className="w-[48%]"
              style={{
                flexDirection: "row",
                justifyContent: "space-between",
                alignItems: "center",
              }}
            >
              <Text className="text-[13px] text-white font-[HY75] leading-4">
                {mainRelicPropNames[i]}
              </Text>
              <Text
                numberOfLines={1}
                className="text-[13px] text-[#DDD] opacity-80 font-[HY65] w-24 text-right leading-4"
              >
                {LOCALES[appLanguage][r.propertyName]}
              </Text>
            </View>
          ))}
        </View>
      </View>
      {/* 副詞條 */}
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
          {adviceRelics?.[4]?.map((sub: any, i: number) => (
            <Text>
              {LOCALES[appLanguage][sub]}
              {i !== adviceRelics?.[4]?.length - 1 && ", "}
            </Text>
          )) || LOCALES[appLanguage].NoDataYet}
        </Text>
      </View>
    </View>
  );
}
