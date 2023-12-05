import { Text, View } from "react-native";
import React, { useContext, useEffect, useState } from "react";
import PopUpCard from "../../../../global/PopUpCard/PopUpCard";
import CharacterContext from "../../../../../context/CharacterContext";
import * as characterListMap from "../../../../../../data/character_data/@character_list_map/character_list_map";
import { CharacterName } from "../../../../../types/character";
import { HtmlText } from "@e-mine/react-native-html-text";
import FixedContext from "../../../../global/Fixed/FixedContext";
import formatDesc from "../../../../../utils/formatDesc";
import MaterialList from "../../../../global/MaterialList/MaterialList";
import Sliderbar from "../../../../global/Sliderbar/Sliderbar";

type Props = {
  id: number;
  onClose: () => void;
};

export default function TracePopUp({ id, onClose }: Props) {
  const charData = useContext(CharacterContext);
  const charId = charData?.id as CharacterName;
  const charSkillGrouping = characterListMap.ZH_CN[charId].skillGrouping;
  const charSkill = characterListMap.ZH_CN[charId].skills.filter(
    (skill) => skill.id === (id > 0 && charSkillGrouping[id - 1][0])
  )[0];

  const [skillLevel, setSkillLevel] = useState(
    charSkill?.levelData?.length || 0 - 1
  );

  const { setFixed } = useContext(FixedContext)!;

  useEffect(() => {
    if (id < 1 || id > 6) {
      setFixed(null);
    } else {
      setFixed(
        <View className="w-[350px] mb-8">
          <PopUpCard
            onClose={onClose}
            title={charSkill.name}
            content={
              <View className="px-4 py-[12px]" style={{ gap: 6 }}>
                <View
                  className="w-[70px] h-[30px] bg-[#666] rounded-[40px]"
                  style={{ justifyContent: "center", alignItems: "center" }}
                >
                  <Text className="font-[HY65] text-[14px] text-white">
                    {charSkill.typeDescHash}
                  </Text>
                </View>
                <View
                  style={{
                    flexDirection: "row",
                    justifyContent: "space-between",
                  }} 
                >
                  <Text className="text-[#DD8200] text-[14px] font-[HY65]">
                    {charSkill.tagHash}
                  </Text>
                  <Text className="text-[#666] text-[14px] font-[HY65]">
                    能量回复：{charSkill.energy}
                  </Text>
                </View>
                <View
                  style={{
                    flexDirection: "row",
                    justifyContent: "space-between",
                  }}
                >
                  <Text className="text-[16px] text-[#222222]">
                    Lv.{skillLevel + 1}/{charSkill?.levelData?.length}
                  </Text>
                  <Sliderbar
                    point={charSkill?.levelData?.length}
                    width={250}
                    bgColor="#00000010"
                    hasDot={false}
                    value={skillLevel}
                    onChange={setSkillLevel}
                  />
                </View>
                <HtmlText
                  style={{ fontSize: 14, color: "#666", fontFamily: "HY65" }}
                >
                  {formatDesc(
                    charSkill.descHash,
                    charSkill.levelData[skillLevel]?.params
                  )}
                </HtmlText>
                <View className="mt-[-12px]">
                  <MaterialList />
                </View>
              </View>
            }
          />
        </View>
      );
    }
  }, [charSkill, skillLevel, id]);

  return <></>;
}
