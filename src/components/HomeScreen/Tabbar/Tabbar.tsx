import React from "react";
import { Linking, Pressable, SafeAreaView, Text, View } from "react-native";
import { cn } from "../../../utils/css/cn";
import { Image } from "expo-image";
import useAppLanguage from "../../../language/AppLanguage/useAppLanguage";
import { LOCALES } from "../../../../locales";
import TypingText from "../../global/TypingText/TypingText";
import { ENV, VERSION } from "../../../../app.config";
import { HtmlText } from "@e-mine/react-native-html-text";
import BetaTabbar from "../../global/Beta/BetaTabbar";
import DonateTab from "./DonateTab/DonateTab";

export default function Tabbar() {
  const { language } = useAppLanguage();
  return (
    <SafeAreaView className={cn("absolute bottom-0", "w-full h-[152px]")}>
      <Divider />
      {/* <View
        style={{
          flex: 1,
          flexDirection: "row",
          justifyContent: "center",
          alignItems: "center",
          gap: 16,
        }}
      >
        <Tab>
          <Person color="white" size={32} weight="fill" />
        </Tab>
        <Tab>
          <Sword color="white" size={32} weight="fill" />
        </Tab>
        <Tab>
          <MathOperations color="white" size={32} weight="fill" />
        </Tab>
      </View> */}
      <View className="py-2" style={{ alignItems: "center", gap: 8 }}>
        <Text
          className="text-text text-[14px] font-[HY65]"
          // color="white"
          // textSize={14}
          // fontFamily="HY65"
          // text={}
        >
          {LOCALES[language].DonationRemoveAds}
        </Text>
        {/* <Pressable
          className="w-full h-[70px]"
          onPress={() => {
            Linking.openURL(
              "https://play.google.com/store/apps/details?id=com.voc.genshin_spirit_gp"
            );
          }}
        >
          <Image
            className="w-full h-full"
            source={require("../../../../assets/ads/gs_ad.png")}
          />
        </Pressable> */}
        <DonateTab />
      </View>
    </SafeAreaView>
  );
}

const Divider = () => (
  <View className="h-[1px] w-full px-4">
    <View
      className="w-full h-full"
      style={{ backgroundColor: "rgba(144, 124, 84, 0.40)" }}
    ></View>
  </View>
);
