import { LOCALES } from "../../../locales";
import useAppLanguage from "../../language/AppLanguage/useAppLanguage";
import {
  LightconeSorting,
  LightconeSortingAction,
} from "./lightconeSorting.types";

const {language} = useAppLanguage();
export const lightconeSorting = (
  prevSate: LightconeSorting = [
    { id: "time", name: LOCALES[language].SortByDate, selected: true },
    { id: "name", name: LOCALES[language].SortByENName, selected: false },
    { id: "atk", name: LOCALES[language].SortByATK, selected: false },
    { id: "def", name: LOCALES[language].SortByDEF, selected: false },
    { id: "hp", name: LOCALES[language].SortByHP, selected: false },
    { id: "rare", name: LOCALES[language].SortByRarity, selected: false },
  ],
  action: LightconeSortingAction
) => {
  if (action.type === "set_lightcone_sorting") {
    const newState = prevSate.map((sorting) => {
      if (sorting.id === action.selectedId) {
        return { ...sorting, selected: true };
      } else {
        return { ...sorting, selected: false };
      }
    });
    return newState;
  } else {
    return prevSate;
  }
};
