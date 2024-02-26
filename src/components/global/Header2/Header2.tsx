import { useNavigation } from "@react-navigation/native";
import { Image } from "expo-image";
import React from "react";
import { Pressable, Text, TouchableOpacity, View } from "react-native";
import { GestureResponderEvent } from "react-native-modal";
import Animated from "react-native-reanimated";
import BlurView from "../BlurView/BlurView";
import { dynamicHeightHeader } from "../../../constant/ui";

const CloseBtn = require("../../../../assets/icons/Close.svg");
const BackBtn = require("../../../../assets/icons/Back.svg");

type Props = {
  children?: any;
  leftBtn?: "close" | "back";
  rightBtn?: React.ReactNode;
  onPress?: (e: GestureResponderEvent) => void;
  onBack?: () => void;
  style?: any;
};

export default function Header2(props: Props) {
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
        className={dynamicHeightHeader}
        style={{ alignItems: "center", justifyContent: "flex-end" }}
      >
        {/* 左邊叉叉 */}

        <TouchableOpacity
          activeOpacity={0.35}
          onPress={handleClose}
          className="absolute left-[17px] bottom-[14px] rounded-full overflow-hidden z-50"
        >
          <BlurView
            className="w-[44px] h-[44px] bg-[#FFFFFF20]"
            style={{ justifyContent: "center", alignItems: "center" }}
          >
            <Image cachePolicy="none"
              style={{ width: 40, height: 40 }}
              source={props.leftBtn === "back" ? BackBtn : CloseBtn}
            />
          </BlurView>
        </TouchableOpacity>
        {/* 中間主體 */}
        <View className="translate-y-[-19px]">{props.children}</View>
        {/* 右邊按鈕 */}
        {props.rightBtn && (
          <View className="absolute right-[17px] bottom-[14px] rounded-full overflow-hidden z-50">
            <BlurView
              className="w-[44px] h-[44px] bg-[#FFFFFF20]"
              style={{ justifyContent: "center", alignItems: "center" }}
            >
              {props.rightBtn}
            </BlurView>
          </View>
        )}
      </View>
    </AnimatedPressable>
  );
}

const AnimatedPressable = Animated.createAnimatedComponent(Pressable);
