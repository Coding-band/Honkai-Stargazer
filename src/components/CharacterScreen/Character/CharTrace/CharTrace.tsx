import { View } from "react-native";
import React, { useContext } from "react";
import CharPageHeading from "../../../global/PageHeading/PageHeading";
import { TreeStructure } from "phosphor-react-native";
import HuntTraceTree from "./TraceTree/HuntTraceTree";
import CharacterContext from "../../../../context/CharacterContext";
import Path from "../../../../constant/path";
import DestructionTraceTree from "./TraceTree/DestructionTraceTree";
import HarmonyTraceTree from "./TraceTree/HarmonyTraceTree";
import EruditionTraceTree from "./TraceTree/EruditionTraceTree";

export default React.memo(function CharTrace() {
  const charData = useContext(CharacterContext);
  const charPathId = charData?.pathId;

  return (
    <View style={{ alignItems: "center" }}>
      <CharPageHeading Icon={TreeStructure}>行迹树</CharPageHeading>
      {charPathId === "Hunt" && <HuntTraceTree />}
      {charPathId === "Destruction" && <DestructionTraceTree />}
      {charPathId === "Harmony" && <HarmonyTraceTree />}
      {charPathId === "Erudition" && <EruditionTraceTree />}
    </View>
  );
});
