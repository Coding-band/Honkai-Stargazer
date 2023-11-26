import React from "react";
import { Text, TouchableNativeFeedback, View } from "react-native";
import Svg, { Defs, LinearGradient, Stop, Rect } from "react-native-svg";

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
    <View
      style={{
        shadowColor: "#000000", // 阴影颜色
        shadowOffset: { width: 0, height: 4 }, // 阴影偏移
        shadowOpacity: 0.25, // 阴影透明度
        shadowRadius: 16, // 模糊半径
        elevation: 16, // 仅用于Android，与阴影相关的视图提升
      }}
    >
      <TouchableNativeFeedback>
        <View
          style={{
            shadowColor: "#000000", // 阴影颜色
            shadowOffset: { width: 0, height: 4 }, // 阴影偏移
            shadowOpacity: 0.25, // 阴影透明度
            shadowRadius: 16, // 模糊半径
            elevation: 5, // 仅用于Android，与阴影相关的视图提升
          }}
        >
          <Svg
            width={width}
            height={height}
            viewBox={`0 0 ${width} ${height}`}
            fill="none"
          >
            {/* LinearGradient definition */}
            <Defs>
              <LinearGradient
                id="paint0_linear"
                x1="56"
                y1="12"
                x2="56"
                y2="92"
              >
                <Stop offset="0" stopColor="#222222" />
                <Stop offset="1" stopColor="#222222" stopOpacity="0" />
              </LinearGradient>
              <LinearGradient id="grad1" x1="0%" y1="0%" x2="0%" y2="100%">
                <Stop offset="0%" stopColor="rgba(255, 255, 255, 0.10)" />
                <Stop offset="100%" stopColor="rgba(255, 255, 255, 0.10)" />
              </LinearGradient>
              <LinearGradient id="grad2" x1="0%" y1="0%" x2="0%" y2="100%">
                <Stop offset="0%" stopColor="rgba(34, 34, 34, 0.60)" />
                <Stop offset="100%" stopColor="rgba(34, 34, 34, 0.00)" />
              </LinearGradient>
            </Defs>

            {/* Rectangles */}
            <Rect
              x="0"
              y="0"
              width={width}
              height={height}
              fill="url(#grad1)"
              fillOpacity="0.1"
            />
            <Rect
              x="0"
              y="0"
              width={width}
              height={height}
              fill="url(#grad2)"
              fillOpacity="0.1"
            />
            <Rect
              x="0"
              y="0"
              width={width}
              height={height}
              rx="4"
              fill="url(#paint0_linear)"
              fillOpacity="0.6"
            />
            <Rect
              x="0"
              y="0"
              width={width}
              height={height}
              rx="4"
              fill="white"
              fillOpacity="0.1"
            />
            <Rect
              x="0.75"
              y="0.75"
              width={width - 1.5}
              height={height - 1.5}
              rx="3.25"
              stroke="#907C54"
              strokeOpacity="0.4"
              strokeWidth="1.5"
            />
          </Svg>
          <View
            className="absolute top-[15px]"
            style={{ alignItems: "center", width, height }}
          >
            <View style={{ gap: 7, alignItems: "center" }}>
              {Icon && <Icon weight="fill" size={32} color="white" />}
              <Text className="text-[14px] text-white">{children}</Text>
            </View>
          </View>
        </View>
      </TouchableNativeFeedback>
    </View>
  );
}
