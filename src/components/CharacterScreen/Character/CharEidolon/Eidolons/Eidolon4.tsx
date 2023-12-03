import { View, Text, GestureResponderEvent, Pressable } from "react-native";
import React, { useContext } from "react";
import { Image } from "expo-image";
import CharacterContext from "../../../../../context/CharacterContext";
import { Chacracter } from "../../../../../../assets/images/images_map/images_map";
import { CharacterName } from "../../../../../types/character";
import { animated, useSpring } from "@react-spring/native";

const eidolonBorder4 = require("../../../../../../assets/images/character_eidolon_border/eidolon_border_4.svg");

export default function Eidolon4({
  onPress,
  selected,
}: {
  selected: boolean;
  onPress: (e: GestureResponderEvent) => void;
}) {
  const charData = useContext(CharacterContext);
  const charEidolon4 = Chacracter[charData?.id as CharacterName]?.eidolon4;

  const animation = useSpring({
    opacity: selected ? 1 : 0,
    config: { tension: 170 *2, clamp: true },
  });

  const animationZ = useSpring({
    zIndex: selected ? 50 : 0,
  });
  
  return (
    <AnimatedPressable
      onPress={onPress}
      style={animationZ}
      className="absolute w-[150px] h-[150px] top-[114px] left-[200px]"
    >
      <Image source={charEidolon4} className="w-full h-full absolute" />
      <AnimatedImage
        contentFit="contain"
        source={eidolonBorder4}
        className="w-full h-full absolute"
        style={{
          transform: [{ scale: 0.94 }, { translateY: 3 }],
          ...animation,
        }}
      />
    </AnimatedPressable>
  );
}

const AnimatedPressable = animated(Pressable);
const AnimatedImage = animated(Image);
