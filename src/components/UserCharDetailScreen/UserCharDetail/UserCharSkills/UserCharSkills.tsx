import { View, Text } from "react-native";
import React from "react";
import { Image } from "expo-image";
import CharacterSkillMain from "../../../../../assets/images/images_map/characterSkillMain";
import useProfileCharId from "../../../../context/UserCharDetailData/hooks/useProfileCharId";
import useProfileHsrInGameInfo from "../../../../context/UserCharDetailData/hooks/useProfileHsrInGameInfo";
import useAppLanguage from "../../../../language/AppLanguage/useAppLanguage";
import { LOCALES } from "../../../../../locales";

export default function UserCharSkills() {
  const charId = useProfileCharId();
  const { inGameCharData } = useProfileHsrInGameInfo();
  const { language } = useAppLanguage();

  return (
    inGameCharData && (
      <View className="mt-2" style={{ flexDirection: "row" }}>
        <View className="w-[72px] items-center">
          <Image
            className="w-9 h-9 mb-1"
            source={CharacterSkillMain[charId].skill1}
          />
          <Text className="text-text font-[HY65] text-center">
            {LOCALES[language].TraceNormalATK}
          </Text>
          <Text className="text-text font-[HY65]">
            Lv {inGameCharData.skills[0].level}
          </Text>
        </View>
        <View className="w-[72px] items-center">
          <Image
            className="w-9 h-9 mb-1"
            source={CharacterSkillMain[charId].skill2}
          />
          <Text className="text-text font-[HY65]">
            {LOCALES[language].TraceSkill}
          </Text>
          <Text className="text-text font-[HY65]">
            Lv {inGameCharData.skills[1].level}
          </Text>
        </View>
        <View className="w-[72px] items-center">
          <Image
            className="w-9 h-9 mb-1"
            source={CharacterSkillMain[charId].skill3}
          />
          <Text className="text-text font-[HY65]">
            {LOCALES[language].TraceUltimate}
          </Text>
          <Text className="text-text font-[HY65]">
            Lv {inGameCharData.skills[2].level}
          </Text>
        </View>
        <View className="w-[72px] items-center">
          <Image
            className="w-9 h-9 mb-1"
            source={CharacterSkillMain[charId].skill4}
          />
          <Text className="text-text font-[HY65]">
            {LOCALES[language].TraceTalent}
          </Text>
          <Text className="text-text font-[HY65]">
            Lv {inGameCharData.skills[3].level}
          </Text>
        </View>
        <View className="w-[72px] items-center">
          <Image
            className="w-9 h-9 mb-1"
            source={CharacterSkillMain[charId].skill6}
          />
          <Text className="text-text font-[HY65]">
            {LOCALES[language].TraceTechnique}
          </Text>
          <Text className="text-text font-[HY65]">
            Lv {inGameCharData.skills[5].level}
          </Text>
        </View>
      </View>
    )
  );
}
