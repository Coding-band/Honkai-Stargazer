import { RelicSearchAction } from "./relicSearch.types";

export const relicSearchAction = (data: string): RelicSearchAction => {
  return {
    type: "set_relic_search",
    payload: data,
  };
};
