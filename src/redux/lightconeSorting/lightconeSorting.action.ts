import {
  LightconeSortingType,
  LightconeSortingAction,
} from "./lightconeSorting.types";

export const lightconeSortingAction = (
  id: LightconeSortingType
): LightconeSortingAction => {
  return {
    type: "set_lightcone_sorting",
    selectedId: id,
  };
};
