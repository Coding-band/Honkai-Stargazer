
/**
 * [未完成] 
 * 參照hoyolab_info.json 內的property_info編寫
 * 透過property_type獲取對應的 attr key及得悉是否百分比
 */
export function getAttrKeyByPropertyType(property_type: number) {
  switch (property_type) {
    case 1: return { key: "hp", isPercent: false }; //Using
    case 2: return { key: "atk", isPercent: false }; //Using
    case 3: return { key: "def", isPercent: false }; //Using
    case 4: return { key: "spd", isPercent: false }; //Using
    case 5: return { key: "crit_rate", isPercent: true }; //Using
    case 6: return { key: "crit_dmg", isPercent: true }; //Using
    case 7: return { key: "heal_rate", isPercent: true }; //Using
    //case 8 : return {key : "?", isPercent : false};
    case 9: return { key: "sp_rate", isPercent: true }; //Using
    case 10: return { key: "effect_hit", isPercent: true }; //Using
    case 11: return { key: "effect_res", isPercent: true }; //Using
    case 12: return { key: "physical_dmg", isPercent: true };  //Using
    case 13: return { key: "physical_res", isPercent: true };
    case 14: return { key: "fire_dmg", isPercent: true }; //Using
    case 15: return { key: "fire_res", isPercent: true };
    case 16: return { key: "ice_dmg", isPercent: true }; //Using
    case 17: return { key: "ice_res", isPercent: true };
    case 18: return { key: "lightning_dmg", isPercent: true }; //Using
    case 19: return { key: "lightning_res", isPercent: true };
    case 20: return { key: "wind_dmg", isPercent: true }; //Using
    case 21: return { key: "wind_res", isPercent: true };
    case 22: return { key: "quantum_dmg", isPercent: true }; //Using
    case 23: return { key: "quantum_res", isPercent: true };
    case 24: return { key: "imaginary_dmg", isPercent: true }; //Using
    case 25: return { key: "imaginary_res", isPercent: true };
    case 26: return { key: "hp_base", isPercent: false };
    case 27: return { key: "hp", isPercent: false }; //Using, for relics
    case 28: return { key: "atk_base", isPercent: false };
    case 29: return { key: "atk", isPercent: false }; //Using, for relics
    case 30: return { key: "def_base", isPercent: false };
    case 31: return { key: "def", isPercent: false }; //Using, for relics
    case 32: return { key: "hp", isPercent: true }; //Using, for relics
    case 33: return { key: "atk", isPercent: true }; //Using, for relics
    case 34: return { key: "def", isPercent: true }; //Using, for relics
    case 35: return { key: "spd", isPercent: true }; //Using, for relics
    case 36: return { key: "get_heal_rate", isPercent: true };
    case 37: return { key: "physical_res", isPercent: true };
    case 38: return { key: "fire_res", isPercent: true };
    case 39: return { key: "ice_res", isPercent: true };
    case 40: return { key: "lightning_res", isPercent: true };
    case 41: return { key: "wind_res", isPercent: true };
    case 42: return { key: "quantum_res", isPercent: true };
    case 43: return { key: "imaginary_res", isPercent: true };
    //case 44: return { key: "wind_res", isPercent: true };
    //case 45: return { key: "wind_res", isPercent: true };
    //case 46: return { key: "wind_res", isPercent: true };
    //case 47: return { key: "wind_res", isPercent: true };
    //case 48: return { key: "wind_res", isPercent: true };
    //case 49: return { key: "wind_res", isPercent: true };
    //case 50: return { key: "wind_res", isPercent: true };
    case 51: return { key: "spd", isPercent: true }; //Using, for relics
    case 52: return { key: "crit_rate", isPercent: true }; //Using, for relics
    case 53: return { key: "crit_dmg", isPercent: true }; //Using, for relics
    case 54: return { key: "sp_rate", isPercent: true }; //Using, for relics
    case 55: return { key: "heal_rate", isPercent: true }; //Using, for relics
    case 56: return { key: "effect_hit", isPercent: true }; //Using, for relics
    case 57: return { key: "effect_res", isPercent: true }; //Using, for relics
    case 58: return { key: "break_dmg", isPercent: true }; //Using, for relics
    case 59: return { key: "break_dmg", isPercent: true }; //Using, for relics
    case 60: return { key: "sp", isPercent: false }; //Using, for relics
    default: return { key: property_type.toString(), isPercent: false } //Using, for relics
  }
}