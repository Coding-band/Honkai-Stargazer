import { View, Text, Pressable } from "react-native";
import React, { useContext, useState } from "react";
import useDelayLoad from "../../../../../hooks/useDelayLoad";
import CharacterContext from "../../../../../context/CharacterData/CharacterContext";
import { getCharFullData } from "../../../../../utils/data/getDataFromMap";
import { useClickOutside } from "../../../../../../lib/react-native-click-outside/src/useClickOutside";
import { Image } from "expo-image";
import Edge from "../TraceItem/Edge";
import Outer from "../TraceItem/Outer";
import Inner from "../TraceItem/Inner";
import CharacterSkillTree from "../../../../../../assets/images/images_map/characterSkillTree";
import CharacterSkillMain from "../../../../../../assets/images/images_map/characterSkillMain";
import TracePopUp from "../TracePopUp/TracePopUp";
import useCharData from "../../../../../context/CharacterData/hooks/useCharData";
import Path from "../../../../../../assets/images/images_map/path";

const TraceLine = require("./images/path_trace_line/harmony_trace_line.svg");

export default function HarmonyTraceTree() {
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
  const skillTreeOuter2Edge1 = skillTreeOuter2.children[0];
  const skillTreeOuter2Edge2 = skillTreeOuter2Edge1.children[0];
  const skillTreeOuter3Edge1 = skillTreeOuter3.children[0];
  const skillTreeOuter3Edge2 = skillTreeOuter3Edge1.children[0];
  const skillTreeOuter3Edge3 = skillTreeOuter3Edge1.children[1];
  const skillTreeOtherEdge1 = skillTrees[3];
  const skillTreeOtherEdge2 = skillTreeOtherEdge1.children[0];
  const skillTreeOtherEdge3 = skillTreeOtherEdge1.children[1];
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
        <Image source={TraceLine} style={{ width: 263, height: 374 }} />
        <Image
          className="absolute left-4 opacity-40 w-[300px] h-[300px]"
          source={Path["Harmony"].icon2}
        />
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
                left={210}
                top={16}
                icon={
                  // @ts-ignore
                  CharacterSkillTree[skillTreeOuter3Edge3.embedBuff.iconPath]
                }
                selected={selectData === skillTreeOuter3Edge3}
                onPress={() => {
                  setSelectType("edge");
                  setSelectData(skillTreeOuter3Edge3);
                }}
              />

              <Edge
                left={18}
                top={107}
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
                left={50}
                top={75}
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
                left={274}
                top={238}
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
                left={240}
                top={270}
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
                left={145}
                top={370}
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
                left={82}
                top={360}
                icon={
                  // @ts-ignore
                  CharacterSkillTree[skillTreeOtherEdge2.embedBuff.iconPath]
                }
                selected={selectData === skillTreeOtherEdge2}
                onPress={() => {
                  setSelectType("edge");
                  setSelectData(skillTreeOtherEdge2);
                }}
              />
              <Edge
                left={210}
                top={360}
                icon={
                  // @ts-ignore
                  CharacterSkillTree[skillTreeOtherEdge3.embedBuff.iconPath]
                }
                selected={selectData === skillTreeOtherEdge3}
                onPress={() => {
                  setSelectType("edge");
                  setSelectData(skillTreeOtherEdge3);
                }}
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
                selected={selectData === skillTreeOuter1}
                onPress={() => {
                  setSelectType("outer");
                  setSelectData(skillTreeOuter1);
                }}
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
                selected={selectData === skillTreeOuter2}
                onPress={() => {
                  setSelectType("outer");
                  setSelectData(skillTreeOuter2);
                }}
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
                selected={selectData === skillTreeOuter3}
                onPress={() => {
                  setSelectType("outer");
                  setSelectData(skillTreeOuter3);
                }}
              />
            </>
            <>
              <Inner
                left={68}
                top={142}
                icon={CharacterSkillMain[charId].skill1}
                selected={selectData === skillTreeInner1}
                onPress={() => {
                  setSelectType("inner");
                  setSelectData(skillTreeInner1);
                }}
              />
              <Inner
                left={132}
                top={134}
                icon={CharacterSkillMain[charId].skill4}
                selected={selectData === skillTreeInner4}
                onPress={() => {
                  setSelectType("inner");
                  setSelectData(skillTreeInner4);
                }}
              />
              <Inner
                left={132}
                top={210}
                icon={CharacterSkillMain[charId].skill3}
                selected={selectData === skillTreeInner3}
                onPress={() => {
                  setSelectType("inner");
                  setSelectData(skillTreeInner3);
                }}
              />
              <Inner
                left={132}
                top={295}
                icon={CharacterSkillMain[charId].skill6}
                selected={selectData === skillTreeInner6}
                onPress={() => {
                  setSelectType("inner");
                  setSelectData(skillTreeInner6);
                }}
              />
              <Inner
                left={196}
                top={142}
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
