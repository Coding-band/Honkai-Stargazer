import React, { useEffect, useMemo, useState } from "react";
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

export default function CharList() {
  const navigation = useNavigation();

  // get characters' data
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

  const charCardListJSX = useMemo(() => {
    if (charSorting?.id === "rare") {
      return charCardListData
        ?.slice()
        ?.sort((a, b) => b.rare - a.rare)
        ?.map((item, i) => (
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
    } else if (charSorting?.id === "name") {
      return (
        charCardListData
          ?.slice()
          // @ts-ignore
          ?.sort((a, b) => b.id < a.id)
          ?.map((item, i) => (
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
          ))
      );
    } else if (charSorting?.id === "atk") {
      return charCardListData
        ?.slice()
        ?.sort((a, b) => b.atk - a.atk)
        ?.map((item, i) => (
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
    } else if (charSorting?.id === "def") {
      return charCardListData
        ?.slice()
        ?.sort((a, b) => b.def - a.def)
        ?.map((item, i) => (
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
    } else if (charSorting?.id === "hp") {
      return charCardListData
        ?.slice()
        ?.sort((a, b) => b.hp - a.hp)
        ?.map((item, i) => (
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
    } else if (charSorting?.id === "energy") {
      return charCardListData
        ?.slice()
        ?.sort((a, b) => b.energy - a.energy)
        ?.map((item, i) => (
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
    } else if (charSorting?.id === "time") {
      return charCardListData?.map((item, i) => (
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
    }
  }, [charCardListData, charSorting?.id]);

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
            {charSortingReverse
              ? charCardListJSX?.slice().reverse()
              : charCardListJSX}
          </View>
        </ScrollView>
      </View>
      <CharAction />
    </>
  );
}
