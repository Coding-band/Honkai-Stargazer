import { View } from "react-native";
import React, { useEffect, useState } from "react";
import { StatusBar } from "expo-status-bar";
import { ImageBackground } from "expo-image";
import { LinearGradient } from "expo-linear-gradient";
import Header from "../components/global/Header/Header";
import { SCREENS } from "../constant/screens";
import { RouteProp, useNavigation, useRoute } from "@react-navigation/native";
import { ParamList } from "../types/navigation";
import CharacterMain from "../components/CharacterScreen/Character/Character";
import { filter } from "lodash";
import { CharacterName, Character } from "../types/character";
import CharacterContext from "../context/CharacterContext";
import charList from "../../data/character_data/character_list.json";
import * as imagesMap from "../../assets/images/@images_map/images_map";
import Fixed from "../components/global/Fixed/Fixed";
import { getCharFullData } from "../utils/dataMap/getDataFromMap";

export default function CharacterScreen() {
  const route = useRoute<RouteProp<ParamList, "Character">>();
  const charId = route.params.id as CharacterName;
  const charName = route.params.name;

  const [charData, setCharData] = useState<Character>({});
  const [showMain, setShowMain] = useState(false);

  useEffect(() => {
    const charDataJson = filter(charList, (char) => char?.name === charId)[0];
    const charFullData = getCharFullData(charId);
    setCharData({
      id: charId,
      name: charFullData?.name,
      rare: charDataJson?.rare,
      path: charFullData?.baseType?.name,
      combatType: charFullData?.damageType?.name,
      location: charFullData?.archive?.camp,
      imageFull: imagesMap.Chacracter[charId]?.imageFull,
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

        <Fixed />
      </View>
    </CharacterContext.Provider>
  );
}
