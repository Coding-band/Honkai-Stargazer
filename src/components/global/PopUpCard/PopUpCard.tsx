import { View, Text, TouchableOpacity, Platform } from "react-native";
import React from "react";
import BlurView from "../BlurView/BlurView";
import { Image } from "expo-image";
import { HtmlText } from "@e-mine/react-native-html-text";

type Props = {
  title: string;
  content: React.ReactNode;
  onClose?: () => void;
};

export default function PopUpCard(props: Props) {
  return (
    <BlurView
      intensity={40}
      disable={Platform.OS==="android"}
      style={{
        backgroundColor: (Platform.OS === "android" ?  "#d2d6db" :  "#F4F4FFD4"),
      }}
      className="w-full rounded-[4px] rounded-tr-[24px] overflow-hidden"
    >
      <View
        className="w-full px-4 py-3"
        style={{
          flexDirection: "row",
          justifyContent: "space-between",
          alignItems: "center",
        }}
      >
        <Text className="font-[HY65] text-[20px] leading-8">{props.title}</Text>
        <TouchableOpacity
          onPress={() => {
            props.onClose && props.onClose();
          }}
        >
          <Image cachePolicy="none"
            style={{ width: 40, height: 40 }}
            source={require("../../../../assets/icons/CloseBlack.svg")}
          />
        </TouchableOpacity>
      </View>
      <View className="w-full px-4">
        <View className="w-full h-[2px] bg-[#00000010]"></View>
      </View>
      <View>
        {typeof props.content === "string" ? (
          <HtmlText
            style={{
              color: "#666",
              fontSize: 14,
              fontFamily: "HY65",
              lineHeight: 20,
              paddingHorizontal: 16,
              paddingVertical: 12,
            }}
          >
            {props.content}
          </HtmlText>
        ) : (
          props.content
        )}
      </View>
    </BlurView>
  );
}
