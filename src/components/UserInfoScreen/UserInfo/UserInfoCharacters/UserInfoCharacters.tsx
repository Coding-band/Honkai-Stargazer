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
import { Link, useNavigation } from "@react-navigation/native";

type Props = {
  uuid: string;
};

export default function UserInfoCharacters(props: Props) {
  const { language: textLanguage } = useTextLanguage();
  const navigation = useNavigation();

  const hsrUUID = useHsrUUID();
  const isOwner = props.uuid === hsrUUID;

  const hsrCharList = useHsrCharList();
  const { data: hsrInGameInfo } = useHsrInGameInfo(props.uuid);

  const inGameCharacters = hsrInGameInfo?.characters?.map((char: any) => {
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
  const inGameCharactersIds: any[] = inGameCharacters?.map(
    (char: any) => char.id
  );

  const hsrCharacters = isOwner
    ? hsrCharList
        ?.filter(
          (char: any) => !inGameCharactersIds?.includes(officalCharId[char.id])
        )
        ?.map((char: any) => {
          // @ts-ignore
          const charId = officalCharId[char.id] as CharacterName;
          const charJsonData = getCharJsonData(charId);
          const charFullData = getCharFullData(charId, textLanguage);
          return {
            id: char.id,
            rare: charJsonData.rare,
            name: charFullData.name,
            image: CharacterImage[charId].icon,
          };
        })
    : [];

  return (
    <View style={{ alignItems: "center", gap: 10 }}>
      <Text className="text-text text-[16px] font-[HY65]">
        擁有角色 {hsrInGameInfo?.player?.space_info?.avatar_count}
      </Text>
      {/* 展示櫃 */}
      <View style={{ flexDirection: "row", gap: 12, flexWrap: "wrap" }}>
        {inGameCharacters?.map((char: any) => (
          <CharCard
            key={char.id}
            {...char}
            onPress={() => {
              // @ts-ignore
              navigation.navigate("UserCharDetail", {
                uuid: props.uuid,
                charId: char.id,
              });
            }}
          />
        ))}
      </View>
      <Image
        source={require("./icons/Divider.svg")}
        className="w-[355px] h-[13px]"
      />
      {/* hoyolab 數據 */}
      <View style={{ flexDirection: "row", gap: 12, flexWrap: "wrap" }}>
        {hsrCharacters?.slice(0, 8)?.map((char: any) => (
          <CharCard key={char.id} {...char} />
        ))}
      </View>
    </View>
  );
}
