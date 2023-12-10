import React from "react";
import { Text, View } from "react-native";
import useHsrUUID from "../../../hooks/hoyolab/useHsrUUID";

export default function UUID() {
  const uuid = useHsrUUID();

  return (
    <View
      className="ml-[-4px] p-2 rounded-[50px]"
      style={{
        justifyContent: "center",
        alignItems: "center",
        backgroundColor: "rgba(0, 0, 0, 0.3)",
      }}
    >
      <Text className="text-white">{uuid || "00000000"}</Text>
    </View>
  );
}
