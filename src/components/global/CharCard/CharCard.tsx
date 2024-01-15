import { View, Text, TouchableOpacity } from "react-native";
import React from "react";
import { LinearGradient } from "expo-linear-gradient";
import { Image, ImageSource } from "expo-image";
import { cn } from "../../../utils/css/cn";
import { Path } from "../../../types/path";
import { CombatType } from "../../../types/combatType";
import CombatTypeCardIcon from "../CombatTypeCardIcon/CombatTypeCardIcon";
import PathCardIcon from "../PathCardIcon/PathCardIcon";

type Props = {
  id: string;
  rare: number;
  name: string;
  rank?: number;
  level?: number;
  path?: Path;
  combatType?: CombatType;
  image?:
    | string
    | number
    | string[]
    | ImageSource
    | ImageSource[]
    | null
    | undefined;
  onPress?: (charId: string, charName: string) => void;
};

export default function CharCard(props: Props) {
  //   const animation = useSpring({ from: { opacity: 0.25 }, to: { opacity: 1 } });

  return (
    <TouchableOpacity
      activeOpacity={0.65}
      onPress={() => {
        props.onPress && props.onPress(props.id, props.name);
      }}
    >
      {/* <Shadow distance={6} offset={[4, 4]} startColor="#00000025"> */}
      <LinearGradient
        style={{
          borderRadius: 4,
          borderTopRightRadius: 10,
          overflow: "hidden",
          shadowOffset: { width: 4, height: 4 },
          shadowRadius: 8,
          shadowColor: "#000000",
          shadowOpacity: 0.25,
          elevation: 8,
        }}
        colors={
          props.rare === 5 ? ["#905A52", "#C8A471"] : ["#404165", "#9763CE"]
        }
      >
        <View
          //   style={animation}
          className={cn("w-20")}
        >
          {/* 角色頭像 */}
          <Image
            cachePolicy="none"
            transition={200}
            style={{ width: 80, height: 80 }}
            source={props.image}
          />
          {/* 角色名稱 */}
          <View
            className="bg-[#222222] translate-y-[-2px]"
            style={{
              justifyContent: "center",
              alignItems: "center",
            }}
          >
            {typeof props.name === "string" ? (
              <Text
                numberOfLines={1}
                className="text-text2 font-[HY65] text-[12px] leading-4"
              >
                {props.name}
              </Text>
            ) : (
              <>{props.name}</>
            )}
          </View>
          {/* 命途 & 元素 */}
          <View
            className="absolute top-0.5 left-1"
            style={{ flexDirection: "column", gap: 8 }}
          >
            <CombatTypeCardIcon value={props.combatType} />
            <PathCardIcon value={props.path} />
          </View>
          {/* 命作 */}
          {props.rank !== undefined && (
            <View
              className="absolute right-1 top-1 bg-[#F3F9FF] rounded-full w-4 h-4"
              style={{ justifyContent: "center", alignItems: "center" }}
            >
              <Text className="text-[#393A5C] text-[12px] font-[HY65]">
                {props.rank}
              </Text>
            </View>
          )}
        </View>
        {/* 等級 */}
        {props.level !== undefined && (
          <View
            className="absolute top-[60px] w-full"
            style={{ alignItems: "center" }}
          >
            <View className="bg-[#22222290] h-4 px-2 rounded-[43px]">
              <Text className="text-text font-[HY65] text-[12px]">
                {props.level}
              </Text>
            </View>
          </View>
        )}
      </LinearGradient>
      {/* </Shadow> */}
    </TouchableOpacity>
  );
}
