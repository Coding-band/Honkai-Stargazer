import React, { useContext } from "react";
import Animated, {
  SharedValue,
  useAnimatedStyle,
  withSpring,
} from "react-native-reanimated";
import { Image } from "expo-image";
import CharacterContext from "../../../../context/CharacterContext";
import { Dimensions, Platform } from "react-native";

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
        style={{
          width: Dimensions.get("window").width,
          height: (Dimensions.get("window").height * 3) / 4,
          transform: [{ scale: Platform.OS === "android" ? 1.2 : 1 }],
        }}
        source={charData?.imageFull}
        contentFit={Platform.OS === "android" ? "none" : "contain"}
      />
    </Animated.View>
  );
}
