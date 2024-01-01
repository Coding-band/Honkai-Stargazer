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
import cookieUtil from "cookie";

import auth from "@react-native-firebase/auth";
import Toast from "../utils/toast/Toast";

export default function LoginScreen() {
  const { language } = useAppLanguage();

  const route = useRoute<RouteProp<ParamList, "Login">>();
  const platform = route.params.platform;
  const serverId = route.params.serverId;

  const { setHoyolabCookie } = useHoyolabCookie();
  const { setHsrServerChosen, hsrServerChosen } = useHsrServerChosen();

  const handleLogin = async () => {
    // hoyolab 或米游社所在伺服器判斷
    setHsrServerChosen(serverId);

    // hoyolab 或米游社 Cookie 處理
    const cookie = await getHoyolabCookieFromCookieManager(
      isHoyolabPlatform(serverId) ? "hoyolab" : "mihoyo"
    );
    const cookieParse = cookieUtil.parse(cookie);
    setHoyolabCookie(cookie);

    // firebase auth
    if (cookieParse.account_id_v2) {
      // firebase 註冊
      try {
        const userCredential = await auth().createUserWithEmailAndPassword(
          `${cookieParse.account_id_v2}@stargazer.com`,
          `${cookieParse.account_mid_v2}`
        );
        const user = userCredential.user;
      } catch (error: any) {
        const errorCode = error.code;
        const errorMessage = error.message;

        if (errorCode === "auth/email-already-in-use") {
          // firebase 登入
          try {
            const userCredential = await auth().signInWithEmailAndPassword(
              `${cookieParse.account_id_v2}@stargazer.com`,
              `${cookieParse.account_mid_v2}`
            );
            const user = userCredential.user;
            console.log(user);
          } catch (error: any) {
            const errorCode = error.code;
            const errorMessage = error.message;
            console.log(errorMessage);
          }
        }
      }
    }
  };

  return (
    <View style={{ flex: 1, backgroundColor: "white" }}>
      <StatusBar style="dark" />
      <WallPaper />
      <Header onBack={handleLogin} Icon={SCREENS.LoginPage.icon}>
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
