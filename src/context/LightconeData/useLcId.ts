import { useContext } from "react";
import LightconeContext from "./LightconeContext";

const useLcId = () => {
  const lcData = useContext(LightconeContext);
  const lcId = lcData?.id!;
  return lcId;
};

export default useLcId;
