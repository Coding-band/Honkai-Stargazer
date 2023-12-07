import { View, Text } from "react-native";
import React from "react";
import { CombatType } from "../../../types/combatType";
import { Image } from "expo-image";
import * as ImagesMap from "../../../../assets/images/@images_map/images_map";

export default function CombatTypeCardIcon({ value }: { value: CombatType }) {
  return (
    <View
      className="top-1 w-4 h-4 rounded-full bg-[#00000040]"
      style={{ justifyContent: "center", alignItems: "center" }}
    >
      <Image className="w-3.5 h-3" source={ImagesMap.CombatType[value].icon} />
    </View>
  );
}
