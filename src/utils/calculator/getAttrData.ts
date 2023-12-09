import { CharacterName } from "../../types/character";
import { LightconeName } from "../../types/lightcone";
import { getCharFullData, getLcFullData } from "../dataMap/getDataFromMap";

const levelMin = [1, 20, 30, 40, 50, 60, 70];
const levelMax = [20, 30, 40, 50, 60, 70, 80];

//* 根據等級取得角色屬性數值
export function getCharAttrData(id: CharacterName, level: number = 1) {
  const charFullData = getCharFullData(id);
  const charLevelData = charFullData.levelData;

  // 邏輯處理
  for (let x = 0; x < levelMax.length; x++) {
    //確定等級是否在這個Range
    if (level <= levelMax[x] && level >= levelMin[x]) {
      /**
       * hp : 生命值
       * atk : 攻擊力
       * def : 防禦力
       * speed : 速度
       * aggro : 嘲諷值
       * energy : 大技充能值
       */
      //記得顯示時候要 **下寫入**
      return {
        hp:
          charLevelData[x].hpBase +
          charLevelData[x].hpAdd * (level - levelMin[x]),
        atk:
          charLevelData[x].attackBase +
          charLevelData[x].attackAdd * (level - levelMin[x]),
        def:
          charLevelData[x].defenseBase +
          charLevelData[x].defenseAdd * (level - levelMin[x]),
        speed:
          charLevelData[x].speedBase +
          charLevelData[x].speedAdd * (level - levelMin[x]),
        aggro: charLevelData[x].aggro,
        energy: charFullData.spRequirement,
      };
    }
  }

  return {
    hp: 0,
    atk: 0,
    def: 0,
    speed: 0,
    aggro: 0,
    energy: 0,
  };
}

//* 根據等級取得角色屬性數值
export function getLcAttrData(id: LightconeName, level: number = 1) {
  const lcFullData = getLcFullData(id);
  const lcLevelData = lcFullData.levelData;

  // 邏輯處理

  for (let x = 0; x < levelMax.length; x++) {
    //確定等級是否在這個 Range
    if (level <= levelMax[x] && level >= levelMin[x]) {
      /**
       * hp : 生命值
       * atk : 攻擊力
       * def : 防禦力
       */
      //記得顯示時候要 **下寫入**
      return {
        hp:
          lcLevelData[x].hpBase + lcLevelData[x].hpAdd * (level - levelMin[x]),
        atk:
          lcLevelData[x].attackBase +
          lcLevelData[x].attackAdd * (level - levelMin[x]),
        def:
          lcLevelData[x].defenseBase +
          lcLevelData[x].defenseAdd * (level - levelMin[x]),
      };
    }
  }

  return {
    hp: 0,
    atk: 0,
    def: 0,
  };
}
