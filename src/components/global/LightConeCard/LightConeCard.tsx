import {
  View,
  Text,
  TouchableOpacity,
  GestureResponderEvent,
} from "react-native";
import React from "react";
import { LinearGradient } from "expo-linear-gradient";
import { Image } from "expo-image";
import { ExpoImage } from "../../../types/image";
import { Shadow } from "react-native-shadow-2";

type Props = {
  image?: ExpoImage;
  rare: number;
  name: string;
  onPress?: (e: GestureResponderEvent) => void;
};

export default function LightConeCard(props: Props) {
  //   const animation = useSpring({ from: { opacity: 0.25 }, to: { opacity: 1 } });

  return (
    <TouchableOpacity activeOpacity={0.65} onPress={props.onPress}>
      {/* <Shadow
        distance={20}
        offset={[2, 15]}
        startColor={props.rare === 5 ? "#C7A37150" : "#9663CC50"}
      > */}
      <LinearGradient
        className="w-20 h-20"
        style={{
          borderRadius: 4,
          borderTopRightRadius: 10,
          overflow: "hidden",
        }}
        colors={
          props.rare === 5 ? ["#905A52", "#C8A471"] : ["#404165", "#9763CE"]
        }
      >
        <View
          className="w-full h-full"
          style={{
            alignItems: "center",
            justifyContent: "center",
          }}
        >
          <Image
            transition={200}
            style={{ width: 72, height: 72 }}
            source={props.image}
            contentFit="contain"
          />
        </View>
      </LinearGradient>
      {/* </Shadow> */}
      <View
        className="w-20 h-20 pt-1 mb-[-50px]"
        style={{ alignItems: "center" }}
      >
        <Text
          className="text-text2 text-[12px] font-[HY65]"
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
