import { RelicIsSearchingAction } from "./relicIsSearching.types";

export const relicIsSearching = (
  prevSate: boolean = false,
  action: RelicIsSearchingAction
) => {
  let newState = prevSate;
  if (action.type === "set_relic_is_searching") {
    newState = action.payload;
    return newState;
  } else {
    return prevSate;
  }
};
