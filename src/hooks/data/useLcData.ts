import { useContext } from "react";
import useTextLanguage from "../../components/global/TextLanguage/useTextLanguage";
import { getLcFullData } from "../../utils/dataMap/getDataFromMap";
import LightconeContext from "../../context/LightconeContext";

const useLcData = () => {
  const { language: textLanguage } = useTextLanguage();
  const lcData = useContext(LightconeContext);
  const lcId = lcData?.id!;
  const lcFullData = getLcFullData(lcId, textLanguage);
  return { lcData, lcId, lcFullData };
};

export default useLcData;
