import { View } from "react-native";
import React from "react";
import CharPageHeading from "../../../global/PageHeading/PageHeading";
import { Person } from "phosphor-react-native";
import CharSuggestTeamCard from "./CharSuggestTeamCard/CharSuggestTeamCard";
import useCharId from "../../../../context/CharacterData/hooks/useCharId";
import charAdviceMap from "../../../../../map/character_advice_map";

export default React.memo(function CharSuggestTeam() {
  const charId = useCharId();
  // @ts-ignore
  const suggestTeamsData = charAdviceMap[charId]?.teams;


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
