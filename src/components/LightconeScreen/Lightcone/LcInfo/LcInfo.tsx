import { View, Text, Dimensions } from "react-native";
import React, { useContext } from "react";
import LcStars from "../../../global/PageStars/PageStars";
import { Image } from "expo-image";
import Path from "../../../../../assets/images/@images_map/path";
import CombatType from "../../../../../assets/images/@images_map/combatType";
import LightconeContext from "../../../../context/LightconeData/LightconeContext";
import useLcData from "../../../../context/LightconeData/useLcData";

export default function LcInfo() {
  const { lcData } = useLcData();

  return (
    <View
      style={{
        paddingTop: Dimensions.get("window").height - 244,
        gap: 8,
      }}
    >
      <View style={{ gap: 12 }}>
        <Text className="text-[32px] font-[HY65] text-white">
          {lcData?.name}
        </Text>
        <LcStars count={lcData?.rare || 5} />
      </View>
      <View
        style={{
          flexDirection: "row",
          justifyContent: "space-between",
          alignItems: "center",
        }}
      >
        <View style={{ flexDirection: "row", gap: 26 }}>
          <View style={{ flexDirection: "row", gap: 8 }}>
            <Image
              // @ts-ignore
              source={Path[lcData?.pathId].icon}
              style={{ width: 24, height: 24 }}
            />
            <Text className="text-[16px] text-white font-[HY65]">
              {lcData?.path}
            </Text>
          </View>
        </View>
        <View>
          {/* <Text className="text-[16px] text-white font-[HY65]">
            {charData?.location}
          </Text> */}
        </View>
      </View>
    </View>
  );
}
