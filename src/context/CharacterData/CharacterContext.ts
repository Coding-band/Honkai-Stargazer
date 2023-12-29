import { createContext } from "react";
import { CharacterData } from "./CharacterData.types";

const CharacterContext = createContext<CharacterData | undefined>(undefined);
export default CharacterContext;
