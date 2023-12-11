import { View, Text } from "react-native";
import React from "react";
import Button from "../../../global/Button/Button";
import { Image } from "expo-image";

const BothSideArrowIcon = require("../../../../../assets/icons/BothSideArrow.svg");

export default function OrderBtn({ children }: { children: string }) {
  return (
    <Button width={212} height={46} disable>
      <Text className="font-[HY65] text-[16px]">{children}</Text>
      <Image
        style={{
          width: 18,
          height: 18,
          position: "absolute",
          right: 12,
        }}
        source={BothSideArrowIcon}
      />
    </Button>
  );
}
