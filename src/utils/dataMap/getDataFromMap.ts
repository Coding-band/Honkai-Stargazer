import charDataMap from "../../../map/character_data_map";
import lcDataMap from "../../../map/lightcone_data_map";
import charAdviceMap from "../../../map/character_advice_map";
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

export function getCharAdviceData(charId: CharacterName) {
  return charAdviceMap[charId];
}
