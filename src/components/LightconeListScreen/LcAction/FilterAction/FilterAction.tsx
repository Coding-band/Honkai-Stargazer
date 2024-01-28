import React, { useMemo, useState } from "react";
import FilterBtn from "./FilterBtn/FilterBtn";
import { View } from "react-native";
import FilterPopUp from "../../../global/FilterPopUp/FilterPopUp";
import { PATHS } from "../../../../constant/path";
import PathMap from "../../../../../assets/images/images_map/path";
import useLcFilter from "../../../../redux/lightconeFilter/useLcFilter";
import CombatTypeMap from "../../../../../assets/images/images_map/combatType";
import { Path as PathType } from "../../../../types/path";
import { CombatType as CombatTypeType } from "../../../../types/combatType";
import useAppLanguage from "../../../../language/AppLanguage/useAppLanguage";
import { LOCALES } from "../../../../../locales";
import { useClickOutside } from "../../../../../lib/react-native-click-outside/src";

export default function FilterAction() {
  const { language } = useAppLanguage();

  const [open, setOpen] = useState(false);

  const { lcFilter, lcFilterSelected, setLcFilterSelected } = useLcFilter();

  const lcFilterItems = useMemo(
    () =>
      lcFilter?.map((item) => ({
        value: item.id,
        name: LOCALES[language][item.id],
        icon: PATHS.includes(item.id)
          ? PathMap[item.id as PathType].icon2
          : CombatTypeMap[item.id as CombatTypeType].icon,
      })),
    [lcFilter]
  );

  const ref = useClickOutside(() => {
    setOpen(false);
  });

  return (
    <View ref={ref}>
      <FilterBtn
        onPress={() => {
          setOpen(!open);
        }}
      />
      {open && (
        <FilterPopUp
          items={lcFilterItems!}
          value={lcFilterSelected!}
          // @ts-ignore
          onChange={setLcFilterSelected}
          onClose={() => {
            setOpen(false);
          }}
          onReset={() => {
            setLcFilterSelected([]);
          }}
          onConfirm={() => {
            setOpen(false);
          }}
        />
      )}
    </View>
  );
}
