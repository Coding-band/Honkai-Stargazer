import { Sword } from "phosphor-react-native";
import CharPageHeading from "../../../global/PageHeading/PageHeading";
import { ScrollView, View } from "react-native";
import CharSuggestLightConeCard from "./LcSuggestCharacterCard/LcSuggestCharacterCard";
import characterList from "../../../../../data/character_data/character_list.json";
import * as characterListMap from "../../../../../data/character_data/character_list_map/character_list_map";
import * as imagesMap from "../../../../../assets/images/images_map/images_map";
import LcSuggestCharacterCard from "./LcSuggestCharacterCard/LcSuggestCharacterCard";

const testData = [
  {
    id: "March 7th",
    rare: characterList.filter((char) => char.name === "March 7th")[0].rare,
    name: characterListMap.ZH_CN["March 7th"].name,
    image: imagesMap.Chacracter["March 7th"].icon,
  },
  {
    id: "Serval",
    rare: characterList.filter((char) => char.name === "Serval")[0].rare,
    name: characterListMap.ZH_CN["Serval"].name,
    image: imagesMap.Chacracter["Serval"].icon,
  },
  {
    id: "Clara",
    rare: characterList.filter((char) => char.name === "Clara")[0].rare,
    name: characterListMap.ZH_CN["Clara"].name,
    image: imagesMap.Chacracter["Clara"].icon,
  },
  {
    id: "Bailu",
    rare: characterList.filter((char) => char.name === "Bailu")[0].rare,
    name: characterListMap.ZH_CN["Bailu"].name,
    image: imagesMap.Chacracter["Bailu"].icon,
  },
];

export default function LcSuggestCharacter() {
  return (
    <View style={{ alignItems: "center" }}>
      <CharPageHeading Icon={Sword}>推荐光锥</CharPageHeading>
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
