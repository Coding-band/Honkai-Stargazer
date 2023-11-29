import { View, Text, Dimensions, ScrollView } from "react-native";
import React, { useContext, useState } from "react";
import { Image } from "expo-image";
import CharAction from "./CharAction/CharAction";
import CharacterContext from "../../../context/CharacterContext";
import CharInfo from "./CharInfo/CharInfo";
import CharAttribute from "./CharAttribute/CharAttribute";
import CharMaterialList from "./CharMaterialList/CharMaterialList";
import CharTrace from "./CharTrace/CharTrace";
import CharStory from "./CharStory/CharStory";
import CharSuggestTeam from "./CharSuggestTeam/CharSuggestTeam";
import CharSuggestRelics from "./CharSuggestRelics/CharSuggestRelics";
import CharSuggestLightCone from "./CharSuggestLightCone/CharSuggestLightCone";

export default function Character() {
  const charData = useContext(CharacterContext);

  const [scrollForMore, setScrollForMore] = useState(false);

  return (
    <View className="absolute bottom-0 w-full" style={{ alignItems: "center" }}>
      <Image
        transition={200}
        style={{ width: 300, height: 692, opacity: scrollForMore ? 0.3 : 1 }}
        source={charData?.imageFull}
      />
      <View
        className="absolute w-full p-[24px] z-50"
        style={{
          height: Dimensions.get("window").height - 40,
        }}
      >
        <ScrollView
          onScroll={(e) => {
            if (e.nativeEvent.contentOffset.y > 0) {
              setScrollForMore(true);
            } else {
              setScrollForMore(false);
            }
          }}
        >
          <CharInfo />
          <View style={{ opacity: scrollForMore ? 1 : 0 }}>
            <CharAttribute />
            <CharMaterialList />
            <CharTrace />
            <CharSuggestLightCone />
            <CharSuggestRelics />
            <CharSuggestTeam />
            <CharStory />
            <View className="pb-[150px]" />
          </View>
        </ScrollView>
      </View>
      <CharAction show={!scrollForMore} />
    </View>
  );
}
