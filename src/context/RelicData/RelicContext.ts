import { createContext } from "react";
import { RelicData } from "./RelicData.types";

const RelicContext = createContext<RelicData | undefined>(undefined);

export default RelicContext;
