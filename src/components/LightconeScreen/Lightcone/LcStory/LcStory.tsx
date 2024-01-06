import { View } from "react-native";
import React, { useContext } from "react";
import PageHeading from "../../../global/PageHeading/PageHeading";
import { ChatsCircle } from "phosphor-react-native";
import { HtmlText } from "@e-mine/react-native-html-text";
import LightconeContext from "../../../../context/LightconeData/LightconeContext";
import useLcData from "../../../../context/LightconeData/hooks/useLcData";
import useAppLanguage from "../../../../language/AppLanguage/useAppLanguage";
import { LOCALES } from "../../../../../locales";

export default function LcStory() {
  const { lcFullData } = useLcData();
  const { language } = useAppLanguage();

  return (
    <View style={{ alignItems: "center" }}>
      <PageHeading Icon={ChatsCircle}>
        {LOCALES[language].SkillDetail}
      </PageHeading>
      <HtmlText style={{ lineHeight: 24, color: "white", fontFamily: "HY65" }}>
        {lcFullData.descHash || ""}
      </HtmlText>
    </View>
  );
}
