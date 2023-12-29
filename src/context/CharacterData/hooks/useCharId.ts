import { useContext } from "react";
import CharacterContext from "../CharacterContext";

const useCharId = () => {
  const charData = useContext(CharacterContext);
  const charId = charData?.id!;
  return charId;
};

export default useCharId;
