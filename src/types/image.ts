import { ImageSource } from "expo-image";

export type ExpoImage =
  | string
  | number
  | ImageSource
  | ImageSource[]
  | string[]
  | null
  | undefined;
