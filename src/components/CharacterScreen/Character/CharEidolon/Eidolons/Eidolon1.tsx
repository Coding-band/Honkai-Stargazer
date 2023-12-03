import { View, Text, GestureResponderEvent, Pressable } from "react-native";
import React, { useContext } from "react";
import { Image } from "expo-image";
import CharacterContext from "../../../../../context/CharacterContext";
import { Chacracter } from "../../../../../../assets/images/images_map/images_map";
import { CharacterName } from "../../../../../types/character";
import { animated, useSpring } from "@react-spring/native";

const eidolonBorder1 = require("../../../../../../assets/images/character_eidolon_border/eidolon_border_1.svg");

export default function Eidolon1({
  onPress,
  selected,
}: {
  selected: boolean;
  onPress: (e: GestureResponderEvent) => void;
}) {
  const charData = useContext(CharacterContext);
  const charEidolon1 = Chacracter[charData?.id as CharacterName]?.eidolon1;

  const animation = useSpring({
    opacity: selected ? 1 : 0,
    config: { tension: 170 * 2, clamp: true },
  });

  const animationZ = useSpring({
    zIndex: selected ? 50 : 30,
  });

  return (
    <AnimatedPressable
      onPress={onPress}
      style={animationZ}
      className="absolute w-[150px] h-[150px] left-[3px] top-[22px]"
    >
      <Image source={charEidolon1} className="w-full h-full absolute " />
      <AnimatedImage
        contentFit="contain"
        source={eidolonBorder1}
        className="w-full h-full absolute"
        style={{ transform: [{ scale: 0.87 }], ...animation }}
      />
    </AnimatedPressable>
  );
}

const AnimatedPressable = animated(Pressable);
const AnimatedImage = animated(Image);
