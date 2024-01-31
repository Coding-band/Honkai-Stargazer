import React from "react";
import Animated, {
  SharedValue,
  useAnimatedStyle,
  withSpring,
} from "react-native-reanimated";
import { Image } from "expo-image";
import { Dimensions, View } from "react-native";
import useLcData from "../../../../context/LightconeData/hooks/useLcData";
import useRelicData from "../../../../context/RelicData/hooks/useRelicData";

type Props = {
  scrollHandler: SharedValue<number>;
  lcContainerHeight: number;
};

export default function RelicImageFull(props: Props) {
  const { relicData } = useRelicData();

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
      {/* <Image
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
          height: (Dimensions.get("window").height * 3) / 4,
          marginTop: (Dimensions.get("screen").height * 1) / 4 - 60,
          flexDirection: "row",
          flexWrap: "wrap",
          justifyContent: "center",
          alignItems: "center",
          gap: 20,
        }}
      >
        <Image
          transition={200}
          className="z-40 w-36 h-36"
          source={relicData?.imageFull?.[0]}
          contentFit={"contain"}
        />
        <Image
          transition={200}
          className="z-40 w-36 h-36"
          source={relicData?.imageFull?.[1]}
          contentFit={"contain"}
        />
        <Image
          transition={200}
          className="z-40 w-36 h-36"
          source={relicData?.imageFull?.[2]}
          contentFit={"contain"}
        />
        <Image
          transition={200}
          className="z-40 w-36 h-36"
          source={relicData?.imageFull?.[3]}
          contentFit={"contain"}
        />
      </View>
    </Animated.View>
  );
}
