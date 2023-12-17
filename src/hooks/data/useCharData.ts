import { useContext } from "react";
import useTextLanguage from "../../components/global/TextLanguage/useTextLanguage";
import CharacterContext from "../../context/CharacterContext";
import { getCharFullData } from "../../utils/dataMap/getDataFromMap";

const useCharData = () => {
  const { language: textLanguage } = useTextLanguage();
  const charData = useContext(CharacterContext);
  const charId = charData?.id!;
  const charFullData = getCharFullData(charId, textLanguage);
  return { charData, charId, charFullData };
};

export default useCharData;
