import { View, Text } from "react-native";
import React, { useState } from "react";
import Button from "../../../../../global/Button/Button";
import { TextInput } from "react-native";
import useHoyolabCookie from "../../../../../../redux/hoyolabCookie/useHoyolabCookie";
import TextButton from "../../../../../global/TextButton/TextButton";
import useHsrServerChosen from "../../../../../../redux/hsrServerChosen/useHsrServerChosen";
import auth from "@react-native-firebase/auth";

type Props = {
  onCookieSave?: () => void;
};

export default function ManualEnterCookie(props: Props) {
  const { setHoyolabCookie } = useHoyolabCookie();
  const { setHsrServerChosen } = useHsrServerChosen();

  const [inputCookie, setInputCookie] = useState("");
  const handleSaveCookie = () => {
    setHoyolabCookie(inputCookie);
    setHsrServerChosen("cn1");
    auth().signOut();
    props.onCookieSave && props.onCookieSave();
  };

  return (
    <View style={{ gap: 12 }}>
      <Text className="text-[14px] font-[HY55] text-black leading-5">
        請選擇服務器並貼上 Cookies。
      </Text>
      <TextButton hasShadow={false} width={"100%"} height={46}>
        星窮列車
      </TextButton>
      <TextInput
        value={inputCookie}
        onChangeText={setInputCookie}
        textAlignVertical="top"
        multiline={true}
        placeholder={`請輸入 Cookies 包含 ltuid_v2、ltoken_v2、account_id_v2、cookie_token_v2、account_mid_v2 和 ltmid_v2。請注意，此選項主要為開發人員設計。Stargazer 建議您在非不得已的情況下不要使用這一選項，以免增加複雜度。
`}
        className="w-full h-[280px] bg-[#ffffff50] rounded-[4px] p-3 font-[HY55] leading-5"
      />
      <TextButton
        onPress={handleSaveCookie}
        hasShadow={false}
        width={"100%"}
        height={46}
      >
        確定
      </TextButton>
    </View>
  );
}
