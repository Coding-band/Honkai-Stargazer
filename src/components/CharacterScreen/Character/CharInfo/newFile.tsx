import { View, Text, Dimensions } from "react-native";
import React from "react";
import CharStars from "../../../global/PageStars/PageStars";
import { Image } from "expo-image";
import Path from "../../../../../assets/images/@images_map/path";
import CombatType from "../../../../../assets/images/@images_map/combatType";
import useCharData from "../../../../context/CharacterData/useCharData";

export default React.memo(function CharInfo() {
  const { charData } = useCharData();

  return (
    <View
      style={{
        paddingTop: Dimensions.get("window").height - 234,
        gap: 8,
      }}
    >
      <View style={{ gap: 12 }}>
        <Text className="text-[32px] font-[HY65] text-white">
          {charData?.name}
        </Text>
        <CharStars count={charData?.rare || 5} />
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
              source={Path[charData?.pathId!].icon}
              style={{ width: 24, height: 24 }}
            />
            <Text className="text-[16px] text-white font-[HY65]">
              {charData?.path}
            </Text>
          </View>
          <View style={{ flexDirection: "row", gap: 5, alignItems: "center" }}>
            <Image
              source={CombatType[charData?.pathId!].icon}
              style={{ width: 24, height: 24 }}
            />
            <Text className="text-[16px] text-white font-[HY65]">
              {charData?.combatType}
            </Text>
          </View>
        </View>
        <View>
          <Text className="text-[16px] text-white font-[HY65]">
            {charData?.location}
          </Text>
        </View>
      </View>
    </View>
  );
});
