import { View } from "react-native";
import React, { useEffect, useRef, useState } from "react";
import { StatusBar } from "expo-status-bar";
import { LinearGradient } from "expo-linear-gradient";
import Header from "../components/global/Header/Header";
import { SCREENS } from "../constant/screens";
import { RouteProp, useNavigation, useRoute } from "@react-navigation/native";
import { ParamList } from "../types/navigation";
import CharacterMain from "../components/CharacterScreen/Character/Character";
import { CharacterName } from "../types/character";
import CharacterContext from "../context/CharacterData/CharacterContext";
import Fixed from "../components/global/Fixed/Fixed";
import WallPaper from "../components/global/WallPaper/WallPaper";
import CharacterProvider from "../context/CharacterData/CharacterProvider";
import useTextLanguage from "../language/TextLanguage/useTextLanguage";
import { getCharFullData } from "../utils/data/getDataFromMap";
import { ScrollView } from "react-native-gesture-handler";
import Animated from "react-native-reanimated";

export default function CharacterScreen() {
  const { language } = useTextLanguage();
  const scrollViewRef = useRef<ScrollView | Animated.ScrollView | undefined | null>();
  const route = useRoute<RouteProp<ParamList, "Character">>();
  const charId = route.params.id as CharacterName;
  const charName = getCharFullData(charId, language).name;

  const [showMain, setShowMain] = useState(false);
  useEffect(() => {
    setTimeout(() => {
      setShowMain(true);
    });
  }, []);

  return (
    <CharacterProvider charId={charId}>
      <View style={{ flex: 1 }} className="overflow-hidden">
        <StatusBar style="dark" />
        <WallPaper isBlur />
        <LinearGradient
          className="absolute w-full h-full"
          colors={["#00000080", "#00000020"]}
        />

        <Header leftBtn="back" Icon={SCREENS.CharacterPage.icon} scrollViewRef={scrollViewRef}>
          {charName}
        </Header>
        {showMain && <CharacterMain scrollViewRef={scrollViewRef}/>}
        <LinearGradient
          className="w-full h-[600px] absolute bottom-0"
          colors={["#00000000", "#000000"]}
        />

        <Fixed />
      </View>
    </CharacterProvider>
  );
}
