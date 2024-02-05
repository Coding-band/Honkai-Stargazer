import { View, Text, Pressable } from 'react-native';
import React, { useContext, useState } from 'react';
import CharacterContext from '../../../../../context/CharacterData/CharacterContext';
import { getCharFullData } from '../../../../../utils/data/getDataFromMap';
import { Image } from 'expo-image';
import { useClickOutside } from '../../../../../../lib/react-native-click-outside/src/useClickOutside';
import Edge from '../TraceItem/Edge';
import useDelayLoad from '../../../../../hooks/useDelayLoad';
import Outer from '../TraceItem/Outer';
import Inner from '../TraceItem/Inner';
import TracePopUp from '../TracePopUp/TracePopUp';
import CharacterSkillTree from '../../../../../../assets/images/images_map/characterSkillTree';
import CharacterSkillMain from '../../../../../../assets/images/images_map/characterSkillMain';
import useCharData from '../../../../../context/CharacterData/hooks/useCharData';
import Path from '../../../../../../assets/images/images_map/path';

const TraceLine = require('./images/path_trace_line/preservation_trace_line.svg');

export default React.memo(function PreservationTraceTree() {
  const { charFullData, charId } = useCharData();

  const skillTrees: any = charFullData.skillTreePoints
    .slice()
    .sort((a, b) => a.id - b.id);
  const skillGrouping = charFullData?.skillGrouping;
  const skills = skillGrouping?.map((group) => {
    const skillId = group[0];
    return charFullData?.skills.filter((skill) => skill.id === skillId)[0];
  });

  const skillTreeOtherEdge1 = skillTrees[1];
  const skillTreeOuter1 = skillTreeOtherEdge1.children[0];
  const skillTreeOuter2 = skillTreeOtherEdge1.children[1];
  const skillTreeOuter3 = skillTrees[0];
  const skillTreeOuter1Edge1 = skillTreeOuter1.children[0];
  const skillTreeOuter1Edge2 = skillTreeOuter1Edge1.children[0];
  const skillTreeOuter2Edge1 = skillTreeOuter2.children[0];
  const skillTreeOuter2Edge2 = skillTreeOuter2Edge1.children[0];
  const skillTreeOuter3Edge1 = skillTreeOuter3.children[0];
  const skillTreeOuter3Edge2 = skillTreeOuter3Edge1.children[0];
  const skillTreeOuter3Edge3 = skillTreeOuter3Edge1.children[1];
  const skillTreeOtherEdge2 = skillTrees[2];
  const skillTreeOtherEdge3 = skillTrees[3];
  const skillTreeInner1 = skills[0];
  const skillTreeInner2 = skills[1];
  const skillTreeInner3 = skills[2];
  const skillTreeInner4 = skills[3];
  const skillTreeInner6 = skills[4];

  const [selectType, setSelectType] = useState<'outer' | 'inner' | 'edge'>();
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
          alignItems: 'center',
          justifyContent: 'center',
        }}
      >
        {/* 軀幹 (線條) */}
        <Image source={TraceLine} style={{ width: 295, height: 367 }} />
        <Image
          className="absolute left-4 opacity-40 w-[300px] h-[300px]"
          source={Path['Preservation'].icon2}
        />
        {/* 選項 */}
        <>
          <>
            <Edge
              left={150}
              top={0}
              icon={
                // @ts-ignore
                CharacterSkillTree[skillTreeOuter3Edge1.embedBuff.iconPath]
              }
              selected={selectData === skillTreeOuter3Edge1}
              onPress={() => {
                setSelectType('edge');
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
                setSelectType('edge');
                setSelectData(skillTreeOuter3Edge2);
              }}
            />
            <Edge
              left={215}
              top={16}
              icon={
                // @ts-ignore
                CharacterSkillTree[skillTreeOuter3Edge3.embedBuff.iconPath]
              }
              selected={selectData === skillTreeOuter3Edge3}
              onPress={() => {
                setSelectType('edge');
                setSelectData(skillTreeOuter3Edge3);
              }}
            />
            <Edge
              left={30}
              top={300}
              icon={
                // @ts-ignore
                CharacterSkillTree[skillTreeOuter1Edge1.embedBuff.iconPath]
              }
              selected={selectData === skillTreeOuter1Edge1}
              onPress={() => {
                setSelectType('edge');
                setSelectData(skillTreeOuter1Edge1);
              }}
            />
            <Edge
              left={0}
              top={250}
              icon={
                // @ts-ignore
                CharacterSkillTree[skillTreeOuter1Edge2.embedBuff.iconPath]
              }
              selected={selectData === skillTreeOuter1Edge2}
              onPress={() => {
                setSelectType('edge');
                setSelectData(skillTreeOuter1Edge2);
              }}
            />
            <Edge
              left={265}
              top={300}
              icon={
                // @ts-ignore
                CharacterSkillTree[skillTreeOuter2Edge1.embedBuff.iconPath]
              }
              selected={selectData === skillTreeOuter2Edge1}
              onPress={() => {
                setSelectType('edge');
                setSelectData(skillTreeOuter2Edge1);
              }}
            />
            <Edge
              left={290}
              top={250}
              icon={
                // @ts-ignore
                CharacterSkillTree[skillTreeOuter2Edge2.embedBuff.iconPath]
              }
              selected={selectData === skillTreeOuter2Edge2}
              onPress={() => {
                setSelectType('edge');
                setSelectData(skillTreeOuter2Edge2);
              }}
            />
            <Edge
              left={20}
              top={150}
              icon={
                // @ts-ignore
                CharacterSkillTree[skillTreeOtherEdge2.embedBuff.iconPath]
              }
              selected={selectData === skillTreeOtherEdge2}
              onPress={() => {
                setSelectType('edge');
                setSelectData(skillTreeOtherEdge2);
              }}
            />
            <Edge
              left={275}
              top={148}
              icon={
                // @ts-ignore
                CharacterSkillTree[skillTreeOtherEdge3.embedBuff.iconPath]
              }
              selected={selectData === skillTreeOtherEdge3}
              onPress={() => {
                setSelectType('edge');
                setSelectData(skillTreeOtherEdge3);
              }}
            />
            <Edge
              left={149}
              top={350}
              icon={
                // @ts-ignore
                CharacterSkillTree[skillTreeOtherEdge1.embedBuff.iconPath]
              }
              selected={selectData === skillTreeOtherEdge1}
              onPress={() => {
                setSelectType('edge');
                setSelectData(skillTreeOtherEdge1);
              }}
            />
            <Outer
              left={55}
              top={345}
              icon={
                // @ts-ignore
                CharacterSkillTree[skillTreeOuter1.embedBonusSkill?.iconPath]
              }
              selected={selectData === skillTreeOuter1}
              onPress={() => {
                setSelectType('outer');
                setSelectData(skillTreeOuter1);
              }}
            />
            <Outer
              left={215}
              top={345}
              icon={
                // @ts-ignore
                CharacterSkillTree[skillTreeOuter2.embedBonusSkill?.iconPath]
              }
              selected={selectData === skillTreeOuter2}
              onPress={() => {
                setSelectType('outer');
                setSelectData(skillTreeOuter2);
              }}
            />
            <Outer
              left={134}
              top={48}
              icon={
                CharacterSkillTree[
                  // @ts-ignore
                  skillTreeOuter3.embedBonusSkill?.iconPath
                ]
              }
              selected={selectData === skillTreeOuter3}
              onPress={() => {
                setSelectType('outer');
                setSelectData(skillTreeOuter3);
              }}
            />
          </>
          <>
            <Inner
              left={50}
              top={200}
              icon={CharacterSkillMain[charId].skill1}
              selected={selectData === skillTreeInner1}
              onPress={() => {
                setSelectType('inner');
                setSelectData(skillTreeInner1);
              }}
            />
            <Inner
              left={136}
              top={125}
              icon={CharacterSkillMain[charId].skill4}
              selected={selectData === skillTreeInner4}
              onPress={() => {
                setSelectType('inner');
                setSelectData(skillTreeInner4);
              }}
            />
            <Inner
              left={136}
              top={195}
              icon={CharacterSkillMain[charId].skill3}
              selected={selectData === skillTreeInner3}
              onPress={() => {
                setSelectType('inner');
                setSelectData(skillTreeInner3);
              }}
            />
            <Inner
              left={136}
              top={265}
              icon={CharacterSkillMain[charId].skill6}
              selected={selectData === skillTreeInner6}
              onPress={() => {
                setSelectType('inner');
                setSelectData(skillTreeInner6);
              }}
            />
            <Inner
              left={220}
              top={200}
              icon={CharacterSkillMain[charId].skill2}
              selected={selectData === skillTreeInner2}
              onPress={() => {
                setSelectType('inner');
                setSelectData(skillTreeInner2);
              }}
            />
          </>
        </>
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
});
