import { createContext } from "react";
import { AppLanguage } from "../language.types";

const AppLanguageContext = createContext<{
  language?: AppLanguage;
  setLanguage?: (l: AppLanguage) => void;
}>({});
export default AppLanguageContext;
