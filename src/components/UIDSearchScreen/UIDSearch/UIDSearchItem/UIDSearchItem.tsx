import { View, Text, TouchableOpacity } from "react-native";
import React from "react";
import { Image } from "expo-image";
import useHsrInGameInfo from "../../../../hooks/mihomo/useHsrInGameInfo";
import getServerFromUUID from "../../../../utils/hoyolab/servers/getServerFromUUID";
import useAppLanguage from "../../../../language/AppLanguage/useAppLanguage";
import { LOCALES } from "../../../../../locales";
import AvatarIcon from "../../../../../assets/images/images_map/avatarIcon";
import { useNavigation } from "@react-navigation/native";
import { SCREENS } from "../../../../constant/screens";

export default function UIDSearchItem({ uuid }: { uuid: string }) {
  const { language: appLanguage } = useAppLanguage();

  const { data: user } = useHsrInGameInfo(uuid) as any;

  return (
    <View
      className="w-full h-[60px] px-3 bg-[#F3F9FF40] rounded-[10px]"
      style={{
        flexDirection: "row",
        alignItems: "center",
        justifyContent: "space-between",
      }}
    >
      <View style={{ flexDirection: "row", gap: 9 }}>
        <Image
          // @ts-ignore
          source={
            AvatarIcon[user?.player?.avatar?.icon?.match(/\d+/g)?.join("")] ||
            AvatarIcon
          }
          className="w-10 h-10 rounded-full bg-[#CAB89E]"
        />
        <View>
          <Text className="text-text text-[16px] font-[HY65] leading-5">
            {uuid}
          </Text>
          <Text className="text-text text-[12px] font-[HY65] leading-5">
            {user?.player?.nickname} ·
            <Text className="text-text text-[12px] font-[HY65]">
              {LOCALES[appLanguage][getServerFromUUID(uuid)!]}
            </Text>
          </Text>
        </View>
      </View>
      <View className="mt-[10px]" style={{ alignSelf: "flex-start" }}>
        <Text className="text-text text-[12px] font-[HY65] leading-5">
          {LOCALES[appLanguage].PlayerLevel + " " + (user?.player?.level || 0)}
        </Text>
      </View>
    </View>
  );
}
