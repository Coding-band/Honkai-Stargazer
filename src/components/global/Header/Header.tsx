import { useNavigation } from "@react-navigation/native";
import BlurView from "../BlurView/BlurView";
import { Image } from "expo-image";
import { IconProps } from "phosphor-react-native";
import React from "react";
import { Pressable, Text, TouchableOpacity, View } from "react-native";
import { GestureResponderEvent } from "react-native-modal";
import { Shadow } from "react-native-shadow-2";

const CloseBtn = require("../../../../assets/icons/Close.svg");
const BackBtn = require("../../../../assets/icons/Back.svg");

type Props = {
  Icon: (e: IconProps) => React.JSX.Element;
  children: string;
  leftBtn?: "close" | "back";
  onPress?: (e: GestureResponderEvent) => void;
  onBack?: () => void;
};

export default function Header(props: Props) {
  const Icon = props.Icon;

  const navigation = useNavigation();

  const handleClose = () => {
    props.onBack && props.onBack();
    navigation.goBack();
  };

  return (
    <Pressable
      onPress={props.onPress}
      style={{ position: "absolute", width: "100%", zIndex: 50 }}
    >
      <BlurView
        style={{ height: 110, width: "100%" }}
        intensity={20}
        tint="dark"
      >
        <Shadow
          style={{ width: "100%", height: 110 }}
          distance={20}
          startColor={"rgba(0, 0, 0, 0.25)"}
        >
          <View
            className="w-full h-[110px]"
            style={{
              backgroundColor: "rgba(255, 255, 255, 0.20)",
            }}
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
            <View
              className="pb-3"
              style={{
                flex: 1,
                alignItems: "center",
                justifyContent: "flex-end",
                gap: 4,
              }}
            >
              <Icon size={32} color="white" weight="fill" />
              <View
                style={{ flexDirection: "row", alignItems: "center", gap: 13 }}
              >
                <View
                  style={{ backgroundColor: "#ffffff40", height: 2, width: 50 }}
                ></View>
                <Text
                  className="text-white text-[16px]"
                  style={{ fontFamily: "HY65" }}
                >
                  {props.children || ""}
                </Text>
                <View
                  style={{ backgroundColor: "#ffffff40", height: 2, width: 50 }}
                ></View>
              </View>
            </View>
          </View>
        </Shadow>
      </BlurView>
    </Pressable>
  );
}
