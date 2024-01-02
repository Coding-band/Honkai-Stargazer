import { LinearGradient } from "expo-linear-gradient";
import React, { useEffect } from "react";
import { Pressable, View } from "react-native";
import Menu from "../components/HomeScreen/Menu/Menu";
import Tabbar from "../components/HomeScreen/Tabbar/Tabbar";
import Player from "../components/HomeScreen/Player/Player";
import { StatusBar } from "expo-status-bar";
import WallPaper from "../components/global/WallPaper/WallPaper";
import useWallPaper from "../redux/wallPaper/useWallPaper";
import useHsrPlayerData from "../hooks/hoyolab/useHsrPlayerData";
import auth from "@react-native-firebase/auth";
import db from "@react-native-firebase/firestore";
import useHoyolabCookie from "../redux/hoyolabCookie/useHoyolabCookie";

export default function HomeScreen() {
 
  const { hoyolabCookieParse } = useHoyolabCookie();
  const hsrPlayerData = useHsrPlayerData();

  useEffect(() => {
    if (hsrPlayerData) {
      const email = `${hsrPlayerData.game_role_id}@stargazer.com`;
      const password = hoyolabCookieParse.account_mid_v2;

      // firebase 註冊
      handleFirebaseSignUp(email, password).then((isAlreadySignUp) => {
        if (isAlreadySignUp) {
          // firebase 登入
          handleFirebaseSignIn(email, password).then(() => {
            createOrUpdateFirebaseProfile();
          });
        } else {
          createOrUpdateFirebaseProfile();
        }
      });
    }
  }, [hsrPlayerData]);

  const handleFirebaseSignUp = async (email: string, password: string) => {
    try {
      await auth().createUserWithEmailAndPassword(email, password);
      return false;
    } catch (error: any) {
      const errorCode = error.code;
      const errorMessage = error.message;
      if (errorCode === "auth/email-already-in-use") {
        return true;
      }
    }
  };

  const handleFirebaseSignIn = async (email: string, password: string) => {
    try {
      await auth().signInWithEmailAndPassword(email, password);
    } catch (error: any) {
      const errorCode = error.code;
      const errorMessage = error.message;
    }
  };

  const createOrUpdateFirebaseProfile = async () => {
    db().collection("Users").doc(hsrPlayerData.game_role_id).set({
      id: hsrPlayerData.game_role_id,
      name: hsrPlayerData.nickname,
      level: hsrPlayerData.level,
      region: hsrPlayerData.region,
    });
  };

  return (
    <Pressable style={{ flex: 1, backgroundColor: "white" }}>
      <StatusBar style="dark" />
      <WallPaper />
      <LinearGradient
        className="absolute w-full h-full"
        colors={["#00000050", "#00000040"]}
      />
      <View className="absolute w-full h-full">
        <Player />
        <LinearGradient
          // Background Linear Gradient
          colors={["rgba(0, 0, 0, 0.20) 0%", "rgba(0, 0, 0, 0.80) 100%"]}
          className="w-full"
          style={{ flex: 1 }}
        >
          <Menu />
          <Tabbar />
        </LinearGradient>
      </View>
    </Pressable>
  );
}
