import { View, Text } from "react-native";
import React from "react";
import Button from "../../../../../global/Button/Button";
import { HtmlText } from "@e-mine/react-native-html-text";
import loginPolicyText from "../../../../../../../data/login_policy.json";

type Props = {
  onAcceptPolicy: () => void;
};

export default function LoginPolicy(props: Props) {
  return (
    <View className="p-4">
      <View style={{ gap: 0, alignItems: "center" }}>
        <HtmlText style={{ color: "#000", fontFamily: "HY55", lineHeight: 20 }}>
          {loginPolicyText.zh_cn}
        </HtmlText>
        <Button
          onPress={props.onAcceptPolicy}
          hasShadow={false}
          width={140}
          height={46}
        >
          <Text>确定</Text>
        </Button>
      </View>
    </View>
  );
}
