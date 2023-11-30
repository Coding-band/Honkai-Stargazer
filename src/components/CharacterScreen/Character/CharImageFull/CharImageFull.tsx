import { View, Text } from "react-native";
import React, { useContext } from "react";
import Animated, {
  SharedValue,
  useAnimatedStyle,
  withSpring,
} from "react-native-reanimated";
import { Image } from "expo-image";
import CharacterContext from "../../../../context/CharacterContext";

type Props = {
  scrollHandler: SharedValue<number>;
  charContainerHeight: number;
};

export default function CharImageFull(props: Props) {
  const charData = useContext(CharacterContext);

  const imageAnimatedStyles = useAnimatedStyle(() => {
    const height = props.charContainerHeight;
    const offsetY = props.scrollHandler.value;

    return { opacity: 1 - (offsetY / height) * 3.5 || 0 };
  });

  return (
    <Animated.View style={imageAnimatedStyles}>
      <Image
        transition={200}
        style={{ width: 300, height: 692 }}
        source={charData?.imageFull}
      />
    </Animated.View>
  );
}
