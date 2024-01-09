import { View, Text } from "react-native";
import React from "react";
import { Image } from "expo-image";
import Path from "../../../../../assets/images/images_map/path";
import CombatType from "../../../../../assets/images/images_map/combatType";
import { CharacterName } from "../../../../types/character";
import useProfileCharJsonData from "../../../../context/UserCharDetailData/hooks/useProfileCharJsonData";

export default function UserCharCombatTypeAndPath() {
  const charJsonData = useProfileCharJsonData();

  return (
    <View className="mt-2" style={{ flexDirection: "row", gap: 26 }}>
      <View style={{ flexDirection: "row", gap: 4 }}>
        <Image
          // @ts-ignore
          source={Path[charJsonData?.path].icon}
          style={{ width: 24, height: 24 }}
        />
        <Text className="text-text text-[16px] font-[HY65]">巡猎</Text>
      </View>
      <View style={{ flexDirection: "row", gap: 4 }}>
        <Image
          // @ts-ignore
          source={CombatType[charJsonData?.element].icon}
          style={{ width: 24, height: 24 }}
        />

        <Text className="text-text text-[16px] font-[HY65]">量子</Text>
      </View>
    </View>
  );
}
