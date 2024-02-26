import { View, Pressable } from "react-native";
import React from "react";
import { ExpoImage } from "../../../../../types/image";
import { GestureResponderEvent } from "react-native-modal";
import { animated, useSpring } from "@react-spring/native";
import { Shadow } from "react-native-shadow-2";
import { Image } from "expo-image";

export default React.memo(
  ({
    left,
    top,
    icon,
    selected,
    onPress,
  }: {
    left: number;
    top: number;
    icon: ExpoImage;
    selected: boolean;
    onPress: (e: GestureResponderEvent) => void;
  }) => {
    const animation = useSpring({
      borderColor: selected ? "#FCBC62" : "#31B5FF60",
      config: { tension: 170 * 5, clamp: true },
    });

    return (
      <Pressable
        onPress={onPress}
        style={{
          position: "absolute",
          left,
          top,
        }}
      >
        <Shadow
          style={{ overflow: "hidden", borderRadius: 100 }}
          distance={4}
          startColor="#31B5FF60"
        >
          <AnimatedView
            style={{
              justifyContent: "center",
              alignItems: "center",
              ...animation,
            }}
            className="w-[60px] h-[60px] bg-[#333] rounded-full border-2"
          >
            <Image cachePolicy="none" source={icon} className="w-9 h-9" />
          </AnimatedView>
        </Shadow>
      </Pressable>
    );
  }
);
const AnimatedView = animated(View);
