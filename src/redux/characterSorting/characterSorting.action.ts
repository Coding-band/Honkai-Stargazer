import {
  CharacterSortingAction,
  CharacterSortingType,
} from "./characterSorting.types";

export const characterSortingAction = (
  id: CharacterSortingType
): CharacterSortingAction => {
  return {
    type: "set_character_sorting",
    selectedId: id,
  };
};
