import React, { useMemo, useState } from "react";
import FilterBtn from "./FilterBtn/FilterBtn";
import { View } from "react-native";
import FilterPopUp from "../../../global/FilterPopUp/FilterPopUp";
import useCharFilter from "../../../../redux/characterFilter/useCharFilter";
import { PATHS } from "../../../../constant/path";
import PathMap from "../../../../../assets/images/images_map/path";
import CombatTypeMap from "../../../../../assets/images/images_map/combatType";
import { Path as PathType } from "../../../../types/path";
import { CombatType as CombatTypeType } from "../../../../types/combatType";
import useAppLanguage from "../../../../context/AppLanguage/useAppLanguage";
import { LOCALES } from "../../../../../locales";

export default function FilterAction() {
  const [open, setOpen] = useState(false);
  const {language} = useAppLanguage();

  const { charFilter, charFilterSelected, setCharFilterSelected } =
    useCharFilter();

  const charFilterItems = useMemo(
    () =>
      charFilter?.map((item) => ({
        value: item.id,
        name: LOCALES[language][item.id],
        icon: PATHS.includes(item.id)
          ? PathMap[item.id as PathType].icon
          : CombatTypeMap[item.id as CombatTypeType].icon,
      })),
    [charFilter]
  );

  return (
    <View>
      <FilterBtn
        onPress={() => {
          setOpen(!open);
        }}
      />
      {open && (
        <FilterPopUp
          items={charFilterItems!}
          value={charFilterSelected!}
          // @ts-ignore
          onChange={setCharFilterSelected}
          onClose={() => {
            setOpen(false);
          }}
          onReset={() => {
            setCharFilterSelected([]);
          }}
          onConfirm={() => {
            setOpen(false);
          }}
        />
      )}
    </View>
  );
}
