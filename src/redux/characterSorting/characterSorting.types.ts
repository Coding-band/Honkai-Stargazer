export type CharacterSortingAction = {
  type: "set_character_sorting";
  selectedId: CharacterSortingType;
};

export type CharacterSorting = {
  id: CharacterSortingType;
  selected: boolean;
}[];

export type CharacterSortingType =
  | "time"
  | "name"
  | "atk"
  | "def"
  | "hp"
  | "energy"
  | "rare";
