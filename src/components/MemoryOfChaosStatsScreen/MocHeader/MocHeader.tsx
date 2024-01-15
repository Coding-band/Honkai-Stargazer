import { View, Text } from "react-native";
import React from "react";
import Header2 from "../../global/Header2/Header2";
import { useAnimatedStyle, withSpring } from "react-native-reanimated";
import useHsrUUID from "../../../hooks/hoyolab/useHsrUUID";
import { LOCALES } from "../../../../locales";
import useAppLanguage from "../../../language/AppLanguage/useAppLanguage";
import getServerFromUUID from "../../../utils/hoyolab/servers/getServerFromUUID";

export default function MocHeader({ scrollHandler }: { scrollHandler: any }) {
  const { language } = useAppLanguage();

  const hsrUUID = useHsrUUID();

  const headerAnimatedStyles = useAnimatedStyle(() => {
    if (scrollHandler.value > 0) {
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
    <Header2 style={headerAnimatedStyles}>
      <View style={{ alignItems: "center" }}>
        <Text className="text-[20px] text-text font-[HY65]">我的戰績</Text>
        <Text className="text-[14px] text-text font-[HY65]">
          {hsrUUID} · {LOCALES[language][getServerFromUUID(hsrUUID)!]}
        </Text>
      </View>
    </Header2>
  );
}

// const StatBtn = () => (
//   <TouchableOpacity onPress={() => {}}>
//     <Image
//       style={{ width: 40, height: 40 }}
//       source={require("./icons/Stat.svg")}
//     />
//   </TouchableOpacity>
// );
