import React, { useEffect, useMemo, useState } from "react";
import { ScrollView, View } from "react-native";
import { SCREENS } from "../../../constant/screens";
import { useNavigation } from "@react-navigation/native";

import lightconeList from "../../../../data/lightcone_data/lightcone_list.json";
import * as lightconeListMap from "../../../../data/lightcone_data/lightcone_list_map/lightcone_list_map";
import * as images_map from "../../../../assets/images/images_map/images_map";
import { LightconeCard } from "../../../types/lightcone";
import LightConeCard from "../../global/LightConeCard/LightConeCard";

type Props = {
  reverse?: boolean;
};

export default function LcList(props: Props) {
  const navigation = useNavigation();

  // get lightcone' data
  const [lcCardListData, setLcCardListData] = useState<LightconeCard[]>();
  useEffect(() => {
    setLcCardListData(
      lightconeList.map((lc) => ({
        id: lc.name,
        name:
          // @ts-ignore
          lightconeListMap.ZH_CN[lc.name]?.name || lc.name,
        rare: lc.rare,
        // @ts-ignore
        image: images_map.Lightcone[lc.name]?.icon,
      }))
    );
  }, []);

  const lcCardListJSX = useMemo(
    () =>
      lcCardListData?.map((item, i) => (
        <LightConeCard
          key={i}
          onPress={() => {
            // @ts-ignore
            navigation.navigate(SCREENS.LightconeScreen.id, {
              id: item?.id,
              name: item?.name,
            });
          }}
          {...item}
        />
      )),
    [lcCardListData]
  );

  return (
    <View style={{ width: "100%" }} className="z-30">
      <ScrollView style={{ padding: 17, paddingBottom: 0 }}>
        <View
          style={{
            paddingVertical: 110,
            flexDirection: "row",
            flexWrap: "wrap",
            gap: 12,
            justifyContent: "center",
          }}
        >
          {props.reverse ? lcCardListJSX?.slice().reverse() : lcCardListJSX}
        </View>
      </ScrollView>
    </View>
  );
}
