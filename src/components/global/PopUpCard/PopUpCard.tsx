import { View, Text, TouchableOpacity } from "react-native";
import React from "react";
import { BlurView } from "expo-blur";
import { Image } from "expo-image";

type Props = {
  title: string;
  content: React.ReactNode;
  onClose?: () => void;
};

export default function PopUpCard(props: Props) {
  return (
    <BlurView
      intensity={250}
      style={{ backgroundColor: "rgba(243, 249, 255, 0.80)" }}
      className="w-full rounded-[4px] rounded-tr-[24px] overflow-hidden"
    >
      <View
        className="w-full p-4"
        style={{
          flexDirection: "row",
          justifyContent: "space-between",
          alignItems: "center",
        }}
      >
        <Text className="font-[HY65] text-[20px]">{props.title}</Text>
        <TouchableOpacity
          onPress={() => {
            props.onClose && props.onClose();
          }}
        >
          <Image
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
          <Text className="text-[#666] text-[14px] font-[HY65] leading-5 px-4 pb-2 pt-3">
            {props.content}
          </Text>
        ) : (
          props.content
        )}
      </View>
    </BlurView>
  );
}
