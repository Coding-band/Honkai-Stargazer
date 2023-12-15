import {
  LightconeFilter,
  LightconeFilterAction,
} from "./lightconeFilter.types";

export const lightconeFilterAction = (
  payload: LightconeFilter
): LightconeFilterAction => {
  return {
    type: "set_lightcone_filter",
    payload,
  };
};
