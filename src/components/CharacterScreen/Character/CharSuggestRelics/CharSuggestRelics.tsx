import { View, Text, ScrollView, StyleSheet } from "react-native";
import React from "react";
import CharPageHeading from "../../../global/PageHeading/PageHeading";
import { BaseballCap } from "phosphor-react-native";
import RelicsCard from "../../../global/RelicsCard/RelicsCard";
import { Image } from "expo-image";
import useCharId from "../../../../context/CharacterData/useCharId";
import { getCharAdviceData } from "../../../../utils/dataMap/getDataFromMap";
import useTextLanguage from "../../../../context/TextLanguage/useTextLanguage";
import CharSuggestRelicsLeft from "./CharSuggestRelicsLeft/CharSuggestRelicsLeft";
import LeftBtn from "./ui/LeftBtn";
import RightBtn from "./ui/RightBtn";
import CharSuggestRelicsRight from "./CharSuggestRelicsRight/CharSuggestRelicsRight";
import CharSuggestRelicsProps from "./CharSuggestRelicsProps/CharSuggestRelicsProps";

// Relic 遺器套裝
// Ornaments 位面飾品
// 通稱 Relic 遺器

const AddIcon = require("../../../../../assets/icons/Add.svg");

const testMain = [
  { title: "躯干", description: "暴击，暴伤" },
  { title: "躯干", description: "暴击，暴伤" },
  { title: "躯干", description: "暴击，暴伤" },
  { title: "躯干", description: "暴击，暴伤" },
];
const testSecond = "暴击率，速度，暴击伤害，攻击力";

export default React.memo(function CharSuggestRelics() {
  return (
    <View style={{ alignItems: "center" }}>
      <CharPageHeading Icon={BaseballCap}>推荐遗器</CharPageHeading>
      <View className="w-full" style={styles.lightconeImages}>
        <CharSuggestRelicsLeft />
        <View className="translate-y-[14px]">
          <AddIconComponent />
        </View>
        <CharSuggestRelicsRight />
      </View>
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
