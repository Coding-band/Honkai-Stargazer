import { Pressable, View } from "react-native";
import React from "react";
import { ExpoImage } from "../../../../../types/image";
import { Image } from "expo-image";
import { GestureResponderEvent } from "react-native";

export default React.memo(
  ({ left, top, icon, onPress }: { left: number; top: number; icon: ExpoImage, onPress: (e: GestureResponderEvent) => void; }) => (
    <Pressable
      onPress={onPress}
      style={{
        position: "absolute",
        left,
        top,
        justifyContent: "center",
        alignItems: "center",
      }}
      className="w-8 h-8 bg-[#666] rounded-full"
    >
      <Image source={icon} className="w-6 h-6" />
    </Pressable>
  )
);
