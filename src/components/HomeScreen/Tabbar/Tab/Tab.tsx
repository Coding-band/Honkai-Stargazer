import { BlurView } from "expo-blur";
import React from "react";
import {
  View,
  Text,
  TouchableNativeFeedback,
  TouchableOpacity,
} from "react-native";
import { Shadow } from "react-native-shadow-2";
import { LinearGradient as LinearGradientExpo } from "expo-linear-gradient";

export default function Tab({ children }: { children: any }) {
  return (
    <Shadow offset={[0, 4]} distance={6} startColor="#00000025">
      <TouchableOpacity activeOpacity={0.65}>
        <View
          style={{
            width: 80,
            height: 80,
            backgroundColor: "#FFFFFF10",
            borderRadius: 4,
            overflow: "hidden",
            borderWidth: 1.5,
            borderColor: "#907C5480",
          }}
        >
          <BlurView intensity={20}>
            <LinearGradientExpo
              style={{ width: 80, height: 80, opacity: 0.6 }}
              colors={["#222222", "#22222200"]}
            ></LinearGradientExpo>
            <View
              className="absolute top-[24px]"
              style={{ alignItems: "center", width: 80, height: 80 }}
            >
              <View style={{ gap: 7, alignItems: "center" }}>{children}</View>
            </View>
          </BlurView>
        </View>
      </TouchableOpacity>
    </Shadow>
  );
}
