import { View, Text, ScrollView, Pressable } from "react-native";
import React, { MutableRefObject, useState } from "react";
import SettingGroup from "../../SettingScreen/SettingGroup/SettingGroup";
import SettingItem from "../../SettingScreen/SettingGroup/SettingItem/SettingItem";
import { Image } from "expo-image";
import useUser from "../../../firebase/hooks/User/useUser";
import useMyFirebaseUid from "../../../firebase/hooks/FirebaseUid/useMyFirebaseUid";
import useCopyToClipboard from "../../../hooks/useCopyToClipboard";
import { useQuery } from "react-query";
import db from "../../../firebase/db";
import useUserInviteCode from "../../../firebase/hooks/User/useUserInviteCode";
import Animated from "react-native-reanimated";

const emojis = [
  require("./images/haha.png"),
  require("./images/haha2.png"),
  require("./images/haha3.png"),
];
type Props = {
  scrollViewRef : MutableRefObject<ScrollView | Animated.ScrollView | undefined | null>;
}

export default function InviteList(props : Props) {
  const firebaseUID = useMyFirebaseUid();
  const inviteCode = useUserInviteCode(firebaseUID);

  const [emojiIndex, setEmojiIndex] = useState(Math.floor(Math.random() * 3));

  const handleCopy = useCopyToClipboard();

  return (
    //@ts-ignore
    <ScrollView className="z-30 h-screen py-[110px]  pb-0" ref={props.scrollViewRef}>
      <View style={{ gap: 20 }} className="pb-48 px-4">
        <SettingGroup title={"我的邀請碼"}>
          <SettingItem
            type="none"
            title={inviteCode || ""}
            content={"複製"}
            onNavigate={() => {
              handleCopy(inviteCode || "");
            }}
          />
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
      {/* 表情 */}
      <Pressable
        onPress={() => {
          setEmojiIndex(Math.floor(Math.random() * 3));
        }}
      >
        <Image cachePolicy="none"
          source={emojis[emojiIndex]}
          className="w-40 h-40 absolute -right-0 bottom-0"
        />
      </Pressable>
    </ScrollView>
  );
}
