import { View, Text } from "react-native";
import React, { useEffect, useState } from "react";
import Button from "../../../../../global/Button/Button";
import { HtmlText } from "@e-mine/react-native-html-text";
import loginPolicyText from "../../../../../../../data/sg_data/login_policy.json";
import useAppLanguage from "../../../../../../language/AppLanguage/useAppLanguage";
import { LOCALES } from "../../../../../../../locales";
import { ScrollView } from "react-native-gesture-handler";

type Props = {
  onAcceptPolicy: () => void;
  onRejectPolicy: () => void;
};

export default function LoginPolicy(props: Props) {
  const { language } = useAppLanguage();

  const [countDown, setCountDown] = useState(5);
  useEffect(() => {
    if (countDown > 0) {
      const i = setInterval(() => {
        setCountDown(countDown - 1);
      }, 1000);
      return () => {
        clearInterval(i);
      };
    }
  }, [countDown]);

  return (
    <View style={{ gap: 10, alignItems: "center" }}>
      <View>
        <HtmlText style={{ color: "#000", fontFamily: "HY65", lineHeight: 20 }}>
          {/* @ts-ignore */}
          {LOCALES[language].LoginPolicy}
        </HtmlText>
      </View>
      <Button
        onPress={countDown === 0 ? props.onAcceptPolicy : () => {}}
        hasShadow={false}
        width={320}
        height={46}
        disable={countDown !== 0}
      >
        {countDown === 0 ? (
          LOCALES[language].OK
        ) : (
          <Text className="opacity-30">{countDown + "s"}</Text>
        )}
      </Button>
      <Button
        onPress={props.onRejectPolicy}
        hasShadow={false}
        width={320}
        height={46}
      >
        {LOCALES[language].NotOK}
      </Button>
    </View>
  );
}
