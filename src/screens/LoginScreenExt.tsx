import { BackHandler, View } from "react-native";
import React, { useEffect, useRef, useState } from "react";
import { StatusBar } from "expo-status-bar";
import { WebView, WebViewNavigation } from "react-native-webview";
import Header from "../components/global/Header/Header";
import { SCREENS } from "../constant/screens";
import useHoyolabCookie from "../redux/hoyolabCookie/useHoyolabCookie";
import WallPaper from "../components/global/WallPaper/WallPaper";
import {
  cookieURLs,
  getHoyolabCookieFromCookieManager,
} from "../utils/hoyolab/cookie/hoyolabCookie";
import { ParamList } from "../types/navigation";
import { RouteProp, useNavigation, useRoute } from "@react-navigation/native";
import useHsrServerChosen from "../redux/hsrServerChosen/useHsrServerChosen";
import { isHoyolabPlatform } from "../utils/hoyolab/utils";
import useAppLanguage from "../language/AppLanguage/useAppLanguage";
import auth from "@react-native-firebase/auth";
import { dynamicHeightLoginWebview } from "../constant/ui";
import DeviceInfo from "react-native-device-info";
import * as WebBrowser from 'expo-web-browser';

export default function LoginScreen() {
  const { language } = useAppLanguage();
  const scrollViewRef = useRef();
  const navigation = useNavigation();

  const route = useRoute<RouteProp<ParamList, "Login">>();
  const platform = route.params.platform;
  const serverId = route.params.serverId;

  const { setHoyolabCookie } = useHoyolabCookie();
  const { setHsrServerChosen } = useHsrServerChosen();
  const webviewRef = useRef<WebView>(null);


  const handleLogin = async () => {
    // hoyolab 或米游社所在伺服器判斷
    setHsrServerChosen(serverId);

    // hoyolab 或米游社 Cookie 處理
    const cookie = await getHoyolabCookieFromCookieManager(
      isHoyolabPlatform(serverId) ? "hoyolab" : "mihoyo"
    );
    setHoyolabCookie(cookie);
    
    navigation.goBack();
  };

  // 當第三方授權成功後，重新載入 WebView 獲得最新 cookies
  const handleExternalAuth = async (url: string) => {
    try {
      const result = await WebBrowser.openAuthSessionAsync(url, (platform === "hoyolab" ? cookieURLs.hoyolab : cookieURLs.mihoyo));
      if (result.type === 'success') {
        // 登入成功後，如有需要，可重新載入 WebView
        webviewRef.current && webviewRef.current.reload();
      }
    } catch (e) {
      console.error(e);
    }
  };

  // 判斷並攔截第三方登入網址
  const handleShouldStartLoadWithRequest = (request: any) => {
    console.log(request);
    const { url } = request;
    // 可根據實際第三方登入網址進行判斷
    if (
      url.includes('accounts.google.com') ||
      url.includes('appleid.apple.com') ||
      url.includes('facebook.com') ||
      url.includes('api.x.com')
    ) {
      handleExternalAuth(url);
      return false;
    }
    return true;
  };

  useEffect(() => {
    const backHandler = BackHandler.addEventListener(
      'hardwareBackPress',
      handleLogin,
    );

    return () => backHandler.remove();
  }, []);

  return (
    <View style={{ flex: 1 }} className="overflow-hidden">
      <StatusBar style="dark" />
      <WallPaper />
      <Header onBack={handleLogin} Icon={SCREENS.LoginPage.icon}>
        {SCREENS.LoginPage.getName(language)}
      </Header>
      <WebView
        incognito={false}
        javaScriptEnabled
        domStorageEnabled
        sharedCookiesEnabled
        //injectedJavaScript="const elements = document.getElementsByClassName('hyv-third-party-login-container-base mt-p16 pb-p16'); while(elements.length > 0){ elements[0].parentNode.removeChild(elements[0]);}"
        //injectedJavaScriptBeforeContentLoaded="const elements = document.getElementsByClassName('hyv-third-party-login-container-base mt-p16 pb-p16'); while(elements.length > 0){ elements[0].parentNode.removeChild(elements[0]);}"
        thirdPartyCookiesEnabled={true}
        onMessage={(event : any) => {}}
        cacheEnabled={true}
        setSupportMultipleWindows={false}
        //userAgent={DeviceInfo.getUserAgentSync().replace("wv", "")} //Key of the Google Login
        originWhitelist={["*"]}
        onShouldStartLoadWithRequest={handleShouldStartLoadWithRequest}
        source={{
          uri: platform === "hoyolab" ? cookieURLs.hoyolab : cookieURLs.mihoyo,
        }}
        className={dynamicHeightLoginWebview}
        style={{ flex: 1 }}
      />
    </View>
  );
}
