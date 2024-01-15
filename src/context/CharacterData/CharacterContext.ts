import { createContext } from "react";
import { CharacterData } from "./CharacterData.types";

const CharacterContext = createContext<
  | {
      charData: CharacterData;
      charJsonData: any;
      charFullData: any;
    }
  | undefined
>(undefined);
export default CharacterContext;
