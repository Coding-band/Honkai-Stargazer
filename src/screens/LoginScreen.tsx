import { View } from "react-native";
import React from "react";
import { StatusBar } from "expo-status-bar";
import { WebView } from "react-native-webview";
import Header from "../components/global/Header/Header";
import { SCREENS } from "../constant/screens";
import useHoyolabCookie from "../redux/hoyolabCookie/useHoyolabCookie";
import WallPaper from "../components/global/WallPaper/WallPaper";
import {
  cookieURLs,
  getHoyolabCookieFromCookieManager,
} from "../utils/hoyolab/cookie/hoyolabCookie";
import { ParamList } from "../types/navigation";
import { RouteProp, useRoute } from "@react-navigation/native";
import useHsrServerChosen from "../redux/hsrServerChosen/useHsrServerChosen";
import { isHoyolabPlatform } from "../utils/hoyolab/utils";
import useAppLanguage from "../context/AppLanguage/useAppLanguage";

export default function LoginScreen() {

    const { language } = useAppLanguage();


  const route = useRoute<RouteProp<ParamList, "Login">>();
  const platform = route.params.platform;
  const serverId = route.params.serverId;

  const { setHoyolabCookie } = useHoyolabCookie();
  const { setHsrServerChosen, hsrServerChosen } = useHsrServerChosen();

  return (
    <View style={{ flex: 1, backgroundColor: "white" }}>
      <StatusBar style="dark" />
      <WallPaper />
      <Header
        onBack={async () => {
          const cookie = await getHoyolabCookieFromCookieManager(
            isHoyolabPlatform(serverId) ? "hoyolab" : "mihoyo"
          );
          setHsrServerChosen(serverId);
          setHoyolabCookie(cookie);
        }}
        Icon={SCREENS.LoginPage.icon}
      >
        {SCREENS.LoginPage.getName(language)}
      </Header>
      <WebView
        incognito
        className="mt-[110px]"
        style={{ flex: 1 }}
        source={{
          uri: platform === "hoyolab" ? cookieURLs.hoyolab : cookieURLs.mihoyo,
        }}
      />
    </View>
  );
}
