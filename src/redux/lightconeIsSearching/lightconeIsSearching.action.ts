import { LightconeIsSearchingAction } from "./lightconeIsSearching.types";

export const lightconeIsSearchingAction = (
  data: boolean
): LightconeIsSearchingAction => {
  return {
    type: "set_lightcone_is_searching",
    payload: data,
  };
};
