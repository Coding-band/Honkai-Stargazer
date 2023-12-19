import { CharacterSearchAction } from "./characterSearch.types";

export const characterSearchAction = (
  data: string
): CharacterSearchAction => {
  return {
    type: "set_character_search",
    payload: data,
  };
};
