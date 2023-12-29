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
import { CharacterName} from "../types/character";
import CharacterContext from "../context/CharacterData/CharacterContext";
import charList from "../../data/character_data/character_list.json";
import Fixed from "../components/global/Fixed/Fixed";
import { getCharFullData } from "../utils/dataMap/getDataFromMap";
import { Path } from "../types/path";
import { CombatType } from "../types/combatType";
import WallPaper from "../components/global/WallPaper/WallPaper";
import useTextLanguage from "../context/TextLanguage/useTextLanguage";
import CharacterImage from "../../assets/images/images_map/chacracterImage";
import CharacterProvider from "../context/CharacterData/CharacterProvider";

export default function CharacterScreen() {
  const route = useRoute<RouteProp<ParamList, "Character">>();
  const charId = route.params.id as CharacterName;
  const charName = route.params.name;

  const [showMain, setShowMain] = useState(false);
  useEffect(() => {
    setTimeout(() => {
      setShowMain(true);
    });
  }, []);

  return (
    <CharacterProvider charId={charId}>
      <View style={{ flex: 1, backgroundColor: "white" }}>
        <StatusBar style="dark" />
        <WallPaper isBlur />
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
    </CharacterProvider>
  );
}
