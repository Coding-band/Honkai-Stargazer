import { createContext } from "react";
import { TextLanguage } from "../language.types";

const TextLanguageContext = createContext<{
  language?: TextLanguage;
  setLanguage?: (l: TextLanguage) => void;
}>({ });
export default TextLanguageContext;
