import {
  CharacterFilter,
  CharacterFilterAction,
} from "./characterFilter.types";

export const characterFilter = (
  prevSate: CharacterFilter = [
    { id: "Fire", name: "火", selected: true },
    { id: "Wind", name: "風", selected: true },
    { id: "Ice", name: "冰", selected: true },
    { id: "Imaginary", name: "虛數", selected: true },
    { id: "Lightning", name: "雷", selected: true },
    { id: "Physical", name: "物理", selected: true },
    { id: "Quantum", name: "量子", selected: true },
    { id: "Erudition", name: "智識", selected: true },
    { id: "Abundance", name: "豐饒", selected: true },
    { id: "Harmony", name: "同諧", selected: true },
    { id: "Destruction", name: "毀滅", selected: true },
    { id: "Hunt", name: "巡獵", selected: true },
    { id: "Nihility", name: "虛無", selected: true },
    { id: "Preservation", name: "存護", selected: true },
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
