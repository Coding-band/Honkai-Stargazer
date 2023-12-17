import { View } from "react-native";
import React, { useContext } from "react";
import PageHeading from "../../../global/PageHeading/PageHeading";
import { ChatsCircle } from "phosphor-react-native";
import { HtmlText } from "@e-mine/react-native-html-text";
import LightconeContext from "../../../../context/LightconeContext";
import useLcData from "../../../../hooks/data/useLcData";

export default function LcStory() {
  const { lcFullData } = useLcData();

  return (
    <View style={{ alignItems: "center" }}>
      <PageHeading Icon={ChatsCircle}>技能詳情</PageHeading>
      <HtmlText style={{ lineHeight: 24, color: "white", fontFamily: "HY65" }}>
        {lcFullData.descHash || ""}
      </HtmlText>
    </View>
  );
}
