import React, { useEffect, useState } from "react";
import { Lightcone, LightconeName } from "../../types/lightcone";
import useTextLanguage from "../TextLanguage/useTextLanguage";
import { filter } from "lodash";
import lcList from "../../../data/lightcone_data/lightcone_list.json";
import { getLcFullData } from "../../utils/dataMap/getDataFromMap";
import LightconeMap from "../../../assets/images/images_map/lightcone";
import LightconeContext from "./LightconeContext";

export default function LightconeProvider({
  children,
  lcId,
}: {
  children: any;
  lcId: LightconeName;
}) {
  const { language: textLanguage } = useTextLanguage();
  const [lcData, setLcData] = useState<Lightcone>({});

  useEffect(() => {
    const lcDataJson = filter(lcList, (lc) => lc?.name === lcId)[0];
    const lcFullData = getLcFullData(lcId, textLanguage);
    setLcData({
      id: lcId,
      name: lcFullData?.name,
      rare: lcDataJson?.rare,
      path: lcFullData?.baseType?.name,
      pathId: lcDataJson.path,
      imageFull: LightconeMap[lcId]?.imageFull,
    });
  }, []);

  return (
    <LightconeContext.Provider value={lcData}>
      {children}
    </LightconeContext.Provider>
  );
}
