import { View, Text } from "react-native";
import React, { useContext, useState } from "react";
import PageHeading from "../../../global/PageHeading/PageHeading";
import { Info } from "phosphor-react-native";
import Sliderbar from "../../../global/Sliderbar/Sliderbar";
import LightconeContext from "../../../../context/LightconeContext";
import { getLcFullData } from "../../../../utils/dataMap/getDataFromMap";
import formatDesc from "../../../../utils/format/formatDesc";
import { HtmlText } from "@e-mine/react-native-html-text";

export default function LcDescription() {
  const lcData = useContext(LightconeContext);
  const lcId = lcData?.id!;
  const lcFullData = getLcFullData(lcId);

  const [skillLevel, setSkillLevel] = useState(0);

  return (
    <View style={{ alignItems: "center" }}>
      <PageHeading Icon={Info}>技能描述</PageHeading>
      <View className="w-full">
        <Text className="text-text font-[HY65] text-[20px] leading-[40px]">
          {lcFullData.skill.name}
        </Text>
        <View
          className="pb-[5px]"
          style={{ flexDirection: "row", justifyContent: "space-between" }}
        >
          <Text className="text-[#DD8200] font-[HY65] text-[14px]">单攻</Text>
          <Text className="text-text font-[HY65] text-[14px]">
            能量回复：20
          </Text>
        </View>
        <View
          className="pb-3"
          style={{ flexDirection: "row", justifyContent: "space-between" }}
        >
          <Text className="text-text font-[HY65] text-[16px]">
            Lv.{skillLevel + 1}/5
          </Text>
          <Sliderbar
            point={5}
            hasDot={false}
            value={skillLevel}
            onChange={setSkillLevel}
          />
        </View>
        <HtmlText
          style={{ color: "#DDD", fontFamily: "HY65", fontSize: 14 }}
          // className="text-text2 font-[HY65] text-[14px]"
        >
          {formatDesc(
            lcFullData.skill.descHash,
            lcFullData.skill.levelData[skillLevel].params
          )}
        </HtmlText>
      </View>
    </View>
  );
}
