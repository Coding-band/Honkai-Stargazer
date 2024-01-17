import { View, Text, TouchableOpacity } from "react-native";
import React from "react";
import Header2 from "../../global/Header2/Header2";
import { useAnimatedStyle, withSpring } from "react-native-reanimated";
import useHsrUUID from "../../../hooks/hoyolab/useHsrUUID";
import { LOCALES } from "../../../../locales";
import useAppLanguage from "../../../language/AppLanguage/useAppLanguage";
import getServerFromUUID from "../../../utils/hoyolab/servers/getServerFromUUID";
import { ChartBarHorizontal } from "phosphor-react-native";
import { useNavigation } from "@react-navigation/native";
import { SCREENS } from "../../../constant/screens";

export default function MocHeader({ scrollHandler }: { scrollHandler: any }) {
  const { language } = useAppLanguage();
  const navigation = useNavigation();

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
    <Header2
      style={headerAnimatedStyles}
      rightBtn={
        <LeaderboardBtn
          onPress={() => {
            // @ts-ignore
            navigation.push(SCREENS.MemoryOfChaosLeaderboardPage.id);
          }}
        />
      }
    >
      <View style={{ alignItems: "center" }}>
        <Text className="text-[20px] text-text font-[HY65]">我的戰績</Text>
        <Text className="text-[14px] text-text font-[HY65]">
          {hsrUUID} · {LOCALES[language][getServerFromUUID(hsrUUID)!]}
        </Text>
      </View>
    </Header2>
  );
}

const LeaderboardBtn = ({ onPress }: { onPress: () => void }) => (
  <TouchableOpacity onPress={onPress}>
    <ChartBarHorizontal
      size={30}
      color="white"
      style={{ transform: [{ rotate: "270deg" }] }}
    />
  </TouchableOpacity>
);
