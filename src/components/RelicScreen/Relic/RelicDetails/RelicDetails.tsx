import { View, ScrollView } from "react-native";
import React from "react";
import PageHeading from "../../../global/PageHeading/PageHeading";
import { ChatCircle } from "phosphor-react-native";
import useRelicData from "../../../../context/RelicData/useRelicData";
import { map } from "lodash";
import RelicsCard from "../../../global/RelicsCard/RelicsCard";

export default function RelicDetails() {
  const { relicData, relicFullData } = useRelicData();
  return (
    <View style={{ alignItems: "center" }}>
      <PageHeading Icon={ChatCircle}>推荐角色</PageHeading>
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
                <RelicsCard
                  name={p.name}
                  rare={5}
                  image={relicData?.imageFull?.[Number(k) - 4 - 1]}
                />
              );
            } else {
              return (
                <RelicsCard
                  name={p.name}
                  rare={5}
                  image={relicData?.imageFull?.[Number(k) - 1]}
                />
              );
            }
          })}
        </View>
      </ScrollView>
    </View>
  );
}
