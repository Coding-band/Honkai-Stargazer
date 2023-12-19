import { View, Text } from "react-native";
import React from "react";
import Button from "../../Button/Button";
import { Image } from "expo-image";

const SearchIcon = require("../../../../../assets/icons/Search.svg");

type Props = {
  onPress: () => void;
};

export default function SearchBtn(props: Props) {
  return (
    <Button width={46} height={46} onPress={props.onPress}>
      <Image style={{ width: 18, height: 18 }} source={SearchIcon} />
    </Button>
  );
}
