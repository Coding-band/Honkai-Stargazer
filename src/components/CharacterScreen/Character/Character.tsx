import { View, LayoutChangeEvent, Dimensions } from "react-native";
import React, { useState } from "react";
import CharAction from "./CharAction/CharAction";
import CharInfo from "./CharInfo/CharInfo";
import CharAttribute from "./CharAttribute/CharAttribute";
import CharTrace from "./CharTrace/CharTrace";
import CharStory from "./CharStory/CharStory";
import CharSuggestTeam from "./CharSuggestTeam/CharSuggestTeam";
import CharSuggestRelics from "./CharSuggestRelics/CharSuggestRelics";
import CharSuggestLightCone from "./CharSuggestLightCone/CharSuggestLightCone";
import Animated, {
  useAnimatedRef,
  useAnimatedStyle,
  useScrollViewOffset,
  withSpring,
} from "react-native-reanimated";
import CharImageFull from "./CharImageFull/CharImageFull";
import CharEidolon from "./CharEidolon/CharEidolon";
import PopUpCard from "../../global/PopUpCard/PopUpCard";
import Fixed from "../../global/Fixed/Fixed";

export default function Character() {
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
        <CharImageFull
          scrollHandler={scrollHandler}
          charContainerHeight={containerHeight}
        />
      </View>
      <View className="absolute w-full h-full pt-0 pb-0 z-40">
        <Animated.ScrollView ref={aref} style={{ padding: 24 }}>
          <View onLayout={handleLayout}>
            <CharInfo />
            <Animated.View style={contentAnimatedStyles}>
              <CharAttribute />
              <CharTrace />
              <CharEidolon />
              <CharSuggestLightCone />
              <CharSuggestRelics />
              <CharSuggestTeam />
              <CharStory />
              <View className="pb-[60px]" />
            </Animated.View>
          </View>
        </Animated.ScrollView>
      </View>
      <CharAction scrollHandler={scrollHandler} />
    </View>
  );
}
