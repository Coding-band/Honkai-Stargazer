import { RelicIsSearchingAction } from "./relicIsSearching.types";

export const relicIsSearchingAction = (data: boolean): RelicIsSearchingAction => {
  return {
    type: "set_relic_is_searching",
    payload: data,
  };
};
