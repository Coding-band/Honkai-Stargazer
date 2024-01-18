import { View, Text } from "react-native";
import React, { useEffect, useState } from "react";
import { Image } from "expo-image";
import useAppLanguage from "../../../language/AppLanguage/useAppLanguage";
import { LOCALES } from "../../../../locales";

export default function Loading() {
  const [dot, setDot] = useState(".");
  const { language } = useAppLanguage()
  useEffect(() => {
    const t = setInterval(() => {
      if (dot === "...") {
        setDot(".");
      } else {
        setDot(dot + ".");
      }
    }, 300);
    return () => {
      clearInterval(t);
    };
  }, [dot]);

  return (
    <View
      className="w-screen h-screen"
      style={{ justifyContent: "center", alignItems: "center" }}
    >
      <View style={{ gap: 21, alignItems: "center" }}>
        <Image
          className="w-[144px] h-[144px]"
          source={require("./images/01.png")}
        />
        <Text className="text-text font-[HY65] text-[16px] leading-5">
          {LOCALES[language].AppStatusLoading}{dot}
        </Text>
      </View>
    </View>
  );
}
