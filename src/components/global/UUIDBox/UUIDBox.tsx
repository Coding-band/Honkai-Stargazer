import { View, Text, TouchableOpacity } from "react-native";
import React, { useCallback } from "react";
import getServerFromUUID from "../../../utils/hoyolab/servers/getServerFromUUID";
import { LOCALES } from "../../../../locales";
import useAppLanguage from "../../../language/AppLanguage/useAppLanguage";
import Toast from "../../../utils/toast/Toast";
import * as Clipboard from "expo-clipboard";

export default function UUIDBox({ uuid }: { uuid: string }) {
  const { language: appLanguage } = useAppLanguage();

  const handleCopyUUID = useCallback(async () => {
    try {
      await Clipboard.setStringAsync(uuid);
      Toast.CopyToClipboard(appLanguage);
    } catch (e) {
      Toast.FailToCopy(appLanguage);
    }
  }, [uuid, appLanguage]);

  return (
    <TouchableOpacity onPress={handleCopyUUID} activeOpacity={0.65}>
      <View
        className="bg-[#00000040] rounded-[49px] px-[12px] py-[6px]"
        style={{ alignItems: "center" }}
      >
        <Text className="text-[#FFFFFF] font-[HY65]">
          {uuid || "000000000"} ·{" "}
          {LOCALES[appLanguage][getServerFromUUID(uuid)!] || "無"}
        </Text>
      </View>
    </TouchableOpacity>
  );
}
