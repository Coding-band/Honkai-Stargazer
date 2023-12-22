import charDataMap from "../../../map/character_data_map";
import lcDataMap from "../../../map/lightcone_data_map";
import relicDataMap from "../../../map/relic_data_map";
import charAdviceMap from "../../../map/character_advice_map";
import { CharacterName } from "../../types/character";
import { LightconeName } from "../../types/lightcone";
import { Language } from "../../types/language";
import { RelicName } from "../../types/relic";

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

export function getRelicFullData(
  relicId: RelicName,
  language: Language = "zh_cn"
) {
  return relicDataMap[language][relicId];
}

export function getCharAdviceData(charId: CharacterName) {
  return charAdviceMap[charId];
}
