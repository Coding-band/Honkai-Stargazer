import { View, TouchableOpacity } from "react-native";
import React from "react";
import { Image } from "expo-image";
import useHsrInGameInfo from "../../../../hooks/mihomo/useHsrInGameInfo";
import useHsrUUID from "../../../../hooks/hoyolab/useHsrUUID";
import officalCharId from "../../../../../map/character_offical_id_map";
import CharacterImage from "../../../../../assets/images/images_map/chacracterImage";
import { CharacterName } from "../../../../types/character";
import { useNavigation } from "@react-navigation/native";

export default function PlayerCharacter() {
  const navigation = useNavigation();

  const hsrUUID = useHsrUUID();
  const { data: inGameInfo } = useHsrInGameInfo(hsrUUID);
  return (
    <View className="flex flex-row gap-1">
      {inGameInfo?.characters ? inGameInfo?.characters?.map((char: any, i: number) => (
        <TouchableOpacity
          key={char.id}
          activeOpacity={0.35}
          className="w-[30px] h-[30px] bg-[#D9D9D9] rounded-full overflow-hidden"
          style={{ justifyContent: "center", alignItems: "center" }}
          onPress={() => {
            // @ts-ignore
            navigation.navigate("UserCharDetail", {
              uuid: hsrUUID,
              charId: officalCharId[char.id] as CharacterName,
            });
          }}
        >
          <Image
            className="w-[30px] h-[30px]"
            source={
              CharacterImage[officalCharId[char.id] as CharacterName].icon
            }
          />
        </TouchableOpacity>
      )) : <View className="w-[30px] h-[30px] bg-[#D9D9D9] rounded-full overflow-hidden"></View>}
    </View>
  );
}
