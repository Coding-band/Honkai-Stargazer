import React, { useEffect, useState } from "react";
import { CharacterName } from "../../types/character";
import useTextLanguage from "../TextLanguage/useTextLanguage";
import { CharacterData } from "./CharacterData.types";
import charList from "../../../data/character_data/character_list.json";
import { filter } from "lodash";
import { getCharFullData } from "../../utils/dataMap/getDataFromMap";
import CharacterImage from "../../../assets/images/images_map/chacracterImage";
import { Path } from "../../types/path";
import { CombatType } from "../../types/combatType";
import CharacterContext from "./CharacterContext";

export default function CharacterProvider({
  children,
  charId,
}: {
  children: any;
  charId: CharacterName;
}) {
  const { language: textLanguage } = useTextLanguage();
  const [charData, setCharData] = useState<CharacterData>();

  useEffect(() => {
    const charDataJson = filter(charList, (char) => char?.name === charId)[0];
    const charFullData = getCharFullData(charId, textLanguage);
    setCharData({
      id: charId,
      name: charFullData?.name,
      rare: charDataJson?.rare,
      pathId: charDataJson.path as Path,
      path: charFullData?.baseType?.name,
      combatTypeId: charDataJson.element as CombatType,
      combatType: charFullData?.damageType?.name,
      location: charFullData?.archive?.camp,
      image: CharacterImage[charId]?.icon,
      imageFull: CharacterImage[charId]?.imageFull,
    });
  }, []);

  return (
    <CharacterContext.Provider value={charData}>
      {children}
    </CharacterContext.Provider>
  );
}
