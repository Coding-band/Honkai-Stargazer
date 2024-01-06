import { createContext } from "react";
import { AppLanguage } from "../language.types";

const AppLanguageContext = createContext<{
  language: AppLanguage;
  setLanguage?: (l: AppLanguage) => void;
}>({ language: "zh_hk" });
export default AppLanguageContext;
