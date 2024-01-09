import {
  CharacterSorting,
  CharacterSortingAction,
} from "./characterSorting.types";

export const characterSorting = (
  prevSate: CharacterSorting = [
    { id: "time", selected: true },
    { id: "name", selected: false },
    { id: "atk", selected: false },
    { id: "def", selected: false },
    { id: "hp", selected: false },
    { id: "energy", selected: false },
    { id: "rare", selected: false },
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
