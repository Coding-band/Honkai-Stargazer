import { useContext } from "react";
import RelicContext from "./RelicContext";

const useRelicId = () => {
  const relicData = useContext(RelicContext);
  const relicId = relicData?.id!;
  return relicId;
};

export default useRelicId;
