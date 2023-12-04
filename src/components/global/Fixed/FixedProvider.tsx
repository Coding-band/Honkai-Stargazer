import React, { useState, createContext } from "react";
import FixedContext from "./FixedContext";

export const FixedProvider = ({ children }: { children: any }) => {
  const [content, setContent] = useState(null);

  return (
    <FixedContext.Provider value={{ content, setFixed: setContent }}>
      {children}
    </FixedContext.Provider>
  );
};

export default FixedProvider;
