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
    // const height = props.charContainerHeight;
    // const offsetY = props.scrollHandler.value;

    // return { opacity: 1 - (offsetY / height) * 12 || 0 };
    if (props.scrollHandler.value > 0) {
      return {
        opacity: withSpring(0),
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
        style={{ width: 500, height: 690, transform: [{ scale: 1.2 }] }}
        source={charData?.imageFull}
        contentFit="scale-down"
      />
    </Animated.View>
  );
}
