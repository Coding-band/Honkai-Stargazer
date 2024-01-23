import React, { useEffect, useState } from "react";
import { CharacterName } from "../../types/character";
import useTextLanguage from "../../language/TextLanguage/useTextLanguage";
import { CharacterData } from "./CharacterData.types";
import charList from "../../../data/character_data/character_list.json";
import { filter } from "lodash";
import {
  getCharFullData,
  getCharJsonData,
} from "../../utils/data/getDataFromMap";
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

  const charJsonData = getCharJsonData(charId);
  const charFullData = getCharFullData(charId, textLanguage);
  const [charData, setCharData] = useState<CharacterData>({});

  useEffect(() => {
    setCharData({
      id: charId,
      name: charFullData?.name,
      rare: charJsonData?.rare,
      pathId: charJsonData.path as Path,
      path: charFullData?.baseType?.name,
      combatTypeId: charJsonData.element as CombatType,
      combatType: charFullData?.damageType?.name,
      location: charFullData?.archive?.camp,
      image: CharacterImage[charId]?.icon,
      imageFull: CharacterImage[charId]?.imageFull,
    });
  }, []);

  return (
    <CharacterContext.Provider value={{ charData, charJsonData, charFullData }}>
      {children}
    </CharacterContext.Provider>
  );
}
