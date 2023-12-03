import { View, Text, LayoutChangeEvent } from "react-native";
import React, { useCallback, useEffect, useState } from "react";
import { cn } from "../../../utils/cn";
import { BlurView } from "expo-blur";
import { PanGestureHandler } from "react-native-gesture-handler";
import Animated, {
  runOnJS,
  useAnimatedGestureHandler,
  useAnimatedStyle,
  useSharedValue,
} from "react-native-reanimated";

type Props = {
  point?: number;
  hasDot?: boolean;
  value: number;
  onChange(v: number): void;
};

export default function Sliderbar({
  point = 8,
  hasDot = true,
  value,
  onChange,
}: Props) {
  // 定義每個點的 X 位置
  const [points, setPoints] = useState([0]);

  const handleLayout = useCallback((event: LayoutChangeEvent) => {
    const width = event.nativeEvent.layout.width;
    const span = (width - 16 * (point - 1)) / (point - 1);
    const points = [0];
    for (let i = 0; i < 8; i++) {
      points.push(points[i] + 16 + span);
    }
    setPoints(points);
  }, []);

  const positionX = useSharedValue(points?.[value] || 0);
  const transitionX = useSharedValue(0);

  useEffect(() => {
    positionX.value = points[value];
  }, [value, points]);

  const animatedStyle = useAnimatedStyle(() => {
    let computedX = positionX.value + transitionX.value;

    if (computedX < 0) computedX = 0;
    else if (computedX > points[point - 1]) computedX = points[point - 1];

    if (computedX === 0) {
      return { left: computedX };
    } else if (computedX === points[point - 1]) {
      return { left: computedX - 16 };
    }

    return { left: computedX - 6 };
  });

  const adjustToNearestPoint = (x: any) => {
    const closest = points.reduce((prev, curr) =>
      Math.abs(curr - x) < Math.abs(prev - x) ? curr : prev
    );
    transitionX.value = 0; // 重置 transitionX
    onChange(points.findIndex((v) => v === closest));
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
      onLayout={handleLayout}
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
          alignItems: "center",
          justifyContent: "center",
        }}
      >
        {hasDot &&
          points.map(
            (p, i) =>
              i !== 0 && (
                <View
                  style={{ left: points[i] }}
                  className="absolute w-1 h-1 bg-[#404165] rounded-full"
                />
              )
          )}
      </BlurView>
    </View>
  );
}
