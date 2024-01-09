import { View, Text, Pressable } from "react-native";
import React, { useContext, useState } from "react";
import CharacterContext from "../../../../../context/CharacterData/CharacterContext";
import { getCharFullData } from "../../../../../utils/dataMap/getDataFromMap";
import { Image } from "expo-image";
import { useClickOutside } from "react-native-click-outside";
import Edge from "../TraceItem/Edge";
import useDelayLoad from "../../../../../hooks/useDelayLoad";
import Outer from "../TraceItem/Outer";
import Inner from "../TraceItem/Inner";
import TracePopUp from "../TracePopUp/TracePopUp";
import CharacterSkillTree from "../../../../../../assets/images/images_map/characterSkillTree";
import CharacterSkillMain from "../../../../../../assets/images/images_map/characterSkillMain";

const TraceLine = require("./images/path_trace_line/nihility_trace_line.svg");

export default function NihilityTraceTree() {
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
  const skillTreeOuter3Edge2 = skillTreeOuter3.children[1];
  const skillTreeOtherEdge1 = skillTrees[3];
  const skillTreeOtherEdge2 = skillTreeOtherEdge1.children[0];

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
        <Image source={TraceLine} style={{ width: 299, height: 360 }} />
        {/* 選項 */}
        {loaded && (
          <>
            <>
              <Edge
                left={210}
                top={20}
                icon={
                  // @ts-ignore
                  CharacterSkillTree[skillTreeOuter3Edge1.embedBuff.iconPath]
                }
              />
              <Edge
                left={87}
                top={20}
                icon={
                  // @ts-ignore
                  CharacterSkillTree[skillTreeOuter3Edge2.embedBuff.iconPath]
                }
              />
              <Edge
                left={255}
                top={230}
                icon={
                  // @ts-ignore
                  CharacterSkillTree[skillTreeOuter1Edge1.embedBuff.iconPath]
                }
              />
              <Edge
                left={275}
                top={185}
                icon={
                  // @ts-ignore
                  CharacterSkillTree[skillTreeOuter1Edge2.embedBuff.iconPath]
                }
              />
              <Edge
                left={295}
                top={140}
                icon={
                  // @ts-ignore
                  CharacterSkillTree[skillTreeOuter1Edge3.embedBuff.iconPath]
                }
              />
              <Edge
                left={43}
                top={230}
                icon={
                  // @ts-ignore
                  CharacterSkillTree[skillTreeOuter2Edge1.embedBuff.iconPath]
                }
              />
              <Edge
                left={20}
                top={185}
                icon={
                  // @ts-ignore
                  CharacterSkillTree[skillTreeOuter2Edge2.embedBuff.iconPath]
                }
              />
              <Edge
                left={-2}
                top={140}
                icon={
                  // @ts-ignore
                  CharacterSkillTree[skillTreeOuter2Edge3.embedBuff.iconPath]
                }
              />
              <Edge
                left={148}
                top={312}
                icon={
                  // @ts-ignore
                  CharacterSkillTree[skillTreeOtherEdge1.embedBuff.iconPath]
                }
              />
              <Edge
                left={148}
                top={365}
                icon={
                  // @ts-ignore
                  CharacterSkillTree[skillTreeOtherEdge2.embedBuff?.iconPath]
                }
              />
              <Outer
                left={245}
                top={73}
                icon={
                  // @ts-ignore
                  CharacterSkillTree[skillTreeOuter1.embedBonusSkill?.iconPath]
                }
              />
              <Outer
                left={20}
                top={73}
                icon={
                  // @ts-ignore
                  CharacterSkillTree[skillTreeOuter2.embedBonusSkill?.iconPath]
                }
              />
              <Outer
                left={133}
                top={-10}
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
                left={60}
                top={158}
                icon={CharacterSkillMain[charId].skill1}
                selected={selectedInner === 1}
                onPress={() => {
                  setSelectedInner(1);
                }}
              />

              <Inner
                left={133}
                top={75}
                icon={CharacterSkillMain[charId].skill4}
                selected={selectedInner === 4}
                onPress={() => {
                  setSelectedInner(4);
                }}
              />
              <Inner
                left={133}
                top={150}
                icon={CharacterSkillMain[charId].skill3}
                selected={selectedInner === 3}
                onPress={() => {
                  setSelectedInner(3);
                }}
              />
              <Inner
                left={133}
                top={230}
                icon={CharacterSkillMain[charId].skill6}
                selected={selectedInner === 5}
                onPress={() => {
                  setSelectedInner(5);
                }}
              />
              <Inner
                left={206}
                top={158}
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
