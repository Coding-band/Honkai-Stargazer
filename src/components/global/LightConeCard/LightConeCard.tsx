import {
  View,
  Text,
  TouchableOpacity,
  GestureResponderEvent,
} from "react-native";
import React from "react";
import { LinearGradient } from "expo-linear-gradient";
import { Image, ImageBackground } from "expo-image";
import { ExpoImage } from "../../../types/image";
import { CardColors } from "../../../constant/card";
import PathCardIcon from "../PathCardIcon/PathCardIcon";
import { Path } from "../../../types/path";
import CardBg from "../CardBg/CardBg";

type Props = {
  image?: ExpoImage;
  rare: number;
  name: string;
  path: Path;
  onPress?: (e: GestureResponderEvent) => void;
};

export default function LightConeCard(props: Props) {
  return (
    <TouchableOpacity activeOpacity={0.65} onPress={props.onPress}>
      {/* <Shadow
        distance={20}
        offset={[2, 15]}
        startColor={props.rare === 5 ? "#C7A37150" : "#9663CC50"}
      > */}
      <CardBg rare={props.rare} />
      <View
        className="w-20 h-20"
        style={{
          borderRadius: 4,
          borderTopRightRadius: 10,
          overflow: "hidden",
        }}
      >
        <View
          className="w-full h-full"
          style={{
            alignItems: "center",
            justifyContent: "center",
          }}
        >
          {/* 光錐圖標 */}
          <Image
            transition={200}
            style={{ width: 72, height: 72 }}
            source={props.image}
            contentFit="contain"
          />
          {/* 命途 & 元素 */}
          <View className="absolute top-1 left-1">
            <PathCardIcon value={props.path} />
          </View>
        </View>
      </View>
      {/* </Shadow> */}
      <View
        className="w-20 h-24 pt-1 mb-[-50px]"
        style={{ alignItems: "center" }}
      >
        <Text
          numberOfLines={2}
          className="text-text2 text-[12px] font-[HY65] leading-4"
          style={{
            flexWrap: "wrap",
            textAlign: "center",
          }}
        >
          {props.name}
        </Text>
      </View>
    </TouchableOpacity>
  );
}
