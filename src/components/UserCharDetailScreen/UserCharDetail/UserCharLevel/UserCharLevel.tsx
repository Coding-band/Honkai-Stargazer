import { View, Text } from "react-native";
import React from "react";
import useProfileHsrInGameInfo from "../../../../context/UserCharDetailData/hooks/useProfileHsrInGameInfo";

export default function UserCharLevel() {
  const { inGameCharData } = useProfileHsrInGameInfo();

  return (
    <View
      className="mt-2 bg-[#00000050] rounded-[49px] px-[12px] py-[4px]"
      style={{ alignItems: "center" }}
    >
      <Text className="text-[12px] text-[#FFFFFF] font-[HY65]">
        Lv {inGameCharData?.level} · {inGameCharData?.rank}星魂
      </Text>
    </View>
  );
}
