import { Pressable, View } from "react-native";
import React from "react";
import { ExpoImage } from "../../../../../types/image";
import { Image } from "expo-image";
import { GestureResponderEvent } from "react-native";
import { animated, useSpring } from "@react-spring/native";

export default React.memo(
  ({
    left,
    top,
    icon,
    onPress,
    selected,
  }: {
    left: number;
    top: number;
    icon: ExpoImage;
    onPress: (e: GestureResponderEvent) => void;
    selected: boolean;
  }) => {
    const animation = useSpring({
      borderColor: selected ? "#FFF" : "#666",
      config: { tension: 170 * 5, clamp: true },
    });

    return (
      <AnimatedPressable
        onPress={onPress}
        style={[
          {
            left,
            top,
            justifyContent: "center",
            alignItems: "center",
            borderWidth: 2,
          },
          ,
          animation,
        ]}
        className="absolute w-8 h-8 bg-[#666] rounded-full"
      >
        <Image source={icon} className="w-6 h-6" />
      </AnimatedPressable>
    );
  }
);

const AnimatedPressable = animated(Pressable);
