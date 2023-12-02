import { View, Text, ScrollView } from "react-native";
import React from "react";
import CharPageHeading from "../../../global/layout/CharPageHeading";
import { BaseballCap } from "phosphor-react-native";
import RelicsCard from "../../../global/layout/RelicsCard/RelicsCard";
import { Image } from "expo-image";

// Relic 遺器套裝
// Ornaments 位面飾品
// 通稱 Relic 遺器

const AddIcon = require("../../../../../assets/icons/Add.svg");
const LeftIcon = require("../../../../../assets/icons/Left.svg");
const RightIcon = require("../../../../../assets/icons/Right.svg");
const LeftLowIcon = require("../../../../../assets/icons/LeftLow.svg");
const RightLowIcon = require("../../../../../assets/icons/RightLow.svg");

const testImage1 = require("../../../../../assets/images/test-relic-1.png");
const testImage2 = require("../../../../../assets/images/test-relic-2.png");

const testData = {
  left: [
    [
      { image: testImage1, rare: 5, name: "繁星璀璨的天才" },
      { image: testImage1, rare: 5, name: "繁星璀璨的天才" },
    ],
  ],
  right: [
    [{ image: testImage2, rare: 5, name: "繁星竞技场" }],
    [{ image: testImage2, rare: 5, name: "繁星竞技场" }],
    [{ image: testImage2, rare: 5, name: "繁星竞技场" }],
  ],
};

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
      <View
        className="w-full"
        style={{ flexDirection: "row", justifyContent: "center",gap: 10 }}
      >
        <View style={{ flexDirection: "row", gap: 8 }}>
          <Image
            className="translate-y-8"
            style={{ width: 7.5, height: 15 }}
            source={LeftIcon}
          />
          <View
            style={{
              flexDirection: "row",
              flexWrap: "wrap",
              columnGap: 8,
            }}
          >
            {testData.left[0].map((l, i) => (
              <RelicsCard key={i} {...l} />
            ))}
          </View>
          <Image
            className="translate-y-8"
            style={{ width: 7.5, height: 15 }}
            source={RightIcon}
          />
        </View>
        <Image
          className="translate-y-8"
          style={{ width: 13, height: 13 }}
          source={AddIcon}
        />
        <View style={{ flexDirection: "row", gap: 8 }}>
          <Image
            className="translate-y-8"
            style={{ width: 7.5, height: 15 }}
            source={LeftIcon}
          />
          <View
            style={{
              flexDirection: "row",
              flexWrap: "wrap",
              columnGap: 8,
            }}
          >
            {testData.right[0].map((l, i) => (
              <RelicsCard key={i} {...l} />
            ))}
          </View>
          <Image
            className="translate-y-8"
            style={{ width: 7.5, height: 15 }}
            source={RightIcon}
          />
        </View>
      </View>
      <View className="w-full mt-4">
        <View className="w-full">
          <Text className="font-[HY75] text-white text-[16px]">主詞條</Text>
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
                className="w-[48%]"
                style={{
                  flexDirection: "row",
                  justifyContent: "space-between",
                  alignItems: "center",
                }}
              >
                <Text className="text-[14px] text-white font-[HY75]">
                  {t.title}
                </Text>
                <Text className="text-[14px] text-[#DDD] opacity-80 font-[HY65]">
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
          <Text className="font-[HY75] text-white text-[16px]">副詞條</Text>
          <Text className="text-[14px] text-[#DDD] opacity-80 font-[HY65]">
            {testSecond}
          </Text>
        </View>
      </View>
    </View>
  );
}
