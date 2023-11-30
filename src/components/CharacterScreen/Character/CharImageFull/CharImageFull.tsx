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
};

export default function CharImageFull(props: Props) {
  const charData = useContext(CharacterContext);

  const imageAnimatedStyles = useAnimatedStyle(() => {
    if (props.scrollHandler.value > 0) {
      return {
        opacity: withSpring(0.3),
      };
    } else {
      return {
        opacity: withSpring(1),
      };
    }
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
