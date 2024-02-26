import { View } from "react-native";
import React from "react";
import { Image } from "expo-image";
import { Path } from "../../../types/path";
import PathMap from "../../../../assets/images/images_map/path";

export default function PathCardIcon({ value }: { value?: Path }) {
  return (
    value && (
      <View
        className="w-5 h-5 rounded-full bg-[#00000040]"
        style={{ justifyContent: "center", alignItems: "center" }}
      >
        <Image cachePolicy="none" className="w-4 h-4" source={PathMap[value].icon} />
      </View>
    )
  );
}
