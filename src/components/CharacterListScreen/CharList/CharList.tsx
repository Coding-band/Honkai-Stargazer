import React, { useEffect, useMemo, useState } from "react";
import { ScrollView, TextInput, View } from "react-native";
import CharCard from "../../global/CharCard/CharCard";
import { SCREENS } from "../../../constant/screens";
import { useNavigation } from "@react-navigation/native";
import characterList from "../../../../data/character_data/character_list.json";
import { CharacterName } from "../../../types/character";
import { CombatType } from "../../../types/combatType";
import { Path } from "../../../types/path";
import useCharSorting from "../../../redux/characterSorting/useCharSorting";
import { getCharFullData } from "../../../utils/data/getDataFromMap";
import { getCharAttrData } from "../../../utils/calculator/getAttrData";
import useCharSortingReverse from "../../../redux/characterSortingReverse/useCharSortingReverse";
import useCharFilter from "../../../redux/characterFilter/useCharFilter";
import { COMBATTYPES } from "../../../constant/combatType";
import _ from "lodash";
import { PATHS } from "../../../constant/path";
import useTextLanguage from "../../../language/TextLanguage/useTextLanguage";
import useCharacterSearch from "../../../redux/characterSearch/useCharacterSearch";
import CharacterImage from "../../../../assets/images/images_map/chacracterImage";
import { ExpoImage } from "../../../types/image";
import DeviceInfo from "react-native-device-info";
import { dynamicHeightScrollView } from "../../../constant/ui";

type CharListItem = {
  id: CharacterName;
  name: string;
  rare: number;
  path: Path;
  combatType: CombatType;
  image: ExpoImage;
  version: string;
  atk: number;
  def: number;
  hp: number;
  energy: number;
};

export default function CharList() {
  const navigation = useNavigation();
  const { language: textLanguage } = useTextLanguage();

  const [charCardListData, setCharCardListData] = useState<CharListItem[]>();

  useEffect(() => {
    setCharCardListData(
      characterList.map((char) => {
        const charId = char.name as CharacterName;
        const charFullData = getCharFullData(charId, textLanguage);
        const charAttrData = getCharAttrData(charId, 80);
        return {
          id: charId,
          name: charFullData?.name || char.name,
          rare: char.rare,
          combatType: char.element as CombatType,
          path: char.path as Path,
          image: CharacterImage[char.name as CharacterName]?.icon,
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
  const { searchValue } = useCharacterSearch();

  const charCardListJSX = useMemo(() => {
    const sortData = (charCardListData: CharListItem[] | undefined) => {
      type Data = CharListItem[] | undefined;

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
        energy: (data: Data) =>
          data?.slice()?.sort((a, b) => b.energy - a.energy),
        time: (data: Data) => data, // No sorting for 'time'
      };
      let sortedData =
        sortStrategies[charSorting?.id || "time"](charCardListData);

      //* 反轉
      if (charSortingReverse) {
        sortedData = sortedData?.slice()?.reverse();
      }

      //* 過濾

      // 無選取
      if (!charFilterSelected?.length) {
      }
      // 只選單邊
      else if (
        (!!_.intersection(COMBATTYPES, charFilterSelected).length &&
          !_.intersection(PATHS, charFilterSelected).length) ||
        (!_.intersection(COMBATTYPES, charFilterSelected).length &&
          !!_.intersection(PATHS, charFilterSelected).length)
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

      //* 搜尋

      if (searchValue) {
        sortedData = sortedData?.filter((data) =>
          data.name.includes(searchValue)
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
    searchValue,
    charSorting?.id,
  ]);

  return (
    <ScrollView className={dynamicHeightScrollView}>
      <View
        style={{
          flexDirection: "row",
          flexWrap: "wrap",
          gap: 11,
          justifyContent: "center",
        }}
        className="pb-60"
      >
        {charCardListJSX}
      </View>
    </ScrollView>
  );
}
