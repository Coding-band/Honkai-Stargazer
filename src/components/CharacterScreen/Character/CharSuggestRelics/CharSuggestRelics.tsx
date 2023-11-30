import { View, Text } from "react-native";
import React from "react";
import CharPageHeading from "../../../global/layout/CharPageHeading";
import { BaseballCap } from "phosphor-react-native";

const testMain = [
  { title: "躯干", description: "暴击，暴伤" },
  { title: "躯干", description: "暴击，暴伤" },
  { title: "躯干", description: "暴击，暴伤" },
  { title: "躯干", description: "暴击，暴伤" },
];
const testSecond = "暴击率，速度，暴击伤害，攻击力";

export default function CharSuggestRelics() {
  return (
    <View style={{ alignItems: "center" }}>
      <CharPageHeading Icon={BaseballCap}>推荐遗器</CharPageHeading>
      <View className="w-full">
        <View className="w-full">
          <Text className="font-[HY75] text-white text-[14px]">主詞條</Text>
          <View
            className="w-full pt-3 pb-4"
            style={{
              flexDirection: "row",
              flexWrap: "wrap",
              rowGap: 9,
              columnGap: 12,
            }}
          >
            {testMain.map((t, i) => (
              <View
                key={i}
                className="w-[47%]"
                style={{
                  flexDirection: "row",
                  justifyContent: "space-between",
                  alignItems: "center",
                }}
              >
                <Text className="text-[12px] text-white font-[HY75]">
                  {t.title}
                </Text>
                <Text className="text-[12px] text-[#DDD] opacity-80 font-[HY65]">
                  {t.description}
                </Text>
              </View>
            ))}
          </View>
        </View>
        <View
          className="w-full"
          style={{
            flexDirection: "row",
            justifyContent: "space-between",
            alignItems: "center",
          }}
        >
          <Text className="font-[HY75] text-white text-[14px]">副詞條</Text>
          <Text className="text-[12px] text-[#DDD] opacity-80 font-[HY65]">
            {testSecond}
          </Text>
        </View>
      </View>
    </View>
  );
}
