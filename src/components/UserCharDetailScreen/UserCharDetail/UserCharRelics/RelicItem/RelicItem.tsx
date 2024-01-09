import { View, Text } from "react-native";
import React from "react";
import { LinearGradient } from "expo-linear-gradient";
import { CardColors } from "../../../../../constant/card";
import { Image } from "expo-image";
import Relic from "../../../../../../assets/images/images_map/relic";
import { RelicName } from "../../../../../types/relic";
import officalRelicId from "../../../../../../map/relic_offical_id_map";
import AttributeImage from "../../../../../../assets/images/images_map/attributeImage";

export default function RelicItem({ userRelicData }: { userRelicData: any }) {
  const relicSetId = officalRelicId[userRelicData.set_id] as RelicName;
  const relicCount =
    Number(
      userRelicData.icon.split(".")[0][
        userRelicData.icon.split(".")[0].length - 1
      ]
    ) + 1;

  console.log(userRelicData.rarity);

  return (
    <View className="w-[160px] py-2" style={{ flexDirection: "row", gap: 10 }}>
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
        <View className="absolute bottom-[-8px] bg-[#00000040] rounded-[15px] px-1.5 py-0.5">
          <Text className="text-text font-[HY65] text-[8px] text-center">
            {userRelicData.level}
          </Text>
        </View>
      </LinearGradient>
      <View>
        <View className="w-[105px]" style={{ flexDirection: "row" }}>
          <Image
            className="w-[20px] h-[20px]"
            // @ts-ignore
            source={AttributeImage[userRelicData.main_affix.field]}
          />
          <Text className="text-text text-[12px] font-[HY65]">
            +{userRelicData.main_affix.display}
          </Text>
        </View>
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
