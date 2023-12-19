import { CharacterIsSearchingAction } from "./characterIsSearching.types";

export const characterIsSearching = (
  prevSate: boolean = false,
  action: CharacterIsSearchingAction
) => {
  let newState = prevSate;
  if (action.type === "set_character_is_searching") {
    newState = action.payload;
    return newState;
  } else {
    return prevSate;
  }
};
