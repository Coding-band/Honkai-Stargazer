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

export default function MaterialCard(props: Props) {
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
              width: 58,
              height: 80,
              alignItems: "center",
            }}
          >
            <Image
              transition={200}
              style={{ width: 46, height: 46, marginVertical: 5 }}
              source={props.image}
            />
            <View
              className="bg-[#222222]"
              style={{
                width: 58,
                height: 20,
                justifyContent: "center",
                alignItems: "center",
              }}
            >
              <Text className="text-white text-[12px]">{props.count}</Text>
            </View>
          </View>
        </LinearGradient>
      </Shadow>
    </TouchableOpacity>
  );
}
