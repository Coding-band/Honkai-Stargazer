import { View, Text } from "react-native";
import React from "react";
import Button from "../../../global/ui/Button/Button";
import { Image } from "expo-image";
import { cn } from "../../../../utils/cn";
import Animated, {
  SharedValue,
  useAnimatedStyle,
  withSpring,
} from "react-native-reanimated";

type Props = {
  scrollHandler: SharedValue<number>;
};

export default function CharAction(props: Props) {
  const bottomAnimatedStyles = useAnimatedStyle(() => {
    if (props.scrollHandler.value > 0) {
      return {
        opacity: withSpring(0),
      };
    } else {
      return {
        opacity: withSpring(1),
      };
    }
  });

  return (
    <Animated.View
      className={cn("w-full h-[83px] pb-[37px] z-50", "absolute bottom-0")}
      style={[
        bottomAnimatedStyles,
        {
          justifyContent: "center",
          alignItems: "center",
          flexDirection: "row",
          gap: 27,
        },
      ]}
    >
      <Button width={140} height={46}>
        <Text className="font-[HY65] text-[16px]">推荐装备</Text>
      </Button>
      <Button width={140} height={46}>
        <Text className="font-[HY65] text-[16px]">推荐配队</Text>
      </Button>
    </Animated.View>
  );
}
