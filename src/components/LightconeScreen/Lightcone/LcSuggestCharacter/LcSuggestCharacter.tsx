import { Sword } from "phosphor-react-native";
import PageHeading from "../../../global/PageHeading/PageHeading";
import { ScrollView, View } from "react-native";
import characterList from "../../../../../data/character_data/character_list.json";
import characterListMap from "../../../../../map/character_data_map";
import * as imagesMap from "../../../../../assets/images/images_map";
import LcSuggestCharacterCard from "./LcSuggestCharacterCard/LcSuggestCharacterCard";

const testData = [
  {
    id: "March 7th",
    rare: characterList.filter((char) => char.name === "March 7th")[0].rare,
    name: characterListMap.zh_cn["March 7th"].name,
    image: imagesMap.Chacracter["March 7th"].icon,
    combatType: characterList.filter((char) => char.name === "March 7th")[0]
      .element,
    path: characterList.filter((char) => char.name === "March 7th")[0].path,
  },
  {
    id: "Serval",
    rare: characterList.filter((char) => char.name === "Serval")[0].rare,
    name: characterListMap.zh_cn["Serval"].name,
    image: imagesMap.Chacracter["Serval"].icon,
    combatType: characterList.filter((char) => char.name === "Serval")[0]
      .element,
    path: characterList.filter((char) => char.name === "Serval")[0].path,
  },
  {
    id: "Clara",
    rare: characterList.filter((char) => char.name === "Clara")[0].rare,
    name: characterListMap.zh_cn["Clara"].name,
    image: imagesMap.Chacracter["Clara"].icon,
    combatType: characterList.filter((char) => char.name === "Clara")[0]
      .element,
    path: characterList.filter((char) => char.name === "Clara")[0].path,
  },
  {
    id: "Bailu",
    rare: characterList.filter((char) => char.name === "Bailu")[0].rare,
    name: characterListMap.zh_cn["Bailu"].name,
    image: imagesMap.Chacracter["Bailu"].icon,
    combatType: characterList.filter((char) => char.name === "Bailu")[0]
      .element,
    path: characterList.filter((char) => char.name === "Bailu")[0].path,
  },
];

export default function LcSuggestCharacter() {
  return (
    <View style={{ alignItems: "center" }}>
      <PageHeading Icon={Sword}>推荐角色</PageHeading>
      <ScrollView horizontal>
        <View
          style={{
            flexDirection: "row",
            flexWrap: "wrap",
            columnGap: 8,
          }}
        >
          {testData.map((l, i) => (
            // @ts-ignore
            <LcSuggestCharacterCard key={i} {...l} />
          ))}
        </View>
      </ScrollView>
    </View>
  );
}
