import { View, Text } from "react-native";
import React, { useContext, useEffect, useState } from "react";
import Animated, {
  SharedValue,
  useAnimatedStyle,
  withSpring,
} from "react-native-reanimated";
import { Image } from "expo-image";
import CharacterContext from "../../../../context/CharacterContext";
import useDelayLoad from "../../../../hooks/useDelayLoad";

type Props = {
  scrollHandler: SharedValue<number>;
  charContainerHeight: number;
};

export default function CharImageFull(props: Props) {
  const charData = useContext(CharacterContext);

  const loaded = useDelayLoad(100);

  const imageAnimatedStyles = useAnimatedStyle(() => {
    const height = props.charContainerHeight;
    const offsetY = props.scrollHandler.value;

    return { opacity: 1 - (offsetY / height) * 14 || 0 };
  });

  return (
    <Animated.View style={imageAnimatedStyles}>
      {loaded && (
        <Image
          transition={200}
          style={{ width: 500, height: 690 }}
          source={charData?.imageFull}
          contentFit="contain"
        />
      )}
    </Animated.View>
  );
}
