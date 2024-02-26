import { useNavigation } from "@react-navigation/native";
import { Image } from "expo-image";
import React from "react";
import { Pressable, Text, TouchableOpacity, View } from "react-native";
import { GestureResponderEvent } from "react-native-modal";
import Animated from "react-native-reanimated";
import BlurView from "../BlurView/BlurView";

const CloseBtn = require("../../../../assets/icons/Close.svg");
const BackBtn = require("../../../../assets/icons/Back.svg");

type Props = {
  children?: any;
  leftBtn?: "close" | "back";
  rightBtn?: any;
  onPress?: (e: GestureResponderEvent) => void;
  onBack?: () => void;
  style?: any;
};

export default function HeaderAlpha(props: Props) {
  const navigation = useNavigation();

  const handleClose = () => {
    props.onBack && props.onBack();
    navigation.goBack();
  };

  return (
    <AnimatedPressable
      className="absolute w-full z-50"
      onPress={props.onPress}
      pointerEvents="box-none"
      style={props.style}
    >
      <View
        className="w-full h-[110px]"
        style={{ alignItems: "center", justifyContent: "flex-end" }}
      >
        {/* 左邊叉叉 */}
        <TouchableOpacity
          activeOpacity={0.35}
          onPress={handleClose}
          className="absolute left-[17px] bottom-[14px] rounded-full overflow-hidden z-50"
        >
          <Image cachePolicy="none"
              style={{ width: 40, height: 40 }}
              source={props.leftBtn === "back" ? BackBtn : CloseBtn}
            />
        </TouchableOpacity>
        {/* 中間主體 */}
        <View className="translate-y-[-19px]">{props.children}</View>
        {/* 右邊按鈕 */}
        {
          <View
          className="absolute right-[17px] bottom-[14px] rounded-full overflow-hidden z-50"
            style={{ alignItems: "center", justifyContent: "flex-end" }}
          >{props.rightBtn}</View>
        }
      </View>
    </AnimatedPressable>
  );
}

const AnimatedPressable = Animated.createAnimatedComponent(Pressable);
