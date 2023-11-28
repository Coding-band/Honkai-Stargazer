import { ExpoImage } from "./image";

type Character = {
  id: number;
  name?: string;
  image?: ExpoImage;
  imageFull?: ExpoImage;
  stars?: 4 | 5;
  path?: string;
  combatType?: string;
  location?: string;
};

export default Character;
