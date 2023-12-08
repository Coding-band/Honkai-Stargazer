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
  const [intensity, setIntensity] = useState(0);

  useEffect(() => {
    setTimeout(() => {
      setIntensity(20);
    }, 300);
  }, []);

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
          {Platform.OS === "ios" ? (
            <BlurView intensity={intensity} tint="light">
              <LinearGradientExpo
                style={{ width, height, opacity: 0.6 }}
                colors={["#222222", "#22222200"]}
              ></LinearGradientExpo>
              <View
                className="absolute top-[15px]"
                style={{
                  justifyContent: "center",
                  flexDirection: "row",
                  gap: width / 30,
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
                  <Text className=" text-text text-[16px] font-medium">
                    {title}
                  </Text>
                  <Text className="text-text2 text-[14px]">{subtitle}</Text>
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
                style={{
                  justifyContent: "center",
                  flexDirection: "row",
                  gap: width / 30,
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
                  <Text className=" text-text text-[16px] font-medium">
                    {title}
                  </Text>
                  <Text className="text-text2 text-[14px]">{subtitle}</Text>
                </View>
              </View>
            </>
          )}
        </View>
      </TouchableOpacity>
    </Shadow>
  );
}
