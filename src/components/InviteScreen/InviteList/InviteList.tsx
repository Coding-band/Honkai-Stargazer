import { View, Text, ScrollView } from "react-native";
import React, { useState } from "react";
import SettingGroup from "../../SettingScreen/SettingGroup/SettingGroup";
import SettingItem from "../../SettingScreen/SettingGroup/SettingItem/SettingItem";
import { Image } from "expo-image";
import useUser from "../../../firebase/hooks/User/useUser";
import useMyFirebaseUid from "../../../firebase/hooks/FirebaseUid/useMyFirebaseUid";

export default function InviteList() {
  const firebaseUID = useMyFirebaseUid();
  const inviteCode = useUser(firebaseUID || "").data?.invite_code;

  const [emoji, setEmoji] = useState(
    [
      require("./images/haha.png"),
      require("./images/haha2.png"),
      require("./images/haha3.png"),
    ][Math.floor(Math.random() * 3)]
  );

  return (
    <ScrollView className="z-30 h-screen py-[110px]  pb-0">
      <View style={{ gap: 20 }} className="pb-48 px-4">
        <SettingGroup title={"我的邀請碼"}>
          <SettingItem type="none" title={inviteCode || ""} content={"複製"} />
        </SettingGroup>
        <SettingGroup title={"使用我的"}>
          <SettingItem
            type="navigation"
            title={"3707258630(Asia)"}
            content={"主頁"}
          />
          <SettingItem
            type="navigation"
            title={"3707258630(Asia)"}
            content={"主頁"}
          />
          <SettingItem
            type="navigation"
            title={"3707258630(Asia)"}
            content={"主頁"}
          />
          <SettingItem
            type="navigation"
            title={"3707258630(Asia)"}
            content={"主頁"}
          />
          <SettingItem
            type="navigation"
            title={"3707258630(Asia)"}
            content={"主頁"}
          />
        </SettingGroup>
        <SettingGroup title={"我使用的"}>
          <SettingItem
            type="navigation"
            title={"3707258630(Asia)"}
            content={"主頁"}
          />
        </SettingGroup>
        <View className="mt-6">
          <Text className="text-text font-[HY65] text-[14px]">
            拥有5名及以上用户使用你的邀请码后你即可永久获得与捐赠完全相同的回报。
          </Text>
        </View>
      </View>
      <Image source={emoji} className="w-40 h-40 absolute -right-0 bottom-0" />
    </ScrollView>
  );
}
