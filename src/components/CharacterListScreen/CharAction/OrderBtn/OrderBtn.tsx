import { View, Text, Pressable } from "react-native";
import React from "react";
import Button from "../../../global/Button/Button";
import { Image } from "expo-image";

const BothSideArrowIcon = require("../../../../../assets/icons/BothSideArrow.svg");

export default function OrderBtn({
  children,
  onPressRight,
}: {
  children: string;
  onPressRight: () => void;
}) {
  return (
    <Button width={212} height={46} disable>
      <Text className="font-[HY65] text-[16px]">{children}</Text>
      <Pressable
        className="w-[18px] h-[18px] absolute right-3"
        style={{ justifyContent: "center", alignItems: "center" }}
        onPress={(e) => {
          onPressRight();
        }}
      >
        <Image
          style={{
            width: 18,
            height: 18,
          }}
          source={BothSideArrowIcon}
        />
      </Pressable>
    </Button>
  );
}
