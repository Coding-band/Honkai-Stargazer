import React, { useEffect, useState } from "react";
import {
  Platform,
  Text,
  TouchableNativeFeedback,
  TouchableOpacity,
  View,
} from "react-native";
import { LinearGradient as LinearGradientExpo } from "expo-linear-gradient";
import { BlurView } from "expo-blur";
import { Shadow } from "react-native-shadow-2";
import withBlurView from "../../../../hoc/withBlurView";

export default function MenuItemLarge({
  children,
  Icon,
  width,
  height,
  title,
  subtitle,
}: {
  children: any;
  Icon: any;
  width: number;
  height: number;
  title?: any;
  subtitle?: any;
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
          {withBlurView(
            <>
              <LinearGradientExpo
                style={{ width, height, opacity: 0.6 }}
                colors={["#222222", "#22222200"]}
              ></LinearGradientExpo>
              <View
                className="absolute top-[15px]"
                style={{
                  justifyContent: "center",
                  flexDirection: "row",
                  width,
                  height,
                }}
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
                <View
                  style={{
                    gap: 6,
                    alignItems: "center",
                  }}
                >
                  <Text className=" text-text text-[16px] font-medium font-[HY65]">
                    {title}
                  </Text>
                  <Text className="text-text2 text-[14px] font-[HY65]">
                    {subtitle}
                  </Text>
                </View>
              </View>
            </>,
            Platform.OS === "ios"
          )}
        </View>
      </TouchableOpacity>
    </Shadow>
  );
}
