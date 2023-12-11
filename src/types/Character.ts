import { CombatType } from "./combatType";
import { ExpoImage } from "./image";
import { Path } from "./path";

export type Character = {
  id?: CharacterName;
  name?: string;
  image?: ExpoImage;
  imageFull?: ExpoImage;
  rare?: number;
  path?: string;
  combatType?: string;
  location?: string;
};

export type CharacterCard = {
  id: CharacterName;
  name: string;
  rare: number;
  path: Path;
  combatType: CombatType;
  image: ExpoImage;
  version: string;
  atk: number;
  def: number;
  hp: number;
  energy: number;
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
