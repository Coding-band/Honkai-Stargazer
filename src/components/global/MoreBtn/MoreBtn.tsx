import React from "react";
import {
  GestureResponderEvent,
  Pressable,
  Text,
  TouchableNativeFeedback,
  TouchableOpacity,
  View,
} from "react-native";

type Props = {
  onPress?: (e: GestureResponderEvent) => void;
};

export default function MoreBtn(props: Props) {
  return (
    <TouchableOpacity activeOpacity={0.65} onPress={props.onPress}>
      <View
        className="w-[45px] h-[23px] bg-[#dddddd] rounded-[49px] flex justify-center items-center"
        style={{
          shadowColor: "#000",
          shadowOpacity: 0.25,
          shadowOffset: { width: 0, height: 4 },
          shadowRadius: 16,
          elevation: 16,
        }}
      >
        <View className="fixed w-[13px] h-[3px] top-0 left-0">
          <View className="left-0 absolute w-[3px] h-[3px] top-0 bg-[#1e1e1e] rounded-[1.5px]" />
          <View className="left-[5px] absolute w-[3px] h-[3px] top-0 bg-[#1e1e1e] rounded-[1.5px]" />
          <View className="left-[10px] absolute w-[3px] h-[3px] top-0 bg-[#1e1e1e] rounded-[1.5px]" />
        </View>
      </View>
    </TouchableOpacity>
  );
}
