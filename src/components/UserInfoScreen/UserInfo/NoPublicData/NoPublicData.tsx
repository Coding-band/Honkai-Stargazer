import { View, Text } from "react-native";
import React from "react";
import { Image } from "expo-image";
import useAppLanguage from "../../../../language/AppLanguage/useAppLanguage";
import { LOCALES } from "../../../../../locales";

export default function NoPublicData() {
  const { language } = useAppLanguage()
  return (
    <View
      className="h-[250px]"
      style={{ justifyContent: "center", alignItems: "center" }}
    >
      <View style={{ gap: 21, alignItems: "center" }}>
        <Image
          className="w-[144px] h-[144px]"
          source={require("./images/04.png")}
        />
        <Text className="text-text font-[HY65] text-[16px] leading-5">
          {LOCALES[language].AppStatusPlayerNoReleaseData}
        </Text>
      </View>
    </View>
  );
}
