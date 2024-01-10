import React from "react";
import { Platform, Text, TouchableOpacity, View } from "react-native";
import { LinearGradient as LinearGradientExpo } from "expo-linear-gradient";
import { Shadow } from "react-native-shadow-2";
import withBlurView from "../../../../hoc/withBlurView";
import { GestureResponderEvent } from "react-native";
import Dot from "../Dot/Dot";

export default function MenuItemLarge({
  children,
  Icon,
  width,
  height,
  title,
  subtitle,
  onPress,
  hasDot,
}: {
  children: any;
  Icon: any;
  width: number;
  height: number;
  title?: any;
  subtitle?: any;
  onPress?: (e: GestureResponderEvent) => void;
  hasDot?: boolean;
}) {
  return (
    <View>
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
            {withBlurView(
              <View>
                <LinearGradientExpo
                  style={{ width, height, opacity: 0.6 }}
                  colors={["#222222", "#22222200"]}
                ></LinearGradientExpo>
                <View
                  className="absolute"
                  style={{
                    alignItems: "center",
                    justifyContent: title ? "flex-start" : "center",
                    flexDirection: "row",
                    width,
                    height,
                  }}
                >
                  {/* left */}
                  <View
                    style={{
                      width: (width - 13) / 2,
                      alignItems: "center",
                      justifyContent: "space-between",
                      gap: 6,
                    }}
                  >
                    {Icon && <Icon weight="fill" size={32} color="white" />}
                    <Text
                      style={{ fontFamily: "HY65" }}
                      className="text-[13px] text-text2 text-center"
                    >
                      {children}
                    </Text>
                  </View>
                  {/* right */}
                  {title && (
                    <View
                      className="translate-x-[-8px] "
                      style={{
                        width: width - (width - 16) / 2,
                        alignItems: "center",
                        justifyContent: "space-between",
                        gap: 6,
                      }}
                    >
                      <Text className=" text-text text-[15px] font-medium font-[HY65]">
                        {title}
                      </Text>
                      <Text className="text-text2 text-[13px] font-[HY65]">
                        {subtitle}
                      </Text>
                    </View>
                  )}
                </View>
              </View>,
              Platform.OS !== "android"
            )}
          </View>
        </TouchableOpacity>
      </Shadow>
      {hasDot && <Dot />}
    </View>
  );
}
