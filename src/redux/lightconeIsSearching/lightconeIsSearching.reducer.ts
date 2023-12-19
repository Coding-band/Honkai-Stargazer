import { LightconeIsSearchingAction } from "./lightconeIsSearching.types";

export const lightconeIsSearching = (
  prevSate: boolean = false,
  action: LightconeIsSearchingAction
) => {
  let newState = prevSate;
  if (action.type === "set_lightcone_is_searching") {
    newState = action.payload;
    return newState;
  } else {
    return prevSate;
  }
};
