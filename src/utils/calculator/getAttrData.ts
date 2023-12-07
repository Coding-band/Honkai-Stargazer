import { CharacterName } from "../../types/character";
import { LightconeName } from "../../types/lightcone";
import { getCharFullData, getLcFullData } from "../dataMap/getDataFromMap";

//* 根據等級取得角色屬性數值
export function getCharAttrData(id: CharacterName, level: number = 1) {
  const charFullData = getCharFullData(id);
  const charLevelData = charFullData.levelData;

  // 邏輯處理

  return {
    hp: 0,
    str: 0,
    def: 0,
    dex: 0,
  };
}

//* 根據等級取得角色屬性數值
export function getLcAttrData(id: LightconeName, level: number = 1) {
  const lcFullData = getLcFullData(id);
  const lcLevelData = lcFullData.levelData;

  // 邏輯處理

  return {
    hp: 0,
    str: 0,
    def: 0,
  };
}
