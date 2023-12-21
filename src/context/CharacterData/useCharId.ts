import { useContext } from "react";
import useTextLanguage from "../TextLanguage/useTextLanguage";
import CharacterContext from "./CharacterContext";
import { getCharFullData } from "../../utils/dataMap/getDataFromMap";

const useCharId = () => {
  const { language: textLanguage } = useTextLanguage();
  const charData = useContext(CharacterContext);
  const charId = charData?.id!;
  return charId;
};

export default useCharId;
