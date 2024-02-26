import { View, Text, TouchableOpacity, Linking } from "react-native";
import React from "react";
import {
  HtmlText,
  HtmlTextContext,
  defaultContextValues,
} from "@e-mine/react-native-html-text";
import { Image } from "expo-image";
import { LOCALES } from "../../../../../../locales";
import useAppLanguage from "../../../../../language/AppLanguage/useAppLanguage";
import { DiscordLogo, GithubLogo, GooglePlayLogo } from "phosphor-react-native";

export default function AboutTheApp() {
  const { language } = useAppLanguage();

  return (
    <View>
      <HtmlTextContext.Provider
        // @ts-ignore
        value={{
          ...defaultContextValues.styles, // If you want to "inherit" some styles from the default ones
          styles: {
            h2: {
              fontSize: 20,
              color: "#e9ba79",
            },
          },
        }}
      >
        <HtmlText
          style={{
            color: "#DDD",
            fontFamily: "HY65",
            fontSize: 16,
            lineHeight: 28,
          }}
        >
          {LOCALES[language].AboutTheAppContent}
        </HtmlText>
      </HtmlTextContext.Provider>
      <Image cachePolicy="none" source={require("../../../../../../assets/images/codingband-banner.png")} className="w-full h-48" contentFit="contain" />
      <View className="flex-row items-center justify-around mt-6">
        <TouchableOpacity
          activeOpacity={0.65}
          onPress={() => {
            Linking.openURL("https://discord.gg/uXatcbWKv2");
          }}
        >
          <DiscordLogo size={32} color="#5865f2" weight="bold" />
        </TouchableOpacity>
        <TouchableOpacity
          activeOpacity={0.65}
          onPress={() => {
            Linking.openURL("https://github.com/Coding-band/Honkai-Stargazer");
          }}
        >
          <GithubLogo size={32} color="#e6edf3" weight="bold" />
        </TouchableOpacity>
      </View>
    </View>
  );
}
