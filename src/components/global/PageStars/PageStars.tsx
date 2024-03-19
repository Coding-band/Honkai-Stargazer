import { View } from "react-native";
import React from "react";
import { range } from "lodash";
import { Image } from "expo-image";

const StarIcon = require("../../../../assets/icons/Star.svg");

type Props = {
  count: number;
  style?: any;
};

export default function PageStars(props: Props) {
  return (
    <View className="ml-1 my-[-8px]" style={...props.style,{ flexDirection: "row" }}>
      {range(props.count).map((i) => (
        <Image cachePolicy="none"
          key={i}
          style={{ width: 40, height: 40, marginLeft: -8 }}
          source={StarIcon}
        />
      ))}
    </View>
  );
}
