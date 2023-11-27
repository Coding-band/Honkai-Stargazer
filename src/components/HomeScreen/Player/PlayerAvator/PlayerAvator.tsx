import { Image } from "expo-image";
import React, { useEffect, useState } from "react";
import { View } from "react-native";

const testAvatorImage = require("../../../../../assets/images/test-avator.png");

export default function PlayerAvator() {
  const [rotate, setRotate] = useState(0);

  useEffect(() => {
    setTimeout(() => {
      setRotate(rotate + 5);
    }, 10);
  }, [rotate]);

  return (
    <View
      className="w-[73px] h-[73px] rounded-full mr-2 bg-white"
      style={{ transform: [{ rotate: rotate + "deg" }] }}
    >
      <Image
        source={testAvatorImage}
        className="w-[73px] h-[73px] rounded-full"
        style={{
          backgroundColor: "rgba(144, 124, 84, 0.4)",
        }}
      />
    </View>
  );
}
