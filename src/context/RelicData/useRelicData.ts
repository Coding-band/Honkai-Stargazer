import { useContext, useMemo } from "react";
import useTextLanguage from "../TextLanguage/useTextLanguage";
import { getRelicFullData } from "../../utils/dataMap/getDataFromMap";
import RelicContext from "./RelicContext";

const useRelicData = () => {
  const { language: textLanguage } = useTextLanguage();
  const relicData = useContext(RelicContext);
  const relicId = relicData?.id!;
  const relicFullData = useMemo(
    () => getRelicFullData(relicId, textLanguage),
    [relicId, textLanguage]
  );
  return { relicData, relicId, relicFullData };
};

export default useRelicData;
