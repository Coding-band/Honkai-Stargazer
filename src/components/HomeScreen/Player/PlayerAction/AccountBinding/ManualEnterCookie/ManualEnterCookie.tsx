import { View, Text, TouchableOpacity } from "react-native";
import React, { useEffect, useState } from "react";
import Button from "../../../../../global/Button/Button";
import { TextInput } from "react-native";
import useHoyolabCookie from "../../../../../../redux/hoyolabCookie/useHoyolabCookie";
import TextButton from "../../../../../global/TextButton/TextButton";
import useHsrServerChosen from "../../../../../../redux/hsrServerChosen/useHsrServerChosen";
import auth from "@react-native-firebase/auth";
import { LOCALES } from "../../../../../../../locales";
import { hsrServer } from "../../../../../../utils/hoyolab/servers/hsrServer";
import { keys } from "lodash";
import Toast from "../../../../../../utils/toast/Toast";
import useAppLanguage from "../../../../../../language/AppLanguage/useAppLanguage";
import { ClipboardText } from "phosphor-react-native";
import * as Clipboard from "expo-clipboard";

type Props = {
  onCookieSave?: () => void;
};

export default function ManualEnterCookie(props: Props) {
  const [btnChooseServerIndex, setBtnChooseServerIndex] = useState(0);
  const { language } = useAppLanguage();
  const { setHoyolabCookie } = useHoyolabCookie();
  const { setHsrServerChosen } = useHsrServerChosen();

  const [inputCookie, setInputCookie] = useState("");

  const handleSaveCookie = () => {
    if (!inputCookie) {
      Toast(LOCALES[language].LoginEnterCookies);
      return;
    }

    setHoyolabCookie(inputCookie);
    // @ts-ignore
    setHsrServerChosen(keys(hsrServer)[btnChooseServerIndex]);
    props.onCookieSave && props.onCookieSave();
  };

  return (
    <View style={{ gap: 12 }}>
      <Text className="text-[14px] font-[HY65] text-black leading-5">
        {LOCALES[language].SelectServerAndPasteCookies}
      </Text>
      <TextButton
        onPress={() => {
          if (btnChooseServerIndex === 5) {
            setBtnChooseServerIndex(0);
          } else {
            setBtnChooseServerIndex(btnChooseServerIndex + 1);
          }
        }}
        hasShadow={false}
        width={"100%"}
        height={46}
      >
        {/* @ts-ignore */}
        {LOCALES["zh_hk"][keys(hsrServer)[btnChooseServerIndex]]}
      </TextButton>
      <View className="w-full h-[280px]">
        <TextInput
          value={inputCookie}
          onChangeText={setInputCookie}
          textAlignVertical="top"
          multiline={true}
          placeholder={LOCALES[language].LoginViaPCToGetCookies}
          placeholderTextColor="gray"
          className="w-full h-full bg-[#ffffff50] rounded-[24px] p-3 font-[HY65] leading-5"
        />
        <PasteBtn onPaste={setInputCookie} />
      </View>
      <Button
        onPress={handleSaveCookie}
        hasShadow={false}
        width={"100%"}
        height={46}
      >
        {LOCALES[language].ConfirmBTN}
      </Button>
    </View>
  );
}

const PasteBtn = ({ onPaste }: { onPaste: (v: string) => void }) => {
  const [copiedText, setCopiedText] = React.useState("");
  const fetchCopiedText = async () => {
    const text = await Clipboard.getStringAsync();
    setCopiedText(text);
  };

  useEffect(() => {
    if (copiedText) {
      onPaste(copiedText);
    }
  }, [copiedText]);

  return (
    <TouchableOpacity onPress={fetchCopiedText} className="absolute right-3 bottom-3 w-[42px] h-[42px] rounded-[12px] bg-[#00000010] items-center justify-center">
      <ClipboardText weight="fill" />
    </TouchableOpacity>
  );
};
