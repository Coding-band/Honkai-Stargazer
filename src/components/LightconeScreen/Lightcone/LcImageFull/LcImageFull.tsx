import React from "react";
import Animated, {
  SharedValue,
  useAnimatedStyle,
  withSpring,
} from "react-native-reanimated";
import { Image } from "expo-image";
import { Dimensions, View } from "react-native";
import useLcData from "../../../../context/LightconeData/hooks/useLcData";

type Props = {
  scrollHandler: SharedValue<number>;
  lcContainerHeight: number;
};

export default function LcImageFull(props: Props) {
  const { lcData } = useLcData();

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
      {/* <Image cachePolicy="none"
        transition={200}
        style={{
          width: Dimensions.get("window").width,
          height: (Dimensions.get("window").height * 3) / 5,
          transform: [{ scale: 1 }],
        }}
        source={lcData?.imageFull}
        contentFit={Platform.OS === "android" ? "none" : "contain"}
      /> */}
      <View
        style={{
          width: Dimensions.get("window").width,
          height: Dimensions.get("window").height,
          transform: [{ scale: 0.8 }, { rotate: "0deg" }],
        }}
      >
        <Image cachePolicy="none"
          transition={200}
          className="z-40 absolute w-full h-full"
          source={lcData?.imageFull}
          contentFit={"contain"}
          style={{ bottom: 880 - Dimensions.get("screen").height }}
        />
      </View>
    </Animated.View>
  );
}
