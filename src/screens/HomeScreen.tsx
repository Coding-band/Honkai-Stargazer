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
import UserMemoryOfChaos, {
  UserMemoryOfChaosFloor,
} from "../firebase/models/UserMemoryOfChaos";
import firestore from "@react-native-firebase/firestore";
import { ENV, VERSION, askEnvDo } from "../../app.config";
import BetaWidget from "../components/global/Beta/BetaWidget";
import WallPaperForMOC from "../components/global/WallPaper/WallPaperForMOC";
import useMemoryOfChaosPrev from "../hooks/hoyolab/useMemoryOfChaosPrev";
import genId from "../utils/genId";
import useHsrInGameInfo from "../hooks/mihomo/useHsrInGameInfo";
import { unionBy } from "lodash";
import useUserCharacters from "../firebase/hooks/UserCharacters/useUserCharacters";
import getCharScore from "../utils/calculator/charScoreCalculator/getCharScore";
import getRelicScore from "../utils/calculator/relicScoreCalculator/getRelicScore";
import officalRelicId from "../../map/relic_offical_id_map";
import getSetIdAndCountFromRelicData from "../utils/data/getSetIdAndCountFromRelicData";
import usePureFiction from "../hooks/hoyolab/usePureFiction";
import usePureFictionPrev from "../hooks/hoyolab/usePureFictionPrev";
import ReactNativeModal from "react-native-modal";
import { Dimensions } from "react-native";
import SelectLanguageAtFirstTime from "../components/global/SelectLanguageAtFirstTime/SelectLanguageAtFirstTime";
import DonateTab from "../components/HomeScreen/Tabbar/DonateTab/DonateTab";
import { dynamicHeightBottomBar } from "../constant/ui";
import { getLcFullData } from "../utils/data/getDataFromMap";
import useAppLanguage from "../language/AppLanguage/useAppLanguage";
import { getLcAttrData, getLcAttrDataJSON } from "../utils/calculator/getAttrData";
import { getAttrKeyByPropertyType } from "../utils/hoyolab/exchange/exchange";

export default function HomeScreen() {
  const uid = useMyFirebaseUid();
  const { hoyolabCookieParse } = useHoyolabCookie();
  const hsrFullData = useHsrFullData().data;

  const hsrUUID = useHsrUUID();
  const hsrPlayerData = useHsrPlayerData();
  const hsrCharList = useHsrCharList().data;
  const hsrInGameInfo = useHsrInGameInfo(hsrUUID).data as any;

  const moc = useMemoryOfChaos().data;
  const mocPrev = useMemoryOfChaosPrev().data;
  const pf = usePureFiction().data;
  const pfPrev = usePureFictionPrev().data;

  const userCharDetailList = useUserCharacters(uid).data?.characters_details;

  const { language: appLanguage } = useAppLanguage();

  const handleFirebaseSignUp = async (email: string, password: string) => {
    try {
      await auth().createUserWithEmailAndPassword(email, password);
      return false;
    } catch (error: any) {
      const errorCode = error.code;
      const errorMessage = error.message;
      // console.log("signup: " + errorMessage);
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
          handleFirebaseSignIn(email, password).then((a) => { });
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
        const UserData = await db.Users.doc(uid).get();
        const UserIsExist = UserData.exists;
        if (UserIsExist) {
          try {
            await db.Users.doc(uid).update({
              avatar_url: hsrFullData.cur_head_icon_url,
              level: hsrPlayerData.level,
              active_days: hsrFullData.stats.active_days,
              char_num: hsrFullData.stats.avatar_num,
              achievement_num: hsrFullData.stats.achievement_num,
              chest_num: hsrFullData.stats.chest_num,
              last_login: firestore.Timestamp.now(),
              app_version: askEnvDo({
                development: "Development Version",
                beta: VERSION.beta,
                production: VERSION.production,
              }),
            });
            if (!UserData?.data()?.invite_code) {
              await db.Users.doc(uid).update({
                invite_code: "SG-" + genId(10),
              });
            }
          } catch (e: any) {
            console.log("update User: " + e.message);
          }
        } else {
          try {
            await db.Users.doc(uid).set({
              uuid: uuid,
              name: hsrPlayerData.nickname,
              avatar_url: hsrFullData.cur_head_icon_url,
              role: ENV === "beta" ? "beta_user" : "user",
              plan: "normal",
              level: hsrPlayerData.level,
              region: hsrPlayerData.region,
              active_days: hsrFullData.stats.active_days,
              char_num: hsrFullData.stats.avatar_num,
              achievement_num: hsrFullData.stats.achievement_num,
              chest_num: hsrFullData.stats.chest_num,
              show_info: false,
              last_login: firestore.Timestamp.now(),
              app_version: askEnvDo({
                development: "Development Version",
                beta: VERSION.beta,
                production: VERSION.production,
              }),
            });
          } catch (e: any) {
            console.log("create User: " + e.message);
          }
        }
        // 更新上線時間
        const i = setInterval(() => {
          db.Users.doc(uid).update({
            last_login: firestore.Timestamp.now(),
          });
        }, 1000 * 60);
        return () => {
          clearInterval(i);
        };
      }
    }
    createOrUpdateUser();
  }, [uid, hsrFullData, hsrPlayerData]);

  //* 建立用戶邀請碼 (UserInviteCodes)
  useEffect(() => {
    async function createOrUpdateUserInviteCodes() {
      if (uid) {
        const doc = db.UserInviteCodes.doc(uid);
        const docIsExist = (await doc.get()).exists;

        if (docIsExist) {
        } else {
          await doc.set({
            invite_code: "SG-" + genId(10),
          });
        }
      }
    }
    createOrUpdateUserInviteCodes();
  }, [uid]);

  //* 建立或更新用戶角色數據 (UserCharacters)
  useEffect(() => {
    async function createOrUpdateUserCharacters() {
      if (uid && hsrCharList) {
        const UserCharacterDocGet = await db.UserCharacters.doc(uid).get();
        const UserCharactersIsExist = UserCharacterDocGet.exists;
        console.log("UserCharactersIsExist : " + UserCharactersIsExist)
        console.log("uid : " + uid)
        const charsData = {
          characters: hsrCharList.map((char: any) => ({
            id: char?.id,
            level: char?.level,
            rank: char?.rank,
            equip: char?.equip
              ? {
                id: char?.equip?.id,
                level: char?.equip?.level,
                rank: char?.equip?.rank,
              }
              : {},
            relics: char?.relics
              ? char?.relics?.map((relic: any) => ({
                id: relic?.id,
                level: relic?.level,
                rarity: relic?.rarity,
                pos: relic?.pos,
              }))
              : [],
            ornaments: char?.ornaments
              ? char?.ornaments?.map((ornament: any) => ({
                id: ornament?.id,
                level: ornament?.level,
                rarity: ornament?.rarity,
                pos: ornament?.pos,
              }))
              : [],
          })),

          /**
           * 金玉其外 敗絮其中
           */
          characters_details: hsrCharList.map((char: any) => {
            return ({
              id: char?.id?.toString(),
              level: char?.level,
              promotion: getPromotionByLevel(char?.level),
              light_cone: char?.equip?.id !== undefined ? {
                attributes: getLcAttrDataJSON(char?.equip?.id, char?.equip?.level),
                id: char?.equip?.id,
                level: char?.equip?.level,
                rank: char?.equip?.rank,
                //what is rank? necessery for path?
                rarity: char?.equip?.rarity,
              } : {},
              //skill and skill_tree ?
              skills: char?.skills.filter((skill: any) => skill.point_type === 2).map((skill: any) => ({
                level: skill?.level,
                point_id: skill?.point_id,
              })).concat({
                level: 1, //這個是秘技
              }),
              relics: char?.relics.map((data: any) => {
                const attrData = getAttrKeyByPropertyType(data?.main_property?.property_type);
                const iconId = getIconStrById(data?.id);
                const iconSetId = Math.floor(iconId / 10).toString();
                return ({
                  id: data?.id,
                  level: data?.level,
                  rarity: data?.rarity,
                  pos: data?.pos,
                  set_id: iconSetId,
                  icon: (iconSetId + "_" + (iconId % 10 - (iconId % 10 >= 5 ? 5 : 1)).toString()),
                  main_affix: {//s
                    type: attrData.type,
                    field: attrData.key,
                    value: Number(data?.main_property?.value.replace("%", "")) / (data?.main_property?.value.includes("%") ? 100 : 1),
                    times: data?.main_property?.times,
                    display: data?.main_property?.value,
                  },
                  sub_affix: data?.properties?.map((sub: any) => {
                    const attrDataSub = getAttrKeyByPropertyType(sub?.property_type);
                    return ({
                      type: attrDataSub.type,
                      field: attrDataSub.key,
                      value: Number(sub?.value.replace("%", "")) / (sub?.value.includes("%") ? 100 : 1),
                      times: sub?.times,
                      display: sub?.value,
                    })
                  })
                })
              }).concat(
                char?.ornaments.map((data: any) => {
                  const attrData = getAttrKeyByPropertyType(data?.main_property?.property_type);
                  const iconId = getIconStrById(data?.id);
                  const idSetId = Math.floor(iconId / 10).toString();
                  return ({
                    id: data?.id,
                    level: data?.level,
                    rarity: data?.rarity,
                    pos: data?.pos,
                    set_id: idSetId,
                    icon: (idSetId + "_" + (iconId % 10 - (iconId % 10 >= 5 ? 5 : 1)).toString()),
                    main_affix: {
                      type: attrData.type,
                      field: attrData.key,
                      value: Number(data?.main_property?.value.replace("%", "")) / (data?.main_property?.value.includes("%") ? 100 : 1),
                      times: data?.main_property?.times,
                      display: data?.main_property?.value,
                    },
                    sub_affix: data?.properties?.map((sub: any) => {
                      const attrDataSub = getAttrKeyByPropertyType(sub?.property_type);
                      return ({
                        type: attrDataSub.type,
                        field: attrDataSub.key,
                        value: Number(sub?.value.replace("%", "")) / (sub?.value.includes("%") ? 100 : 1),
                        times: sub?.times,
                        display: sub?.value,
                      })
                    })
                  })
                })
              ), //property -> affix
              //attributes, additions, properties
              attributes: (char?.properties?.map((data: any) => {
                const attrData = getAttrKeyByPropertyType(data?.property_type);
                return ({
                  field: attrData.key,
                  value: (
                    //Case : 檢查是否雙暴，True的話就要直接套用50% / 5%基數
                    (["crit_rate", "crit_dmg"].indexOf(attrData.key) !== -1
                      ? (attrData.key === "crit_rate" ? 0.05 : 0.5)
                      : Number(data?.base?.replace("%", "")) / (data?.base?.includes("%") ? 100 : 1))
                  ),
                  percent: attrData.isPercent,
                  display:
                    //Case : 檢查是否雙暴，True的話就要直接套用50% / 5%基數
                    (["crit_rate", "crit_dmg"].indexOf(attrData.key) !== -1
                      ? (attrData.key === "crit_rate" ? "5.0%" : "50.0%")
                      : data?.base),
                })
              })),
              additions: (char?.properties?.map((data: any) => {
                const attrData = getAttrKeyByPropertyType(data?.property_type);
                return ({
                  field: attrData.key,
                  value: (
                    //Case : 檢查是否雙暴，True的話就要直接套用50% / 5%基數
                    (["crit_rate", "crit_dmg"].indexOf(attrData.key) !== -1
                      ? Number(data?.base?.replace("%", "")) / 100 - (attrData.key === "crit_rate" ? 0.05 : 0.5)
                      : Number(data?.add?.replace("%", "")) / (data?.add?.includes("%") ? 100 : 1))
                  ),
                  percent: attrData.isPercent,
                  display:
                    //Case : 檢查是否雙暴，True的話就要直接套用50% / 5%基數
                    (["crit_rate", "crit_dmg"].indexOf(attrData.key) !== -1
                      ? ((Number(data?.base?.replace("%", "")) / 100 - (attrData.key === "crit_rate" ? 0.05 : 0.5))
                        * 100).toFixed(1).toString() + "%"
                      : data?.add),
                })
              })),
              properties: (char?.properties?.map((data: any) => {
                const attrData = getAttrKeyByPropertyType(data?.property_type);
                return ({
                  field: attrData.key,
                  value: Number(data?.final?.replace("%", "")) / (data?.final?.includes("%") ? 100 : 1),
                  percent: attrData.isPercent,
                  display: data?.final,
                })
              }))
              //pos
            })
          }),
        } as UserCharacters;

        if (UserCharactersIsExist) {
          try {
            db.UserCharacters.doc(uid).update(charsData);
          } catch (e: any) {
            console.log("updated UserCharacters: " + e.message);
          }
        } else {
          try {
            db.UserCharacters.doc(uid).set(charsData);
          } catch (e: any) {
            console.log("create UserCharacters: " + e.message);
          }
        }
      }
    }
    createOrUpdateUserCharacters();
  }, [uid, hsrCharList, hsrInGameInfo]);

  //* 建立或更新用戶混沌回憶資料 (UserMemoryOfChaos)
  useEffect(() => {
    async function createOrUpdateUserMemoryOfChaos() {
      if (uid && moc && mocPrev && hsrPlayerData) {
        // 完整混沌回憶資料
        const mocData = {
          begin_time: moc.begin_time,
          end_time: moc.end_time,
          star_num: moc.star_num,
          battle_num: moc.battle_num,
          max_floor_id: moc.max_floor_id,
          max_floor: moc.all_floor_detail.length || null,
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
        } as UserMemoryOfChaos;
        const mocDoc = db.UserMemoryOfChaos(moc.schedule_id).doc(uid);
        const docIsExist = (await mocDoc.get()).exists;
        if (docIsExist) {
          await mocDoc.update(mocData);
        } else {
          await mocDoc.set(mocData);
        }
        // 單層混沌回憶資料
        mocData?.all_floor_detail
          ?.slice()
          ?.reverse()
          ?.forEach(async (floor, i) => {
            const floorNum = i + 1;
            const floorData = {
              uuid: hsrPlayerData.game_role_id,
              name: hsrPlayerData.nickname,
              floor_id: floor.floor_id,
              floor_num: floorNum,
              round_num: floor.round_num,
              star_num: floor.star_num,
              challenge_time: toTimestamp(floor.layer_1.challenge_time),
              layer_1: {
                characters: floor.layer_1.characters,
              },
              layer_2: {
                characters: floor.layer_2.characters,
              },
            } as UserMemoryOfChaosFloor;
            const floorDoc = db
              .UserMemoryOfChaos(moc.schedule_id, floorNum)
              .doc(uid);
            const docIsExist = (await floorDoc.get()).exists;
            if (docIsExist) {
              floorDoc.update(floorData);
            } else {
              floorDoc.set(floorData);
            }
          });

        // 完整前一次混沌回憶資料
        const mocPrevData = {
          begin_time: mocPrev.begin_time,
          end_time: mocPrev.end_time,
          star_num: mocPrev.star_num,
          battle_num: mocPrev.battle_num,
          max_floor_id: mocPrev.max_floor_id,
          max_floor: mocPrev.all_floor_detail.length || null,
          all_floor_detail: mocPrev.all_floor_detail.map((f: any) => ({
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
        } as UserMemoryOfChaos;

        const mocPrevDoc = db.UserMemoryOfChaos(mocPrev.schedule_id).doc(uid);
        const prevDocIsExist = (await mocPrevDoc.get()).exists;

        if (prevDocIsExist) {
          await mocPrevDoc.update(mocPrevData);
        } else {
          await mocPrevDoc.set(mocPrevData);
        }
        // 單層前一次混沌回憶資料
        mocPrevData?.all_floor_detail
          ?.slice()
          ?.reverse()
          ?.forEach(async (floor, i) => {
            const floorNum = i + 1;
            const floorData = {
              uuid: hsrPlayerData.game_role_id,
              name: hsrPlayerData.nickname,
              floor_id: floor.floor_id,
              floor_num: floorNum,
              round_num: floor.round_num,
              star_num: floor.star_num,
              challenge_time: toTimestamp(floor.layer_1.challenge_time),
              layer_1: {
                characters: floor.layer_1.characters,
              },
              layer_2: {
                characters: floor.layer_2.characters,
              },
            } as UserMemoryOfChaosFloor;
            const floorDoc = db
              .UserMemoryOfChaos(mocPrev.schedule_id, floorNum)
              .doc(uid);
            const docIsExist = (await floorDoc.get()).exists;
            if (docIsExist) {
              floorDoc.update(floorData);
            } else {
              floorDoc.set(floorData);
            }
          });
        // 完整前一次混沌回憶資料
      }
    }
    createOrUpdateUserMemoryOfChaos();
  }, [uid, moc, mocPrev, hsrPlayerData]);

  //* 建立或更新用戶虛構敘事資料 (UserPureFiction)
  useEffect(() => {
    async function createOrUpdatePureFiction() {
      if (uid && pf && pfPrev && hsrPlayerData) {
        // 完整混沌回憶資料
        const pfData = {
          star_num: pf.star_num,
          battle_num: pf.battle_num,
          max_floor_id: pf.max_floor_id,
          max_floor: pf.all_floor_detail.length || null,

          all_floor_detail: pf.all_floor_detail.map((f: any) => ({
            floor_id: f.maze_id,
            round_num: f.round_num,
            star_num: f.star_num,
            score: Number(f.node_1.score) + Number(f.node_2.score),
            layer_1: {
              challenge_time: f.node_1.challenge_time,
              characters: f.node_1.avatars.map((c: any) => ({
                id: c.id,
                level: c.level,
                rank: c.rank,
              })),
              score: Number(f.node_1.score),
            },
            layer_2: {
              challenge_time: f.node_2.challenge_time,
              characters: f.node_2.avatars.map((c: any) => ({
                id: c.id,
                level: c.level,
                rank: c.rank,
              })),
              score: Number(f.node_2.score),
            },
          })),
        };
        const pfDoc = db.UserPureFiction(pf.groups[0].schedule_id).doc(uid);
        const docIsExist = (await pfDoc.get()).exists;
        if (docIsExist) {
          await pfDoc.update(pfData);
        } else {
          await pfDoc.set(pfData);
        }
        // 單層混沌回憶資料
        pfData?.all_floor_detail
          ?.slice()
          ?.reverse()
          ?.forEach(async (floor, i) => {
            const floorNum = i + 1;
            const floorData = {
              uuid: hsrPlayerData.game_role_id,
              name: hsrPlayerData.nickname,
              floor_id: floor.floor_id,
              floor_num: floorNum,
              round_num: floor.round_num,
              star_num: floor.star_num,
              challenge_time: toTimestamp(floor.layer_1.challenge_time),
              score: Number(floor.layer_1.score) + Number(floor.layer_2.score),
              layer_1: {
                characters: floor.layer_1.characters,
                score: floor.layer_1.score,
              },
              layer_2: {
                characters: floor.layer_2.characters,
                score: floor.layer_2.score,
              },
            };
            const floorDoc = db
              .UserPureFiction(pf.groups[0].schedule_id, floorNum)
              .doc(uid);
            const docIsExist = (await floorDoc.get()).exists;
            if (docIsExist) {
              floorDoc.update(floorData);
            } else {
              floorDoc.set(floorData);
            }
          });

        // // 完整前一次混沌回憶資料
        // const pfPrevData = {
        //   star_num: pfPrev.star_num,
        //   battle_num: pfPrev.battle_num,
        //   max_floor_id: pfPrev.max_floor_id,
        //   max_floor: pfPrev.all_floor_detail.length || null,
        //   all_floor_detail: pfPrev.all_floor_detail.map((f: any) => ({
        //     floor_id: f.maze_id,
        //     round_num: f.round_num,
        //     star_num: f.star_num,
        //     layer_1: {
        //       challenge_time: f.node_1.challenge_time,
        //       characters: f.node_1.avatars.map((c: any) => ({
        //         id: c.id,
        //         level: c.level,
        //         rank: c.rank,
        //       })),
        //       score: Number(f.node_1.score),
        //     },
        //     layer_2: {
        //       challenge_time: f.node_2.challenge_time,
        //       characters: f.node_2.avatars.map((c: any) => ({
        //         id: c.id,
        //         level: c.level,
        //         rank: c.rank,
        //       })),
        //       score: Number(f.node_1.score),
        //     },
        //   })),
        // };

        // const pfPrevDataDoc = db.UserPureFiction(pfPrev.groups[1].schedule_id).doc(uid);
        // const prevDocIsExist = (await pfPrevDataDoc.get()).exists;

        // if (prevDocIsExist) {
        //   await pfPrevDataDoc.update(pfPrevData);
        // } else {
        //   await pfPrevDataDoc.set(pfPrevData);
        // }
        // // 單層前一次混沌回憶資料
        // pfPrevData?.all_floor_detail
        //   ?.slice()
        //   ?.reverse()
        //   ?.forEach(async (floor, i) => {
        //     const floorNum = i + 1;
        //     const floorData = {
        //       uuid: hsrPlayerData.game_role_id,
        //       name: hsrPlayerData.nickname,
        //       floor_id: floor.floor_id,
        //       floor_num: floorNum,
        //       round_num: floor.round_num,
        //       star_num: floor.star_num,
        //       challenge_time: toTimestamp(floor.layer_1.challenge_time),
        //       layer_1: {
        //         characters: floor.layer_1.characters,
        //         score: floor.layer_1.score,
        //       },
        //       layer_2: {
        //         characters: floor.layer_2.characters,
        //         score: floor.layer_1.score,
        //       },
        //     };
        //     const floorDoc = db
        //       .UserPureFiction(pfPrev.schedule_id, floorNum)
        //       .doc(uid);
        //     const docIsExist = (await floorDoc.get()).exists;
        //     if (docIsExist) {
        //       floorDoc.update(floorData);
        //     } else {
        //       floorDoc.set(floorData);
        //     }
        //   });
      }
    }
    createOrUpdatePureFiction();
  }, [uid, pf, pfPrev, hsrPlayerData]);

  //* 建立或更新用戶角色練度數據 (UserCharacterScores)
  useEffect(() => {
    async function createOrUpdateUserCharacterScores() {
      if (uid && userCharDetailList) {
        userCharDetailList?.map(async (char: any) => {
          const doc = db.UserCharacterScores(char.id).doc(uid);
          const docIsExist = (await doc.get()).exists;
          const relicScore = getRelicScore(char.id, char.relics);

          const scoreData: any = {
            score: getCharScore(char.id, char),
            rank: char.rank,
            lightcone_id: Number(char.light_cone?.id) || null,
            relic_score: relicScore.totalScore,
          };

          relicScore.eachScore?.map((scoreObj: any) => {
            const [partName, score] = Object.entries(scoreObj)[0];
            if (partName === "Head") scoreData.relic_head_score = score;
            if (partName === "Hands") scoreData.relic_hands_score = score;
            if (partName === "Body") scoreData.relic_body_score = score;
            if (partName === "Shoes") scoreData.relic_shoes_score = score;
            if (partName === "Ball") scoreData.relic_ball_score = score;
            if (partName === "Link") scoreData.relic_link_score = score;
          });

          char.relics?.map((relic: any) => {
            const [setId, countNum] = getSetIdAndCountFromRelicData(relic);
            if (countNum === 1) scoreData.relic_head_set_id = setId;
            if (countNum === 2) scoreData.relic_hands_set_id = setId;
            if (countNum === 3) scoreData.relic_body_set_id = setId;
            if (countNum === 4) scoreData.relic_shoes_set_id = setId;
            if (countNum === 5) scoreData.relic_ball_set_id = setId;
            if (countNum === 6) scoreData.relic_link_set_id = setId;
          });

          if (docIsExist) {
            doc.update(scoreData);
          } else {
            doc.set(scoreData);
          }
        });
      }
    }
    createOrUpdateUserCharacterScores();
  }, [uid, userCharDetailList]);


  return (
    <>
      <Pressable style={{ flex: 1 }} className="overflow-hidden">
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
            <View style={{ flex: 1, flexDirection: "column", }}>
              <View style={{ flex: 1 }}>
                <Menu />
              </View>
              <View style={{ width: "100%", height: (48 + dynamicHeightBottomBar) }}>
                <DonateTab />
              </View>
            </View>
          </LinearGradient>
        </View>
      </Pressable>
      <SelectLanguageAtFirstTime key={"selectLanguageAtFirstTime"} />
    </>
  );
}

function toTimestamp(obj: any) {
  // Destructure the object to extract year, month, day, hour, and minute
  const { year, month, day, hour, minute } = obj;

  // Create a new Date instance - remember JavaScript months are 0-indexed
  const date = new Date(year, month - 1, day, hour, minute);

  // Return the Unix timestamp (in milliseconds)
  return date.getTime();
}

function getIconStrById(id: number) {
  let tmpId = id;
  while (tmpId > 10000) {
    tmpId -= 10000;
  }
  return tmpId;
}

function getPromotionByLevel(level: number) {
  if (level > 70) { return 6 }
  if (level > 60) { return 5 }
  if (level > 50) { return 4 }
  if (level > 40) { return 3 }
  if (level > 30) { return 2 }
  if (level > 20) { return 1 }
  return 0
}