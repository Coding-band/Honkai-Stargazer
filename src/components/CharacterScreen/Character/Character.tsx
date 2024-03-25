import { View, LayoutChangeEvent, Dimensions, Keyboard } from "react-native";
import React, { MutableRefObject, useEffect, useState } from "react";
import CharAction from "./CharAction/CharAction";
import CharInfo from "./CharInfo/CharInfo";
import CharAttribute from "./CharAttribute/CharAttribute";
import CharTrace from "./CharTrace/CharTrace";
import CharStory from "./CharStory/CharStory";
import CharSuggestTeam from "./CharSuggestTeam/CharSuggestTeam";
import CharComment from "./CharComment/CharComment";
import CharSuggestRelics from "./CharSuggestRelics/CharSuggestRelics";
import CharSuggestLightCone from "./CharSuggestLightCone/CharSuggestLightCone";
import Animated, {
  useAnimatedProps,
  useAnimatedRef,
  useAnimatedStyle,
  useScrollViewOffset,
  withSpring,
} from "react-native-reanimated";
import CharImageFull from "./CharImageFull/CharImageFull";
import CharEidolon from "./CharEidolon/CharEidolon";
import { ScrollView } from "react-native-gesture-handler";

type Props = {
  scrollViewRef : MutableRefObject<ScrollView | Animated.ScrollView | undefined | null>;
}

export default React.memo(function Character(props : Props) {
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
      <CharImageFull
        scrollHandler={scrollHandler}
        charContainerHeight={containerHeight}
      />
      <View className="absolute w-full h-full pt-0 pb-0 z-40">
        <Animated.ScrollView
          bounces={true}
          // @ts-ignore
          ref={aref}
          nestedScrollEnabled={true}
          keyboardShouldPersistTaps="always"
          onScroll={() => {
            Keyboard.dismiss();
          }}
        >
          <View onLayout={handleLayout}>
            <View>
              <CharInfo />
              <Animated.View style={contentAnimatedStyles}>
                <CharAttribute />
                <CharTrace />
                <CharEidolon />
                <CharSuggestLightCone />
                <CharSuggestRelics />
                <CharSuggestTeam />
                <CharStory />
              </Animated.View>
            </View>
            <CharComment containerRef={aref} />
            <View className="pb-[40px]" />
          </View>
        </Animated.ScrollView>
      </View>
      <CharAction
        onLeftClick={() => {
          aref?.current?.scrollTo({
            x: 0,
            y: 1950,
            animated: true,
          });
        }}
        onRightClick={() => {
          aref?.current?.scrollTo({
            x: 0,
            y: 3000,
            animated: true,
          });
        }}
        scrollHandler={scrollHandler}
      />
    </View>
  );
});
