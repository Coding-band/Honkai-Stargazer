import { View, Text } from "react-native";
import React from "react";
import { Image } from "expo-image";
import Path from "../../../../../../assets/images/images_map/path";
import { LightconeName } from "../../../../../types/lightcone";
import {
  getLcFullData,
  getLcJsonData,
} from "../../../../../utils/dataMap/getDataFromMap";
import useTextLanguage from "../../../../../language/TextLanguage/useTextLanguage";
import { LOCALES } from "../../../../../../locales";
import useAppLanguage from "../../../../../language/AppLanguage/useAppLanguage";
import { Path as PathType } from "../../../../../types/path";

export default function LightconeLevel({ lcId }: { lcId: LightconeName }) {
    
  const { language: appLanguage } = useAppLanguage();
  const lcJsonData = getLcJsonData(lcId);

  return (
    <View className="mt-2" style={{ flexDirection: "row", gap: 4 }}>
      <Image
        // @ts-ignore
        source={Path[lcJsonData.path].icon}
        style={{ width: 24, height: 24 }}
      />
      <Text className="text-text text-[16px] font-[HY65]">
        {LOCALES[appLanguage][lcJsonData.path as PathType]}
      </Text>
    </View>
  );
}
