import { View, Text } from "react-native";
import React from "react";
import CharCard from "../../../global/CharCard/CharCard";
import useHsrInGameInfo from "../../../../hooks/mihomo/useHsrInGameInfo";
import useHsrUUID from "../../../../hooks/hoyolab/useHsrUUID";
import officalCharId from "../../../../../map/character_offical_id_map";
import {
  getCharFullData,
  getCharJsonData,
} from "../../../../utils/dataMap/getDataFromMap";
import useTextLanguage from "../../../../language/TextLanguage/useTextLanguage";
import CharacterImage from "../../../../../assets/images/images_map/chacracterImage";
import { CharacterName } from "../../../../types/character";
import { Image } from "expo-image";
import useHsrCharList from "../../../../hooks/hoyolab/useHsrCharList";

export default function UserInfoCharacters() {
  const { language: textLanguage } = useTextLanguage();

  const hsrUUID = useHsrUUID();
  const hsrCharList = useHsrCharList();
  const { data: hsrInGameInfo } = useHsrInGameInfo(hsrUUID);

  const inGameCharacters = hsrInGameInfo?.characters?.map((char) => {
    // @ts-ignore
    const charId = officalCharId[char.id] as CharacterName;
    const charJsonData = getCharJsonData(charId);
    const charFullData = getCharFullData(charId, textLanguage);
    return {
      id: charId,
      rare: charJsonData.rare,
      name: charFullData.name,
      image: CharacterImage[charId].icon,
    };
  });

  return (
    <View style={{ alignItems: "center", gap: 10 }}>
      <Text className="text-text text-[16px] font-[HY65]">
        擁有角色 {hsrInGameInfo?.player?.space_info?.avatar_count}
      </Text>
      <View style={{ flexDirection: "row", gap: 12, flexWrap: "wrap" }}>
        {inGameCharacters?.map((char) => (
          <CharCard key={char.id} {...char} />
        ))}
      </View>
      <Image
        source={require("./icons/Divider.svg")}
        className="w-[355px] h-[13px]"
      />
      <View style={{ flexDirection: "row", gap: 12, flexWrap: "wrap" }}>
        <CharCard />
        <CharCard />
        <CharCard />
        <CharCard />
        <CharCard />
        <CharCard />
        <CharCard />
        <CharCard />
      </View>
    </View>
  );
}
