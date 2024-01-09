import { View, Text, TouchableOpacity, Dimensions } from "react-native";
import React from "react";
import Header2 from "../../global/Header2/Header2";
import useHsrUUID from "../../../hooks/hoyolab/useHsrUUID";
import useAppLanguage from "../../../language/AppLanguage/useAppLanguage";
import { Image } from "expo-image";
import { LOCALES } from "../../../../locales";
import getServerFromUUID from "../../../utils/hoyolab/servers/getServerFromUUID";
import UUIDBox from "../../global/UUIDBox/UUIDBox";

type Props = {
  uuid: string;
};

export default function UserCharDetail(props: Props) {
  const { language: appLangauge } = useAppLanguage();

  const hsrUUID = useHsrUUID();
  const isOwner = props.uuid === hsrUUID;

  return (
    <View className="z-30">
      <Header2 rightBtn={isOwner ? <ShareBtn /> : null} />
      <View
        className="mt-12 px-4"
        style={[
          {
            alignItems: "center",
            gap: 18,
            height: Dimensions.get("window").height,
          },
        ]}
      >
        <View style={{ alignItems: "center", gap: 4 }}>
          {/* 用戶名 */}
          <Text className="text-[#FFFFFF] font-[HY65] text-[16px]">2O48</Text>
          {/* UUID & 伺服器 */}
          <UUIDBox uuid={props.uuid} />
        </View>
      </View>
    </View>
  );
}

const ShareBtn = () => (
  <TouchableOpacity onPress={() => {}}>
    <Image
      style={{ width: 40, height: 40 }}
      source={require("../../../../assets/icons/Share.svg")}
    />
  </TouchableOpacity>
);
