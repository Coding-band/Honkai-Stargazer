import { View, Text } from "react-native";
import React from "react";
import Header2 from "../../global/Header2/Header2";
import { TouchableOpacity } from "react-native";
import { Image } from "expo-image";

export default function UserInfo() {
  return (
    <View>
      <Header2 rightBtn={<ShareBtn />} />
    </View>
  );
}

const ShareBtn = () => (
  <TouchableOpacity onPress={() => {}}>
    <Image
      style={{ width: 40, height: 40 }}
      source={require("../../../../assets/icons/Share.svg")}
    />
  </TouchableOpacity>
);
