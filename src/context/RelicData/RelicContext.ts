import { createContext } from "react";
import { Relic } from "../../types/relic";

const RelicContext = createContext<Relic | null>(null);

export default RelicContext;
