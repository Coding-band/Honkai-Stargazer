import { View, LayoutChangeEvent, Dimensions } from "react-native";
import React, { useState } from "react";
import Animated, {
  useAnimatedRef,
  useAnimatedStyle,
  useScrollViewOffset,
  withSpring,
} from "react-native-reanimated";
import RelicImageFull from "./RelicImageFull/RelicImageFull";
import RelicInfo from "./RelicInfo/RelicInfo";
import RelicAction from "./RelicAction/RelicAction";
import RelicDescription from "./RelicDescription/RelicDescription";
import RelicDetails from "./RelicDetails/RelicDetails";
import RelicSuggestCharacter from "./RelicSuggestCharacter/RelicSuggestCharacter";

export default function Relic() {
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
        style={{ marginTop: Dimensions.get("window").height / 9 }}
      >
        <RelicImageFull
          scrollHandler={scrollHandler}
          lcContainerHeight={containerHeight}
        />
      </View>
      <View className="absolute w-full h-full pt-0 pb-0 z-40">
        <Animated.ScrollView ref={aref} style={{ padding: 24 }}>
          <View onLayout={handleLayout}>
            <RelicInfo />
            <Animated.View style={contentAnimatedStyles}>
              <RelicDescription />
              <RelicDetails />
              {/* <RelicSuggestCharacter /> */}
              <View className="pb-[60px]" />
            </Animated.View>
          </View>
        </Animated.ScrollView>
      </View>
      <RelicAction
        onLeftClick={() => {
          aref?.current?.scrollTo({
            x: 0,
            y: 1950,
            animated: true,
          });
        }}
        onRightClick={() => {}}
        scrollHandler={scrollHandler}
      />
    </View>
  );
}
