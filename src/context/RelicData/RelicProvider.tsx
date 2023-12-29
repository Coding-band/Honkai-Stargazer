import React, { useEffect, useState } from "react";
import RelicContext from "./RelicContext";
import { RelicName } from "../../types/relic";
import { getRelicFullData } from "../../utils/dataMap/getDataFromMap";
import useTextLanguage from "../TextLanguage/useTextLanguage";
import RelicMap from "../../../assets/images/images_map/relic";
import { RelicData } from "./RelicData.types";

export default function RelicProvider({
  children,
  relicId,
}: {
  children: any;
  relicId: RelicName;
}) {
  const { language: textLanguage } = useTextLanguage();
  const [relicData, setRelicData] = useState<RelicData>();

  useEffect(() => {
    const relicFullData = getRelicFullData(relicId, textLanguage);
    setRelicData({
      id: relicId,
      name: relicFullData?.name,
      rare: 5,
      image: RelicMap[relicId].icon1,
      imageFull: [
        RelicMap[relicId].icon1,
        RelicMap[relicId].icon2,
        // @ts-ignore
        RelicMap[relicId]?.icon3,
        // @ts-ignore
        RelicMap[relicId]?.icon4,
      ],
    });
  }, []);

  return (
    <RelicContext.Provider value={relicData}>{children}</RelicContext.Provider>
  );
}
