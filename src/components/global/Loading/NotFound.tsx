import { View, Text } from "react-native";
import React from "react";
import { Image } from "expo-image";
import useAppLanguage from "../../../language/AppLanguage/useAppLanguage";
import { LOCALES } from "../../../../locales";

export default function NotFound() {
  const { language } = useAppLanguage();
  return (
    <View
      className="w-screen h-screen z-30"
      style={{ justifyContent: "center", alignItems: "center" }}
    >
      <View style={{ gap: 21, alignItems: "center" }}>
        <Image
          className="w-[144px] h-[144px]"
          source={require("./images/02.png")}
        />
        <Text className="text-text font-[HY65] text-[16px] leading-5">
          {LOCALES[language].AppStatusLostConnect}
        </Text>
      </View>
    </View>
  );
}
