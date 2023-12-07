import { View, Text, SafeAreaView } from "react-native";
import React from "react";
import WebView from "react-native-webview";
import { StatusBar } from "expo-status-bar";
import Header from "../components/global/Header/Header";
import { SCREENS } from "../constant/screens";
import { ImageBackground } from "expo-image";

export default function MapScreen() {
  return (
    <View style={{ flex: 1, backgroundColor: "white" }}>
      <StatusBar style="dark" />
      <Header Icon={SCREENS.MapPage.icon}>{SCREENS.MapPage.name}</Header>
      <WebView
        style={{ marginTop: 110 }}
        source={{
          uri: "https://act.hoyolab.com/sr/app/interactive-map/index.html",
        }}
      />
    </View>
  );
}
