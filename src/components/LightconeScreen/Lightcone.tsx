import { View, LayoutChangeEvent, Dimensions } from "react-native";
import React, { useState } from "react";
import Animated, {
  useAnimatedRef,
  useAnimatedStyle,
  useScrollViewOffset,
  withSpring,
} from "react-native-reanimated";
import LcAction from "./Lightcone/LcAction/LcAction";
import LcImageFull from "./Lightcone/LcImageFull/LcImageFull";
import LcInfo from "./Lightcone/LcInfo/LcInfo";
import LcAttribute from "./Lightcone/LcAttribute/LcAttribute";
import LcStory from "./Lightcone/LcStory/LcStory";
import LcSuggestCharacter from "./Lightcone/LcSuggestCharacter/LcSuggestCharacter";
import LcDescription from "./Lightcone/LcDescription/LcDescription";

export default function Lightcone() {
  const [containerHeight, setContainerHeight] = useState(0);

  const handleLayout = (event: LayoutChangeEvent) => {
    const { height } = event.nativeEvent.layout;
    setContainerHeight(height);
  };

  const aref = useAnimatedRef<Animated.ScrollView>();
  const scrollHandler = useScrollViewOffset(aref);

  const contentAnimatedStyles = useAnimatedStyle(() => {
    if (scrollHandler.value > 0) {
      return {
        opacity: withSpring(1),
      };
    } else {
      return {
        opacity: withSpring(0),
      };
    }
  });

  return (
    <View
      className="absolute bottom-0 w-full h-screen z-30"
      style={{ alignItems: "center" }}
    >
      <View
        className="z-30"
        style={{ marginTop: Dimensions.get("window").height / 5 }}
      >
        <LcImageFull
          scrollHandler={scrollHandler}
          lcContainerHeight={containerHeight}
        />
      </View>
      <View className="absolute w-full h-full pt-0 pb-0 z-40">
        <Animated.ScrollView ref={aref} style={{ padding: 24 }}>
          <View onLayout={handleLayout}>
            <LcInfo />
            <Animated.View style={contentAnimatedStyles}>
              <LcAttribute />
              <LcDescription />
              <LcSuggestCharacter />
              <LcStory />
              <View className="pb-[60px]" />
            </Animated.View>
          </View>
        </Animated.ScrollView>
      </View>
      <LcAction scrollHandler={scrollHandler} />
    </View>
  );
}
