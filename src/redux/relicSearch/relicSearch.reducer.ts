import { RelicSearchAction } from "./relicSearch.types";

export const relicSearch = (prevSate: string = "", action: RelicSearchAction) => {
  let newState = prevSate;
  if (action.type === "set_relic_search") {
    newState = action.payload;
    return newState;
  } else {
    return prevSate;
  }
};
