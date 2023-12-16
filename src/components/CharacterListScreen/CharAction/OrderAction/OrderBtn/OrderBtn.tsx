import { View, Text, Pressable, Touchable, PanResponder } from "react-native";
import React from "react";
import Button from "../../../../global/Button/Button";
import { Image } from "expo-image";
import { TouchableOpacity } from "react-native-gesture-handler";

const BothSideArrowIcon = require("../../../../../../assets/icons/BothSideArrow.svg");

export default function OrderBtn({
  children,
  onPressReverse,
  reverse,
}: {
  children: string;
  onPressReverse: () => void;
  reverse: boolean;
}) {
  return (
    <Button width={212} height={46} disable>
      <Text className="font-[HY65] text-[16px]">{children}</Text>

      <View className="absolute right-0">
        <TouchableOpacity
          className="w-9 h-9"
          onPress={() => {
            onPressReverse();
          }}
          style={{ justifyContent: "center", alignItems: "center" }}
        >
          <Image
            style={{
              width: 18,
              height: 18,
              transform: [{ rotate: reverse ? "180deg" : "0deg" }],
            }}
            source={BothSideArrowIcon}
          />
        </TouchableOpacity>
      </View>
    </Button>
  );
}
