import { LightconeSearchAction } from "./lightconeSearch.types";

export const lightconeSearch = (
  prevSate: string = "",
  action: LightconeSearchAction
) => {
  let newState = prevSate;
  if (action.type === "set_lightcone_search") {
    newState = action.payload;
    return newState;
  } else {
    return prevSate;
  }
};
