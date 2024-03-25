import { View } from "react-native";
import React, { useRef } from "react";
import { StatusBar } from "expo-status-bar";
import { LinearGradient } from "expo-linear-gradient";
import Header from "../components/global/Header/Header";
import { SCREENS } from "../constant/screens";
import WallPaper from "../components/global/WallPaper/WallPaper";
import useAppLanguage from "../language/AppLanguage/useAppLanguage";
import { RouteProp, useRoute } from "@react-navigation/native";
import { ParamList } from "../types/navigation";
import Event from "../components/EventScreen/Event/Event";

export default function EventScreen() {
  const { language } = useAppLanguage();
  const scrollViewRef = useRef();

  return (
    <View style={{ flex: 1 }} className="overflow-hidden">
      <StatusBar style="dark" />
      <WallPaper isBlur />
      <LinearGradient
        className="absolute w-full h-full"
        colors={["#00000080", "#00000020"]}
      />

      <Header leftBtn="back" Icon={SCREENS.EventPage.icon} scrollViewRef={scrollViewRef}>
        {SCREENS.EventPage.getName(language)}
      </Header>
      <Event scrollViewRef={scrollViewRef}/>
      <LinearGradient
        className="w-full h-[600px] absolute bottom-0"
        colors={["#00000000", "#000000"]}
      />
    </View>
  );
}
