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