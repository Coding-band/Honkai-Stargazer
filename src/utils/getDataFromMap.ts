import * as charDataMap from "../../data/character_data/@character_data_map/character_data_map";
import Language from "../types/Language";
import { CharacterName } from "../types/character";

export function getCharFullData(
  charId: CharacterName,
  language: Language = "ZH_CN"
) {
  return charDataMap[language][charId];
}
