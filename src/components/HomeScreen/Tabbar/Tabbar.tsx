import React from "react";
import { SafeAreaView, StyleSheet, View } from "react-native";
import { cn } from "../../../utils/cn";
import Tab from "./Tab/Tab";
import { MathOperations, Person, Sword } from "phosphor-react-native";

export default function Tabbar() {
  return (
    <SafeAreaView className={cn("absolute bottom-0", "w-full h-[130px]")}>
      <Divider />
      <View
        style={{
          flex: 1,
          flexDirection: "row",
          justifyContent: "center",
          alignItems: "center",
          gap: 16,
        }}
      >
        <Tab>
          <Person color="white" size={32} weight="fill" />
        </Tab>
        <Tab>
          <Sword color="white" size={32} weight="fill" />
        </Tab>
        <Tab>
          <MathOperations color="white" size={32} weight="fill" />
        </Tab>
      </View>
    </SafeAreaView>
  );
}

const Divider = () => (
  <View className="h-[1px] w-full px-4">
    <View
      className="w-full h-full"
      style={{ backgroundColor: "rgba(144, 124, 84, 0.40)" }}
    ></View>
  </View>
);
