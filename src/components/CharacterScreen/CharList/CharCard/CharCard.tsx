import { View, Text, TouchableOpacity } from "react-native";
import React from "react";
import { Shadow } from "react-native-shadow-2";
import { LinearGradient } from "expo-linear-gradient";
import { Image, ImageSource } from "expo-image";
import { cn } from "../../../../utils/cn";
import { useSpring, animated } from "@react-spring/native";

type Props = {
  image?:
    | string
    | number
    | string[]
    | ImageSource
    | ImageSource[]
    | null
    | undefined;
  stars: 4 | 5;
};

export default function CharCard(props: Props) {
  //   const animation = useSpring({ from: { opacity: 0.25 }, to: { opacity: 1 } });

  return (
    <TouchableOpacity activeOpacity={0.65}>
      <Shadow distance={6} offset={[4, 4]} startColor="#00000025">
        <LinearGradient
          style={{
            borderRadius: 4,
            borderTopRightRadius: 10,
            overflow: "hidden",
          }}
          colors={
            props.stars === 5 ? ["#905A52", "#C8A471"] : ["#404165", "#9763CE"]
          }
        >
          <AnimatedView
            //   style={animation}
            className={cn("w-20 h-[102px]")}
          >
            <Image style={{ width: 80, height: 80 }} source={props.image} />
            <View
              className="bg-[#222222]"
              style={{
                width: 80,
                height: 20,
                justifyContent: "center",
                alignItems: "center",
              }}
            >
              <Text className="text-white font-[HY65] text-[12px]">镜流</Text>
            </View>
          </AnimatedView>
        </LinearGradient>
      </Shadow>
    </TouchableOpacity>
  );
}

const AnimatedView = animated(View);
