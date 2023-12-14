import { View, Text, Pressable } from "react-native";
import React, { useContext, useState } from "react";
import CharacterContext from "../../../../../context/CharacterContext";
import { getCharFullData } from "../../../../../utils/dataMap/getDataFromMap";
import { Image } from "expo-image";
import { useClickOutside } from "react-native-click-outside";
import Edge from "../TraceItem/Edge";
import useDelayLoad from "../../../../../hooks/useDelayLoad";
import Outer from "../TraceItem/Outer";
import Inner from "../TraceItem/Inner";
import TracePopUp from "../TracePopUp/TracePopUp";
import CharacterSkillTree from "../../../../../../assets/images/@images_map/characterSkillTree";
import CharacterSkillMain from "../../../../../../assets/images/@images_map/characterSkillMain";

const TraceLine = require("../../../../../../assets/images/path_trace_line/destruction_trace_line.svg");

export default function DestructionTraceTree() {
  const loaded = useDelayLoad(100);

  const charData = useContext(CharacterContext);
  const charId = charData?.id!;
  const charFullData = getCharFullData(charId);

  const skillTrees = charFullData.skillTreePoints
    .slice()
    .sort((a, b) => a.id - b.id);

  const skillTreeOuter1 = skillTrees[0];
  const skillTreeOuter2 = skillTrees[1];
  const skillTreeOuter3 = skillTrees[2];
  const skillTreeOuter1Edge1 = skillTreeOuter1.children[0];
  const skillTreeOuter1Edge2 = skillTreeOuter1Edge1.children[0];
  const skillTreeOuter1Edge3 = skillTreeOuter1Edge2.children[0];
  const skillTreeOuter2Edge1 = skillTreeOuter2.children[0];
  const skillTreeOuter2Edge2 = skillTreeOuter2Edge1.children[0];
  const skillTreeOuter2Edge3 = skillTreeOuter2Edge2.children[0];
  const skillTreeOuter3Edge1 = skillTreeOuter3.children[0];
  const skillTreeOuter3Edge2 = skillTreeOuter3Edge1.children[0];
  const skillTreeOuter3Edge3 = skillTreeOuter3Edge1.children[1];
  const skillTreeOtherEdge1 = skillTrees[3];

  const [selectedInner, setSelectedInner] = useState(0);

  const containerRef = useClickOutside<View>(() => {
    setSelectedInner(0);
  });

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
        <Image source={TraceLine} style={{ width: 296, height: 374 }} />
        {/* 選項 */}
        {loaded && (
          <>
            <>
              <Edge
                left={150}
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
                left={215}
                top={16}
                icon={
                  // @ts-ignore
                  CharacterSkillTree[skillTreeOuter3Edge3.embedBuff.iconPath]
                }
              />

              <Edge
                left={30}
                top={260}
                icon={
                  // @ts-ignore
                  CharacterSkillTree[skillTreeOuter1Edge1.embedBuff.iconPath]
                }
              />
              <Edge
                left={0}
                top={210}
                icon={
                  // @ts-ignore
                  CharacterSkillTree[skillTreeOuter1Edge2.embedBuff.iconPath]
                }
              />
              <Edge
                left={23}
                top={160}
                icon={
                  // @ts-ignore
                  CharacterSkillTree[skillTreeOuter1Edge3.embedBuff.iconPath]
                }
              />
              <Edge
                left={265}
                top={260}
                icon={
                  // @ts-ignore
                  CharacterSkillTree[skillTreeOuter2Edge1.embedBuff.iconPath]
                }
              />
              <Edge
                left={290}
                top={210}
                icon={
                  // @ts-ignore
                  CharacterSkillTree[skillTreeOuter2Edge2.embedBuff.iconPath]
                }
              />
              <Edge
                left={271}
                top={160}
                icon={
                  // @ts-ignore
                  CharacterSkillTree[skillTreeOuter2Edge3.embedBuff.iconPath]
                }
              />
              <Edge
                left={150}
                top={374}
                icon={
                  // @ts-ignore
                  CharacterSkillTree[skillTreeOtherEdge1.embedBuff.iconPath]
                }
              />
              <Outer
                left={55}
                top={305}
                icon={
                  CharacterSkillTree[
                    // @ts-ignore
                    skillTreeOuter1.embedBonusSkill?.iconPath
                  ]
                }
              />
              <Outer
                left={215}
                top={305}
                icon={
                  CharacterSkillTree[
                    // @ts-ignore
                    skillTreeOuter2.embedBonusSkill?.iconPath
                  ]
                }
              />
              <Outer
                left={135}
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
                left={55}
                top={188}
                icon={CharacterSkillMain[charId].skill1}
                selected={selectedInner === 1}
                onPress={() => {
                  setSelectedInner(1);
                }}
              />

              <Inner
                left={136}
                top={134}
                icon={CharacterSkillMain[charId].skill4}
                selected={selectedInner === 4}
                onPress={() => {
                  setSelectedInner(4);
                }}
              />
              <Inner
                left={136}
                top={216}
                icon={CharacterSkillMain[charId].skill3}
                selected={selectedInner === 3}
                onPress={() => {
                  setSelectedInner(3);
                }}
              />
              <Inner
                left={136}
                top={295}
                icon={CharacterSkillMain[charId].skill6}
                selected={selectedInner === 5}
                onPress={() => {
                  setSelectedInner(5);
                }}
              />
              <Inner
                left={214}
                top={188}
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
