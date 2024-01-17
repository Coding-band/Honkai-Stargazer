import { View, Text, TouchableOpacity } from "react-native";
import React, { useCallback, useState } from "react";
import { LinearGradient } from "expo-linear-gradient";
import * as Clipboard from "expo-clipboard";
import Toast from "../../../../utils/toast/Toast";
import { LOCALES } from "../../../../../locales";
import useAppLanguage from "../../../../language/AppLanguage/useAppLanguage";
import { ShareNetwork } from "phosphor-react-native";
import { Share } from "react-native";

type Props = {
  time: string;
  code: string;
};

export default function CodeItem(props: Props) {
  const { language } = useAppLanguage();

  const [hasCopy, setHasCopy] = useState(false);

  const handleCopyCode = useCallback(async () => {
    try {
      await Clipboard.setStringAsync(props.code);
      Toast.CopyToClipboard();
      setHasCopy(true);
    } catch (e) {
      //
    }
  }, [props.code]);

  const handleShareCope = async () => {
    try {
      await Share.share({
        message: props.code,
      });
      Toast("分享成功！");
    } catch (error: any) {
      Toast("分享失敗，錯誤訊息：" + error.message);
    }
  };

  return (
    <TouchableOpacity
      className="w-full h-[90px]"
      activeOpacity={0.65}
      onPress={handleCopyCode}
    >
      <View
        className="w-full h-[90px] bg-[#7D8390] rounded-[4px] overflow-hidden"
        style={{ justifyContent: "center" }}
      >
        <View className="w-full h-[87px] bg-[#D2DBE3] rounded-[4px] overflow-hidden">
          <LinearGradient
            className="w-full h-[30px] px-2.5 opacity-80"
            colors={["#020510", "#454C65"]}
            start={[0, 0]}
            end={[1, 1]}
            style={{
              flexDirection: "row",
              justifyContent: "space-between",
              alignItems: "center",
            }}
          >
            {/* 日期 / 已複製 */}
            <Text className="text-text font-[HY65] leading-5">
              {hasCopy ? LOCALES[language].HasCopy : props.time}
            </Text>
            {/* 國際服 / 中國服 */}
            <Text className="text-text font-[HY65]">ALL</Text>
          </LinearGradient>
          <View
            className="px-2.5"
            style={{
              flex: 1,
              alignItems: "center",
              justifyContent: "space-between",
              flexDirection: "row",
            }}
          >
            {/* 兌換碼 */}
            <Text
              className="text-[#222] font-[HY65] text-[17px] font-bold translate-y-[-2px]"
              style={{
                textDecorationLine: hasCopy ? "line-through" : "none",
                opacity: hasCopy ? 0.3 : 1,
              }}
            >
              {props.code}
            </Text>
            <TouchableOpacity
              className="w-9 h-9 rounded-full bg-white justify-center items-center"
              onPress={handleShareCope}
            >
              <ShareNetwork weight="fill" />
            </TouchableOpacity>
          </View>
        </View>
      </View>
    </TouchableOpacity>
  );
}
