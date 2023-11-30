import {
  View,
  Text,
  Dimensions,
  ScrollView,
  Pressable,
  LayoutChangeEvent,
} from "react-native";
import React, { useContext, useState } from "react";
import { Image } from "expo-image";
import CharAction from "./CharAction/CharAction";
import CharacterContext from "../../../context/CharacterContext";
import CharInfo from "./CharInfo/CharInfo";
import CharAttribute from "./CharAttribute/CharAttribute";
import CharMaterialList from "./CharMaterialList/CharMaterialList";
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

export default function Character() {
  const [containerHeight, setContainerHeight] = useState(0);
  const handleLayout = (event: LayoutChangeEvent) => {
    const { width, height } = event.nativeEvent.layout;
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
    <View className="absolute bottom-0 w-full" style={{ alignItems: "center" }}>
      <CharImageFull
        scrollHandler={scrollHandler}
        charContainerHeight={containerHeight}
      />
      <View
        className="absolute w-full p-[24px] z-50"
        style={{
          height: Dimensions.get("window").height - 40,
        }}
      >
        <Animated.ScrollView ref={aref}>
          <View onLayout={handleLayout}>
            <CharInfo />
            <Animated.View style={contentAnimatedStyles}>
              <CharAttribute />
              <CharMaterialList />
              <CharTrace />
              <CharSuggestLightCone />
              <CharSuggestRelics />
              <CharSuggestTeam />
              <CharStory />
              <View className="pb-[150px]" />
            </Animated.View>
          </View>
        </Animated.ScrollView>
      </View>
      <CharAction scrollHandler={scrollHandler} />
    </View>
  );
}
