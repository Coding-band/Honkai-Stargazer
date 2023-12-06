import { View } from "react-native";
import React from "react";
import CharPageHeading from "../../../global/PageHeading/PageHeading";
import { Person } from "phosphor-react-native";
import CharSuggestTeamCard from "./CharSuggestTeamCard/CharSuggestTeamCard";

import characterList from "../../../../../data/character_data/character_list.json";
import * as characterListMap from "../../../../../data/character_data/@character_list_map/character_list_map";
import * as imagesMap from "../../../../../assets/images/@images_map/images_map";

const testData = [
  [
    {
      id: "Jing Yuan",
      rare: characterList.filter((char) => char.name === "Jing Yuan")[0].rare,
      name: characterListMap.ZH_CN["Jing Yuan"].name,
      image: imagesMap.Chacracter["Jing Yuan"].icon,
    },
    {
      id: "Hook",
      rare: characterList.filter((char) => char.name === "Hook")[0].rare,
      name: characterListMap.ZH_CN["Hook"].name,
      image: imagesMap.Chacracter["Hook"].icon,
    },
    {
      id: "Pela",
      rare: characterList.filter((char) => char.name === "Pela")[0].rare,
      name: characterListMap.ZH_CN["Pela"].name,
      image: imagesMap.Chacracter["Pela"].icon,
    },
    {
      id: "Seele",
      rare: characterList.filter((char) => char.name === "Seele")[0].rare,
      name: characterListMap.ZH_CN["Seele"].name,
      image: imagesMap.Chacracter["Seele"].icon,
    },
  ],
  [
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
