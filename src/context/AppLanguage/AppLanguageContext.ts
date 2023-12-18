import { createContext } from "react";
import { AppLanguage } from "../../types/language";

const AppLanguageContext = createContext<{
  language: AppLanguage;
  setLanguage?: (l: AppLanguage) => void;
}>({ language: "zh_cn" });
export default AppLanguageContext;
