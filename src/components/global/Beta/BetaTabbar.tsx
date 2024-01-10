import { View, Text, ScrollView } from "react-native";
import React, { useEffect, useState } from "react";
import { HtmlText } from "@e-mine/react-native-html-text";
import axios from "axios";
import useAppLanguage from "../../../language/AppLanguage/useAppLanguage";

export default function BetaTabbar() {
  const { language } = useAppLanguage();
  const [tabbarMessage, setTabbatMessage] = useState<any>();

  useEffect(() => {
    axios
      .get(
        "https://firebasestorage.googleapis.com/v0/b/honkai-stargazer-bf382.appspot.com/o/ads%2Fbeta-tab-message.json?alt=media&token=152829c0-20bb-4095-a776-35fcf03f7240"
      )
      .then((data) => {
        setTabbatMessage(data.data);
      });
  }, []);

  return (
    <View className="w-full h-full px-4">
      <HtmlText
        style={{ color: "white", fontFamily: "HY65", textAlign: "center" }}
      >
        {tabbarMessage?.[language]}
      </HtmlText>
    </View>
  );
}
