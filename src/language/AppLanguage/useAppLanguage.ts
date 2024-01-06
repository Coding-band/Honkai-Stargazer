import { useContext } from "react";
import AppLanguageContext from "./AppLanguageContext";

const useAppLanguage = () => {
  return useContext(AppLanguageContext);
};

export default useAppLanguage;
