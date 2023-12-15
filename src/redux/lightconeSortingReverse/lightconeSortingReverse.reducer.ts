import { LightconeSortingReverseAction } from "./lightconeSortingReverse.types";

export const lightconeSortingReverse = (
  prevSate: boolean = false,
  action: LightconeSortingReverseAction
) => {
  let newState = prevSate;
  if (action.type === "set_lightcone_sorting_reverse") {
    newState = action.payload;
    return newState;
  } else {
    return prevSate;
  }
};
