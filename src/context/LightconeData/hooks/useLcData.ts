import { useContext, useMemo } from "react";
import useTextLanguage from "../../TextLanguage/useTextLanguage";
import { getLcFullData } from "../../../utils/dataMap/getDataFromMap";
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
