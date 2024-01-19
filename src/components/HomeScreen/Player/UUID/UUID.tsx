import React, { useCallback } from "react";
import { Text, TouchableOpacity, View } from "react-native";
import useHsrUUID from "../../../../hooks/hoyolab/useHsrUUID";
import Toast from "../../../../utils/toast/Toast";
import * as Clipboard from "expo-clipboard";
import useAppLanguage from "../../../../language/AppLanguage/useAppLanguage";
import { LOCALES } from "../../../../../locales";

export default function UUID() {
  const { language } = useAppLanguage();

  const uuid = useHsrUUID();
  const handleCopyUUID = useCallback(async () => {
    try {
      await Clipboard.setStringAsync(uuid);
      Toast.CopyToClipboard(language);
    } catch (e) {
      Toast.FailToCopy(language);
    }
  }, [uuid, language]);

  return (
    <TouchableOpacity activeOpacity={0.35} onPress={handleCopyUUID}>
      <View
        className="ml-[-4px] p-2 rounded-[50px]"
        style={{
          justifyContent: "center",
          alignItems: "center",
          backgroundColor: "rgba(0, 0, 0, 0.3)",
        }}
      >
        <Text className="text-white font-[HY65]">{uuid || "00000000"}</Text>
      </View>
    </TouchableOpacity>
  );
}
