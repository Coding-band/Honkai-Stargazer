import { View, Text } from "react-native";
import React from "react";
import { range } from "lodash";
import { Image } from "expo-image";

type Props = {
  count: number;
};

const StarIcon = require("../../../../../assets/icons/Star.svg");

export default function UserCharDetailStars(props: Props) {
  return (
    <View className="ml-1 my-[-8px]" style={{ flexDirection: "row" }}>
      {range(props.count).map((i) => (
        <Image cachePolicy="none"
          key={i}
          style={{ width: 24, height: 24, marginLeft: -4 }}
          source={StarIcon}
        />
      ))}
    </View>
  );
}
