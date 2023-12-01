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
