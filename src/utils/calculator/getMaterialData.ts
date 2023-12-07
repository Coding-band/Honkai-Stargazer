import { CharacterName } from "../../types/character";
import { LightconeName } from "../../types/lightcone";
import { getCharFullData, getLcFullData } from "../dataMap/getDataFromMap";

//* 根據等級取得角色屬性數值
export function getCharMaterialData(
  id: CharacterName,
  fromLevel: number = 1,
  toLevel: number = 80
) {
  const charFullData = getCharFullData(id);
  const charLevelData = charFullData.levelData;

  // 邏輯處理

  return [];
}

//* 根據等級取得角色屬性數值
export function getLcMaterialData(
  id: LightconeName,
  fromLevel: number = 1,
  toLevel: number = 80
) {
  const lcFullData = getLcFullData(id);
  const lcLevelData = lcFullData.levelData;

  // 邏輯處理

  return [];
}
