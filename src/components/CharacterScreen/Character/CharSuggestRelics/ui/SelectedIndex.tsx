import { View, Text } from "react-native";
import React from "react";

export default function SelectedIndex({
  max,
  index,
}: {
  max: number;
  index: number;
}) {
  return (
    <View style={{ flexDirection: "row" }}>
      {Array.from({ length: max }, (_, i) => (
        <View
          key={i}
          style={{
            width: i === index ? 40 : 10,
            height: 4,
            borderRadius: 4,
            backgroundColor: i === index ? "#FFFFFF40" : "#FFFFFF20",
            marginHorizontal: 2,
          }}
        ></View>
      ))}
    </View>
  );
}
