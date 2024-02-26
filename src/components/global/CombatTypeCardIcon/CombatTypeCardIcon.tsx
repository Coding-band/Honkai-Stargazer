import { View, Text } from "react-native";
import React from "react";
import { CombatType } from "../../../types/combatType";
import { Image } from "expo-image";
import CombatTypeMap from "../../../../assets/images/images_map/combatType";

export default function CombatTypeCardIcon({ value }: { value?: CombatType }) {
  return (
    value && (
      <View
        className="top-1 w-5 h-5 rounded-full bg-[#00000040]"
        style={{ justifyContent: "center", alignItems: "center" }}
      >
        <Image cachePolicy="none" className="w-4 h-3.5" source={CombatTypeMap[value].icon} />
      </View>
    )
  );
}
