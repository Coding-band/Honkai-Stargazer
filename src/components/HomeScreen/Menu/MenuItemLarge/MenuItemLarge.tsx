import React from "react";
import {
  Text,
  TouchableNativeFeedback,
  TouchableOpacity,
  View,
} from "react-native";
import { LinearGradient as LinearGradientExpo } from "expo-linear-gradient";
import { BlurView } from "expo-blur";
import { Shadow } from "react-native-shadow-2";

export default function MenuItemLarge({
  children,
  Icon,
  width,
  height,
}: {
  children: any;
  Icon: any;
  width: number;
  height: number;
}) {
  return (
    <Shadow offset={[0, 4]} distance={6} startColor="#00000025">
      <TouchableOpacity activeOpacity={0.65}>
        <View
          style={{
            width,
            height,
            backgroundColor: "#FFFFFF10",
            borderRadius: 4,
            overflow: "hidden",
            borderWidth: 1.5,
            borderColor: "#907C5480",
          }}
        >
          <BlurView intensity={40}>
            <LinearGradientExpo
              style={{ width, height, opacity: 0.6 }}
              colors={["#222222", "#22222200"]}
            ></LinearGradientExpo>
            <View
              className="absolute top-[15px]"
              style={{ alignItems: "center", width, height }}
            >
              <View style={{ gap: 7, alignItems: "center" }}>
                {Icon && <Icon weight="fill" size={32} color="white" />}
                <Text className="text-[14px] text-white">{children}</Text>
              </View>
            </View>
          </BlurView>
        </View>
      </TouchableOpacity>
    </Shadow>
  );
}
