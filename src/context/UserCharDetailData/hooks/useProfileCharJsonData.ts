import useTextLanguage from "../../../language/TextLanguage/useTextLanguage";
import {
  getCharFullData,
  getCharJsonData,
} from "../../../utils/data/getDataFromMap";
import useCharId from "./useProfileCharId";

const useProfileCharJsonData = () => {
  const charId = useCharId();
  const charJsonData = getCharJsonData(charId);
  return charJsonData;
};

export default useProfileCharJsonData;
