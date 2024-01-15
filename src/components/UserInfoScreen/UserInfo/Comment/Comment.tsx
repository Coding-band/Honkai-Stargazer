import {
  View,
  Text,
  TouchableWithoutFeedback,
  TouchableOpacity,
  TouchableHighlight,
} from "react-native";
import React, { useState, useEffect } from "react";
import { Image } from "expo-image";
import useTextLanguage from "../../../../language/TextLanguage/useTextLanguage";
import { getCharFullData } from "../../../../utils/dataMap/getDataFromMap";
import officalCharId from "../../../../../map/character_offical_id_map";
import CharacterImage from "../../../../../assets/images/images_map/chacracterImage";
import { CharacterName } from "../../../../types/character";
import TagContent from "../../../global/TagContent/TagContent";
import { useNavigation } from "@react-navigation/native";
import { SCREENS } from "../../../../constant/screens";

type Props = {
  type: "character-comment";
  id: string;
  title: string;
  content: string;
};

export default function Comment(props: Props) {
  const { language: textLanguage } = useTextLanguage();
  const navigation = useNavigation();

  const charId = officalCharId[props.title] as CharacterName;

  return (
    <TouchableOpacity
      activeOpacity={0.6}
      onPress={() => {
        // @ts-ignore
        navigation.push(SCREENS.CharacterPage.id, {
          id: charId,
          commentId: props.id,
        });
      }}
    >
      <View
        className="w-screen px-6 py-3"
        style={{ flexDirection: "row", gap: 14 }}
      >
        <TouchableOpacity activeOpacity={1}>
          <Image
            className="w-9 h-9 rounded-full"
            source={CharacterImage[charId]?.icon}
          />
        </TouchableOpacity>
        <View style={{ gap: 2, flex: 1 }}>
          <View style={{ flexDirection: "row", gap: 10, alignItems: "center" }}>
            <TouchableWithoutFeedback onPress={() => { }}>
              <Text className="text-[#CCC] text-[12px] font-[HY65] leading-5">
                {`在「${getCharFullData(charId, textLanguage)?.name}」的評論區`}
              </Text>
            </TouchableWithoutFeedback>
          </View>
          <TagContent>{props.content}</TagContent>
        </View>
      </View>
    </TouchableOpacity>
  );
}
