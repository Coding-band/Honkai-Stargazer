import { View, Text } from "react-native";
import React from "react";
import useProfileHsrInGameInfo from "../../../../context/UserCharDetailData/hooks/useProfileHsrInGameInfo";
import useAppLanguage from "../../../../language/AppLanguage/useAppLanguage";
import { LOCALES } from "../../../../../locales";
import useUserCharactersByUUID from "../../../../firebase/hooks/UserCharacters/useUserCharactersByUUID";
import useProfileUUID from "../../../../context/UserCharDetailData/hooks/useProfileUUID";
import officalCharId from "../../../../../map/character_offical_id_map";
import useProfileCharId from "../../../../context/UserCharDetailData/hooks/useProfileCharId";

export default function UserCharLevel() {
  const { language } = useAppLanguage();

  const charId = useProfileCharId();
  const profileUUID = useProfileUUID();

  const { inGameCharData } = useProfileHsrInGameInfo();
  const { data: hoyolabCharacters } = useUserCharactersByUUID(profileUUID);
  const hoyolabCharData = hoyolabCharacters?.characters?.filter(
    (char: any) => officalCharId[char.id] === charId
  )[0];

  return (
    <View
      className="mt-2 bg-[#00000070] rounded-[49px] px-[12px] py-[4px]"
      style={{ alignItems: "center" }}
    >
      <Text className="text-[12px] text-[#FFFFFF] font-[HY65]">
        {LOCALES[language].UserCharLevelLv}{" "}
        {inGameCharData?.level || hoyolabCharData?.level || 0} ·{" "}
        {LOCALES[language].CharSoul.replace(
          "${1}",
          inGameCharData?.rank || hoyolabCharData?.rank || 0
        )}
      </Text>
    </View>
  );
}
