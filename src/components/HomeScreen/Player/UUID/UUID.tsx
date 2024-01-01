import React, { useCallback } from "react";
import { Text, TouchableOpacity, View } from "react-native";
import useHsrUUID from "../../../../hooks/hoyolab/useHsrUUID";
import Toast from "../../../../utils/toast/Toast";
import * as Clipboard from "expo-clipboard";

export default function UUID() {
  const uuid = useHsrUUID();

  const handleCopyUUID = useCallback(async () => {
    try {
      await Clipboard.setStringAsync(uuid);
      Toast("已複製到剪貼簿");
    } catch (e) {
      Toast("複製失敗, 錯誤代碼：" + e);
    }
  }, [uuid]);

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
        <Text className="text-white">{uuid || "00000000"}</Text>
      </View>
    </TouchableOpacity>
  );
}
