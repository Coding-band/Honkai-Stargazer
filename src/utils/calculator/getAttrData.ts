import officalLightconeId from "../../../map/lightcone_offical_id_map";
import { CharacterName } from "../../types/character";
import { LightconeName } from "../../types/lightcone";
import { getCharFullData, getLcFullData } from "../data/getDataFromMap";

const levelMin = [1, 20, 30, 40, 50, 60, 70];
const levelMax = [20, 30, 40, 50, 60, 70, 80];

//* 根據等級取得角色屬性數值
export function getCharAttrData(id: CharacterName, level: number = 1) {
  const charFullData = getCharFullData(id);
  const charLevelData = charFullData?.levelData;

  const attributes = {
    atk: 0,
    hp: 0,
    def: 0,
    speed: 0,
    aggro: 0,
    energy: charFullData?.spRequirement,
  };

  // 找到對應等級的數據
  const dataForLevel = charLevelData?.find((data) =>
    level === 80 ? level <= data?.maxLevel : level < data?.maxLevel
  );

  if (dataForLevel) {
    // 計算屬性值
    attributes.atk =
      dataForLevel.attackBase + dataForLevel.attackAdd * (level - 1);
    attributes.hp = dataForLevel.hpBase + dataForLevel.hpAdd * (level - 1);
    attributes.def =
      dataForLevel.defenseBase + dataForLevel.defenseAdd * (level - 1);
    attributes.speed =
      dataForLevel.speedBase + dataForLevel.speedAdd * (level - 1);
    attributes.aggro = dataForLevel.aggro;
  }

  return attributes;
}

//* 根據等級取得角色屬性數值
export function getLcAttrData(id: LightconeName, level: number = 1) {
  const lcFullData = getLcFullData(id);
  const lcLevelData = lcFullData.levelData;

  const attributes = {
    atk: 0,
    hp: 0,
    def: 0,
  };

  // 找到對應等級的數據
  const dataForLevel = lcLevelData.find((data) => level === 80 ? level <= data.maxLevel : level < data.maxLevel);

  if (dataForLevel) {
    // 計算屬性值
    attributes.atk =
      dataForLevel.attackBase + dataForLevel.attackAdd * (level - 1);
    attributes.hp = dataForLevel.hpBase + dataForLevel.hpAdd * (level - 1);
    attributes.def =
      dataForLevel.defenseBase + dataForLevel.defenseAdd * (level - 1);
  }

  return attributes;
}

//* 根據等級取得角色屬性數值
export function getLcAttrDataJSON(id: number, level: number = 1) {
  const lcFullData = getLcFullData(officalLightconeId[id]);
  const lcLevelData = lcFullData.levelData;

  const attributes = [
    {
      field: "hp",
      value: 0,
      percent : false,
      display : "0.0",
    },
    {
      field: "hp",
      value: 0,
      percent : false,
      display : "0.0",
    },
    {
      field: "def",
      value: 0,
      percent : false,
      display : "0.0",
    },
  ];

  // 找到對應等級的數據
  const dataForLevel = lcLevelData.find((data) => level === 80 ? level <= data.maxLevel : level < data.maxLevel) ;

  if (dataForLevel) {
    // 計算屬性值
    attributes[0].value =
      dataForLevel.attackBase + dataForLevel.attackAdd * (level - 1);
    attributes[1].value = dataForLevel.hpBase + dataForLevel.hpAdd * (level - 1);
    attributes[2].value =
      dataForLevel.defenseBase + dataForLevel.defenseAdd * (level - 1);

      attributes.map((attribute : any) => {
        attribute.display = (attribute.value * (attribute.percent ? 100 : 1)).toFixed(1).toString()+(attribute.percent ? "%" : "")
      })
  }

  return attributes;
}
