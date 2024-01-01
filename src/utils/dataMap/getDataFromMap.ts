import charDataMap from "../../../map/character_data_map";
import lcDataMap from "../../../map/lightcone_data_map";
import relicDataMap from "../../../map/relic_data_map";
import relicPcMap from "../../../map/relic_pc_map";
import { CharacterName } from "../../types/character";
import { LightconeName } from "../../types/lightcone";
import { TextLanguage } from "../../types/language";
import { RelicName } from "../../types/relic";

export function getCharFullData(
  charId: CharacterName,
  language: TextLanguage = "zh_cn"
) {
  return charDataMap[language][charId];
}

export function getLcFullData(
  lcId: LightconeName,
  language: TextLanguage = "zh_cn"
) {
  return lcDataMap[language][lcId];
}

export function getRelicFullData(
  relicId: RelicName,
  language: TextLanguage = "zh_cn"
) {
  return relicDataMap[language][relicId];
}

export function getRelicPcData(
  relicId: RelicName,
  language: TextLanguage = "zh_cn"
) {
  return relicPcMap[language][relicId];
}
