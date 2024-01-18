import { View, Text, ScrollView } from "react-native";
import React from "react";
import SettingGroup from "../../SettingScreen/SettingGroup/SettingGroup";
import SettingItem from "../../SettingScreen/SettingGroup/SettingItem/SettingItem";

export default function InviteList() {
  return (
    <ScrollView
      style={{
        paddingVertical: 110,
        paddingHorizontal: 17,
        paddingBottom: 0,
      }}
      className="z-30 h-screen"
    >
      <View style={{ gap: 20 }} className="pb-48">
        <SettingGroup title={"我的邀請碼"}>
          <SettingItem type="none" title={"3CXYB5879A"} content={"複製"} />
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
        <View className="mt-6">
          <Text className="text-text font-[HY65] text-[16px]">
            拥有5名及以上用户使用你的邀请码后你即可永久获得与捐赠完全相同的回报
          </Text>
        </View>
      </View>
    </ScrollView>
  );
}
