export interface UserRelicProperties {
  property_type: string;
  value: number;
  times: number;
}
export default interface UserCharacters {
  characters: {
    id: number;
    level: number;
    rank: number;
    equip: {
      id: number;
      level: number;
      rank: number;
    };
    relics: {
      id: number;
      level: number;
      rarity: number;
      pos: number;
    }[];
    ornaments: {
      id: number;
      level: number;
      rarity: number;
      pos: number;
    }[];
  }[];
}

/**
 * [未完成] 
 * 參照hoyolab_info.json 內的property_info編寫
 * 透過property_type獲取對應的 attr key及得悉是否百分比
 */
export function getAttrKeyByPropertyType(property_type: number) {
  switch (property_type) {
    case 1: return { key: "hp", isPercent: false };
    case 2: return { key: "atk", isPercent: false };
    case 3: return { key: "def", isPercent: false };
    case 4: return { key: "spd", isPercent: false };
    case 5: return { key: "crit_rate", isPercent: true };
    case 6: return { key: "crit_dmg", isPercent: true };
    case 7: return { key: "heal_rate", isPercent: true };
    //case 8 : return {key : "?", isPercent : false};
    case 9: return { key: "sp_rate", isPercent: true };
    case 10: return { key: "effect_hit", isPercent: true };
    case 11: return { key: "effect_res", isPercent: true };
    case 12: return { key: "physical_dmg", isPercent: true };
    case 13: return { key: "physical_res", isPercent: true };
    case 14: return { key: "fire_dmg", isPercent: true };
    case 15: return { key: "fire_res", isPercent: true };
    case 16: return { key: "ice_dmg", isPercent: true };
    case 17: return { key: "ice_res", isPercent: true };
    case 10: return { key: "effect_hit", isPercent: true };
    case 11: return { key: "effect_res", isPercent: true };
    case 12: return { key: "physical_dmg", isPercent: true };
    case 13: return { key: "physical_res", isPercent: true };
    case 14: return { key: "fire_dmg", isPercent: true };
    case 15: return { key: "fire_res", isPercent: true };
    case 16: return { key: "ice_dmg", isPercent: true };
    case 17: return { key: "ice_res", isPercent: true };
    case 18: return { key: "lightning_dmg", isPercent: true };
    case 19: return { key: "lightning_res", isPercent: true };
    case 20: return { key: "wind_dmg", isPercent: true };
    case 21: return { key: "wind_res", isPercent: true };
    case 22: return { key: "quantum_dmg", isPercent: true };
    case 23: return { key: "quantum_res", isPercent: true };
    case 24: return { key: "imaginary_dmg", isPercent: true };
    case 25: return { key: "imaginary_res", isPercent: true };
    case 26: return { key: "hp", isPercent: false };
    case 27: return { key: "effect_res", isPercent: true };
    case 28: return { key: "physical_dmg", isPercent: true };
    case 29:return { key: "physical_res", isPercent: true };
    case 30: return { key: "fire_dmg", isPercent: true };
    case 31: return { key: "fire_res", isPercent: true };
    case 32: return { key: "ice_dmg", isPercent: true };
    case 33: return { key: "ice_res", isPercent: true };
  }
}