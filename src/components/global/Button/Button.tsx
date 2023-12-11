import React from "react";
import { DimensionValue, Text, TouchableOpacity, View } from "react-native";
import { GestureResponderEvent } from "react-native-modal";
import { Shadow } from "react-native-shadow-2";

export default function Button({
  children,
  width,
  height,
  onPress,
  hasShadow = true,
  disable = false,
}: {
  children: any;
  width: DimensionValue;
  height: DimensionValue;
  onPress?: (e: GestureResponderEvent) => void;
  hasShadow?: boolean;
  disable?: boolean;
}) {
  return disable ? (
    hasShadow ? (
      <Shadow offset={[0, 4]}>
        <View
          className="bg-[#dddddd] rounded-[49px] p-[3px]"
          style={{
            width,
            height,
            justifyContent: "center",
            alignItems: "center",
          }}
        >
          <View
            className="rounded-[49px] border border-solid border-[#c7c7c7] w-full h-full"
            style={{
              justifyContent: "center",
              alignItems: "center",
            }}
          >
            {children}
          </View>
        </View>
      </Shadow>
    ) : (
      <View
        className="bg-[#dddddd] rounded-[49px] p-[3px]"
        style={{
          width,
          height,
          justifyContent: "center",
          alignItems: "center",
        }}
      >
        <View
          className="rounded-[49px] border border-[#c7c7c7] w-full h-full"
          style={{
            justifyContent: "center",
            alignItems: "center",
          }}
        >
          {children}
        </View>
      </View>
    )
  ) : (
    <TouchableOpacity activeOpacity={0.65} onPress={onPress}>
      {hasShadow ? (
        <Shadow offset={[0, 4]}>
          <View
            className="bg-[#dddddd] rounded-[49px] p-[3px]"
            style={{
              width,
              height,
              justifyContent: "center",
              alignItems: "center",
            }}
          >
            <View
              className="rounded-[49px] border border-solid border-[#c7c7c7] w-full h-full"
              style={{
                justifyContent: "center",
                alignItems: "center",
              }}
            >
              {children}
            </View>
          </View>
        </Shadow>
      ) : (
        <View
          className="bg-[#dddddd] rounded-[49px] p-[3px]"
          style={{
            width,
            height,
            justifyContent: "center",
            alignItems: "center",
          }}
        >
          <View
            className="rounded-[49px] border border-[#c7c7c7] w-full h-full"
            style={{
              justifyContent: "center",
              alignItems: "center",
            }}
          >
            {children}
          </View>
        </View>
      )}
    </TouchableOpacity>
  );
}
