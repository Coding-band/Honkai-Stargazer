import { View, Text } from "react-native";
import React from "react";
import { Image } from "expo-image";
import CharacterSkillMain from "../../../../../assets/images/images_map/characterSkillMain";
import useProfileCharId from "../../../../context/UserCharDetailData/hooks/useProfileCharId";
import useProfileHsrInGameInfo from "../../../../context/UserCharDetailData/hooks/useProfileHsrInGameInfo";

export default function UserCharSkills() {
  const charId = useProfileCharId();
  const { inGameCharData } = useProfileHsrInGameInfo();

  return (
    <View className="mt-2" style={{ flexDirection: "row", gap: 30 }}>
      <View style={{ gap: 2, alignItems: "center" }}>
        <Image className="w-9 h-9" source={CharacterSkillMain[charId].skill1} />
        <Text className="text-text">普攻</Text>
        <Text className="text-text">Lv {inGameCharData.skills[0].level}</Text>
      </View>
      <View style={{ gap: 2, alignItems: "center" }}>
        <Image className="w-9 h-9" source={CharacterSkillMain[charId].skill2} />
        <Text className="text-text">战技</Text>
        <Text className="text-text">Lv {inGameCharData.skills[1].level}</Text>
      </View>
      <View style={{ gap: 2, alignItems: "center" }}>
        <Image className="w-9 h-9" source={CharacterSkillMain[charId].skill3} />
        <Text className="text-text">终结技</Text>
        <Text className="text-text">Lv {inGameCharData.skills[2].level}</Text>
      </View>
      <View style={{ gap: 2, alignItems: "center" }}>
        <Image className="w-9 h-9" source={CharacterSkillMain[charId].skill4} />
        <Text className="text-text">天赋</Text>
        <Text className="text-text">Lv {inGameCharData.skills[3].level}</Text>
      </View>
      <View style={{ gap: 2, alignItems: "center" }}>
        <Image className="w-9 h-9" source={CharacterSkillMain[charId].skill6} />
        <Text className="text-text">秘技</Text>
        <Text className="text-text">Lv {inGameCharData.skills[5].level}</Text>
      </View>
    </View>
  );
}
