import {
  LightconeFilter,
  LightconeFilterAction,
} from "./lightconeFilter.types";

export const lightconeFilter = (
  prevSate: LightconeFilter = [
    { id: "Erudition", selected: false },
    { id: "Abundance", selected: false },
    { id: "Harmony", selected: false },
    { id: "Destruction", selected: false },
    { id: "Hunt", selected: false },
    { id: "Nihility", selected: false },
    { id: "Preservation", selected: false },
  ],
  action: LightconeFilterAction
) => {
  if (action.type === "set_lightcone_filter") {
    const newState = action.payload;
    return newState;
  } else {
    return prevSate;
  }
};
