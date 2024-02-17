import { View } from "react-native";
import React from "react";
import { StatusBar } from "expo-status-bar";
import { LinearGradient } from "expo-linear-gradient";
import Header from "../components/global/Header/Header";
import { SCREENS } from "../constant/screens";
import WallPaper from "../components/global/WallPaper/WallPaper";
import SettingList from "../components/SettingScreen/SettingList/SettingList";
import useAppLanguage from "../language/AppLanguage/useAppLanguage";
import { RouteProp, useNavigation, useRoute } from "@react-navigation/native";
import { ParamList } from "../types/navigation";
import { ScrollView } from "react-native-gesture-handler";
import { dynamicHeightScrollViewLRPadding } from "../constant/ui";

export default function DescriptionScreen() {
  const route = useRoute<RouteProp<ParamList, "Description">>();
  const title = route.params.title;
  const Icon = route.params.icon;
  const content = route.params.content;

  return (
    <View style={{ flex: 1 }} className="overflow-hidden">
      <StatusBar style="dark" />
      <WallPaper isBlur />
      <LinearGradient
        className="absolute w-full h-full"
        colors={["#00000080", "#00000020"]}
      />

      <Header leftBtn="back" Icon={Icon}>
        {title}
      </Header>
      <ScrollView className={dynamicHeightScrollViewLRPadding}>
        {content}
        <View className="mt-48"/>
      </ScrollView>
      <LinearGradient
        className="w-full h-[600px] absolute bottom-0"
        colors={["#00000000", "#000000"]}
      />
    </View>
  );
}
