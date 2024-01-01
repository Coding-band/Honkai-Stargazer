import { Sword } from "phosphor-react-native";
import CharPageHeading from "../../../global/PageHeading/PageHeading";
import { ScrollView, View } from "react-native";
import CharSuggestLightConeCard from "./CharSuggestLightConeCard/CharSuggestLightConeCard";
import lightconeList from "../../../../../data/lightcone_data/lightcone_list.json";
import React from "react";
import {
  getCharAdviceData,
  getLcFullData,
} from "../../../../utils/dataMap/getDataFromMap";
import useCharId from "../../../../context/CharacterData/hooks/useCharId";
import LightconeNameMap from "../../../../../map/lightcone_name_map";
import { LightconeName } from "../../../../types/lightcone";
import useTextLanguage from "../../../../context/TextLanguage/useTextLanguage";
import Lightcone from "../../../../../assets/images/images_map/lightcone";

export default React.memo(function CharSuggestLightCone() {
  const { language: textLanguage } = useTextLanguage();

  const charId = useCharId();
  // @ts-ignore
  const suggestConesData = getCharAdviceData(charId)?.conesNew;
  const suggestCones = suggestConesData?.map((cone: any) => {
    // @ts-ignore
    const lcId: LightconeName = LightconeNameMap[cone.cone];
    const lcFullData = getLcFullData(lcId, textLanguage);

    return {
      id: lcId,
      rare: lightconeList.filter((lc) => lc.name === lcId)[0]?.rare,
      name: lcFullData?.name,
      description: lcFullData?.descHash,
      image: Lightcone[lcId]?.icon,
      path: lightconeList.filter((lc) => lc.name === lcId)[0]?.path,
    };
  });

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
          {suggestCones
            ?.slice()
            .sort((a: any, b: any) => b.rare - a.rare)
            .map((l: any, i: any) => (
              // @ts-ignore
              <CharSuggestLightConeCard key={i} {...l} />
            ))}
        </View>
      </ScrollView>
    </View>
  );
});
