import { LightconeSearchAction } from "./lightconeSearch.types";

export const lightconeSearchAction = (data: string): LightconeSearchAction => {
  return {
    type: "set_lightcone_search",
    payload: data,
  };
};
