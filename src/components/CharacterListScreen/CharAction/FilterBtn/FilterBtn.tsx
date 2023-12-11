import { View, Text } from "react-native";
import React from "react";
import Button from "../../../global/Button/Button";
import { Image } from "expo-image";

const FilterIcon = require("../../../../../assets/icons/Filter.svg");

export default function FilterBtn() {
  return (
    <Button width={46} height={46}>
      <Image
        style={{
          width: 16,
          height: 16,
        }}
        source={FilterIcon}
      />
    </Button>
  );
}
