import React, { useCallback, useEffect, useMemo, useState } from "react";
import { ScrollView, View } from "react-native";
import CharCard from "../../global/CharCard/CharCard";
import { SCREENS } from "../../../constant/screens";
import { useNavigation } from "@react-navigation/native";
import characterList from "../../../../data/character_data/character_list.json";
import * as images_map from "../../../../assets/images/@images_map/images_map";
import { CharacterCard, CharacterName } from "../../../types/character";
import { CombatType } from "../../../types/combatType";
import { Path } from "../../../types/path";
import CharAction from "../CharAction/CharAction";
import useCharSorting from "../../../redux/characterSorting/useCharSorting";
import { getCharFullData } from "../../../utils/dataMap/getDataFromMap";
import { getCharAttrData } from "../../../utils/calculator/getAttrData";
import useCharSortingReverse from "../../../redux/characterSortingReverse/useCharSortingReverse";
import useCharFilter from "../../../redux/characterFilter/useCharFilter";
import combatType from "../../../constant/combatType";
import _ from "lodash";
import path from "../../../constant/path";

export default function CharList() {
  const navigation = useNavigation();

  const [charCardListData, setCharCardListData] = useState<CharacterCard[]>();

  useEffect(() => {
    setCharCardListData(
      characterList.map((char) => {
        const charId = char.name as CharacterName;
        const charFullData = getCharFullData(charId);
        const charAttrData = getCharAttrData(charId, 80);
        return {
          id: charId,
          name: charFullData?.name || char.name,
          rare: char.rare,
          combatType: char.element as CombatType,
          path: char.path as Path,
          image: images_map.Chacracter[char.name as CharacterName]?.icon,
          version: char.version,
          atk: charAttrData.atk,
          def: charAttrData.def,
          hp: charAttrData.hp,
          energy: charAttrData.energy,
        };
      })
    );
  }, []);

  const { charSorting } = useCharSorting();
  const { charSortingReverse } = useCharSortingReverse();
  const { charFilterSelected } = useCharFilter();

  const charCardListJSX = useMemo(() => {
    const sortData = (charCardListData: CharacterCard[] | undefined) => {
      type Data = CharacterCard[] | undefined;

      //* 排序
      const sortStrategies = {
        rare: (data: Data) => data?.slice()?.sort((a, b) => b.rare - a.rare),
        // @ts-ignore
        name: (data: Data) => data?.slice()?.sort((a, b) => b.id < a.id),
        atk: (data: Data) => data?.slice()?.sort((a, b) => b.atk - a.atk),
        def: (data: Data) => data?.slice()?.sort((a, b) => b.def - a.def),
        hp: (data: Data) => data?.slice()?.sort((a, b) => b.hp - a.hp),
        energy: (data: Data) =>
          data?.slice()?.sort((a, b) => b.energy - a.energy),
        time: (data: Data) => data, // No sorting for 'time'
      };
      let sortedData =
        sortStrategies[charSorting?.id || "time"](charCardListData);

      //* 反轉
      if (charSortingReverse) {
        sortedData?.reverse();
      }

      //* 過濾

      // 無選取
      if (!charFilterSelected?.length) {
      }
      // 只選單邊
      else if (
        (!!_.intersection(combatType, charFilterSelected).length &&
          !_.intersection(path, charFilterSelected).length) ||
        (!_.intersection(combatType, charFilterSelected).length &&
          !!_.intersection(path, charFilterSelected).length)
      ) {
        sortedData = sortedData?.filter(
          (data) =>
            charFilterSelected?.includes(data.combatType) ||
            charFilterSelected?.includes(data.path)
        );
      }
      // 兩邊都選
      else {
        sortedData = sortedData?.filter(
          (data) =>
            charFilterSelected?.includes(data.combatType) &&
            charFilterSelected?.includes(data.path)
        );
      }

      return sortedData;
    };

    const sortedData = sortData(charCardListData);

    return sortedData?.map((item, i) => (
      <CharCard
        key={i}
        onPress={() => {
          // @ts-ignore
          navigation.navigate(SCREENS.CharacterPage.id, {
            id: item?.id,
            name: item?.name,
          });
        }}
        {...item}
      />
    ));
  }, [
    charCardListData,
    charSortingReverse,
    charFilterSelected,
    charSorting?.id,
  ]);

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
            {charCardListJSX}
          </View>
        </ScrollView>
      </View>
      <CharAction />
    </>
  );
}
