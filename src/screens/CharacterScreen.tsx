import { View, Text } from "react-native";
import React from "react";
import { StatusBar } from "expo-status-bar";
import { ImageBackground } from "expo-image";
import { LinearGradient } from "expo-linear-gradient";
import Header from "../components/global/layout/Header";
import { SCREENS } from "../constant/screens";
import { RouteProp, useRoute } from "@react-navigation/native";
import { ParamList } from "../types/navigation";
import Character from "../components/CharacterScreen/Character/Character";
import { filter } from "lodash";
import testCharsData from "../../data/test-chars-data";
import CharacterType from "../types/Character";
import CharacterContext from "../context/CharacterContext";

export default function CharacterScreen() {
  const route = useRoute<RouteProp<ParamList, "Character">>();
  const charId = route.params.id;
  // @ts-ignore
  const charData: CharacterType = filter(
    testCharsData,
    (char) => char.id === charId
  )[0];

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
          {charData.name || ""}
        </Header>
        <Character />
        <LinearGradient
          className="w-full h-[400px] absolute bottom-0"
          colors={["#00000000", "#000000"]}
        />
      </View>
    </CharacterContext.Provider>
  );
}
