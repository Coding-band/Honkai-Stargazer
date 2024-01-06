import { View } from "react-native";
import React, { useContext } from "react";
import CharPageHeading from "../../../global/PageHeading/PageHeading";
import { TreeStructure } from "phosphor-react-native";
import HuntTraceTree from "./TraceTree/HuntTraceTree";
import CharacterContext from "../../../../context/CharacterData/CharacterContext";
import Path from "../../../../constant/path";
import DestructionTraceTree from "./TraceTree/DestructionTraceTree";
import HarmonyTraceTree from "./TraceTree/HarmonyTraceTree";
import EruditionTraceTree from "./TraceTree/EruditionTraceTree";
import AbundanceTraceTree from "./TraceTree/AbundanceTraceTree";
import PreservationTraceTree from "./TraceTree/PreservationTraceTree";
import useCharData from "../../../../context/CharacterData/hooks/useCharData";
import useAppLanguage from "../../../../language/AppLanguage/useAppLanguage";
import { LOCALES } from "../../../../../locales";

export default React.memo(function CharTrace() {
  const { language } = useAppLanguage();
  const { charData } = useCharData();
  const charPathId = charData?.pathId;

  return (
    <View style={{ alignItems: "center" }}>
      <CharPageHeading Icon={TreeStructure}>
        {LOCALES[language].TraceTree}
      </CharPageHeading>
      {charPathId === "Hunt" && <HuntTraceTree />}
      {charPathId === "Destruction" && <DestructionTraceTree />}
      {charPathId === "Harmony" && <HarmonyTraceTree />}
      {charPathId === "Erudition" && <EruditionTraceTree />}
      {charPathId === "Abundance" && <AbundanceTraceTree />}
      {charPathId === "Preservation" && <PreservationTraceTree />}
    </View>
  );
});
