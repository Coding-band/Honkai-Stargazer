import { CharacterIsSearchingAction } from "./characterIsSearching.types";

export const characterIsSearchingAction = (data: boolean): CharacterIsSearchingAction => {
  return {
    type: "set_character_is_searching",
    payload: data,
  };
};
