import { View, Text } from "react-native";
import React from "react";
import Button from "../../../global/ui/Button/Button";
import { Image } from "expo-image";
import { cn } from "../../../../utils/cn";
import { useSpring, animated } from "@react-spring/native";

export default function CharAction({ show }: { show: boolean }) {
  const animation = useSpring({ bottom: show ? 0 : -100 });

  return (
    <AnimatedView
      className={cn("w-full h-[83px] pb-[37px]", "absolute bottom-0 z-50")}
      style={{
        justifyContent: "center",
        alignItems: "center",
        flexDirection: "row",
        gap: 27,
        ...animation,
      }}
    >
      <Button width={140} height={46}>
        <Text className="font-[HY65] text-[16px]">推荐装备</Text>
      </Button>
      <Button width={140} height={46}>
        <Text className="font-[HY65] text-[16px]">推荐配队</Text>
      </Button>
    </AnimatedView>
  );
}

const AnimatedView = animated(View);
