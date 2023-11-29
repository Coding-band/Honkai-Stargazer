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

const CardColors = {
  2: ["#374860", "#3F797C"],
  3: ["#393A5C", "#497AB8"],
  4: ["#404165", "#9763CE"],
};

type Props = {
  image?:
    | string
    | number
    | string[]
    | ImageSource
    | ImageSource[]
    | null
    | undefined;
  stars: 2 | 3 | 4;
  count: number;
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
          colors={CardColors[props.stars]}
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
              style={{ width: 70, height: 70 }}
              source={props.image}
            />
          </View>
        </LinearGradient>
      </Shadow>
    </TouchableOpacity>
  );
}
