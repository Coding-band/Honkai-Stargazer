import { View, Text } from "react-native";
import React from "react";
import getServerFromUUID from "../../../utils/hoyolab/servers/getServerFromUUID";
import { LOCALES } from "../../../../locales";
import useAppLanguage from "../../../language/AppLanguage/useAppLanguage";

export default function UUIDBox({ uuid }: { uuid: string }) {
  const { language: appLanguage } = useAppLanguage();

  return (
    <View
      className="bg-[#00000040] rounded-[49px] px-[12px] py-[6px]"
      style={{ alignItems: "center" }}
    >
      <Text className="text-[#FFFFFF] font-[HY65]">
        {uuid} · {LOCALES[appLanguage][getServerFromUUID(uuid)]}
      </Text>
    </View>
  );
}
