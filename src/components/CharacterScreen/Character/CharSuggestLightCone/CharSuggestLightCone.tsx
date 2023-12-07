import { Sword } from "phosphor-react-native";
import CharPageHeading from "../../../global/PageHeading/PageHeading";
import { ScrollView, View } from "react-native";
import CharSuggestLightConeCard from "./CharSuggestLightConeCard/CharSuggestLightConeCard";
import lightconeList from "../../../../../data/lightcone_data/lightcone_list.json";
import * as lightconeListMap from "../../../../../data/lightcone_data/@lightcone_data_map/lightcone_data_map";
import * as imagesMap from "../../../../../assets/images/@images_map/images_map";
import React from "react";

const testData = [
  {
    id: "Shattered Home",
    rare: lightconeList.filter((char) => char.name === "Shattered Home")[0]
      .rare,
    name: lightconeListMap.ZH_CN["Shattered Home"].name,
    image: imagesMap.Lightcone["Shattered Home"].icon,
    path: lightconeList.filter((char) => char.name === "Shattered Home")[0]
      .path,
  },
  {
    id: "Quid Pro Quo",
    rare: lightconeList.filter((char) => char.name === "Quid Pro Quo")[0].rare,
    name: lightconeListMap.ZH_CN["Quid Pro Quo"].name,
    image: imagesMap.Lightcone["Quid Pro Quo"].icon,
    path: lightconeList.filter((char) => char.name === "Quid Pro Quo")[0].path,
  },
  {
    id: "Fermata",
    rare: lightconeList.filter((char) => char.name === "Fermata")[0].rare,
    name: lightconeListMap.ZH_CN["Fermata"].name,
    image: imagesMap.Lightcone["Fermata"].icon,
    path: lightconeList.filter((char) => char.name === "Fermata")[0].path,
  },
  {
    id: "In the Night",
    rare: lightconeList.filter((char) => char.name === "In the Night")[0].rare,
    name: lightconeListMap.ZH_CN["In the Night"].name,
    image: imagesMap.Lightcone["In the Night"].icon,
    path: lightconeList.filter((char) => char.name === "In the Night")[0].path,
  },
];

export default React.memo(function CharSuggestLightCone() {
  return (
    <View style={{ alignItems: "center" }}>
      <CharPageHeading Icon={Sword}>推荐光锥</CharPageHeading>
      <View>
        <View
          style={{
            flexDirection: "row",
            flexWrap: "wrap",
            columnGap: 8,
          }}
        >
          {testData
            .slice()
            .sort((d) => 1 - d.rare)
            .map((l, i) => (
              // @ts-ignore
              <CharSuggestLightConeCard key={i} {...l} />
            ))}
        </View>
      </View>
    </View>
  );
});
