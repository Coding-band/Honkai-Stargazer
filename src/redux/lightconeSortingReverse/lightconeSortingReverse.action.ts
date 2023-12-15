import { LightconeSortingReverseAction } from "./lightconeSortingReverse.types";

export const lightconeSortingReverseAction = (
  data: boolean
): LightconeSortingReverseAction => {
  return {
    type: "set_lightcone_sorting_reverse",
    payload: data,
  };
};
