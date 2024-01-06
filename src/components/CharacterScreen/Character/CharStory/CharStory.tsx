import { View, Text } from "react-native";
import React, { useContext } from "react";
import CharPageHeading from "../../../global/PageHeading/PageHeading";
import { ChatsCircle } from "phosphor-react-native";
import CharacterContext from "../../../../context/CharacterData/CharacterContext";
import { HtmlText } from "@e-mine/react-native-html-text";
import { getCharFullData } from "../../../../utils/dataMap/getDataFromMap";
import useCharData from "../../../../context/CharacterData/hooks/useCharData";
import useAppLanguage from "../../../../language/AppLanguage/useAppLanguage";
import { LOCALES } from "../../../../../locales";

export default React.memo(function CharStory() {
  const { charFullData } = useCharData();
  const { language } = useAppLanguage();

  return (
    <View>
      <CharPageHeading Icon={ChatsCircle}>
        {LOCALES[language].CharacterStory}
      </CharPageHeading>
      <HtmlText style={{ lineHeight: 24, color: "white", fontFamily: "HY65" }}>
        {charFullData.storyItems[0].text || ""}
      </HtmlText>
    </View>
  );
});
