import {
  View,
  Text,
  TouchableOpacity,
  GestureResponderEvent,
} from "react-native";
import React from "react";
import { Shadow } from "react-native-shadow-2";
import { LinearGradient } from "expo-linear-gradient";
import { Image, ImageSource } from "expo-image";
import { cn } from "../../../../utils/cn";

type Props = {
  image?:
    | string
    | number
    | string[]
    | ImageSource
    | ImageSource[]
    | null
    | undefined;
  star: 4 | 5;
  name: string;
  onPress?: (e: GestureResponderEvent) => void;
};

export default function LightConeCard(props: Props) {
  //   const animation = useSpring({ from: { opacity: 0.25 }, to: { opacity: 1 } });

  return (
    <TouchableOpacity activeOpacity={0.65} onPress={props.onPress}>
      <Shadow distance={6} offset={[4, 4]} startColor="#00000025">
        <LinearGradient
          style={{
            borderRadius: 4,
            borderTopRightRadius: 10,
            overflow: "hidden",
          }}
          colors={
            props.star === 5 ? ["#905A52", "#C8A471"] : ["#404165", "#9763CE"]
          }
        >
          <View
            //   style={animation}
            style={{
              width: 80,
              height: 80,
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
      </Shadow>
      <View style={{ alignItems: "center" }}>
        <Text className="text-white text-[12px] font-[HY65]">{props.name}</Text>
      </View>
    </TouchableOpacity>
  );
}
