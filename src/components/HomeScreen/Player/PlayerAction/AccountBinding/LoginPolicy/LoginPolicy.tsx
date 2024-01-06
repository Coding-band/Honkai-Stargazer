import { View, Text } from "react-native";
import React from "react";
import Button from "../../../../../global/Button/Button";
import { HtmlText } from "@e-mine/react-native-html-text";
import loginPolicyText from "../../../../../../../data/sg_data/login_policy.json";
import TextButton from "../../../../../global/TextButton/TextButton";
import useAppLanguage from "../../../../../../language/AppLanguage/useAppLanguage";
import { LOCALES } from "../../../../../../../locales";

type Props = {
  onAcceptPolicy: () => void;
};

export default function LoginPolicy(props: Props) {
  const { language } = useAppLanguage();
  return (
    <View>
      <View style={{ gap: 0, alignItems: "center" }}>
        <HtmlText style={{ color: "#000", fontFamily: "HY55", lineHeight: 20 }}>
          {loginPolicyText.zh_cn}
        </HtmlText>
        <TextButton
          onPress={props.onAcceptPolicy}
          hasShadow={false}
          width={140}
          height={46}
        >
          {LOCALES[language].OK}
        </TextButton>
      </View>
    </View>
  );
}
