import { View, ScrollView } from "react-native";
import React from "react";
import PageHeading from "../../../global/PageHeading/PageHeading";
import { ChatCircle } from "phosphor-react-native";
import useRelicData from "../../../../context/RelicData/hooks/useRelicData";
import { map } from "lodash";
import RelicDetailsCard from "./RelicDetailsCard/CharSuggestLightConeCard";
import useAppLanguage from "../../../../context/AppLanguage/useAppLanguage";
import { LOCALES } from "../../../../../locales";

export default function RelicDetails() {
  const { relicData, relicFullData } = useRelicData();
  const {language} = useAppLanguage();
  return (
    <View style={{ alignItems: "center" }}>
      <PageHeading Icon={ChatCircle}>{LOCALES[language].RelicDetail}</PageHeading>
      <ScrollView horizontal>
        <View
          style={{
            flexDirection: "row",
            flexWrap: "wrap",
            columnGap: 8,
          }}
        >
          {map(relicFullData.pieces, (p: any, k) => {
            if (relicFullData.pageId.startsWith("3")) {
              return (
                <RelicDetailsCard
                  key={k}
                  name={p.name}
                  rare={5}
                  image={relicData?.imageFull?.[Number(k) - 4 - 1]}
                  description={p.miniLore}
                />
              );
            } else {
              return (
                <RelicDetailsCard
                  key={k}
                  name={p.name}
                  rare={5}
                  image={relicData?.imageFull?.[Number(k) - 1]}
                  description={p.miniLore}
                />
              );
            }
          })}
        </View>
      </ScrollView>
    </View>
  );
}
