import { View, Text } from "react-native";
import React, { useEffect } from "react";
import { cn } from "../../../../../utils/cn";
import { BlurView } from "expo-blur";
import { PanGestureHandler } from "react-native-gesture-handler";
import Animated, {
  runOnJS,
  useAnimatedGestureHandler,
  useAnimatedStyle,
  useSharedValue,
} from "react-native-reanimated";

type Props = {
  value: number;
  onChange(v: number): void;
};

export default function AttrSliderbar(props: Props) {
  // 定義每個點的 X 位置
  const points = [0, 22, 57, 92, 130, 162, 200, 235, 258]; // 根据您的布局调整这些值

  const positionX = useSharedValue(points[props.value]);
  const transitionX = useSharedValue(0);

  useEffect(() => {
    positionX.value = points[props.value];
  }, [props.value]);

  const animatedStyle = useAnimatedStyle(() => {
    let computedX = positionX.value + transitionX.value;

    if (computedX < 0) computedX = 0;
    else if (computedX > 260) computedX = 260;

    return { left: computedX };
  });

  const adjustToNearestPoint = (x: any) => {
    const closest = points.reduce((prev, curr) =>
      Math.abs(curr - x) < Math.abs(prev - x) ? curr : prev
    );
    transitionX.value = 0; // 重置 transitionX
    props.onChange(points.findIndex((v) => v === closest));
  };

  const gestureHandler = useAnimatedGestureHandler({
    onActive(e) {
      transitionX.value = e.translationX;
    },
    onEnd(e) {
      runOnJS(adjustToNearestPoint)(positionX.value + e.translationX);
    },
  });

  return (
    <View
      className={cn("w-[275px] h-12 my-[-8px]", "overflow-hidden")}
      style={{ justifyContent: "center" }}
    >
      <PanGestureHandler onGestureEvent={gestureHandler}>
        <Animated.View
          style={[
            animatedStyle,
            {
              justifyContent: "center",
              alignItems: "center",
              transform: [{ translateX: -16 }, { translateY: -16 }],
            },
          ]}
          className="z-50 w-12 h-12 rounded-full absolute top-4"
        >
          <View className="w-4 h-4 bg-[#FFFFFF] rounded-full absolute"></View>
        </Animated.View>
      </PanGestureHandler>
      <BlurView
        intensity={0}
        className={cn("w-full h-4 bg-[#ffffff50] rounded-[30px] ")}
        style={{
          flexDirection: "row",
          gap: 32,
          alignItems: "center",
          justifyContent: "center",
        }}
      >
        <View className="w-1 h-1 bg-[#404165] rounded-full"></View>
        <View className="w-1 h-1 bg-[#404165] rounded-full"></View>
        <View className="w-1 h-1 bg-[#404165] rounded-full"></View>
        <View className="w-1 h-1 bg-[#404165] rounded-full"></View>
        <View className="w-1 h-1 bg-[#404165] rounded-full"></View>
        <View className="w-1 h-1 bg-[#404165] rounded-full"></View>
        <View className="w-1 h-1 bg-[#404165] rounded-full"></View>
        <View className="w-1 h-1 bg-[#404165] rounded-full"></View>
      </BlurView>
    </View>
  );
}
