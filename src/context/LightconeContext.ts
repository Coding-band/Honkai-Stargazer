import { createContext } from "react";
import { Lightcone } from "../types/lightcone";

const LightconeContext = createContext<Lightcone | null>(null);

export default LightconeContext;
