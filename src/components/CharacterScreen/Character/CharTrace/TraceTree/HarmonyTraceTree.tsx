import { View, Text, Pressable } from "react-native";
import React, { useContext, useState } from "react";
import useDelayLoad from "../../../../../hooks/useDelayLoad";
import CharacterContext from "../../../../../context/CharacterContext";
import { getCharFullData } from "../../../../../utils/dataMap/getDataFromMap";
import { useClickOutside } from "react-native-click-outside";
import { Image } from "expo-image";
import Edge from "../TraceItem/Edge";
import Outer from "../TraceItem/Outer";
import Inner from "../TraceItem/Inner";
import CharacterSkillTree from "../../../../../../assets/images/@images_map/characterSkillTree";
import CharacterSkillMain from "../../../../../../assets/images/@images_map/characterSkillMain";
import TracePopUp from "../TracePopUp/TracePopUp";

const TraceLine = require("../../../../../../assets/images/path_trace_line/harmony_trace_line.svg");

export default function HarmonyTraceTree() {
  const loaded = useDelayLoad(100);

  const charData = useContext(CharacterContext);
  const charId = charData?.id!;
  const charFullData = getCharFullData(charId);

  const [selectedInner, setSelectedInner] = useState(0);

  const containerRef = useClickOutside<View>(() => {
    setSelectedInner(0);
  });

  const skillTrees = charFullData.skillTreePoints
    .slice()
    .sort((a, b) => a.id - b.id);

  const skillTreeOuter1 = skillTrees[0];
  const skillTreeOuter2 = skillTrees[1];
  const skillTreeOuter3 = skillTrees[2];
  const skillTreeOuter1Edge1 = skillTreeOuter1.children[0];
  const skillTreeOuter1Edge2 = skillTreeOuter1Edge1.children[0];
  const skillTreeOuter2Edge1 = skillTreeOuter2.children[0];
  const skillTreeOuter2Edge2 = skillTreeOuter2Edge1.children[0];
  const skillTreeOuter3Edge1 = skillTreeOuter3.children[0];
  const skillTreeOuter3Edge2 = skillTreeOuter3Edge1.children[0];
  const skillTreeOuter3Edge3 = skillTreeOuter3Edge1.children[1];
  const skillTreeOtherEdge1 = skillTrees[3];
  const skillTreeOtherEdge2 = skillTreeOtherEdge1.children[0];
  const skillTreeOtherEdge3 = skillTreeOtherEdge1.children[1];

  return (
    <>
      <Pressable
        ref={containerRef}
        onPress={() => {
          setSelectedInner(0);
        }}
        style={{
          width: 325,
          height: 404,
          alignItems: "center",
          justifyContent: "center",
        }}
      >
        {/* 軀幹 (線條) */}
        <Image source={TraceLine} style={{ width: 263, height: 374 }} />
        {/* 選項 */}
        {loaded && (
          <>
            <>
              <Edge
                left={147}
                top={0}
                icon={
                  // @ts-ignore
                  CharacterSkillTree[skillTreeOuter3Edge1.embedBuff.iconPath]
                }
              />
              <Edge
                left={85}
                top={16}
                icon={
                  // @ts-ignore
                  CharacterSkillTree[skillTreeOuter3Edge2.embedBuff.iconPath]
                }
              />
              <Edge
                left={210}
                top={16}
                icon={
                  // @ts-ignore
                  CharacterSkillTree[skillTreeOuter3Edge3.embedBuff.iconPath]
                }
              />

              <Edge
                left={18}
                top={107}
                icon={
                  // @ts-ignore
                  CharacterSkillTree[skillTreeOuter1Edge1.embedBuff.iconPath]
                }
              />
              <Edge
                left={50}
                top={75}
                icon={
                  // @ts-ignore
                  CharacterSkillTree[skillTreeOuter1Edge2.embedBuff.iconPath]
                }
              />
              <Edge
                left={274}
                top={238}
                icon={
                  // @ts-ignore
                  CharacterSkillTree[skillTreeOuter2Edge1.embedBuff.iconPath]
                }
              />
              <Edge
                left={240}
                top={270}
                icon={
                  // @ts-ignore
                  CharacterSkillTree[skillTreeOuter2Edge2.embedBuff.iconPath]
                }
              />
              <Edge
                left={145}
                top={370}
                icon={
                  // @ts-ignore
                  CharacterSkillTree[skillTreeOtherEdge1.embedBuff.iconPath]
                }
              />
              <Edge
                left={82}
                top={360}
                icon={
                  // @ts-ignore
                  CharacterSkillTree[skillTreeOtherEdge2.embedBuff.iconPath]
                }
              />
              <Edge
                left={210}
                top={360}
                icon={
                  // @ts-ignore
                  CharacterSkillTree[skillTreeOtherEdge3.embedBuff.iconPath]
                }
              />
              <Outer
                left={0}
                top={155}
                icon={
                  CharacterSkillTree[
                    // @ts-ignore
                    skillTreeOuter1.embedBonusSkill?.iconPath
                  ]
                }
              />
              <Outer
                left={260}
                top={155}
                icon={
                  CharacterSkillTree[
                    // @ts-ignore
                    skillTreeOuter2.embedBonusSkill?.iconPath
                  ]
                }
              />
              <Outer
                left={130}
                top={55}
                icon={
                  CharacterSkillTree[
                    // @ts-ignore
                    skillTreeOuter3.embedBonusSkill?.iconPath
                  ]
                }
              />
            </>
            <>
              <Inner
                left={68}
                top={142}
                icon={CharacterSkillMain[charId].skill1}
                selected={selectedInner === 1}
                onPress={() => {
                  setSelectedInner(1);
                }}
              />
              <Inner
                left={132}
                top={134}
                icon={CharacterSkillMain[charId].skill4}
                selected={selectedInner === 4}
                onPress={() => {
                  setSelectedInner(4);
                }}
              />
              <Inner
                left={132}
                top={210}
                icon={CharacterSkillMain[charId].skill3}
                selected={selectedInner === 3}
                onPress={() => {
                  setSelectedInner(3);
                }}
              />
              <Inner
                left={132}
                top={295}
                icon={CharacterSkillMain[charId].skill6}
                selected={selectedInner === 5}
                onPress={() => {
                  setSelectedInner(5);
                }}
              />
              <Inner
                left={196}
                top={142}
                icon={CharacterSkillMain[charId].skill2}
                selected={selectedInner === 2}
                onPress={() => {
                  setSelectedInner(2);
                }}
              />
            </>
          </>
        )}
      </Pressable>
      <TracePopUp
        id={selectedInner}
        onClose={() => {
          setSelectedInner(0);
        }}
      />
    </>
  );
}
