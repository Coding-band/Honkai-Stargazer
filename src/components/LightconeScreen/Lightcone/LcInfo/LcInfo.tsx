import { View, Text, Dimensions } from "react-native";
import React, { useContext } from "react";
import LcStars from "../../../global/PageStars/PageStars";
import { Image } from "expo-image";
import Path from "../../../../../assets/images/@images_map/path";
import CombatType from "../../../../../assets/images/@images_map/combatType";
import LightconeContext from "../../../../context/LightconeContext";

export default function LcInfo() {
  const lcData = useContext(LightconeContext);

  return (
    <View
      style={{
        paddingTop: Dimensions.get("window").height - 255,
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
              source={getPathIcon(lcData?.path)}
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

function getPathIcon(path: string | undefined) {
  let charPathIcon;
  switch (path) {
    case Path.Abundance.localeName:
      charPathIcon = Path.Abundance.icon;
      break;
    case Path.Destruction.localeName:
      charPathIcon = Path.Destruction.icon;
      break;
    case Path.Erudition.localeName:
      charPathIcon = Path.Erudition.icon;
      break;
    case Path.Harmony.localeName:
      charPathIcon = Path.Harmony.icon;
      break;
    case Path.Hunt.localeName:
      charPathIcon = Path.Hunt.icon;
      break;
    case Path.Nihility.localeName:
      charPathIcon = Path.Nihility.icon;
      break;
    case Path.Preservation.localeName:
      charPathIcon = Path.Preservation.icon;
      break;
  }
  return charPathIcon;
}
function getCombatIcon(combatType: string | undefined) {
  let charCombatType;
  switch (combatType) {
    case CombatType.Fire.localeName:
      charCombatType = CombatType.Fire.icon;
      break;
    case CombatType.Ice.localeName:
      charCombatType = CombatType.Ice.icon;
      break;
    case CombatType.Imaginary.localeName:
      charCombatType = CombatType.Imaginary.icon;
      break;
    case CombatType.Lightning.localeName:
      charCombatType = CombatType.Lightning.icon;
      break;
    case CombatType.Quantum.localeName:
      charCombatType = CombatType.Quantum.icon;
      break;
    case CombatType.Physical.localeName:
      charCombatType = CombatType.Physical.icon;
      break;
    case CombatType.Wind.localeName:
      charCombatType = CombatType.Wind.icon;
      break;
  }
  return charCombatType;
}
