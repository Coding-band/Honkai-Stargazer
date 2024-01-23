import useTextLanguage from "../../../language/TextLanguage/useTextLanguage";
import { getCharFullData } from "../../../utils/data/getDataFromMap";
import useCharId from "./useProfileCharId";

const useProfileCharFullData = () => {
  const { language: textLanguage } = useTextLanguage();
  const charId = useCharId();
  const charFullData = getCharFullData(charId, textLanguage);
  return charFullData;
};

export default useProfileCharFullData;
