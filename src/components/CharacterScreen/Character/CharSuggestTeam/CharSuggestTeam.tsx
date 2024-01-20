import { Text, View } from "react-native";
import React from "react";
import useAppLanguage from "../../../../language/AppLanguage/useAppLanguage";
import { LOCALES } from "../../../../../locales";
import CharPageHeading from "../../../../components/global/PageHeading/PageHeading";
import useCharId from "../../../../context/CharacterData/hooks/useCharId";
import { Person } from "phosphor-react-native";
import CharacterName from "../../../../../map/character_name_map";
import charAdviceMap from "../../../../../map/character_advice_map";
import CharSuggestTeamCard from "./CharSuggestTeamCard/CharSuggestTeamCard";
import CharacterImage from "../../../../../assets/images/images_map/chacracterImage";
import { CharacterName as CharNameType } from "../../../../types/character";
import useTextLanguage from "../../../../language/TextLanguage/useTextLanguage";
import {
  getCharFullData,
  getCharJsonData,
} from "../../../../utils/dataMap/getDataFromMap";

export default React.memo(function CharSuggestTeam() {
  const { language: textLanguage } = useTextLanguage();
  const { language: appLanguage } = useAppLanguage();

  const charId = useCharId();
  // @ts-ignore
  const suggestTeamsData = charAdviceMap[charId]?.teams?.map((team) => {
    // @ts-ignore
    const teamCharId1 = CharacterName[team.member_1] as CharNameType;
    // @ts-ignore
    const teamCharId2 = CharacterName[team.member_2] as CharNameType;
    // @ts-ignore
    const teamCharId3 = CharacterName[team.member_3] as CharNameType;
    // @ts-ignore
    const teamCharId4 = CharacterName[team.member_4] as CharNameType;

    const charJsonData1 = getCharJsonData(teamCharId1);
    const charJsonData2 = getCharJsonData(teamCharId2);
    const charJsonData3 = getCharJsonData(teamCharId3);
    const charJsonData4 = getCharJsonData(teamCharId4);

    const charFullData1 = getCharFullData(teamCharId1, textLanguage);
    const charFullData2 = getCharFullData(teamCharId2, textLanguage);
    const charFullData3 = getCharFullData(teamCharId3, textLanguage);
    const charFullData4 = getCharFullData(teamCharId4, textLanguage);

    return {
      name: team.name,
      team: [
        {
          id: teamCharId1,
          image: CharacterImage[teamCharId1]?.icon,
          rare: charJsonData1?.rare,
          name: charFullData1?.name,
          path: charJsonData1?.path,
          combatType: charJsonData1?.element,
        },
        {
          id: teamCharId2,
          image: CharacterImage[teamCharId2]?.icon,
          rare: charJsonData2?.rare,
          name: charFullData2?.name,
          path: charJsonData2?.path,
          combatType: charJsonData2?.element,
        },
        {
          id: teamCharId3,
          image: CharacterImage[teamCharId3]?.icon,
          rare: charJsonData3?.rare,
          name: charFullData3?.name,
          path: charJsonData3?.path,
          combatType: charJsonData3?.element,
        },
        {
          id: teamCharId4,
          image: CharacterImage[teamCharId4]?.icon,
          rare: charJsonData4?.rare,
          name: charFullData4?.name,
          path: charJsonData4?.path,
          combatType: charJsonData4?.element,
        },
      ],
    };
  });

  return (
    <View style={{ alignItems: "center" }}>
      <CharPageHeading Icon={Person}>
        {LOCALES[appLanguage].AdviceTeams}
      </CharPageHeading>
      {suggestTeamsData ? (
        <View
          style={{
            flexDirection: "row",
            justifyContent: "center",
            flexWrap: "wrap",
            rowGap: 16,
          }}
        >
          {suggestTeamsData?.map((team: any, i: number) => (
            // @ts-ignore
            <CharSuggestTeamCard key={i} team={team.team} name={team.name} />
          ))}
        </View>
      ) : (
        <Text className="text-text text-[HY65]">
          {LOCALES[appLanguage].NoDataYet}
        </Text>
      )}
    </View>
  );
});
