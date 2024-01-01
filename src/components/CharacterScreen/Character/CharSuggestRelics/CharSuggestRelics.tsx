import { View, StyleSheet, Text } from "react-native";
import React from "react";
import CharPageHeading from "../../../global/PageHeading/PageHeading";
import { BaseballCap } from "phosphor-react-native";
import { Image } from "expo-image";
import CharSuggestRelicsLeft from "./CharSuggestRelicsLeft/CharSuggestRelicsLeft";
import CharSuggestRelicsRight from "./CharSuggestRelicsRight/CharSuggestRelicsRight";
import CharSuggestRelicsProps from "./CharSuggestRelicsProps/CharSuggestRelicsProps";
<<<<<<< HEAD
import useAppLanguage from "../../../../context/AppLanguage/useAppLanguage";
import { LOCALES } from "../../../../../locales";
=======
import useCharId from "../../../../context/CharacterData/hooks/useCharId";
import charAdviceMap from "../../../../../map/character_advice_map";
import { LOCALES } from "../../../../../locales";
import useAppLanguage from "../../../../context/AppLanguage/useAppLanguage";
>>>>>>> 884dc9966fac382282b4c53713889206665746a6

// Relic 遺器套裝
// Ornaments 位面飾品
// 通稱 Relic 遺器

const AddIcon = require("../../../../../assets/icons/Add.svg");

export default React.memo(function CharSuggestRelics() {
<<<<<<< HEAD
  const { language } = useAppLanguage();
  return (
    <View style={{ alignItems: "center" }}>
      <CharPageHeading Icon={BaseballCap}>{LOCALES[language].AdviceRelics}</CharPageHeading>
      <View className="w-full" style={styles.lightconeImages}>
        <CharSuggestRelicsLeft />
        <View className="translate-y-[14px]">
          <AddIconComponent />
=======
  const { language: appLanguage } = useAppLanguage();

  const charId = useCharId();
  const advices = charAdviceMap[charId];
  const suggestRelics = advices?.relics!;

  return (
    <View style={{ alignItems: "center" }}>
      <CharPageHeading Icon={BaseballCap}>推荐遗器</CharPageHeading>
      {suggestRelics ? (
        <View className="w-full" style={styles.lightconeImages}>
          <CharSuggestRelicsLeft />
          <View className="translate-y-[14px]">
            <AddIconComponent />
          </View>
          <CharSuggestRelicsRight />
>>>>>>> 884dc9966fac382282b4c53713889206665746a6
        </View>
      ) : (
        <Text className="text-text text-[HY65]">
          {LOCALES[appLanguage].NoDataYet}
        </Text>
      )}
      <CharSuggestRelicsProps />
    </View>
  );
});

const styles = StyleSheet.create({
  lightconeImages: {
    flexDirection: "row",
    justifyContent: "center",
    gap: 4,
  },
  lightconeImagesContainer: {
    flexDirection: "row",
    flexWrap: "wrap",
    columnGap: 8,
  },
});

const AddIconComponent = () => {
  return (
    <Image
      className="translate-y-8"
      style={{ width: 13, height: 13 }}
      source={AddIcon}
    />
  );
};
