import React from "react";
import { View, Text, TouchableNativeFeedback } from "react-native";
import Svg, { Rect, Defs, LinearGradient, Stop } from "react-native-svg";
export default function Tab({ children }: { children: any }) {
  return (
    <TouchableNativeFeedback>
      <View>
        <Svg width="80" height="80" viewBox="0 0 80 80" fill="none">
          {/* LinearGradient definition */}
          <Defs>
            <LinearGradient id="paint0_linear" x1="56" y1="12" x2="56" y2="92">
              <Stop offset="0" stopColor="#222222" />
              <Stop offset="1" stopColor="#222222" stopOpacity="0" />
            </LinearGradient>
            <LinearGradient id="grad1" x1="0%" y1="0%" x2="0%" y2="100%">
              <Stop offset="0%" stopColor="rgba(255, 255, 255, 0.10)" />
              <Stop offset="100%" stopColor="rgba(255, 255, 255, 0.10)" />
            </LinearGradient>
            <LinearGradient id="grad2" x1="0%" y1="0%" x2="0%" y2="100%">
              <Stop offset="0%" stopColor="rgba(34, 34, 34, 0.60)" />
              <Stop offset="100%" stopColor="rgba(34, 34, 34, 0.00)" />
            </LinearGradient>
          </Defs>

          {/* Rectangles */}
          <Rect
            x="0"
            y="0"
            width="80"
            height="80"
            fill="url(#grad1)"
            fillOpacity="0.1"
          />
          <Rect
            x="0"
            y="0"
            width="80"
            height="80"
            fill="url(#grad2)"
            fillOpacity="0.1"
          />
          <Rect
            x="0"
            y="0"
            width="80"
            height="80"
            rx="4"
            fill="url(#paint0_linear)"
            fillOpacity="0.6"
          />
          <Rect
            x="0"
            y="0"
            width="80"
            height="80"
            rx="4"
            fill="white"
            fillOpacity="0.1"
          />
          <Rect
            x="0.75"
            y="0.75"
            width="78.5"
            height="78.5"
            rx="3.25"
            stroke="#907C54"
            strokeOpacity="0.4"
            strokeWidth="1.5"
          />
        </Svg>
        <View
          className="absolute w-20 h-20"
          style={{ justifyContent: "center", alignItems: "center" }}
        >
          {children}
        </View>
      </View>
    </TouchableNativeFeedback>
  );
}
