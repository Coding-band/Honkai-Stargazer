import { CharacterName } from "../../types/character";
import { CombatType } from "../../types/combatType";
import { ExpoImage } from "../../types/image";
import { Path } from "../../types/path";

export type CharacterData = {
  id: CharacterName;
  name: string;
  image: ExpoImage;
  imageFull: ExpoImage;
  rare: number;
  pathId: Path;
  path: string;
  combatTypeId: CombatType;
  combatType: string;
  location: string;
} ;
