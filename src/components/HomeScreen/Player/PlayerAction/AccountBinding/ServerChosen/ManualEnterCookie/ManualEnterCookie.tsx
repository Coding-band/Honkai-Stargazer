import { View, Text } from "react-native";
import React, { useState } from "react";
import Button from "../../../../../../global/Button/Button";
import { TextInput } from "react-native";
import useHoyolabCookie from "../../../../../../../redux/hoyolabCookie/useHoyolabCookie";

type Props = {
  onCookieSave?: () => void;
};

export default function ManualEnterCookie(props: Props) {
  const { setHoyolabCookie } = useHoyolabCookie();

  const [inputCookie, setInputCookie] = useState("");
  const handleSaveCookie = () => {
    setHoyolabCookie(inputCookie);
    props.onCookieSave && props.onCookieSave();
  };

  return (
    <View style={{ gap: 12 }}>
      <Text className="text-[14px] font-[HY55] text-black leading-5">
        请选择服务器并粘贴 Cookies。
      </Text>
      <Button hasShadow={false} width={"100%"} height={46}>
        <Text className="font-[HY55]">Asia</Text>
      </Button>
      <TextInput
        value={inputCookie}
        onChangeText={setInputCookie}
        textAlignVertical="top"
        multiline={true}
        placeholder="請輸入 Cookies 包含 ltuid_v2、ltoken_v2、account_id_v2、cookie_token_v2、account_mid_v2 和 ltmid_v2。"
        className="w-full h-[280px] bg-[#ffffff50] rounded-[4px] p-4 font-[HY55] leading-5"
      />
      <Button
        onPress={handleSaveCookie}
        hasShadow={false}
        width={"100%"}
        height={46}
      >
        <Text className="font-[HY55]">確定</Text>
      </Button>
    </View>
  );
}
