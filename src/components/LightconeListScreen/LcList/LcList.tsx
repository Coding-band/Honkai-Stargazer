import React, { useEffect, useMemo, useState } from "react";
import { ScrollView, View } from "react-native";
import { SCREENS } from "../../../constant/screens";
import { useNavigation } from "@react-navigation/native";

import lightconeList from "../../../../data/lightcone_data/lightcone_list.json";
import * as lightconeListMap from "../../../../data/lightcone_data/@lightcone_data_map/lightcone_data_map";
import * as images_map from "../../../../assets/images/@images_map/images_map";
import { LightconeCard, LightconeName } from "../../../types/lightcone";
import LightConeCard from "../../global/LightConeCard/LightConeCard";
import { Path } from "../../../types/path";
import { getLcFullData } from "../../../utils/dataMap/getDataFromMap";
import { getLcAttrData } from "../../../utils/calculator/getAttrData";
import { CombatType } from "../../../types/combatType";
import useLcSorting from "../../../redux/lightconeSorting/useLcSorting";
import useLcSortingReverse from "../../../redux/lightconeSortingReverse/useLcSortingReverse";
import useLcFilter from "../../../redux/lightconeFilter/useLcFilter";
import LcAction from "../LcAction/LcAction";

export default function LcList() {
  const navigation = useNavigation();

  const [lcCardListData, setLcCardListData] = useState<LightconeCard[]>();

  useEffect(() => {
    setLcCardListData(
      lightconeList.map((lc) => {
        const lcId = lc.name as LightconeName;
        const lcFullData = getLcFullData(lcId);
        const lcAttrData = getLcAttrData(lcId, 80);
        return {
          id: lcId,
          name: lcFullData?.name || lc.name,
          rare: lc.rare,
          path: lc.path as Path,
          image: images_map.Lightcone[lc.name as LightconeName]?.icon,
          version: lc.version,
          atk: lcAttrData.atk,
          def: lcAttrData.def,
          hp: lcAttrData.hp,
        };
      })
    );
  }, []);

  const { lcSorting } = useLcSorting();
  const { lcSortingReverse } = useLcSortingReverse();
  const { lcFilterSelected } = useLcFilter();

  const lcCardListJSX = useMemo(() => {
    const sortData = (lcCardListData: LightconeCard[] | undefined) => {
      type Data = LightconeCard[] | undefined;

      //* 排序
      const sortStrategies = {
        rare: (data: Data) => data?.slice()?.sort((a, b) => b.rare - a.rare),
        // @ts-ignore
        name: (data: Data) => data?.slice()?.sort((a, b) => b.id < a.id),
        atk: (data: Data) => data?.slice()?.sort((a, b) => b.atk - a.atk),
        def: (data: Data) => data?.slice()?.sort((a, b) => b.def - a.def),
        hp: (data: Data) => data?.slice()?.sort((a, b) => b.hp - a.hp),
        time: (data: Data) => data, // No sorting for 'time'
      };
      let sortedData = sortStrategies[lcSorting?.id || "time"](lcCardListData);

      //* 反轉
      if (lcSortingReverse) {
        sortedData?.reverse();
      }

      //* 過濾

      // 無選取
      if (!lcFilterSelected?.length) {
      }
      // 兩邊都選
      else {
        sortedData = sortedData?.filter((data) =>
          lcFilterSelected?.includes(data.path)
        );
      }

      return sortedData;
    };

    const sortedData = sortData(lcCardListData);

    return sortedData?.map((item, i) => (
      <LightConeCard
        key={i}
        onPress={() => {
          // @ts-ignore
          navigation.navigate(SCREENS.LightconePage.id, {
            id: item?.id,
            name: item?.name,
          });
        }}
        {...item}
      />
    ));
  }, [lcCardListData, lcSortingReverse, lcFilterSelected, lcSorting?.id]);

  return (
    <>
      <View style={{ width: "100%" }} className="z-30">
        <ScrollView style={{ padding: 17, paddingBottom: 0 }}>
          <View
            style={{
              paddingVertical: 110,
              flexDirection: "row",
              flexWrap: "wrap",
              gap: 11,
              justifyContent: "center",
            }}
          >
            {lcCardListJSX}
          </View>
        </ScrollView>
      </View>
      <LcAction />
    </>
  );
}
