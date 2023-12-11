import {
  CharacterSorting,
  CharacterSortingAction,
} from "./characterSorting.types";

export const characterSorting = (
  prevSate: CharacterSorting = [
    { id: "time", name: "实装日期", selected: true },
    { id: "name", name: "名称（英文）", selected: false },
    { id: "atk", name: "攻击力", selected: false },
    { id: "def", name: "防御力", selected: false },
    { id: "hp", name: "生命值", selected: false },
    { id: "energy", name: "能量上限", selected: false },
    { id: "rare", name: "稀有度", selected: false },
  ],
  action: CharacterSortingAction
) => {
  if (action.type === "set_character_sorting") {
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
