import { createContext } from "react";
import Character from "../types/Character";

type CharacterContextType = Character | null;

const CharacterContext = createContext<CharacterContextType>(null);

export default CharacterContext