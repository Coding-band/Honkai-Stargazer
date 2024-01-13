import { useNavigation } from "@react-navigation/native";
import { Image } from "expo-image";
import React from "react";
import { Pressable, Text, TouchableOpacity, View } from "react-native";
import { GestureResponderEvent } from "react-native-modal";

const CloseBtn = require("../../../../assets/icons/Close.svg");
const BackBtn = require("../../../../assets/icons/Back.svg");

type Props = {
  children?: any;
  leftBtn?: "close" | "back";
  rightBtn?: React.ReactNode;
  onPress?: (e: GestureResponderEvent) => void;
  onBack?: () => void;
};

export default function Header2(props: Props) {
  const navigation = useNavigation();

  const handleClose = () => {
    props.onBack && props.onBack();
    navigation.goBack();
  };

  return (
    <Pressable
      className="absolute w-full z-50"
      onPress={props.onPress}
      pointerEvents="box-none"
    >
      <View
        className="w-full h-[110px]"
        style={{ alignItems: "center", justifyContent: "flex-end" }}
      >
        {/* 左邊叉叉 */}
        <TouchableOpacity
          onPress={handleClose}
          className="absolute left-[17px] bottom-[19px] z-50"
        >
          <Image
            style={{ width: 40, height: 40 }}
            source={props.leftBtn === "back" ? BackBtn : CloseBtn}
          />
        </TouchableOpacity>
        {/* 中間主體 */}
        <View className="translate-y-[-19px]">{props.children}</View>
        {/* 右邊按鈕 */}
        <View className="absolute right-[17px] bottom-[19px] z-50">
          {props.rightBtn}
        </View>
      </View>
    </Pressable>
  );
}
