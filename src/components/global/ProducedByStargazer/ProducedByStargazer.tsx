import { View, Text } from "react-native";
import React from "react";
import useAppLanguage from "../../../language/AppLanguage/useAppLanguage";
import { LOCALES } from "../../../../locales";

export default function ProducedByStargazer() {
  const { language } = useAppLanguage()
  return (
    // 由 Stargazer 製作
    <Text className="text-text text-[12px] font-[HY65] leading-5">{LOCALES[language].ProducedByStargazer}</Text>
  );
}
