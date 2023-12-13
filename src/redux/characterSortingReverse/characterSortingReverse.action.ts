import { CharacterSortingReverseAction } from "./characterSortingReverse.types";

export const characterSortingReverseAction = (
  data: boolean
): CharacterSortingReverseAction => {
  return {
    type: "set_character_sorting_reverse",
    payload: data,
  };
};
