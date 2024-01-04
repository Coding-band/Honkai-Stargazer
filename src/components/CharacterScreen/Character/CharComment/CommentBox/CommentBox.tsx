import { View, Text } from "react-native";
import React, { useEffect } from "react";
import Animated, {
  useAnimatedStyle,
  useSharedValue,
  withSpring,
} from "react-native-reanimated";
import { Dimensions } from "react-native";
import { Gesture, GestureDetector } from "react-native-gesture-handler";
import { Image } from "expo-image";
import useCharComments from "../../../../../firebase/hooks/useCharComments";

const UpArrow = require("./icons/UpArrow.svg");
const DownArrow = require("./icons/DownArrow.svg");

type Props = {
  containerRef: any;
  children: any;
};

export default function CommentBox(props: Props) {
  // data
  const { data: charComments } = useCharComments("1107");

  // animation
  const hieght = useSharedValue(Dimensions.get("window").height - 160);
  const isPressed = useSharedValue(false);
  const translation = useSharedValue({ x: 0, y: 0 });

  useEffect(() => {
    if (translation.value.y < 0) {
      props.containerRef?.current?.scrollTo({
        x: 0,
        y: 3000,
        animated: true,
      });
    }
  }, [translation.value.y]);

  const gesture = Gesture.Pan()
    .onBegin(() => {
      isPressed.value = true;
    })
    .onUpdate((e) => {
      translation.value = {
        x: e.translationX,
        y: e.translationY,
      };
    })
    .onEnd(() => {})
    .onFinalize(() => {
      isPressed.value = false;
    });

  const animatedStyles = useAnimatedStyle(() => {
    return {
      transform: [{ scale: withSpring(isPressed.value ? 1.2 : 1) }],
    };
  });

  const animatedStyles2 = useAnimatedStyle(() => {
    return {
      height: translation.value.y < 0 ? hieght.value : /* withSpring(0) */ 0,
      opacity: translation.value.y < 0 ? withSpring(1) : 0,
    };
  });

  return (
    <View>
      <GestureDetector gesture={gesture}>
        <Animated.View style={[animatedStyles]}>
          <View
            className="w-full pt-[32px] pb-[16px]"
            style={{ alignItems: "center", gap: 16 }}
          >
            <Image
              source={translation.value.y < 0 ? DownArrow : UpArrow}
              className="w-[12px] h-[12px]"
            />
            <Text className="text-white text-[16px] font-[HY65]">
              {charComments?.comment_num}條評論
            </Text>
          </View>
        </Animated.View>
      </GestureDetector>
      <Animated.View className="w-full" style={[animatedStyles2, { gap: 24 }]}>
        {props.children}
      </Animated.View>
    </View>
  );
}
