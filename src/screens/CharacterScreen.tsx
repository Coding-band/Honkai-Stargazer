import { View, Text, SafeAreaView } from "react-native";
import React from "react";
import { ImageBackground } from "expo-image";
import { Shadow } from "react-native-shadow-2";
import { BlurView } from "expo-blur";
import { RouteProp, useRoute } from "@react-navigation/native";
import { ParamList } from "../types/navigation";
import { LinearGradient } from "expo-linear-gradient";
import Header from "../components/global/layout/Header";
import { StatusBar } from "expo-status-bar";
import CharList from "../components/CharacterScreen/CharList/CharList";

export default function CharacterScreen() {
  const route = useRoute<RouteProp<ParamList, "Character">>();

  const HeaderIcon = route?.params?.Icon;

  return (
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
      <Header Icon={HeaderIcon}>角色列表</Header>
      <CharList />
    </View>
  );
}
