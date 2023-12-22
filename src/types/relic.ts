import { ExpoImage } from "./image";

export type Relic = {
  id?: RelicName;
  name?: string;
  image?: ExpoImage;
  imageFull?: ExpoImage[];
  rare?: number;
};

export type RelicCard = {
  id: string;
  name: string;
  rare: number;
  image: ExpoImage;
};

export type RelicName =
  | "Passerby of Wandering Cloud"
  | "Musketeer of Wild Wheat"
  | "Knight of Purity Palace"
  | "Hunter of Glacial Forest"
  | "Champion of Streetwise Boxing"
  | "Guard of Wuthering Snow"
  | "Firesmith of Lava-Forging"
  | "Genius of Brilliant Stars"
  | "Band of Sizzling Thunder"
  | "Eagle of Twilight Line"
  | "Thief of Shooting Meteor"
  | "Wastelander of Banditry Desert"
  | "Longevous Disciple"
  | "Messenger Traversing Hackerspace"
  | "The Ashblazing Grand Duke"
  | "Prisoner in Deep Confinement"
  | "Space Sealing Station"
  | "Fleet of the Ageless"
  | "Pan-Cosmic Commercial Enterprise"
  | "Belobog of the Architects"
  | "Celestial Differentiator"
  | "Inert Salsotto"
  | "Talia: Kingdom of Banditry"
  | "Sprightly Vonwacq"
  | "Rutilant Arena"
  | "Broken Keel"
  | "Firmament Frontline: Glamoth"
  | "Penacony, Land of the Dreams";
