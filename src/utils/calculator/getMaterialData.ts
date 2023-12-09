import { CharacterName } from "../../types/character";
import { LightconeName } from "../../types/lightcone";
import { Material } from "../../types/material";
import { getCharFullData, getLcFullData } from "../dataMap/getDataFromMap";

const levelMin = [1,20,30,40,50,60,70]
const levelMax = [20,30,40,50,60,70,80]

export interface MaterialHash {
  [id: number] : Material
}
export interface MaterialCount {
  [id: number] : number
}

//* 根據角色等級取得 升級角色所需素材數值
export function getCharMaterialData(
  id: CharacterName,
  fromLevel: number = 1,
  toLevel: number = 80
) {
 
  //初始化 - 素材映射
  let charFullData = getCharFullData(id);
  let charLevelData = charFullData.levelData;
  let materialHash : MaterialHash = {};
  for(let key in charFullData.itemReferences){
    materialHash[Number(key)] = {
      id : Number(key),
      name : charFullData.itemReferences[key].name,
      rare : charFullData.itemReferences[key].rarity,
      desc : charFullData.itemReferences[key].desc,
      purposeId : charFullData.itemReferences[key].purposeId,
      iconPath : charFullData.itemReferences[key].iconPath,
      comeFrom : charFullData.itemReferences[key].comeFrom,
    }
  }

  // 計算素材數量
  let startPointer = 0;
  let endPointer = 0;
  let materialCount : MaterialCount = {};
  for(let x = 0 ; x < levelMin.length ; x++){
    if(fromLevel < levelMax[x] && fromLevel >= levelMin[x]){  //lv20已突破 -> lv40未突破的假設
      startPointer = x;
    }
    if(toLevel <= levelMax[x] && toLevel > levelMin[x]){  //lv20已突破 -> lv40未突破的假設
      endPointer = x;
    }
  }

  
  for(let ascLvl = startPointer ; ascLvl < endPointer ; ascLvl++){
    // 突破素材
    for(let x = 0 ; x < charLevelData[ascLvl].cost.length ; x++){
      materialCount[charLevelData[ascLvl].cost[x].id] = (materialCount[charLevelData[ascLvl].cost[x].id] === undefined ? 0 : materialCount[charLevelData[ascLvl].cost[x].id]) + charLevelData[ascLvl].cost[x].count
    }
  }

  
  //等級經驗 - 必須遵守突破原則
  let tmpEXP = 0
  let tmpCoin = 0
  for(let lvl = fromLevel ; lvl < toLevel ; lvl++){
    tmpEXP += Number(charFullData.calculator.expCost[lvl])
    tmpCoin += Number(charFullData.calculator.sCoinId[lvl])
    
    if(levelMax.includes(lvl+1)){
      console.log("LVL"+lvl +" : "+tmpEXP)
      materialCount[409960] = (materialCount[409960] === undefined ? 0 : materialCount[409960]) + Math.trunc(tmpEXP / 20000) //紫色
      materialCount[409961] = (materialCount[409961] === undefined ? 0 : materialCount[409961]) +  Math.trunc((tmpEXP % 20000) / 5000)  //藍色
      materialCount[409962] = (materialCount[409962] === undefined ? 0 : materialCount[409962]) +  (Math.trunc(((tmpEXP % 20000) % 5000) / 1000) !== (((tmpEXP % 20000) % 5000) / 1000) ? Math.trunc(((tmpEXP % 20000) % 5000) / 1000)+1 : Math.trunc(((tmpEXP % 20000) % 5000) / 1000)) //綠色
      //materialCount[29328] = (materialCount[29328] === undefined ? 0 : materialCount[29328]) + 
      tmpEXP = 0;
    }
  }
  
  return materialCount;
}

//* 根據等級取得角色素材數值
export function getLcMaterialData(
  id: LightconeName,
  fromLevel: number = 1,
  toLevel: number = 80
) {
  const lcFullData = getLcFullData(id);
  const lcLevelData = lcFullData.levelData;
  let materialHash : MaterialHash = {};
  for(let key in lcFullData.itemReferences){
    materialHash[Number(key)] = {
      id : Number(key),
      name : lcFullData.itemReferences[key].name,
      rare : lcFullData.itemReferences[key].rarity,
      desc : lcFullData.itemReferences[key].desc,
      purposeId : lcFullData.itemReferences[key].purposeId,
      iconPath : lcFullData.itemReferences[key].iconPath,
      comeFrom : lcFullData.itemReferences[key].comeFrom,
    }
  }

  // 計算素材數量
  let startPointer = 0;
  let endPointer = 0;
  let materialCount : MaterialCount = {};
  for(let x = 0 ; x < levelMin.length ; x++){
    if(fromLevel < levelMax[x] && fromLevel >= levelMin[x]){  //lv20已突破 -> lv40未突破的假設
      startPointer = x;
    }
    if(toLevel <= levelMax[x] && toLevel > levelMin[x]){  //lv20已突破 -> lv40未突破的假設
      endPointer = x;
    }
  }

  
  for(let ascLvl = startPointer ; ascLvl < endPointer ; ascLvl++){
    // 突破素材
    for(let x = 0 ; x < lcLevelData[ascLvl].cost.length ; x++){
      materialCount[lcLevelData[ascLvl].cost[x].id] = (materialCount[lcLevelData[ascLvl].cost[x].id] === undefined ? 0 : materialCount[lcLevelData[ascLvl].cost[x].id]) + lcLevelData[ascLvl].cost[x].count
    }
  }

  
  //等級經驗 - 必須遵守突破原則
  let tmpEXP = 0
  let tmpCoin = 0
  for(let lvl = fromLevel ; lvl < toLevel ; lvl++){
    tmpEXP += Number(lcFullData.calculator.expCost[lvl])
    tmpCoin += Number(lcFullData.calculator.sCoinId[lvl])
    
    if(levelMax.includes(lvl+1)){
      console.log("LVL"+lvl +"  : "+tmpEXP)
      materialCount[694487] = (materialCount[694487] === undefined ? 0 : materialCount[694487]) + Math.trunc(tmpEXP / 6000) //紫色
      materialCount[694488] = (materialCount[694488] === undefined ? 0 : materialCount[694488]) +  Math.trunc((tmpEXP % 6000) / 2000)  //藍色
      materialCount[694489] = (materialCount[694489] === undefined ? 0 : materialCount[694489]) +  (Math.trunc(((tmpEXP % 6000) % 2000) / 500) !== (((tmpEXP % 6000) % 2000) / 500) ? Math.trunc(((tmpEXP % 20000) % 5000) / 1000)+1 : Math.trunc(((tmpEXP % 20000) % 5000) / 1000)) //綠色
      tmpEXP = 0;
    }
  }

  return materialCount;
}