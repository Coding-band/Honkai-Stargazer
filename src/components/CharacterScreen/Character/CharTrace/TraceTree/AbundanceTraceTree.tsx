import { View, Pressable } from "react-native";
import React, { useState } from "react";
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
import Path from "../../../../../../assets/images/images_map/path";

const TraceLine = require("./images/path_trace_line/abundance_trace_line.svg");

export default function AbundanceTraceTree() {
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
  const skillTreeOuter1Edge3 = skillTreeOuter1Edge2.children[0];
  const skillTreeOuter2Edge1 = skillTreeOuter2.children[0];
  const skillTreeOuter2Edge2 = skillTreeOuter2Edge1.children[0];
  const skillTreeOuter2Edge3 = skillTreeOuter2Edge2.children[0];
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
        <Image source={TraceLine} style={{ width: 308, height: 366 }} />
        <Image
          className="absolute left-4 opacity-40 w-[300px] h-[300px]"
          source={Path["Abundance"].icon2}
        />
        {/* 選項 */}
        {loaded && (
          <>
            <>
              <Edge
                left={210}
                top={16}
                icon={
                  // @ts-ignore
                  CharacterSkillTree[skillTreeOuter3Edge1.embedBuff.iconPath]
                }
                selected={selectData === skillTreeOuter3Edge1}
                onPress={() => {
                  setSelectType("edge");
                  setSelectData(skillTreeOuter3Edge1);
                }}
              />
              <Edge
                left={85}
                top={16}
                icon={
                  // @ts-ignore
                  CharacterSkillTree[skillTreeOuter3Edge2.embedBuff.iconPath]
                }
                selected={selectData === skillTreeOuter3Edge2}
                onPress={() => {
                  setSelectType("edge");
                  setSelectData(skillTreeOuter3Edge2);
                }}
              />
              <Edge
                left={270}
                top={230}
                icon={
                  // @ts-ignore
                  CharacterSkillTree[skillTreeOuter1Edge1.embedBuff.iconPath]
                }
                selected={selectData === skillTreeOuter1Edge1}
                onPress={() => {
                  setSelectType("edge");
                  setSelectData(skillTreeOuter1Edge1);
                }}
              />
              <Edge
                left={295}
                top={180}
                icon={
                  // @ts-ignore
                  CharacterSkillTree[skillTreeOuter1Edge2.embedBuff.iconPath]
                }
                selected={selectData === skillTreeOuter1Edge2}
                onPress={() => {
                  setSelectType("edge");
                  setSelectData(skillTreeOuter1Edge2);
                }}
              />
              <Edge
                left={275}
                top={132}
                icon={
                  // @ts-ignore
                  CharacterSkillTree[skillTreeOuter1Edge3.embedBuff.iconPath]
                }
                selected={selectData === skillTreeOuter1Edge2}
                onPress={() => {
                  setSelectType("edge");
                  setSelectData(skillTreeOuter1Edge2);
                }}
              />
              <Edge
                left={25}
                top={230}
                icon={
                  // @ts-ignore
                  CharacterSkillTree[skillTreeOuter2Edge1.embedBuff.iconPath]
                }
                selected={selectData === skillTreeOuter2Edge1}
                onPress={() => {
                  setSelectType("edge");
                  setSelectData(skillTreeOuter2Edge1);
                }}
              />
              <Edge
                left={0}
                top={180}
                icon={
                  // @ts-ignore
                  CharacterSkillTree[skillTreeOuter2Edge2.embedBuff.iconPath]
                }
                selected={selectData === skillTreeOuter2Edge2}
                onPress={() => {
                  setSelectType("edge");
                  setSelectData(skillTreeOuter2Edge2);
                }}
              />
              <Edge
                left={20}
                top={132}
                icon={
                  // @ts-ignore
                  CharacterSkillTree[skillTreeOuter2Edge3.embedBuff.iconPath]
                }
                selected={selectData === skillTreeOuter2Edge3}
                onPress={() => {
                  setSelectType("edge");
                  setSelectData(skillTreeOuter2Edge3);
                }}
              />
              <Edge
                left={75}
                top={365}
                icon={
                  // @ts-ignore
                  CharacterSkillTree[skillTreeOtherEdge1.embedBuff.iconPath]
                }
                selected={selectData === skillTreeOtherEdge1}
                onPress={() => {
                  setSelectType("edge");
                  setSelectData(skillTreeOtherEdge1);
                }}
              />
              <Edge
                left={210}
                top={365}
                icon={
                  // @ts-ignore
                  CharacterSkillTree[skillTreeOtherEdge2.embedBuff.iconPath]
                }
                skillTreeOtherEdge2
                onPress={() => {
                  setSelectType("edge");
                  setSelectData(skillTreeOtherEdge2);
                }}
              />
              <Outer
                left={215}
                top={275}
                icon={
                  CharacterSkillTree[
                    // @ts-ignore
                    skillTreeOuter1.embedBonusSkill?.iconPath
                  ]
                }
                selected={selectData === skillTreeOuter1}
                onPress={() => {
                  setSelectType("outer");
                  setSelectData(skillTreeOuter1);
                }}
              />
              <Outer
                left={50}
                top={275}
                icon={
                  CharacterSkillTree[
                    // @ts-ignore
                    skillTreeOuter2.embedBonusSkill?.iconPath
                  ]
                }
                selected={selectData === skillTreeOuter2}
                onPress={() => {
                  setSelectType("outer");
                  setSelectData(skillTreeOuter2);
                }}
              />
              <Outer
                left={130}
                top={-10}
                icon={
                  CharacterSkillTree[
                    // @ts-ignore
                    skillTreeOuter3.embedBonusSkill?.iconPath
                  ]
                }
                selected={selectData === skillTreeOuter3}
                onPress={() => {
                  setSelectType("outer");
                  setSelectData(skillTreeOuter3);
                }}
              />
            </>
            <>
              <Inner
                left={55}
                top={160}
                icon={CharacterSkillMain[charId].skill1}
                selected={selectData === skillTreeInner1}
                onPress={() => {
                  setSelectType("inner");
                  setSelectData(skillTreeInner1);
                }}
              />
              <Inner
                left={131}
                top={102}
                icon={CharacterSkillMain[charId].skill4}
                selected={selectData === skillTreeInner4}
                onPress={() => {
                  setSelectType("inner");
                  setSelectData(skillTreeInner4);
                }}
              />
              <Inner
                left={131}
                top={187}
                icon={CharacterSkillMain[charId].skill3}
                selected={selectData === skillTreeInner3}
                onPress={() => {
                  setSelectType("inner");
                  setSelectData(skillTreeInner3);
                }}
              />
              <Inner
                left={131}
                top={270}
                icon={CharacterSkillMain[charId].skill6}
                selected={selectData === skillTreeInner6}
                onPress={() => {
                  setSelectType("inner");
                  setSelectData(skillTreeInner6);
                }}
              />
              <Inner
                left={212}
                top={160}
                icon={CharacterSkillMain[charId].skill2}
                selected={selectData === skillTreeInner2}
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
