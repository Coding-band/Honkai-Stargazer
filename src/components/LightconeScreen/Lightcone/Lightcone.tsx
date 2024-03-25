import { View, LayoutChangeEvent, Dimensions } from "react-native";
import React, { MutableRefObject, useEffect, useState } from "react";
import Animated, {
  useAnimatedRef,
  useAnimatedStyle,
  useScrollViewOffset,
  withSpring,
} from "react-native-reanimated";
import LcAction from "./LcAction/LcAction";
import LcImageFull from "./LcImageFull/LcImageFull";
import LcInfo from "./LcInfo/LcInfo";
import LcAttribute from "./LcAttribute/LcAttribute";
import LcStory from "./LcStory/LcStory";
import LcSuggestCharacter from "./LcSuggestCharacter/LcSuggestCharacter";
import LcDescription from "./LcDescription/LcDescription";
import { ScrollView } from "react-native-gesture-handler";

type Props = {
  scrollViewRef : MutableRefObject<ScrollView | Animated.ScrollView | undefined | null>;
}

export default function Lightcone(props : Props) {
  const [containerHeight, setContainerHeight] = useState(0);

  const handleLayout = (event: LayoutChangeEvent) => {
    const { height } = event.nativeEvent.layout;
    setContainerHeight(height);
  };

  const aref = useAnimatedRef<Animated.ScrollView>();
  const scrollHandler = useScrollViewOffset(aref);

  useEffect(() => {
    props.scrollViewRef = aref;
    props.scrollViewRef.current = aref.current;
  })

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
      className="absolute bottom-0 w-full z-30"
      style={{ alignItems: "center", height: Dimensions.get("screen").height }}
    >
      <View className="z-30">
        <LcImageFull
          scrollHandler={scrollHandler}
          lcContainerHeight={containerHeight}
        />
      </View>
      <View className="absolute w-full h-full pt-0 pb-0 z-40">
        <Animated.ScrollView bounces={true} ref={aref} className="pt-6">
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
      <LcAction
        onLeftClick={() => {
          aref?.current?.scrollTo({
            x: 0,
            y: 1950,
            animated: true,
          });
        }}
        scrollHandler={scrollHandler}
      />
    </View>
  );
}
