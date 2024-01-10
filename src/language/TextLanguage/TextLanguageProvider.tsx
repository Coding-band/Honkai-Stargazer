import React from "react";
import TextLanguageContext from "./TextLanguageContext";
import { TextLanguage } from "../language.types";
import useLocalState from "../../hooks/useLocalState";

export default function TextLanguageProvider({ children }: { children: any }) {
  const [language, setLanguage] = useLocalState<TextLanguage>(
    "text-language",
    "zh_hk"
  );

  return (
    <TextLanguageContext.Provider value={{ language, setLanguage }}>
      {children}
    </TextLanguageContext.Provider>
  );
}
