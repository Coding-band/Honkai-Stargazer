import { View, Text } from "react-native";
import React from "react";
import {
  HtmlText,
  HtmlTextContext,
  defaultContextValues,
} from "@e-mine/react-native-html-text";
import { Image } from "expo-image";
import { LOCALES } from "../../../../../../locales";
import useAppLanguage from "../../../../../language/AppLanguage/useAppLanguage";

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
      <Image
        source={
          "https://truth.bahamut.com.tw/s01/202401/forum/72822/eb351f271a3edd842e237b0ff2fcc71d.PNG?w=1000"
        }
        className="w-full h-48"
        contentFit="contain"
      />
    </View>
  );
}
