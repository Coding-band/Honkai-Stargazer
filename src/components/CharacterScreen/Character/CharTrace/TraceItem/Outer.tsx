import { GestureResponderEvent, Pressable, View } from "react-native";
import React from "react";
import { ExpoImage } from "../../../../../types/image";
import { Image } from "expo-image";

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
      className="w-16 h-16 bg-[#666] rounded-full"
    >
      <Image source={icon} className="w-12 h-12" />
    </Pressable>
  )
);
