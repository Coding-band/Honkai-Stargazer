import { View, Text, Platform } from "react-native";
import React, { useEffect, useRef, useState } from "react";
import * as Notifications from "expo-notifications";
import * as Device from "expo-device";
import Constants from "expo-constants";
import useFirebaseUid from "../firebase/hooks/useFirebaseUid";
import db from "../firebase/db";
import UserToken from "../firebase/models/UserTokens";
import { pushExpoNotiType } from "./constant/pushExpoNotiType";
import { useNavigation } from "@react-navigation/native";
import { SCREENS } from "../constant/screens";

Notifications.setNotificationHandler({
  handleNotification: async () => ({
    shouldShowAlert: true,
    shouldPlaySound: false,
    shouldSetBadge: false,
  }),
});

export default function NotificationWrapper({
  children,
  onResponseReceived,
}: {
  children: React.ReactNode;
  onResponseReceived: (data: any, type: any) => void;
}) {
  //* expo push token
  const [expoPushToken, setExpoPushToken] = useState<string>();
  useEffect(() => {
    registerForPushNotificationsAsync().then((token) => {
      setExpoPushToken(token);
    });
  }, []);

  //* sync to firebase
  const uid = useFirebaseUid();
  // 建立或更新用戶數據 (User)
  useEffect(() => {
    async function createOrUpdateUser() {
      if (uid && expoPushToken) {
        const UserTokensIsExist = (await db.UserTokens.doc(uid).get()).exists;
        if (UserTokensIsExist) {
          try {
            await db.UserTokens.doc(uid).update({
              expo_push_token: expoPushToken,
            } as UserToken);
          } catch (e: any) {
            console.log("update UserTokens: " + e.message);
          }
        } else {
          try {
            await db.UserTokens.doc(uid).set({
              expo_push_token: expoPushToken,
            } as UserToken);
          } catch (e: any) {
            console.log("create UserTokens: " + e.message);
          }
        }
      }
    }
    createOrUpdateUser();
  }, [uid, expoPushToken]);

  //* 點擊通知做處理 (如跳轉頁面)
  const responseListener = useRef<Notifications.Subscription>();
  useEffect(() => {
    responseListener.current =
      Notifications.addNotificationResponseReceivedListener((response) => {
        const notiData = response.notification.request.content.data;
        const notiType = notiData.type;
        onResponseReceived(notiData, notiType);
      });

    return () => {
      if (responseListener.current) {
        Notifications.removeNotificationSubscription(responseListener.current);
      }
    };
  }, []);

  return children;
}

async function registerForPushNotificationsAsync() {
  let token;

  if (Platform.OS === "android") {
    await Notifications.setNotificationChannelAsync("default", {
      name: "default",
      importance: Notifications.AndroidImportance.MAX,
      vibrationPattern: [0, 250, 250, 250],
      lightColor: "#FF231F7C",
    });
  }

  if (Device.isDevice) {
    const { status: existingStatus } =
      await Notifications.getPermissionsAsync();
    let finalStatus = existingStatus;
    if (existingStatus !== "granted") {
      const { status } = await Notifications.requestPermissionsAsync();
      finalStatus = status;
    }
    if (finalStatus !== "granted") {
      alert("Failed to get push token for push notification!");
      return;
    }
    // Learn more about projectId:
    // https://docs.expo.dev/push-notifications/push-notifications-setup/#configure-projectid
    token = (
      await Notifications.getExpoPushTokenAsync({
        projectId: Constants.expoConfig?.extra?.eas.projectId,
      })
    ).data;
    // console.log(token);
  } else {
    alert("Must use physical device for Push Notifications");
  }

  return token;
}
