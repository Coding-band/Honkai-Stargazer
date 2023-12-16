import { View, Text, ScrollView } from "react-native";
import React from "react";
import { BlurView } from "expo-blur";
import SettingGroup from "../SettingGroup/SettingGroup";
import SettingItem from "../SettingItem/SettingItem";

export default function SettingList() {
  return (
    <ScrollView
      style={{
        paddingVertical: 127,
        paddingHorizontal: 17,
        paddingBottom: 0,
      }}
      className="z-30 h-screen"
    >
      <View style={{ gap: 20 }} className="pb-40">
        <SettingGroup>
          <SettingItem />
        </SettingGroup>
        <SettingGroup>
          <SettingItem />
          <SettingItem />
        </SettingGroup>
        <SettingGroup>
          <SettingItem />
          <SettingItem />
          <SettingItem />
        </SettingGroup>
        <SettingGroup>
          <SettingItem />
          <SettingItem />
          <SettingItem />
        </SettingGroup>
        <SettingGroup>
          <SettingItem />
          <SettingItem />
          <SettingItem />
        </SettingGroup>
        <SettingGroup>
          <SettingItem />
          <SettingItem />
          <SettingItem />
        </SettingGroup>
      </View>
    </ScrollView>
  );
}
