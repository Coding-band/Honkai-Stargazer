import { CharacterSearchAction } from "./characterSearch.types";

export const characterSearch = (
  prevSate: string = "",
  action: CharacterSearchAction
) => {
  let newState = prevSate;
  if (action.type === "set_character_search") {
    newState = action.payload;
    return newState;
  } else {
    return prevSate;
  }
};
