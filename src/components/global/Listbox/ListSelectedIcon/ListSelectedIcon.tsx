import { View, Text } from "react-native";
import React from "react";
import Svg, { Circle } from "react-native-svg";

export default function ListSelectedIcon() {
  return (
    <Svg height="20" width="20" viewBox="0 0 20 20" fill="none">
      <Circle
        cx="10"
        cy="10"
        r="9"
        stroke="#EF8C00"
        strokeWidth="2"
        fill="none"
      />
      <Circle cx="10" cy="10" r="6" fill="#EF8C00" />
    </Svg>
  );
}
