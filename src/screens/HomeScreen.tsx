import { LinearGradient } from "expo-linear-gradient";
import React from "react";
import { Dimensions, Text, View } from "react-native";
import { Image, ImageBackground } from "expo-image";
import { blurhash } from "../constant/Image";
import { cn } from "../utils/cn";
import UUID from "../components/HomeScreen/UUID/UUID";
import PlayerAvator from "../components/HomeScreen/Player/PlayerAvator/PlayerAvator";
import MoreBtn from "../components/global/ui/MoreBtn/MoreBtn";
import PlayerAction from "../components/HomeScreen/Player/PlayerAction/PlayerAction";
import Menu from "../components/HomeScreen/Menu/Menu";
import Tabbar from "../components/HomeScreen/Tabbar/Tabbar";
import Tab from "../components/HomeScreen/Tabbar/Tab/Tab";
import { MathOperations, Person, Sword } from "phosphor-react-native";
import PlayerLevelBar from "../components/HomeScreen/Player/PlayerLevelBar/PlayerLevelBar";
import Player from "../components/HomeScreen/Player/Player";
import { StatusBar } from "expo-status-bar";

export default function HomeScreen() {
  return (
    <View style={{ flex: 1, backgroundColor: "white" }}>
      <StatusBar style="dark" />
      <ImageBackground
        className="absolute w-full h-full"
        // 把背景關掉
        source={require("../../assets/images/test-bg.png")}
        // placeholder={blurhash}
        contentFit="cover"
      />
      <LinearGradient
        className="absolute w-full h-full"
        colors={["#00000050", "#00000040"]}
      />
      <View className="absolute w-full h-full">
        <Player />
        <LinearGradient
          // Background Linear Gradient
          colors={["rgba(0, 0, 0, 0.20) 0%", "rgba(0, 0, 0, 0.80) 100%"]}
          className="w-full"
          style={{ flex: 1 }}
        >
          <Menu />
          <Tabbar/>
        </LinearGradient>
      </View>
    </View>
  );
}
