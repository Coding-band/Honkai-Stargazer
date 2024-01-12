import { LinearGradient } from "expo-linear-gradient";
import React, { useEffect, useRef, useState } from "react";
import { Pressable, Text, View } from "react-native";
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
import useMyFirebaseUid from "../firebase/hooks/FirebaseUid/useMyFirebaseUid";
import useHsrUUID from "../hooks/hoyolab/useHsrUUID";
import UserMemoryOfChaos from "../firebase/models/UserMemoryOfChaos";
import firestore from "@react-native-firebase/firestore";
import { ENV } from "../../app.config";
import BetaWidget from "../components/global/Beta/BetaWidget";
import WallPaperForMOC from "../components/global/WallPaper/WallPaperForMOC";

export default function HomeScreen() {
  const uid = useMyFirebaseUid();

  const { hoyolabCookieParse } = useHoyolabCookie();
  const hsrFullData = useHsrFullData().data;
  const hsrUUID = useHsrUUID();
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
      console.log("signup: " + errorMessage);
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
      console.log("signin: " + errorMessage);
    }
  };

  //* Firebase 註冊或登入
  useEffect(() => {
    //* 如果存在崩鐵 UUID (表示已登入 hoyolab + 擁有崩鐵帳號，執行 firebase 登入)
    if (hsrUUID && hoyolabCookieParse) {
      const email = `${hsrUUID}@stargazer.com`;
      const password = hoyolabCookieParse.account_mid_v2;
      // firebase 註冊
      handleFirebaseSignUp(email, password).then((isAlreadySignUp) => {
        if (isAlreadySignUp) {
          // firebase 登入
          handleFirebaseSignIn(email, password).then(() => {});
        } else {
        }
      });
    }
  }, [hsrUUID, hoyolabCookieParse, handleFirebaseSignUp, handleFirebaseSignIn]);

  //* 建立或更新用戶數據 (User)
  useEffect(() => {
    async function createOrUpdateUser() {
      if (uid && hsrFullData && hsrPlayerData) {
        // uid 表示 firebase uid, uuid 表示崩鐵遊戲 id
        const uuid = hsrPlayerData.game_role_id;
        const UsersIsExist = (await db.Users.doc(uid).get()).exists;
        if (UsersIsExist) {
          try {
            await db.Users.doc(uid).update({
              avatar_url: hsrFullData.cur_head_icon_url,
              level: hsrPlayerData.level,
              active_days: hsrFullData.stats.active_days,
              char_num: hsrFullData.stats.avatar_num,
              achievement_num: hsrFullData.stats.achievement_num,
              chest_num: hsrFullData.stats.chest_num,
              last_login: firestore.Timestamp.now(),
            });
          } catch (e: any) {
            console.log("update User: " + e.message);
          }
        } else {
          try {
            await db.Users.doc(uid).set({
              uuid: uuid,
              name: hsrPlayerData.nickname,
              avatar_url: hsrFullData.cur_head_icon_url,
              role: "user",
              plan: "normal",
              level: hsrPlayerData.level,
              region: hsrPlayerData.region,
              active_days: hsrFullData.stats.active_days,
              char_num: hsrFullData.stats.avatar_num,
              achievement_num: hsrFullData.stats.achievement_num,
              chest_num: hsrFullData.stats.chest_num,
              show_info: false,
              last_login: firestore.Timestamp.now(),
            } as Users);
          } catch (e: any) {
            console.log("create User: " + e.message);
          }
        }
      }
    }
    createOrUpdateUser();
  }, [uid, hsrPlayerData, hsrPlayerData]);

  //* 建立或更新用戶角色數據 (UserCharacters)
  useEffect(() => {
    async function createOrUpdateUserCharacters() {
      if (uid && hsrFullData && hsrCharList) {
        const UserCharactersIsExist = (await db.UserCharacters.doc(uid).get())
          .exists;
        if (UserCharactersIsExist) {
          try {
            db.UserCharacters.doc(uid).update({
              characters: hsrCharList.map((char: any) => ({
                id: char.id,
                level: char.level,
                rank: char.rank,
              })),
            } as UserCharacters);
          } catch (e: any) {
            console.log("updated UserCharacters: " + e.message);
          }
        } else {
          try {
            db.UserCharacters.doc(uid).set({
              characters: hsrCharList.map((char: any) => ({
                id: char.id,
                level: char.level,
                rank: char.rank,
              })),
            } as UserCharacters);
          } catch (e: any) {
            console.log("create UserCharacters: " + e.message);
          }
        }
      }
    }
    createOrUpdateUserCharacters();
  }, [uid, hsrFullData, hsrCharList]);

  //* 建立或更新用戶混沌回憶資料 (UserMemoryOfChaos)
  useEffect(() => {
    async function createOrUpdateUserMemoryOfChaos() {
      if (uid && moc) {
        const mocData = {
          [moc.schedule_id]: {
            begin_time: moc.begin_time,
            end_time: moc.end_time,
            star_num: moc.star_num,
            battle_num: moc.battle_num,
            max_floor_id: moc.max_floor_id,
            max_floor: moc.all_floor_detail.length,
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
        const UserMemoryOfChaosIsExist = (
          await db.UserMemoryOfChaos.doc(uid).get()
        ).exists;
        if (UserMemoryOfChaosIsExist) {
          try {
            await db.UserMemoryOfChaos.doc(uid).update(mocData);
          } catch (e: any) {
            console.log("updated UserMemoryOfChaos: " + e.message);
          }
        } else {
          try {
            await db.UserMemoryOfChaos.doc(uid).set(mocData);
          } catch (e: any) {
            console.log("create UserMemoryOfChaos: " + e.message);
          }
        }
      }
    }
    createOrUpdateUserMemoryOfChaos();
  }, [uid, moc]);

  return (
    <Pressable style={{ flex: 1, backgroundColor: "white" }}>
      <StatusBar style="dark" />
      {/* 渾沌回憶背景預先加載 */}
      <WallPaperForMOC />
      <WallPaper />
      <LinearGradient
        className="absolute w-full h-full"
        colors={["#00000050", "#00000040"]}
      />
      <View className="absolute w-full h-full">
        {ENV === "beta" ? <BetaWidget /> : null}
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
