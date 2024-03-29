import { GestureResponderEvent, Pressable } from "react-native";
import React from "react";
import { Image } from "expo-image";
import { animated, useSpring } from "@react-spring/native";
import useCharData from "../../../../../context/CharacterData/hooks/useCharData";
import CharacterSoul from "../../../../../../assets/images/images_map/characterSoul";

const eidolonBorder6 = require("../../../../../../assets/images/character_eidolon_border/eidolon_border_6.svg");

export default function Eidolon6({
  onPress,
  selected,
}: {
  selected: boolean;
  onPress: (e: GestureResponderEvent) => void;
}) {
  const { charId } = useCharData();
  const charEidolon6 = CharacterSoul[charId]?.eidolon6;

  const animation = useSpring({
    opacity: selected ? 1 : 0, // 更平滑的透明度过渡
    config: { duration: 350, easing: (t) => t * (2 - t) }, // 平滑的缓动函数
  });

  const animationZ = useSpring({
    zIndex: selected ? 60 : 40,
    shadowOpacity: selected ? 0.5 : 0, // 添加阴影效果
  });

  return (
    <AnimatedPressable
      onPress={onPress}
      style={animationZ}
      className="absolute w-[150px] h-[150px] top-[120px]"
    >
      <Image cachePolicy="none" source={charEidolon6} className="w-full h-full absolute" />
      <AnimatedImage
        contentFit="contain"
        source={eidolonBorder6}
        className="w-full h-full absolute"
        style={{
          transform: [{ scale: 0.97 }, { translateY: 7 }],
          ...animation,
        }}
      />
    </AnimatedPressable>
  );
}

const AnimatedPressable = animated(Pressable);
const AnimatedImage = animated(Image);
