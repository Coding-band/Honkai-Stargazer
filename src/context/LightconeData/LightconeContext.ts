import { createContext } from "react";
import { LightconeData } from "./LightconeData.types";

const LightconeContext = createContext<LightconeData | undefined>(undefined);

export default LightconeContext;
