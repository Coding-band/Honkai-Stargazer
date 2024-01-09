import {
  LightconeSorting,
  LightconeSortingAction,
} from "./lightconeSorting.types";

export const lightconeSorting = (
  prevSate: LightconeSorting = [
    { id: "time", name: "實裝日期", selected: true },
    { id: "name", name: "名稱（英文）", selected: false },
    { id: "atk", name: "攻擊力", selected: false },
    { id: "def", name: "防禦力", selected: false },
    { id: "hp", name: "生命值", selected: false },
    { id: "rare", name: "稀有度", selected: false },
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
