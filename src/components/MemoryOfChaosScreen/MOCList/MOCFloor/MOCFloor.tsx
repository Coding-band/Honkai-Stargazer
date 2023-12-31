import { View, Text } from "react-native";
import React from "react";
import CharCard from "../../../global/CharCard/CharCard";
import { Image } from "expo-image";

type Props = {
  title: string;
  stars: number;
  round: number;
  roundAverage: number;
  roundRemaining: number;
  teams: {
    date: string;
    characters: {
      level: number;
      image: string;
      rare: number;
      combatType: string;
    }[];
  }[];
};

export default function MOCFloor(props: Props) {
  return (
    <View className="w-full  border border-[#DDDDDD20] rounded-[4px] p-2.5">
      {/* Layer 1 */}
      <View style={{ flexDirection: "row", justifyContent: "space-between" }}>
        <View>
          <Text className="text-[#FFF] text-[16px] font-[HY65]">
            {props.title}
          </Text>
          <Text className="text-[#FFFFFF90] text-[12px] font-[HY65] leading-5">
            剩餘{props.roundRemaining}輪
          </Text>
        </View>
        <View style={{ flexDirection: "row", alignItems: "center", gap: 8 }}>
          {Array.from({ length: props.stars }, (_, i) => (
            <Star key={i} />
          ))}
        </View>
      </View>
      {/* Com */}
      <View>
        <View className="mt-2">
          <View
            style={{
              flexDirection: "row",
              justifyContent: "space-between",
            }}
          >
            <View style={{ flexDirection: "row", gap: 4 }}>
              <Text className="text-[#FFF] text-[12px] font-[HY65] leading-5">
                {props.teams[0].date}
              </Text>
              <Text className="text-[#FFF] text-[12px] font-[HY65] leading-5">
                {props.round}輪
              </Text>
            </View>
            <Text className="text-[#FFF] text-[12px] font-[HY65] leading-5">
              平均{props.roundAverage}輪
            </Text>
          </View>
        </View>
        <View className="mt-2" style={{ flexDirection: "row", gap: 6 }}>
          {props.teams[0].characters?.map((char, i) => (
            <CharCard
              key={i}
              name={`Lv ${char.level}`}
              image={char.image}
              rare={char.rare}
              // @ts-ignore
              // path={char.path}
              // @ts-ignore
              combatType={char.combatType}
            />
          ))}
        </View>
        <View className="w-full h-[1px] bg-[#FFFFFF20] mt-3"></View>
      </View>
      {/* Com */}
      <View>
        <View className="mt-2">
          <View
            style={{
              flexDirection: "row",
              justifyContent: "space-between",
            }}
          >
            <View style={{ flexDirection: "row", gap: 4 }}>
              <Text className="text-[#FFF] text-[12px] font-[HY65] leading-5">
                2023.12.19 17:24
              </Text>
              <Text className="text-[#FFF] text-[12px] font-[HY65] leading-5">
                {props.round}輪
              </Text>
            </View>
            <Text className="text-[#FFF] text-[12px] font-[HY65] leading-5">
              平均{props.roundAverage}輪
            </Text>
          </View>
        </View>
        <View className="mt-2" style={{ flexDirection: "row", gap: 6 }}>
          {props.teams[1].characters?.map((char, i) => (
            <CharCard
              key={i}
              name={`Lv ${char.level}`}
              image={char.image}
              rare={char.rare}
              // @ts-ignore
              // path={char.path}
              // @ts-ignore
              combatType={char.combatType}
            />
          ))}
        </View>
        
      </View>
    </View>
  );
}

const StarIcon = require("./icons/Star.svg");
const Star = () => <Image source={StarIcon} className="w-6 h-6" />;
