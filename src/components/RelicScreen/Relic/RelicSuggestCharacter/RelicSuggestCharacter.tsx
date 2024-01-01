import { Sword } from "phosphor-react-native";
import PageHeading from "../../../global/PageHeading/PageHeading";
import { ScrollView, View } from "react-native";
import characterList from "../../../../../data/character_data/character_list.json";
import characterListMap from "../../../../../map/character_data_map";
import LcSuggestCharacterCard from "./RelicSuggestCharacterCard/RelicSuggestCharacterCard";
import RelicSuggestCharacterCard from "./RelicSuggestCharacterCard/RelicSuggestCharacterCard";
import CharacterImage from "../../../../../assets/images/images_map/chacracterImage";
import useAppLanguage from "../../../../context/AppLanguage/useAppLanguage";
import { LOCALES } from "../../../../../locales";

const testData = [
  {
    id: "March 7th",
    rare: characterList.filter((char) => char.name === "March 7th")[0].rare,
    name: characterListMap.zh_cn["March 7th"].name,
    image: CharacterImage["March 7th"].icon,
    combatType: characterList.filter((char) => char.name === "March 7th")[0]
      .element,
    path: characterList.filter((char) => char.name === "March 7th")[0].path,
  },
  {
    id: "Serval",
    rare: characterList.filter((char) => char.name === "Serval")[0].rare,
    name: characterListMap.zh_cn["Serval"].name,
    image: CharacterImage["Serval"].icon,
    combatType: characterList.filter((char) => char.name === "Serval")[0]
      .element,
    path: characterList.filter((char) => char.name === "Serval")[0].path,
  },
  {
    id: "Clara",
    rare: characterList.filter((char) => char.name === "Clara")[0].rare,
    name: characterListMap.zh_cn["Clara"].name,
    image: CharacterImage["Clara"].icon,
    combatType: characterList.filter((char) => char.name === "Clara")[0]
      .element,
    path: characterList.filter((char) => char.name === "Clara")[0].path,
  },
  {
    id: "Bailu",
    rare: characterList.filter((char) => char.name === "Bailu")[0].rare,
    name: characterListMap.zh_cn["Bailu"].name,
    image: CharacterImage["Bailu"].icon,
    combatType: characterList.filter((char) => char.name === "Bailu")[0]
      .element,
    path: characterList.filter((char) => char.name === "Bailu")[0].path,
  },
];

export default function RelicSuggestCharacter() {
  const {language} = useAppLanguage();
  return (
    <View style={{ alignItems: "center" }}>
      <PageHeading Icon={Sword}>{LOCALES[language].AdviceCharacters}</PageHeading>
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
            <RelicSuggestCharacterCard key={i} {...l} />
          ))}
        </View>
      </ScrollView>
    </View>
  );
}
