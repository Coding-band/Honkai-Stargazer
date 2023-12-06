import { ExpoImage } from "./image";

export type Character = {
  id?: CharacterName;
  name?: string;
  image?: ExpoImage;
  imageFull?: ExpoImage;
  rare?: number;
  path?: string;
  combatType?: string;
  location?: string;
  storyText?: string;
  levelData?: {
    attackBase: number;
    attackAdd: number;
    hpBase: number;
    hpAdd: number;
    defenseBase: number;
    defenseAdd: number;
    crate: number;
    cdmg: number;
    aggro: number;
    speedBase: number;
    speedAdd: number;
    promotion: number;
    maxLevel: number;
    cost: {
      id: number;
      count: number;
    }[];
  }[];
  ranks?: {
    id: number;
    iconPath: string;
    artPath: string;
    name: string;
    descHash: string;
    params: number[];
  }[];
  skillGrouping?: number[][];
  skills?: {
    tagHash: string;
    descHash: string;
    ultimateCost: number;
    break: string;
    energy: string;
    id: number;
    name: string;
    iconPath: string;
    typeDescHash: string;
    levelReq: number;
    promotionReq: number;
    levelData: {
      params: number[];
      level: number;
      levelReq: number;
      promotionReq: number;
      cost: {
        id: number;
        count: number;
      }[];
    }[];
  }[];
};

export type CharacterCard = {
  id: string;
  name: string;
  rare: number;
  image: ExpoImage;
};

export type CharacterName =
  | "Argenti"
  | "Huohuo"
  | "Hanya"
  | "Jingliu"
  | "Topaz & Numby"
  | "Guinaifen"
  | "Dan Heng • Imbibitor Lunae"
  | "Fu Xuan"
  | "Lynx"
  | "Kafka"
  | "Blade"
  | "Luka"
  | "Yukong"
  | "Luocha"
  | "Silver Wolf"
  | "Arlan"
  | "Asta"
  | "Bailu"
  | "Bronya"
  | "Clara"
  | "Dan Heng"
  | "Gepard"
  | "Herta"
  | "Himeko"
  | "Hook"
  | "Jing Yuan"
  | "March 7th"
  | "Natasha"
  | "Pela"
  | "Qingque"
  | "Sampo"
  | "Seele"
  | "Serval"
  | "Sushang"
  | "Tingyun"
  | "Welt"
  | "Yanqing";
