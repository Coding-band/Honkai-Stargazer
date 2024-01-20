import { View, Text } from "react-native";
import React from "react";
import { Image } from "expo-image";
import AttributeImage from "../../../../../../assets/images/images_map/attributeImage";
import { LOCALES } from "../../../../../../locales";
import useAppLanguage from "../../../../../language/AppLanguage/useAppLanguage";

type Props = {
  field: string;
  currScore: number;
  gradScore: number;
};

export default function UserCharScoreBar(props: Props) {
  const { language } = useAppLanguage();

  return (
    <View style={{ flexDirection: "row", alignItems: "center", gap: 8 }}>
      <Image source={AttributeImage?.[props.field]} className="w-6 h-6" />
      <View style={{ gap: 3 }}>
        <View
          style={{
            flexDirection: "row",
            justifyContent: "space-between",
          }}
        >
          <Text className="text-text font-[HY65]">
            {LOCALES[language]["ATTR_" + props.field.toUpperCase()]}
          </Text>
          <Text className="text-text font-[HY65]">
            {Math.floor(props.currScore * 10) / 10}/
            {Math.floor(props.gradScore * 10) / 10}
          </Text>
        </View>
        <View className="w-[280px] h-[3px]" style={{ flexDirection: "row" }}>
          <View
            style={{
              width: `${Math.min(
                (props.currScore / props.gradScore) * 100,
                100
              )}%`,
            }}
            className="bg-[#FFF]"
          ></View>
          <View
            style={{
              width: `${(1 - props.currScore / props.gradScore) * 100}%`,
            }}
            className="bg-[#FFFFFF80]"
          ></View>
        </View>
      </View>
    </View>
  );
}
