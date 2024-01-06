import React, { useEffect, useState } from "react";
import { LightconeName } from "../../types/lightcone";
import useTextLanguage from "../../language/TextLanguage/useTextLanguage";
import { filter } from "lodash";
import lcList from "../../../data/lightcone_data/lightcone_list.json";
import { getLcFullData } from "../../utils/dataMap/getDataFromMap";
import LightconeMap from "../../../assets/images/images_map/lightcone";
import LightconeContext from "./LightconeContext";
import { LightconeData } from "./LightconeData.types";
import { Path } from "../../types/path";

export default function LightconeProvider({
  children,
  lcId,
}: {
  children: any;
  lcId: LightconeName;
}) {
  const { language: textLanguage } = useTextLanguage();
  const [lcData, setLcData] = useState<LightconeData>();

  useEffect(() => {
    const lcDataJson = filter(lcList, (lc) => lc?.name === lcId)[0];
    const lcFullData = getLcFullData(lcId, textLanguage);
    setLcData({
      id: lcId,
      name: lcFullData?.name,
      rare: lcDataJson?.rare,
      path: lcFullData?.baseType?.name,
      pathId: lcDataJson.path as Path,
      image: LightconeMap[lcId]?.icon,
      imageFull: LightconeMap[lcId]?.imageFull,
    });
  }, []);

  return (
    <LightconeContext.Provider value={lcData}>
      {children}
    </LightconeContext.Provider>
  );
}
