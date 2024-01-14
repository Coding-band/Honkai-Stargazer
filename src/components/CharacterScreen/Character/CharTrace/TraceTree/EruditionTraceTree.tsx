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
import useCharData from "../../../../../context/CharacterData/hooks/useCharData";

const TraceLine = require("./images/path_trace_line/erudition_trace_line.svg");

export default function EruditionTraceTree() {
  const loaded = useDelayLoad(100);

  const { charFullData, charId } = useCharData();

  const skillTrees: any = charFullData.skillTreePoints
    .slice()
    .sort((a, b) => a.id - b.id);
  const skillGrouping = charFullData?.skillGrouping;
  const skills = skillGrouping?.map((group) => {
    const skillId = group[0];
    return charFullData?.skills.filter((skill) => skill.id === skillId)[0];
  });
  const skillTreeOuter1 = skillTrees[0];
  const skillTreeOuter2 = skillTrees[1];
  const skillTreeOuter3 = skillTrees[2];
  const skillTreeOuter1Edge1 = skillTreeOuter1.children[0];
  const skillTreeOuter1Edge2 = skillTreeOuter1Edge1.children[0];
  const skillTreeOuter1Edge3 = skillTreeOuter1Edge1.children[1];
  const skillTreeOuter2Edge1 = skillTreeOuter2.children[0];
  const skillTreeOuter2Edge2 = skillTreeOuter2Edge1.children[0];
  const skillTreeOuter2Edge3 = skillTreeOuter2Edge1.children[1];
  const skillTreeOuter3Edge1 = skillTreeOuter3.children[0];
  const skillTreeOuter3Edge2 = skillTreeOuter3.children[1];
  const skillTreeOtherEdge1 = skillTrees[3];
  const skillTreeOtherEdge2 = skillTrees[4];
  const skillTreeInner1 = skills[0];
  const skillTreeInner2 = skills[1];
  const skillTreeInner3 = skills[2];
  const skillTreeInner4 = skills[3];
  const skillTreeInner6 = skills[4];

  const [selectType, setSelectType] = useState<"outer" | "inner" | "edge">();
  const [selectData, setSelectData] = useState<any>(null);

  const containerRef = useClickOutside<View>(() => {
    setSelectData(null);
  });

  return (
    <>
      <Pressable
        ref={containerRef}
        onPress={() => {
          setSelectData(null);
        }}
        style={{
          width: 325,
          height: 404,
          alignItems: "center",
          justifyContent: "center",
        }}
      >
        {/* 軀幹 (線條) */}
        <Image source={TraceLine} style={{ width: 297, height: 353 }} />
        {/* 選項 */}
        {loaded && (
          <>
            <>
              <Edge
                left={85}
                top={24}
                icon={
                  // @ts-ignore
                  CharacterSkillTree[skillTreeOuter3Edge1.embedBuff.iconPath]
                }
                onPress={() => {
                  setSelectType("edge");
                  setSelectData(skillTreeOuter3Edge1);
                }}
              />
              <Edge
                left={214}
                top={24}
                icon={
                  // @ts-ignore
                  CharacterSkillTree[skillTreeOuter3Edge2.embedBuff.iconPath]
                }
                onPress={() => {
                  setSelectType("edge");
                  setSelectData(skillTreeOuter3Edge2);
                }}
              />
              <Edge
                left={0}
                top={153}
                icon={
                  // @ts-ignore
                  CharacterSkillTree[skillTreeOuter1Edge1.embedBuff.iconPath]
                }
                onPress={() => {
                  setSelectType("edge");
                  setSelectData(skillTreeOuter1Edge1);
                }}
              />
              <Edge
                left={10}
                top={215}
                icon={
                  // @ts-ignore
                  CharacterSkillTree[skillTreeOuter1Edge2.embedBuff.iconPath]
                }
                onPress={() => {
                  setSelectType("edge");
                  setSelectData(skillTreeOuter1Edge2);
                }}
              />

              <Edge
                left={10}
                top={95}
                icon={
                  // @ts-ignore
                  CharacterSkillTree[skillTreeOuter1Edge3.embedBuff.iconPath]
                }
                onPress={() => {
                  setSelectType("edge");
                  setSelectData(skillTreeOuter1Edge3);
                }}
              />

              <Edge
                left={285}
                top={95}
                icon={
                  // @ts-ignore
                  CharacterSkillTree[skillTreeOuter2Edge1.embedBuff.iconPath]
                }
                onPress={() => {
                  setSelectType("edge");
                  setSelectData(skillTreeOuter2Edge1);
                }}
              />
              <Edge
                left={285}
                top={215}
                icon={
                  // @ts-ignore
                  CharacterSkillTree[skillTreeOuter2Edge2.embedBuff.iconPath]
                }
                onPress={() => {
                  setSelectType("edge");
                  setSelectData(skillTreeOuter2Edge2);
                }}
              />
              <Edge
                left={295}
                top={153}
                icon={
                  // @ts-ignore
                  CharacterSkillTree[skillTreeOuter2Edge3.embedBuff.iconPath]
                }
                onPress={() => {
                  setSelectType("edge");
                  setSelectData(skillTreeOuter2Edge3);
                }}
              />
              <Edge
                left={80}
                top={345}
                icon={
                  // @ts-ignore
                  CharacterSkillTree[skillTreeOtherEdge1.embedBuff.iconPath]
                }
                onPress={() => {
                  setSelectType("edge");
                  setSelectData(skillTreeOtherEdge1);
                }}
              />
              <Edge
                left={215}
                top={345}
                icon={
                  // @ts-ignore
                  CharacterSkillTree[skillTreeOtherEdge2.embedBuff.iconPath]
                }
                onPress={() => {
                  setSelectType("edge");
                  setSelectData(skillTreeOtherEdge2);
                }}
              />
              <Outer
                left={51}
                top={134}
                icon={
                  CharacterSkillTree[
                    // @ts-ignore
                    skillTreeOuter1.embedBonusSkill?.iconPath
                  ]
                }
                onPress={() => {
                  setSelectType("outer");
                  setSelectData(skillTreeOuter1);
                }}
              />
              <Outer
                left={213}
                top={134}
                icon={
                  CharacterSkillTree[
                    // @ts-ignore
                    skillTreeOuter2.embedBonusSkill?.iconPath
                  ]
                }
                onPress={() => {
                  setSelectType("outer");
                  setSelectData(skillTreeOuter2);
                }}
              />
              <Outer
                left={134}
                top={0}
                icon={
                  CharacterSkillTree[
                    // @ts-ignore
                    skillTreeOuter2.embedBonusSkill?.iconPath
                  ]
                }
                onPress={() => {
                  setSelectType("outer");
                  setSelectData(skillTreeOuter2);
                }}
              />
            </>
            <>
              <Inner
                left={52}
                top={205}
                icon={CharacterSkillMain[charId].skill1}
                onPress={() => {
                  setSelectType("inner");
                  setSelectData(skillTreeInner1);
                }}
              />
              <Inner
                left={133}
                top={125}
                icon={CharacterSkillMain[charId].skill4}
                onPress={() => {
                  setSelectType("inner");
                  setSelectData(skillTreeInner4);
                }}
              />
              <Inner
                left={133}
                top={205}
                icon={CharacterSkillMain[charId].skill3}
                onPress={() => {
                  setSelectType("inner");
                  setSelectData(skillTreeInner3);
                }}
              />

              <Inner
                left={133}
                top={344}
                icon={CharacterSkillMain[charId].skill6}
                onPress={() => {
                  setSelectType("inner");
                  setSelectData(skillTreeInner6);
                }}
              />
              <Inner
                left={214}
                top={205}
                icon={CharacterSkillMain[charId].skill2}
                onPress={() => {
                  setSelectType("inner");
                  setSelectData(skillTreeInner2);
                }}
              />
            </>
          </>
        )}
      </Pressable>
      <TracePopUp
        type={selectType}
        data={selectData}
        // id={selectedInner}
        onClose={() => {
          setSelectData(null);
        }}
      />
    </>
  );
}
