import { View, Text, Dimensions } from "react-native";
import React, { useContext } from "react";
import CharacterContext from "../../../../context/CharacterContext";
import CharStars from "./CharStars/CharStars";
import { Image } from "expo-image";
import Path from "../../../../../assets/images/images_map/path"
import CombatType from "../../../../../assets/images/images_map/combatType"

export default function CharInfo() {
  const charData = useContext(CharacterContext);
  
  let charPathIcon = require("../../../../../assets/images/test-path.png");
  let charCompatType = require("../../../../../assets/images/test-combat-type.png");
  switch(charData?.path){
    case Path.Abundance.localeName : charPathIcon = Path.Abundance.icon; break;
    case Path.Destruction.localeName : charPathIcon = Path.Destruction.icon; break;
    case Path.Erudition.localeName : charPathIcon = Path.Erudition.icon; break;
    case Path.Harmony.localeName : charPathIcon = Path.Harmony.icon; break;
    case Path.Hunt.localeName : charPathIcon = Path.Hunt.icon; break;
    case Path.Nihility.localeName : charPathIcon = Path.Nihility.icon; break;
    case Path.Preservation.localeName : charPathIcon = Path.Preservation.icon; break;
  }
  switch(charData?.combatType){
    case CombatType.Fire.localeName : charCompatType = CombatType.Fire.icon; break;
    case CombatType.Ice.localeName : charCompatType = CombatType.Ice.icon; break;
    case CombatType.Imaginary.localeName : charCompatType = CombatType.Imaginary.icon; break;
    case CombatType.Lightning.localeName : charCompatType = CombatType.Lightning.icon; break;
    case CombatType.Quantum.localeName : charCompatType = CombatType.Quantum.icon; break;
    case CombatType.Physical.localeName : charCompatType = CombatType.Physical.icon; break;
    case CombatType.Wind.localeName : charCompatType = CombatType.Wind.icon; break;
  }

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
            <Image source={charPathIcon} style={{ width: 24, height: 24 }} />
            <Text className="text-[16px] text-white font-[HY65]">
              {charData?.path}
            </Text>
          </View>
          <View style={{ flexDirection: "row", gap: 5, alignItems: "center" }}>
            <Image source={charCompatType} style={{ width: 24, height: 24 }} />
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
