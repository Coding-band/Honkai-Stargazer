import { LinearGradient } from "expo-linear-gradient";
import React, { useEffect } from "react";
import { Pressable, View } from "react-native";
import Menu from "../components/HomeScreen/Menu/Menu";
import Tabbar from "../components/HomeScreen/Tabbar/Tabbar";
import Player from "../components/HomeScreen/Player/Player";
import { StatusBar } from "expo-status-bar";
import WallPaper from "../components/global/WallPaper/WallPaper";
import useHsrPlayerData from "../hooks/hoyolab/useHsrPlayerData";
import auth from "@react-native-firebase/auth";
import useHoyolabCookie from "../redux/hoyolabCookie/useHoyolabCookie";
import db from "../firebase/db";
import Users from "../firebase/models/Users";
import useHsrFullData from "../hooks/hoyolab/useHsrFullData";
import UserCharacters from "../firebase/models/UserCharacters";
import useHsrCharList from "../hooks/hoyolab/useHsrCharList";
import useMemoryOfChaos from "../hooks/hoyolab/useMemoryOfChaos";
import UserMemoryOfChaos from "../firebase/models/UserMemoryOfChaos";
import { ADMIN_LIST } from "../firebase/constant/adminList";

export default function HomeScreen() {
  const { hoyolabCookieParse } = useHoyolabCookie();

  const hsrFullData = useHsrFullData().data;
  const hsrPlayerData = useHsrPlayerData();
  const hsrCharList = useHsrCharList();
  const moc = useMemoryOfChaos().data;

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
    if (hsrFullData && hsrPlayerData && hsrCharList && moc) {
      const uuid = hsrPlayerData.game_role_id;

      // Users
      db.Users.doc(uuid).set({
        id: uuid,
        name: hsrPlayerData.nickname,
        role: ADMIN_LIST.includes(uuid) ? "admin" : "user",
        plan: "normal",
        level: hsrPlayerData.level,
        region: hsrPlayerData.region,
        active_days: hsrFullData.stats.active_days,
        char_num: hsrFullData.stats.avatar_num,
        achievement_num: hsrFullData.stats.achievement_num,
        chest_num: hsrFullData.stats.chest_num,
      } as Users);

      // UserCharacters
      db.UserCharacters.doc(uuid).set({
        count: hsrFullData.stats.avatar_num,
        characters: hsrCharList.map((char: any) => ({
          id: char.id,
          level: char.level,
          rank: char.rank,
        })),
      } as UserCharacters);

      // UserMemoryOfChaos
      const mocData = {
        [moc.schedule_id]: {
          begin_time: moc.begin_time,
          end_time: moc.end_time,
          star_num: moc.star_num,
          battle_num: moc.battle_num,
          max_floor_id: moc.max_floor_id,
          all_floor_detail: moc.all_floor_detail.map((f: any) => ({
            floor_id: f.maze_id,
            round_num: f.round_num,
            star_num: f.star_num,
            layer_1: {
              challenge_time: f.node_1.challenge_time,
              characters: f.node_1.avatars.map((c: any) => ({
                id: c.id,
                level: c.level,
                rank: c.rank,
              })),
            },
            layer_2: {
              challenge_time: f.node_2.challenge_time,
              characters: f.node_2.avatars.map((c: any) => ({
                id: c.id,
                level: c.level,
                rank: c.rank,
              })),
            },
          })),
        },
      } as UserMemoryOfChaos;
      try {
        await db.UserMemoryOfChaos.doc(uuid).update(mocData);
      } catch (error: any) {
        if (error.code === "firestore/not-found") {
          await db.UserMemoryOfChaos.doc(uuid).set(mocData);
        }
      }
    }
  };

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
  }, [
    hsrPlayerData,
    hoyolabCookieParse,
    handleFirebaseSignUp,
    handleFirebaseSignIn,
    createOrUpdateFirebaseProfile,
  ]);

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
