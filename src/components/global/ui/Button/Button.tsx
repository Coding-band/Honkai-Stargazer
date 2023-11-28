import React from "react";
import { Text, TouchableOpacity, View } from "react-native";
import { Shadow } from "react-native-shadow-2";

export default function Button({
  children,
  width,
  height,
}: {
  children: any;
  width: number;
  height: number;
}) {
  return (
    <TouchableOpacity activeOpacity={0.65}>
      <Shadow offset={[0, 4]}>
        <View
          className="bg-[#dddddd] rounded-[49px]"
          style={{
            width,
            height,
            justifyContent: "center",
            alignItems: "center",
          }}
        >
          <View
            className="rounded-[49px] border border-solid border-[#0000001a]"
            style={{
              width: width - 6,
              height: height - 6,
              justifyContent: "center",
              alignItems: "center",
            }}
          >
            {children}
          </View>
        </View>
      </Shadow>
    </TouchableOpacity>
  );
}
