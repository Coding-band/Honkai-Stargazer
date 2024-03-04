import { Dimensions, PixelRatio, Text } from "react-native";
import React from "react";
import Button from "../../../global/Button/Button";
import { cn } from "../../../../utils/css/cn";
import Animated, {
  SharedValue,
  useAnimatedStyle,
  withSpring,
} from "react-native-reanimated";
import useAppLanguage from "../../../../language/AppLanguage/useAppLanguage";
import { LOCALES } from "../../../../../locales";
import { dynamicHeightBottomBar } from "../../../../constant/ui";

type Props = {
  scrollHandler: SharedValue<number>;
  onLeftClick: () => void;
  onRightClick: () => void;
};

export default React.memo(function CharAction(props: Props) {
  const { language } = useAppLanguage();
  const bottomAnimatedStyles = useAnimatedStyle(() => {
    if (props.scrollHandler.value > 0) {
      return {
        opacity: withSpring(0),
        display: "none",
      };
    } else {
      return {
        opacity: withSpring(1),
        display: "absolute",
      };
    }
  });

  return (
    <Animated.View
      className={cn("w-full z-50", "absolute bottom-0")}
      style={[
        bottomAnimatedStyles,
        {
          justifyContent: "center",
          alignItems: "center",
          flexDirection: "row",
          gap: 27,
          paddingBottom : (dynamicHeightBottomBar > 36 ? dynamicHeightBottomBar : 36)/1.5
        },
      ]}
    >
      <Button onPress={props.onLeftClick} width={140} height={46}>
        <Text className="font-[HY65] text-[16px]">
          {LOCALES[language].AdviceLightcones}
        </Text>
      </Button>
      <Button onPress={props.onRightClick} width={140} height={46}>
        <Text className="font-[HY65] text-[16px]">
          {LOCALES[language].AdviceTeams}
        </Text>
      </Button>
    </Animated.View>
  );
});
