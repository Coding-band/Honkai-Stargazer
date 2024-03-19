import { View } from "react-native";
import React from "react";
import WebView from "react-native-webview";
import { StatusBar } from "expo-status-bar";
import Header from "../components/global/Header/Header";
import { SCREENS } from "../constant/screens";
import useAppLanguage from "../language/AppLanguage/useAppLanguage";
import { dynamicHeightHeader, dynamicHeightHeaderValue } from "../constant/ui";

export default function MapScreen() {
  const { language } = useAppLanguage();

  return (
    <View style={{ flex: 1 }} className="overflow-hidden">
      <StatusBar style="dark" />
      <Header Icon={SCREENS.MapPage.icon}>
        {SCREENS.MapPage.getName(language)}
      </Header>
      <WebView
        style={{ marginTop: dynamicHeightHeaderValue }}
        source={{
          uri: "https://act.hoyolab.com/sr/app/interactive-map/index.html",
        }}
      />
    </View>
  );
}
