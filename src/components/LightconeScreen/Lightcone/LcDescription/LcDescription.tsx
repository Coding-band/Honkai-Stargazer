import { View, Text } from "react-native";
import React, { useState } from "react";
import PageHeading from "../../../global/PageHeading/PageHeading";
import { Info } from "phosphor-react-native";
import Sliderbar from "../../../global/Sliderbar/Sliderbar";

export default function LcDescription() {
  const [skillLevel, setSkillLevel] = useState(0);

  return (
    <View style={{ alignItems: "center" }}>
      <PageHeading Icon={Info}>技能故事</PageHeading>
      <View className="w-full">
        <Text className="text-text font-[HY65] text-[20px] leading-[40px]">
          花与蝶
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
        <Text className="text-text2 font-[HY65] text-[14px]">
          使装备者的暴击率提高18%。当装备者在战斗中速度大于100时，每超过10点，普攻和战技造成的伤害提高6%，同时终结技的暴击伤害提高12%，该效果可叠加6层。
        </Text>
      </View>
    </View>
  );
}
