import { Sword } from "phosphor-react-native";
import CharPageHeading from "../../../global/PageHeading/PageHeading";
import { ScrollView, Text, View } from "react-native";
import CharSuggestLightConeCard from "./CharSuggestLightConeCard/CharSuggestLightConeCard";
import lightconeList from "../../../../../data/lightcone_data/lightcone_list.json";
import React from "react";
import { getLcFullData } from "../../../../utils/data/getDataFromMap";
import useCharId from "../../../../context/CharacterData/hooks/useCharId";
import LightconeNameMap from "../../../../../map/lightcone_name_map";
import { LightconeName } from "../../../../types/lightcone";
import useTextLanguage from "../../../../language/TextLanguage/useTextLanguage";
import Lightcone from "../../../../../assets/images/images_map/lightcone";
import charAdviceMap from "../../../../../map/character_advice_map";
import { LOCALES } from "../../../../../locales";
import useAppLanguage from "../../../../language/AppLanguage/useAppLanguage";

export default React.memo(function CharSuggestLightCone() {
  const { language: textLanguage } = useTextLanguage();
  const { language: appLanguage } = useAppLanguage();
  const charId = useCharId();
  // @ts-ignore
  const suggestConesData = charAdviceMap[charId]?.conesNew;
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
    <View className="px-4" style={{ alignItems: "center" }}>
      <CharPageHeading Icon={Sword}>
        {LOCALES[appLanguage].AdviceLightcones}
      </CharPageHeading>
      {suggestCones ? (
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
      ) : (
        <Text className="text-text text-[HY65]">
          {LOCALES[appLanguage].NoDataYet}
        </Text>
      )}
    </View>
  );
});
