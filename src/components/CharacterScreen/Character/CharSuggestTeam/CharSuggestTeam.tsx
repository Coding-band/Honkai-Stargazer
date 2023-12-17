import { View } from "react-native";
import React from "react";
import CharPageHeading from "../../../global/PageHeading/PageHeading";
import { Person } from "phosphor-react-native";
import CharSuggestTeamCard from "./CharSuggestTeamCard/CharSuggestTeamCard";

import characterList from "../../../../../data/character_data/character_list.json";
import characterListMap from "../../../../../data/character_data/@character_data_map/character_data_map";
import * as imagesMap from "../../../../../assets/images/@images_map/images_map";

const testData = [
  [
    {
      id: "Jing Yuan",
      rare: characterList.filter((char) => char.name === "Jing Yuan")[0].rare,
      name: characterListMap.zh_cn["Jing Yuan"].name,
      image: imagesMap.Chacracter["Jing Yuan"].icon,
      path: characterList.filter((char) => char.name === "Jing Yuan")[0].path,
      combatType: characterList.filter((char) => char.name === "Jing Yuan")[0]
        .element,
    },
    {
      id: "Hook",
      rare: characterList.filter((char) => char.name === "Hook")[0].rare,
      name: characterListMap.zh_cn["Hook"].name,
      image: imagesMap.Chacracter["Hook"].icon,
      path: characterList.filter((char) => char.name === "Hook")[0].path,
      combatType: characterList.filter((char) => char.name === "Hook")[0]
        .element,
    },
    {
      id: "Pela",
      rare: characterList.filter((char) => char.name === "Pela")[0].rare,
      name: characterListMap.zh_cn["Pela"].name,
      image: imagesMap.Chacracter["Pela"].icon,
      path: characterList.filter((char) => char.name === "Pela")[0].path,
      combatType: characterList.filter((char) => char.name === "Pela")[0]
        .element,
    },
    {
      id: "Seele",
      rare: characterList.filter((char) => char.name === "Seele")[0].rare,
      name: characterListMap.zh_cn["Seele"].name,
      image: imagesMap.Chacracter["Seele"].icon,
      path: characterList.filter((char) => char.name === "Seele")[0].path,
      combatType: characterList.filter((char) => char.name === "Seele")[0]
        .element,
    },
  ],
  [
    {
      id: "March 7th",
      rare: characterList.filter((char) => char.name === "March 7th")[0].rare,
      name: characterListMap.zh_cn["March 7th"].name,
      image: imagesMap.Chacracter["March 7th"].icon,
      path: characterList.filter((char) => char.name === "March 7th")[0].path,
      combatType: characterList.filter((char) => char.name === "March 7th")[0]
        .element,
    },
    {
      id: "Serval",
      rare: characterList.filter((char) => char.name === "Serval")[0].rare,
      name: characterListMap.zh_cn["Serval"].name,
      image: imagesMap.Chacracter["Serval"].icon,
      path: characterList.filter((char) => char.name === "Serval")[0].path,
      combatType: characterList.filter((char) => char.name === "Serval")[0]
        .element,
    },
    {
      id: "Clara",
      rare: characterList.filter((char) => char.name === "Clara")[0].rare,
      name: characterListMap.zh_cn["Clara"].name,
      image: imagesMap.Chacracter["Clara"].icon,
      path: characterList.filter((char) => char.name === "Clara")[0].path,
      combatType: characterList.filter((char) => char.name === "Clara")[0]
        .element,
    },
    {
      id: "Bailu",
      rare: characterList.filter((char) => char.name === "Bailu")[0].rare,
      name: characterListMap.zh_cn["Bailu"].name,
      image: imagesMap.Chacracter["Bailu"].icon,
      path: characterList.filter((char) => char.name === "Bailu")[0].path,
      combatType: characterList.filter((char) => char.name === "Bailu")[0]
        .element,
    },
  ],
];

export default React.memo(function CharSuggestTeam() {
  return (
    <View style={{ alignItems: "center" }}>
      <CharPageHeading Icon={Person}>推荐队伍</CharPageHeading>
      <View
        style={{
          flexDirection: "row",
          flexWrap: "wrap",
          rowGap: 16,
        }}
      >
        {testData.map((team, i) => (
          // @ts-ignore
          <CharSuggestTeamCard key={i} team={team} />
        ))}
      </View>
    </View>
  );
});
