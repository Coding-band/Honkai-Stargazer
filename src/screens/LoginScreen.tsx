import { View } from "react-native";
import React from "react";
import { StatusBar } from "expo-status-bar";
import { ImageBackground } from "expo-image";
import { WebView } from "react-native-webview";

export default function LoginScreen() {
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
      <WebView
        style={{ flex: 1 }}
        source={{
          uri: "https://act.hoyolab.com/app/community-game-records-sea/index.html",
        }}
        onNavigationStateChange={(e) => {
            console.log(e)
        }}
      />
    </View>
  );
}
