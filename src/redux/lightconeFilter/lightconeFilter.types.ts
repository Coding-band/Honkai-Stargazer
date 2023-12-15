import { CombatType } from "../../types/combatType";
import { Path } from "../../types/path";

export type LightconeFilterAction = {
  type: "set_lightcone_filter";
  payload: LightconeFilter;
};

export type LightconeFilter = {
  id: LightconeFilterType;
  selected: boolean;
}[];

export type LightconeFilterType = Path | CombatType;
