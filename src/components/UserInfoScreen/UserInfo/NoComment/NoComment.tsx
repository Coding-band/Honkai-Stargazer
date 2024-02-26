import { View, Text } from "react-native";
import React from "react";
import { Image } from "expo-image";
import { LOCALES } from "../../../../../locales";
import useAppLanguage from "../../../../language/AppLanguage/useAppLanguage";

export default function NoComment() {

    const {language} = useAppLanguage();

  return (
    <View
      className="h-[250px]"
      style={{ justifyContent: "center", alignItems: "center" }}
    >
      <View style={{ gap: 21, alignItems: "center" }}>
        <Image cachePolicy="none"
          className="w-[144px] h-[144px]"
          source={require("./images/05.png")}
        />
        <Text className="text-text font-[HY65] text-[16px] leading-5">
          {LOCALES[language].NoCommentYet}
        </Text>
      </View>
    </View>
  );
}
