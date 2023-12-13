import { CharacterSortingReverseAction } from "./characterSortingReverse.types";

export const characterSortingReverse = (
  prevSate: boolean = false,
  action: CharacterSortingReverseAction
) => {
  let newState = prevSate;
  if (action.type === "set_character_sorting_reverse") {
    newState = action.payload;
    return newState;
  } else {
    return prevSate;
  }
};
