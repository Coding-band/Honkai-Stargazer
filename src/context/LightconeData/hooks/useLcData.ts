import { useContext, useMemo } from "react";
import useTextLanguage from "../../../language/TextLanguage/useTextLanguage";
import { getLcFullData } from "../../../utils/data/getDataFromMap";
import LightconeContext from "../LightconeContext";

const useLcData = () => {
  const { language: textLanguage } = useTextLanguage();
  const lcData = useContext(LightconeContext);
  const lcId = lcData?.id!;
  const lcFullData = useMemo(
    () => getLcFullData(lcId, textLanguage),
    [lcId, textLanguage]
  );
  return { lcData, lcId, lcFullData };
};

export default useLcData;
