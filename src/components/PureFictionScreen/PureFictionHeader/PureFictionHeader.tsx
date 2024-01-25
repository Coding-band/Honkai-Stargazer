import { View, Text } from "react-native";
import React from "react";
import Header2 from "../../global/Header2/Header2";
import { SCREENS } from "../../../constant/screens";
import useAppLanguage from "../../../language/AppLanguage/useAppLanguage";
import Animated, {
  useAnimatedStyle,
  withSpring,
} from "react-native-reanimated";
import { TouchableOpacity } from "react-native";
import { Image } from "expo-image";
import { useNavigation } from "@react-navigation/native";

export default function PureFictionHeader({
  scrollHandler,
}: {
  scrollHandler: any;
}) {
  const { language } = useAppLanguage();
  const navigation = useNavigation();

  const Icon = SCREENS.PureFictionPage.icon;
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
        <StatBtn
          onPress={() => {
            // @ts-ignore
            navigation.push(SCREENS.PureFictionStatsPage.id);
          }}
        />
      }
    >
      <Animated.View
        className="pt-2"
        style={{
          flex: 1,
          alignItems: "center",
          justifyContent: "flex-end",
          gap: 4,
        }}
      >
        <Icon size={32} color="white" weight="fill" />
        <View style={{ flexDirection: "row", alignItems: "center", gap: 13 }}>
          <View
            style={{ backgroundColor: "#ffffff40", height: 2, width: 50 }}
          ></View>
          <Text
            numberOfLines={1}
            className="text-white text-[16px] leading-5"
            style={{ fontFamily: "HY65", maxWidth: 160 }}
          >
            {SCREENS.PureFictionPage.getName(language)}
          </Text>
          <View
            style={{ backgroundColor: "#ffffff40", height: 2, width: 50 }}
          ></View>
        </View>
      </Animated.View>
    </Header2>
  );
}

const StatBtn = ({ onPress }: { onPress: () => void }) => (
  <TouchableOpacity onPress={onPress}>
    <Image
      style={{ width: 40, height: 40 }}
      source={require("./icons/Stat.svg")}
    />
  </TouchableOpacity>
);
