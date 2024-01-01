import charDataMap from "../../../map/character_data_map";
import lcDataMap from "../../../map/lightcone_data_map";
import relicDataMap from "../../../map/relic_data_map";
import relicPcMap from "../../../map/relic_pc_map";
import { CharacterName } from "../../types/character";
import { LightconeName } from "../../types/lightcone";
import { Language } from "../../types/language";
import { RelicName } from "../../types/relic";

export function getCharFullData(
  charId: CharacterName,
  language: Language = "zh_cn"
) {
  if(language === "vocchinese"){return charDataMap["zh_hk"][charId]}
  return charDataMap[language][charId];
}

export function getLcFullData(
  lcId: LightconeName,
  language: Language = "zh_cn"
) {
  if(language === "vocchinese"){return lcDataMap["zh_hk"][lcId]}
  return lcDataMap[language][lcId];
}

export function getRelicFullData(
  relicId: RelicName,
  language: Language = "zh_cn"
) {
  if(language === "vocchinese"){return relicDataMap["zh_hk"][relicId]}
  return relicDataMap[language][relicId];
}

export function getRelicPcData(
  relicId: RelicName,
  language: Language = "zh_cn"
) {
  if(language === "vocchinese"){return relicPcMap["zh_hk"][relicId]}
  return relicPcMap[language][relicId];
}
