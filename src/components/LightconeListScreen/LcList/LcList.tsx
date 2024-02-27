import React, { useEffect, useMemo, useState } from "react";
import { ScrollView, View } from "react-native";
import { SCREENS } from "../../../constant/screens";
import { useNavigation } from "@react-navigation/native";
import lightconeList from "../../../../data/lightcone_data/lightcone_list.json";
import { LightconeName } from "../../../types/lightcone";
import LightConeCard from "../../global/LightConeCard/LightConeCard";
import { Path } from "../../../types/path";
import { getLcFullData } from "../../../utils/data/getDataFromMap";
import { getLcAttrData } from "../../../utils/calculator/getAttrData";
import useLcSorting from "../../../redux/lightconeSorting/useLcSorting";
import useLcSortingReverse from "../../../redux/lightconeSortingReverse/useLcSortingReverse";
import useLcFilter from "../../../redux/lightconeFilter/useLcFilter";
import useTextLanguage from "../../../language/TextLanguage/useTextLanguage";
import useLightconeSearch from "../../../redux/lightconeSearch/useLightconeSearch";
import Lightcone from "../../../../assets/images/images_map/lightcone";
import { ExpoImage } from "../../../types/image";
import { dynamicHeightScrollView } from "../../../constant/ui";

type LcListItem = {
  id: string;
  name: string;
  rare: number;
  path: Path;
  image: ExpoImage;
  atk: number;
  def: number;
  hp: number;
};

export default function LcList() {
  const navigation = useNavigation();
  const { language: textLanguage } = useTextLanguage();

  const [lcCardListData, setLcCardListData] = useState<LcListItem[]>();

  useEffect(() => {
    setLcCardListData(
      lightconeList.map((lc) => {
        const lcId = lc.name as LightconeName;
        const lcFullData = getLcFullData(lcId, textLanguage);
        const lcAttrData = getLcAttrData(lcId, 80);
        return {
          id: lcId,
          name: lcFullData?.name || lc.name,
          rare: lc.rare,
          path: lc.path as Path,
          image: Lightcone[lc.name as LightconeName]?.icon,
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
  const { searchValue } = useLightconeSearch();

  const lcCardListJSX = useMemo(() => {
    const sortData = (lcCardListData: LcListItem[] | undefined) => {
      type Data = LcListItem[] | undefined;

      //* 排序
      const sortStrategies = {
        rare: (data: Data) => data?.slice()?.sort((a, b) => b.rare - a.rare),
        // @ts-ignore
        name: (data: Data) =>
          data?.slice()?.sort((a, b) => {
            if (a.id < b.id) {
              return -1;
            }
            if (a.id > b.id) {
              return 1;
            }
            return 0;
          }),
        atk: (data: Data) => data?.slice()?.sort((a, b) => b.atk - a.atk),
        def: (data: Data) => data?.slice()?.sort((a, b) => b.def - a.def),
        hp: (data: Data) => data?.slice()?.sort((a, b) => b.hp - a.hp),
        time: (data: Data) => data, // No sorting for 'time'
      };
      let sortedData = sortStrategies[lcSorting?.id || "time"](lcCardListData);

      //* 反轉
      if (lcSortingReverse) {
        sortedData = sortedData?.slice()?.reverse();
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

      //* 搜尋

      if (searchValue) {
        sortedData = sortedData?.filter((data) =>
          data.name.includes(searchValue)
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
  }, [
    lcCardListData,
    lcSortingReverse,
    lcFilterSelected,
    searchValue,
    lcSorting?.id,
  ]);

  return (
    <ScrollView className={dynamicHeightScrollView}>
      <View
        style={{
          flexDirection: "row",
          flexWrap: "wrap",
          rowGap: 8,
          justifyContent: "center",
        }}
        className="pb-60"
      >
        {lcCardListJSX}
      </View>
    </ScrollView>
  );
}
