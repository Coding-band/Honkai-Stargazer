import { createContext } from "react";
import { CharacterName } from "../../types/character";

const UserCharDetailContext = createContext<
  { charId: CharacterName; uuid: string } | undefined
>(undefined);

export default UserCharDetailContext;
