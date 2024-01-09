import { View, Text } from 'react-native'
import React from 'react'

export default function BetaWidget() {
  return (
    <View
      className="absolute top-4 -right-4"
      style={{ transform: [{ rotate: "270deg" }] }}
    >
      <View
        className="w-[60px] h-[30px] bg-black"
        style={{ justifyContent: "center", alignItems: "center" }}
      >
        <Text className="text-text text-[12px]">BETA</Text>
      </View>
    </View>
  );
}