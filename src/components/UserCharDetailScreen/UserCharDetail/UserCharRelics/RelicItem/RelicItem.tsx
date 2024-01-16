import { View, Text, TouchableOpacity, Dimensions } from "react-native";
import React from "react";
import { LinearGradient } from "expo-linear-gradient";
import { CardColors } from "../../../../../constant/card";
import { Image } from "expo-image";
import Relic from "../../../../../../assets/images/images_map/relic";
import { RelicName } from "../../../../../types/relic";
import officalRelicId from "../../../../../../map/relic_offical_id_map";
import AttributeImage from "../../../../../../assets/images/images_map/attributeImage";
import BlurView from "../../../../global/BlurView/BlurView";
import RelicScore from "./RelicScore/RelicScore";
import getRelicScore from "../../../../../utils/calculator/relicScoreCalculator/getRelicScore";

export default function RelicItem({
  userRelicData,
  score,
}: {
  userRelicData: any;
  score: number;
}) {
  const relicSetId = officalRelicId[userRelicData.set_id] as RelicName;
  const relicCount =
    Number(
      userRelicData.icon.split(".")[0][
        userRelicData.icon.split(".")[0].length - 1
      ]
    ) + 1;

  return (
    <View
      className="py-2"
      style={{
        flexDirection: "row",
        gap: 6,
        width: Dimensions.get("screen").width / 2 - 36,
      }}
    >
      {/* 圖片 */}
      <TouchableOpacity activeOpacity={0.65}>
        <LinearGradient
          className="w-[47px] h-[47px]"
          style={{
            borderRadius: 4,
            borderTopRightRadius: 10,
            justifyContent: "center",
            alignItems: "center",
          }}
          // @ts-ignore
          colors={CardColors[userRelicData.rarity]}
        >
          <Image
            transition={200}
            className="w-[30px] h-[30px]"
            // @ts-ignore
            source={Relic[relicSetId]?.["icon" + relicCount]}
          />
        </LinearGradient>
      </TouchableOpacity>
      {/* 等級 */}
      <View
        className="absolute bottom-0 w-[47px] z-50"
        style={{ alignItems: "center" }}
      >
        <View className="bg-[#00000040] rounded-[15px] px-1.5 py-0.5">
          <Text className="text-text font-[HY65] text-[8px] text-center">
            {userRelicData.level}
          </Text>
        </View>
      </View>
      {/* 詞條 */}
      <View>
        <View
          className="w-[120px]"
          style={{
            flexDirection: "row",
            alignItems: "center",
            justifyContent: "space-between",
          }}
        >
          {/* 主詞條 */}
          <View style={{ flexDirection: "row" }}>
            <Image
              className="w-[20px] h-[20px]"
              // @ts-ignore
              source={AttributeImage[userRelicData.main_affix.field]}
            />
            <Text className="text-text text-[12px] font-[HY65]">
              +{userRelicData.main_affix.display}
            </Text>
          </View>
          {/* 評分 */}
          <RelicScore score={score} />
        </View>
        {/* 副詞條 */}
        <View style={{ flexDirection: "row", flexWrap: "wrap" }}>
          {userRelicData.sub_affix.map((sub: any) => (
            <View
              className="w-[61px]"
              style={{ flexDirection: "row", alignItems: "center" }}
            >
              <Image
                className="w-[20px] h-[20px]"
                // @ts-ignore
                source={AttributeImage[sub.field]}
              />
              <Text className="text-text text-[10px] font-[HY65]">
                +{sub.display}
              </Text>
            </View>
          ))}
        </View>
      </View>
    </View>
  );
}
