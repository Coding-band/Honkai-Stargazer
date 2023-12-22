import {
  View,
  Text,
  TouchableOpacity,
  GestureResponderEvent,
} from "react-native";
import React from "react";
import { Shadow } from "react-native-shadow-2";
import { LinearGradient } from "expo-linear-gradient";
import { Image } from "expo-image";
import { ExpoImage } from "../../../types/image";

type Props = {
  image?: ExpoImage;
  rare: number;
  name: string;
  onPress?: (e: GestureResponderEvent) => void;
};

export default function RelicsCard(props: Props) {
  //   const animation = useSpring({ from: { opacity: 0.25 }, to: { opacity: 1 } });

  return (
    <TouchableOpacity activeOpacity={0.65} onPress={props.onPress}>
      {/* <Shadow distance={6} offset={[4, 4]} startColor="#00000025"> */}
      <LinearGradient
        className="w-20 h-20"
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
          className="w-full h-full"
          style={{
            alignItems: "center",
            justifyContent: "center",
          }}
        >
          <Image
            transition={200}
            style={{ width: 75, height: 75 }}
            source={props.image}
          />
        </View>
      </LinearGradient>
      {/* </Shadow> */}
      <View
        className="w-20 h-24 pt-1 mb-[-40px]"
        style={{ alignItems: "center" }}
      >
        <Text
          className="text-text2 text-[12px] font-[HY65]"
          style={{
            flexWrap: "wrap", // 允许文本换行
            textAlign: "center", // 文本居中
          }}
        >
          {props.name}
        </Text>
      </View>
    </TouchableOpacity>
  );
}
