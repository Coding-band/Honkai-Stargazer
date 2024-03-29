import charDataMap from "../../../map/character_data_map";
import lcDataMap from "../../../map/lightcone_data_map";
import relicDataMap from "../../../map/relic_data_map";
import relicPcMap from "../../../map/relic_pc_map";
import { CharacterName } from "../../types/character";
import { LightconeName } from "../../types/lightcone";
import { TextLanguage } from "../../language/language.types";
import { RelicName } from "../../types/relic";
import characterList from "../../../data/character_data/character_list.json";
import lightconeList from "../../../data/lightcone_data/lightcone_list.json";

export function getCharFullData(
  charId: CharacterName,
  language: TextLanguage = "zh_cn"
) {
  return charDataMap[language][charId];
}

export function getCharJsonData(charId: CharacterName) {
  return characterList.filter((char) => char.name === charId)[0];
}

export function getLcFullData(
  lcId: LightconeName,
  language: TextLanguage = "zh_cn"
) {
  return lcDataMap[language][lcId];
}

export function getLcJsonData(lcId: LightconeName) {
  return lightconeList.filter((lc) => lc.name === lcId)[0];
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
