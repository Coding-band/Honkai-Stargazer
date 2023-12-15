import {
  CharacterFilter,
  CharacterFilterAction,
} from "./characterFilter.types";

export const characterFilter = (
  prevSate: CharacterFilter = [
    { id: "Fire", selected: false },
    { id: "Wind", selected: false },
    { id: "Ice", selected: false },
    { id: "Imaginary", selected: false },
    { id: "Lightning", selected: false },
    { id: "Physical", selected: false },
    { id: "Quantum", selected: false },
    { id: "Erudition", selected: false },
    { id: "Abundance", selected: false },
    { id: "Harmony", selected: false },
    { id: "Destruction", selected: false },
    { id: "Hunt", selected: false },
    { id: "Nihility", selected: false },
    { id: "Preservation", selected: false },
  ],
  action: CharacterFilterAction
) => {
  if (action.type === "set_character_filter") {
    const newState = action.payload;
    return newState;
  } else {
    return prevSate;
  }
};
