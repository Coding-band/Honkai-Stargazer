import { ExpoImage } from "../../types/image";
import { LightconeName } from "../../types/lightcone";
import { Path } from "../../types/path";

export type LightconeData = {
  id: LightconeName;
  name: string;
  image: ExpoImage;
  imageFull: ExpoImage;
  rare: number;
  path: string;
  pathId: Path;
};
