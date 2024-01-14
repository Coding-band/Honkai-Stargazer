import { View, Text } from "react-native";
import React from "react";
import useProfileHsrInGameInfo from "../../../../context/UserCharDetailData/hooks/useProfileHsrInGameInfo";
import useAppLanguage from "../../../../language/AppLanguage/useAppLanguage";
import { LOCALES } from "../../../../../locales";

export default function UserCharLevel() {
  const { inGameCharData } = useProfileHsrInGameInfo();
  const { language } = useAppLanguage();

  return (
    <View
      className="mt-2 bg-[#00000070] rounded-[49px] px-[12px] py-[4px]"
      style={{ alignItems: "center" }}
    >
      <Text className="text-[12px] text-[#FFFFFF] font-[HY65]">
        {LOCALES[language].UserCharLevelLv} {inGameCharData?.level} · {inGameCharData?.rank}{LOCALES[language].UserCharLevelSoul}
      </Text>
    </View>
  );
}
