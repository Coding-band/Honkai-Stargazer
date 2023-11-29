import { View, Text, Dimensions, ScrollView, Pressable } from "react-native";
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
import { useSpring, animated } from "@react-spring/native";

export default function Character() {
  const charData = useContext(CharacterContext);

  const [scrollForMore, setScrollForMore] = useState(false);
  const imageAnimation = useSpring({ opacity: scrollForMore ? 0.3 : 1 });
  const contentAnimation = useSpring({ opacity: scrollForMore ? 1 : 0 });

  return (
    <View className="absolute bottom-0 w-full" style={{ alignItems: "center" }}>
      <AnimatedImage
        transition={200}
        style={{ width: 300, height: 692, ...imageAnimation }}
        source={charData?.imageFull}
      />
      <View
        className="absolute w-full p-[24px] z-50"
        style={{
          height: Dimensions.get("window").height - 40,
        }}
      >
        <ScrollView
          onScroll={(e) => {
            console.log(1);
            if (e.nativeEvent.contentOffset.y > 0) {
              setScrollForMore(true);
            } else {
              setScrollForMore(false);
            }
          }}
        >
          <CharInfo />
          <AnimatedView style={{ ...contentAnimation }}>
            <CharAttribute />
            <CharMaterialList />
            <CharTrace />
            <CharSuggestLightCone />
            <CharSuggestRelics />
            <CharSuggestTeam />
            <CharStory />
            <View className="pb-[150px]" />
          </AnimatedView>
        </ScrollView>
      </View>
      <CharAction show={!scrollForMore} />
    </View>
  );
}

const AnimatedView = animated(View)
const AnimatedImage = animated(Image);

// const startY = useSharedValue(0);
// const scrollY = useSharedValue(0);

// const animatedStyle = useAnimatedStyle(() => {
//   return {
//     transform: [
//       {
//         translateY: withTiming(scrollY.value, {
//           duration: 400,
//           easing: Easing.linear,
//         }),
//       },
//     ],
//   };
// });

// const gestureHandler = useAnimatedGestureHandler({
//   onStart() {
//     startY.value = scrollY.value;
//   },
//   onActive(e) {
//     scrollY.value = startY.value + e.translationY * 1.6;
//   },
//   onEnd() {
//     // 这里可以加入一些逻辑来决定滚动结束后的行为，比如使用动画平滑地回到起始位置
//     // scrollY.value = withSpring(0);
//   },
// });
