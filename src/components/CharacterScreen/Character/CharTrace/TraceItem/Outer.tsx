import { GestureResponderEvent, Pressable, View } from "react-native";
import React from "react";
import { ExpoImage } from "../../../../../types/image";
import { Image } from "expo-image";
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
        className="absolute w-16 h-16 bg-[#666] rounded-full"
      >
        <Image cachePolicy="none" source={icon} className="w-12 h-12" />
      </AnimatedPressable>
    );
  }
);

const AnimatedPressable = animated(Pressable);
