import React from "react";
import { AppLanguage } from "../language.types";
import useLocalState from "../../hooks/useLocalState";
import AppLanguageContext from "./AppLanguageContext";

export default function AppLanguageProvider({ children }: { children: any }) {
  const [language, setLanguage] = useLocalState<AppLanguage>(
    "app-language",
    "zh_hk"
  );

  return (
    <AppLanguageContext.Provider value={{ language, setLanguage }}>
      {children}
    </AppLanguageContext.Provider>
  );
}
