import React from "react";
import {
  GestureResponderEvent,
  Text,
  TouchableOpacity,
  View,
} from "react-native";
import { Shadow } from "react-native-shadow-2";
import { LinearGradient as LinearGradientExpo } from "expo-linear-gradient";
import { BlurView } from "expo-blur";
import { Platform } from "react-native";

export default function MenuItem({
  children,
  Icon,
  width,
  height,
  onPress,
}: {
  children: any;
  Icon: any;
  width: number;
  height: number;
  onPress?: (e: GestureResponderEvent) => void;
}) {
  return (
    <Shadow offset={[0, 4]} distance={6} startColor="#00000025">
      <TouchableOpacity onPress={onPress} activeOpacity={0.65}>
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
          {Platform.OS === "ios" ? (
            <BlurView intensity={20} tint="light">
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
                  <Text
                    style={{ fontFamily: "HY65" }}
                    className="text-[14px] text-white text-center"
                  >
                    {children}
                  </Text>
                </View>
              </View>
            </BlurView>
          ) : (
            <>
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
                  <Text
                    style={{ fontFamily: "HY65" }}
                    className="text-[14px] text-white text-center"
                  >
                    {children}
                  </Text>
                </View>
              </View>
            </>
          )}
        </View>
      </TouchableOpacity>
    </Shadow>
  );
}
