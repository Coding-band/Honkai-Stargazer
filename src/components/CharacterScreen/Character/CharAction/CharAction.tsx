import { View, Text } from "react-native";
import React from "react";
import Button from "../../../global/ui/Button/Button";
import { Image } from "expo-image";
import { cn } from "../../../../utils/cn";

export default function CharAction({ show }: { show: boolean }) {
  return (
    <View
      className={cn("w-full h-[83px] pb-[37px]", "absolute bottom-0 z-50")}
      style={{
        opacity: show ? 1 : 0,
        justifyContent: "center",
        alignItems: "center",
        flexDirection: "row",
        gap: 27,
      }}
    >
      <Button width={140} height={46}>
        <Text className="font-[HY65] text-[16px]">推荐装备</Text>
      </Button>
      <Button width={140} height={46}>
        <Text className="font-[HY65] text-[16px]">推荐配队</Text>
      </Button>
    </View>
  );
}
