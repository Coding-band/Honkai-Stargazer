import { View, Text, TouchableOpacity } from "react-native";
import React, { useContext, useRef, useState } from "react";
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

  const storyLength = useRef(charFullData.storyItems.length);
  const [currentStoryIndex, setCurrentStoryIndex] = useState(0);
  const handleNextStory = () => {
    if (currentStoryIndex === storyLength.current - 1) {
      setCurrentStoryIndex(0);
    } else {
      setCurrentStoryIndex(currentStoryIndex + 1);
    }
  };
  const handlePrevStory = () => {
    if (currentStoryIndex === 0) {
      setCurrentStoryIndex(storyLength.current - 1);
    } else {
      setCurrentStoryIndex(currentStoryIndex - 1);
    }
  };

  return (
    <View>
      <CharPageHeading Icon={ChatsCircle}>
        {charFullData.storyItems[currentStoryIndex].title}
      </CharPageHeading>
      <TouchableOpacity
        activeOpacity={0.35}
        onPress={handleNextStory}
        onLongPress={handlePrevStory}
      >
        <HtmlText
          style={{ lineHeight: 24, color: "white", fontFamily: "HY65" }}
        >
          {charFullData.storyItems[currentStoryIndex].text || ""}
        </HtmlText>
      </TouchableOpacity>
    </View>
  );
});
