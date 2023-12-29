import { ExpoImage } from "../../types/image";
import { RelicName } from "../../types/relic";

export type RelicData = {
  id: RelicName;
  name: string;
  image: ExpoImage;
  imageFull: ExpoImage[];
  rare: number;
};
