export type LightconeSortingAction = {
  type: "set_lightcone_sorting";
  selectedId: LightconeSortingType;
};

export type LightconeSorting = {
  id: LightconeSortingType;
  selected: boolean;
}[];

export type LightconeSortingType =
  | "time"
  | "name"
  | "atk"
  | "def"
  | "hp"
  | "rare";
