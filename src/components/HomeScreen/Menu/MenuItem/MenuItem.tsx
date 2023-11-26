import React from "react";
import { Text, TouchableNativeFeedback, View } from "react-native";
import Svg, { Defs, LinearGradient, Stop, Rect } from "react-native-svg";
import { BlurView } from "expo-blur";
import { LinearGradient as LinearGradientExpo } from "expo-linear-gradient";

export default function MenuItem({
  children,
  Icon,
}: {
  children: any;
  Icon: any;
}) {
  return (
    <TouchableNativeFeedback>
      <View>
        {/* 子組件 */}
        {/* <BlurView
          className="rounded-[4px]"
          style={{ position: "absolute", top: 0, left: 0, bottom: 0, right: 0 }}
          tint="light" // 或 "dark"
          intensity={1} // 模糊強度
        ></BlurView> */}
        <Svg width="80" height="90" viewBox="0 0 80 90" fill="none">
          {/* LinearGradient definition */}
          <Defs>
            <LinearGradient id="paint0_linear" x1="56" y1="12" x2="56" y2="92">
              <Stop offset="0" stopColor="#222222" />
              <Stop offset="1" stopColor="#222222" stopOpacity="0" />
            </LinearGradient>
          </Defs>

          {/* Rectangles */}
          <Rect
            x="0"
            y="0"
            width="80"
            height="90"
            rx="4"
            fill="url(#paint0_linear)"
            fillOpacity="0.6"
          />
          <Rect
            x="0"
            y="0"
            width="80"
            height="90"
            rx="4"
            fill="white"
            fillOpacity="0.1"
          />
          <Rect
            x="0.75"
            y="0.75"
            width="78.5"
            height="88.5"
            rx="3.25"
            stroke="#907C54"
            strokeOpacity="0.4"
            strokeWidth="1.5"
          />
        </Svg>
        <View
          className="absolute top-[15px] w-20 h-[90px]"
          style={{ alignItems: "center" }}
        >
          <View style={{ gap: 7, alignItems: "center" }}>
            {Icon && <Icon weight="fill" size={32} color="white" />}
            <Text className="text-[14px] text-white">{children}</Text>
          </View>
        </View>
      </View>
    </TouchableNativeFeedback>
  );
}
