import React from "react";
import { View } from "react-native";
import { cn } from "../../../utils/cn";

export default function Tabbar({ children }: { children: any }) {
  return (
    <View className={cn("absolute bottom-0", "w-full h-[130px]")}>
      <View className="h-[1px] w-full px-4">
        <View
          className="w-full h-full"
          style={{ backgroundColor: "rgba(144, 124, 84, 0.40)" }}
        ></View>
      </View>
      <View
        style={{
          flex: 1,
          flexDirection: "row",
          justifyContent: "center",
          alignItems: "center",
          gap: 16,
        }}
      >
        {children}
      </View>
    </View>
  );
}
