import { View, Text } from "react-native";
import React from "react";
import CharPageHeading from "../../../global/layout/CharPageHeading";
import { TreeStructure } from "phosphor-react-native";

export default function CharTrace() {
  return (
    <View style={{ alignItems: "center" }}>
      <CharPageHeading Icon={TreeStructure}>行迹树</CharPageHeading>
    </View>
  );
}
