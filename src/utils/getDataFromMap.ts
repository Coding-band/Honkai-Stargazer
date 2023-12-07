import * as charDataMap from "../../data/character_data/@character_data_map/character_data_map";
import * as lcDataMap from "../../data/lightcone_data/@lightcone_data_map/lightcone_data_map";
import Language from "../types/language";
import { CharacterName } from "../types/character";
import { LightconeName } from "../types/lightcone";

export function getCharFullData(
  charId: CharacterName,
  language: Language = "ZH_CN"
) {
  return charDataMap[language][charId];
}

export function getLcFullData(
  lcId: LightconeName,
  language: Language = "ZH_CN"
) {
  return lcDataMap[language][lcId];
}
