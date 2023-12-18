import { useContext } from "react";
import TextLanguageContext from "./TextLanguageContext";

const useTextLanguage = () => {
  return useContext(TextLanguageContext);
};

export default useTextLanguage;
