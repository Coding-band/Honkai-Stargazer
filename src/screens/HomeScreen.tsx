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
import PlayerLevel from "../components/HomeScreen/Player/PlayerLevel/PlayerLevel";

export default function HomeScreen() {
  return (
    <View style={{ flex: 1, backgroundColor: "white" }}>
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
        <View
          className={cn("w-full pt-8 px-5")}
          style={{ gap: 12, alignItems: "flex-start" }}
        >
          {/* uuid */}
          <UUID />
          {/* player */}
          <View className="w-full" style={{ flexDirection: "column", gap: 12 }}>
            <View
              style={{
                justifyContent: "space-between",
                flexDirection: "row",
              }}
            >
              <View style={{ flexDirection: "row" }}>
                <PlayerAvator />
                <View>
                  <Text className="text-white text-xl font-medium mb-2">
                    2O48
                  </Text>
                  <View className="flex flex-row gap-1">
                    <View className="w-[30px] h-[30px] bg-[#D9D9D9] border-2 border-[#D3D3D3] rounded-full" />
                    <View className="w-[30px] h-[30px] bg-[#D9D9D9] border-2 border-[#D3D3D3] rounded-full" />
                    <View className="w-[30px] h-[30px] bg-[#D9D9D9] border-2 border-[#D3D3D3] rounded-full" />
                  </View>
                </View>
              </View>
              <View
                style={{
                  flexDirection: "column",
                  gap: 20,
                  alignItems: "flex-end",
                }}
              >
                <PlayerAction />
                <View>
                  <Text className="text-[#DBC291] text-[14px] font-medium">
                    开拓等级 58
                  </Text>
                </View>
              </View>
            </View>
            <PlayerLevel />
          </View>
        </View>
        <LinearGradient
          // Background Linear Gradient
          colors={["rgba(0, 0, 0, 0.20) 0%", "rgba(0, 0, 0, 0.80) 100%"]}
          className="w-full"
          style={{ flex: 1 }}
        >
          <Menu />
          <Tabbar>
            <Tab>
              <Person color="white" size={32} weight="fill" />
            </Tab>
            <Tab>
              <Sword color="white" size={32} weight="fill" />
            </Tab>
            <Tab>
              <MathOperations color="white" size={32} weight="fill" />
            </Tab>
          </Tabbar>
        </LinearGradient>
      </View>
    </View>
  );
}
