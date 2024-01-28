import { View, Text } from "react-native";
import React from "react";
import { Platform } from "react-native";
import { cn } from "../../../../../utils/css/cn";

export default function UserCharScoreItem({
  title,
  value,
}: {
  title: string;
  value: any;
}) {
  return (
    <View
      className="h-[54px] w-16"
      style={{ justifyContent: "space-between", alignItems: "center" }}
    >
      {typeof value === "string" ? (
        <Text
          className={cn(
            "text-text font-[HY65] text-[24px]",
            Platform.OS === "ios" ? "translate-y-[6px]" : ""
          )}
        >
          {value}
        </Text>
      ) : (
        value
      )}
      <Text className="text-text font-[HY65] text-[12px] text-center">{title}</Text>
    </View>
  );
}
