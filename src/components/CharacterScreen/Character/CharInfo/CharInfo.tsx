import { View, Text, Dimensions } from "react-native";
import React, { useContext } from "react";
import CharacterContext from "../../../../context/CharacterContext";
import CharStars from "./CharStars/CharStars";
import { Image } from "expo-image";

const testPath = require("../../../../../assets/images/test-path.png");
const testCompatType = require("../../../../../assets/images/test-combat-type.png");

export default function CharInfo() {
  const charData = useContext(CharacterContext);

  return (
    <View
      style={{
        paddingTop: Dimensions.get("window").height - 220,
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
            <Image source={testPath} style={{ width: 24, height: 24 }} />
            <Text className="text-[16px] text-white font-[HY65]">
              {charData?.path}
            </Text>
          </View>
          <View style={{ flexDirection: "row", gap: 5, alignItems: "center" }}>
            <Image source={testCompatType} style={{ width: 24, height: 24 }} />
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
}
