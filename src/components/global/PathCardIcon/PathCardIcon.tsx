import { View } from "react-native";
import React from "react";
import { Image } from "expo-image";
import { Path } from "../../../types/path";
import PathMap from "../../../../assets/images/images_map/path";

export default function PathCardIcon({ value }: { value: Path }) {
  return (
    <View
      className="w-4 h-4 rounded-full bg-[#00000040]"
      style={{ justifyContent: "center", alignItems: "center" }}
    >
      <Image className="w-3.5 h-3.5" source={PathMap[value].icon} />
    </View>
  );
}
