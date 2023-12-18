import { useContext } from "react";
import useTextLanguage from "../TextLanguage/useTextLanguage";
import CharacterContext from "./CharacterContext";
import { getCharFullData } from "../../utils/dataMap/getDataFromMap";

const useCharData = () => {
  const { language: textLanguage } = useTextLanguage();
  const charData = useContext(CharacterContext);
  const charId = charData?.id!;
  const charFullData = getCharFullData(charId, textLanguage);
  return { charData, charId, charFullData };
};

export default useCharData;
