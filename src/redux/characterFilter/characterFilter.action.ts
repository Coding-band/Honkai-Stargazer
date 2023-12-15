import {
  CharacterFilter,
  CharacterFilterAction,
} from "./characterFilter.types";

export const characterFilterAction = (
  payload: CharacterFilter
): CharacterFilterAction => {
  return {
    type: "set_character_filter",
    payload
  };
};
