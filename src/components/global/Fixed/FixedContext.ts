import { createContext } from "react";

const FixedContext = createContext<{
  content: any;
  setFixed: any;
} | null>(null);
export default FixedContext;
