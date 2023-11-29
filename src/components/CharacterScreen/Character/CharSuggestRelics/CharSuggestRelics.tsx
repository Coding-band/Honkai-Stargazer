import { View, Text } from "react-native";
import React from "react";
import CharPageHeading from "../../../global/layout/CharPageHeading";
import { BaseballCap } from "phosphor-react-native";

export default function CharSuggestRelics() {
  return (
    <View style={{ alignItems: "center" }}>
      <CharPageHeading Icon={BaseballCap}>推荐遗器</CharPageHeading>
    </View>
  );
}
