import { useContext, useMemo } from "react";
import useTextLanguage from "../../../language/TextLanguage/useTextLanguage";
import CharacterContext from "../CharacterContext";
import { getCharFullData } from "../../../utils/dataMap/getDataFromMap";
import { CharacterName } from "../../../types/character";

const useCharData = () => {
  const { charData, charFullData, charJsonData } =
    useContext(CharacterContext)!;
  const charId = charData?.id! as CharacterName;
  return { charData, charId, charFullData, charJsonData };
};

export default useCharData;
