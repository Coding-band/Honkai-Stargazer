import { createContext } from "react";
import { Character } from "../types/character";

const CharacterContext = createContext<Character | null>(null);

export default CharacterContext;
