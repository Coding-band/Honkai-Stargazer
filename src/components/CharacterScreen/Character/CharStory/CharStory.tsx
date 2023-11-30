import { View, Text } from "react-native";
import React, { useContext } from "react";
import CharPageHeading from "../../../global/layout/CharPageHeading";
import { ChatsCircle } from "phosphor-react-native";
import CharacterContext from "../../../../context/CharacterContext";
import { HtmlText } from "@e-mine/react-native-html-text";

export default function CharStory() {
  const charData = useContext(CharacterContext);
  return (
    <View style={{ alignItems: "center" }}>
      <CharPageHeading Icon={ChatsCircle}>角色故事</CharPageHeading>
      <HtmlText style={{ lineHeight: 24, color: "white", fontFamily: "HY65" }}>
        {charData?.storyText || ""}
      </HtmlText>
    </View>
  );
}
