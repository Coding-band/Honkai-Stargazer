import { View } from "react-native";
import React from "react";
import { StatusBar } from "expo-status-bar";
import { ImageBackground } from "expo-image";
import { WebView } from "react-native-webview";
import Header from "../components/global/Header/Header";
import { SCREENS } from "../constant/screens";
import useHoyolabCookie from "../redux/hoyolabCookie/useHoyolabCookie";
// import { getHoyolabCookieFromCookieManager } from "../utils/hoyolab/hoyolabCookie";

export default function LoginScreen() {
  const { setHoyolabCookie } = useHoyolabCookie();

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
      <Header
        onBack={async () => {
          // const cookie = await getHoyolabCookieFromCookieManager();
          // if (cookie) {
          //   setHoyolabCookie(cookie);
          // }
        }}
        Icon={SCREENS.LoginPage.icon}
      >
        {SCREENS.LoginPage.name}
      </Header>
      <WebView
        incognito
        className="mt-[110px]"
        style={{ flex: 1 }}
        source={{
          uri: "https://act.hoyolab.com/app/community-game-records-sea/index.html",
        }}
      />
    </View>
  );
}
