import { View } from "react-native";
import React, { useEffect, useState } from "react";
import { StatusBar } from "expo-status-bar";
import { ImageBackground } from "expo-image";
import { LinearGradient } from "expo-linear-gradient";
import Header from "../components/global/layout/Header";
import { SCREENS } from "../constant/screens";
import { RouteProp, useRoute } from "@react-navigation/native";
import { ParamList } from "../types/navigation";
import CharacterMain from "../components/CharacterScreen/Character/Character";
import { filter } from "lodash";
import { CharacterName, Character } from "../types/character";
import CharacterContext from "../context/CharacterContext";

import charList from "../../data/character_data/character_list.json";
import * as charListMap from "../../data/character_data/character_list_map/character_list_map";
import * as imagesMap from "../../assets/images/images_map/images_map";

export default function CharacterScreen() {
  const route = useRoute<RouteProp<ParamList, "Character">>();
  const charId = route.params.id as CharacterName;
  const charName = route.params.name;

  const [charData, setCharData] = useState<Character>({});
  const [showMain, setShowMain] = useState(false);

  useEffect(() => {
    const charDataJson = filter(charList, (char) => char?.name === charId)[0];
    setCharData({
      id: charId,
      name: charListMap.ZH_CN[charId]?.name,
      rare: charDataJson?.rare,
      path: charListMap.ZH_CN[charId]?.baseType?.name,
      combatType: charListMap.ZH_CN[charId]?.damageType?.name,
      location: charListMap.ZH_CN[charId]?.archive?.camp,
      // @ts-ignore
      imageFull: imagesMap.Chacracter[charId]?.imageFull,
      storyText: charListMap.ZH_CN[charId]?.storyItems[0].text,
    });
    setShowMain(true);
  }, []);

  return (
    <CharacterContext.Provider value={charData}>
      <View style={{ flex: 1, backgroundColor: "white" }}>
        <StatusBar style="dark" />
        <ImageBackground
          className="absolute w-full h-full"
          // 把背景關掉
          source={require("../../assets/images/test-bg.png")}
          // placeholder={blurhash}
          contentFit="cover"
          blurRadius={10}
        />
        <LinearGradient
          className="absolute w-full h-full"
          colors={["#00000080", "#00000020"]}
        />

        <Header leftBtn="back" Icon={SCREENS.CharacterPage.icon}>
          {charName}
        </Header>
        {showMain && <CharacterMain />}
        <LinearGradient
          className="w-full h-[600px] absolute bottom-0"
          colors={["#00000000", "#000000"]}
        />
      </View>
    </CharacterContext.Provider>
  );
}
