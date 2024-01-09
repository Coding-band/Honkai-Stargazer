import { View, Text, LayoutChangeEvent, Vibration } from "react-native";
import React, { useCallback, useEffect, useState } from "react";
import { cn } from "../../../utils/css/cn";
import BlurView from "../BlurView/BlurView";
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
  width?: number;
  bgColor?: string;
  value: number;
  onChange(v: number): void;
};

export default function Sliderbar({
  point = 9,
  hasDot = true,
  value,
  onChange,
  bgColor,
  width,
}: Props) {
  const [containerWidth, setContainerWidth] = useState(250);
  const [points, setPoints] = useState([0]);

  const handleLayout = useCallback((event: LayoutChangeEvent) => {
    const width = event.nativeEvent.layout.width;
    setContainerWidth(width);
  }, []);

  useEffect(() => {
    const span = (containerWidth - 16 * (point - 1)) / (point - 1);
    const points = [0];
    for (let i = 0; i < point - 1; i++) {
      points.push(points[i] + 16 + span);
    }
    setPoints(points);
  }, [containerWidth, point]);

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
    // Vibration.vibrate(2);
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
      className={cn("h-12 my-[-8px]", "overflow-hidden")}
      style={{
        width: width || 275,
        justifyContent: "center",
      }}
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
        className={cn("w-full h-4 rounded-[30px] ")}
        style={{
          backgroundColor: bgColor || "#ffffff50",
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
                  key={i}
                  style={{ left: points[i] }}
                  className="absolute w-1 h-1 bg-[#404165] rounded-full"
                />
              )
          )}
      </BlurView>
    </View>
  );
}
