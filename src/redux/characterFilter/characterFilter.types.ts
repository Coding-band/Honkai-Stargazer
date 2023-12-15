import { CombatType } from "../../types/combatType";
import { Path } from "../../types/path";

export type CharacterFilterAction = {
  type: "set_character_filter";
  payload: CharacterFilter;
};

export type CharacterFilter = {
  id: CharacterFilterType;
  selected: boolean;
}[];

export type CharacterFilterType = Path | CombatType;
