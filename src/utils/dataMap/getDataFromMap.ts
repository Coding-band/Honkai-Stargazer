import charDataMap from "../../../data/character_data/@character_data_map/character_data_map";
import lcDataMap from "../../../data/lightcone_data/@lightcone_data_map/lightcone_data_map";
import { CharacterName } from "../../types/character";
import { LightconeName } from "../../types/lightcone";
import { Language } from "../../types/language";

export function getCharFullData(
  charId: CharacterName,
  language: Language = "zh_cn"
) {
  return charDataMap[language][charId];
}

export function getLcFullData(
  lcId: LightconeName,
  language: Language = "zh_cn"
) {
  return lcDataMap[language][lcId];
}
