import { View, Text } from "react-native";
import React from "react";
import Button from "../../../global/Button/Button";
import { Image } from "expo-image";

const SearchIcon = require("../../../../../assets/icons/Search.svg");

export default function SearchBtn() {
  return (
    <Button width={46} height={46}>
      <Image style={{ width: 16, height: 15 }} source={SearchIcon} />
    </Button>
  );
}
