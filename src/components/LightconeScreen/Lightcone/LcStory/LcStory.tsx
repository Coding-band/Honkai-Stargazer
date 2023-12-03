import { View } from "react-native";
import React, { useContext } from "react";
import CharPageHeading from "../../../global/PageHeading/PageHeading";
import { ChatsCircle } from "phosphor-react-native";
import { HtmlText } from "@e-mine/react-native-html-text";
import LightconeContext from "../../../../context/LightconeContext";

export default function LcStory() {
  const lcData = useContext(LightconeContext);
  return (
    <View style={{ alignItems: "center" }}>
      <CharPageHeading Icon={ChatsCircle}>技能故事</CharPageHeading>
      <HtmlText style={{ lineHeight: 24, color: "white", fontFamily: "HY65" }}>
        {lcData?.description || ""}
      </HtmlText>
    </View>
  );
}
