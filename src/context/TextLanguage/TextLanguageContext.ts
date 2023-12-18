import { createContext } from "react";
import { TextLanguage } from "../../types/language";

const TextLanguageContext = createContext<{
  language: TextLanguage;
  setLanguage?: (l: TextLanguage) => void;
}>({ language: "zh_cn" });
export default TextLanguageContext;
