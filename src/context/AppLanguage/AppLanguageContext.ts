import { createContext } from "react";
import { AppLanguage } from "../../language/language.types";

const AppLanguageContext = createContext<{
  language: AppLanguage;
  setLanguage?: (l: AppLanguage) => void;
}>({ language: "zh_cn" });
export default AppLanguageContext;
