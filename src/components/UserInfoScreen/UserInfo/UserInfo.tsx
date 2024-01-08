import { View, Text, Dimensions } from "react-native";
import React from "react";
import Header2 from "../../global/Header2/Header2";
import { TouchableOpacity } from "react-native";
import { Image } from "expo-image";
import useHsrFullData from "../../../hooks/hoyolab/useHsrFullData";
import useHsrInGameInfo from "../../../hooks/mihomo/useHsrInGameInfo";
import useHsrUUID from "../../../hooks/hoyolab/useHsrUUID";
import useHsrServerChosen from "../../../redux/hsrServerChosen/useHsrServerChosen";
import useAppLanguage from "../../../language/AppLanguage/useAppLanguage";
import { LOCALES } from "../../../../locales";
import UserInfoCharacters from "./UserInfoCharacters/UserInfoCharacters";
import useMemoryOfChaos from "../../../hooks/hoyolab/useMemoryOfChaos";
import Toast from "../../../utils/toast/Toast";

export default function UserInfo() {
  const { language: appLangauge } = useAppLanguage();

  const hsrUUID = useHsrUUID();
  const { hsrServerChosen } = useHsrServerChosen();
  const { data: hsrFullData } = useHsrFullData();
  const { data: hsrInGameInfo } = useHsrInGameInfo(hsrUUID);
  const { data: moc } = useMemoryOfChaos();

  return (
    <View className="z-30">
      <Header2 rightBtn={<ShareBtn />} />
      <View
        className="mt-12 px-4"
        style={{
          alignItems: "center",
          gap: 18,
          height: Dimensions.get("window").height,
        }}
      >
        <View style={{ alignItems: "center", gap: 6 }}>
          {/* 頭像 */}
          <Avatar
            image={hsrFullData?.cur_head_icon_url}
            onPress={() => {
              const signature = hsrInGameInfo?.player?.signature;
              if (signature) {
                Toast(signature);
              }
            }}
          />
          {/* 玩家名 */}
          <Text className="text-text text-[24px] font-[HY65]">
            {hsrInGameInfo?.player?.nickname}
          </Text>
          {/* UUID & 伺服器 */}
          <View
            className="bg-[#00000040] rounded-[49px] px-[12px] py-[6px]"
            style={{ alignItems: "center" }}
          >
            <Text className="text-[#FFFFFF] font-[HY65]">
              {hsrUUID} · {LOCALES[appLangauge][hsrServerChosen]}
            </Text>
          </View>
        </View>
        {/* 等級 */}
        <View style={{ flexDirection: "row", gap: 16, alignItems: "center" }}>
          <View className="w-[1px] h-6 bg-[#F3F9FF40]"></View>
          <InfoItem title={"開拓等級"} value={hsrInGameInfo?.player?.level} />
          <InfoItem
            title={"均衡等级"}
            value={hsrInGameInfo?.player?.world_level}
          />
        </View>
        {/* 擁有角色 */}
        <UserInfoCharacters />
        {/* 其他資訊 */}
        <View
          className="w-full px-3"
          style={{ flexDirection: "row", justifyContent: "space-between" }}
        >
          <InfoItem title={"活跃天数"} value={hsrFullData?.stats.active_days} />
          <InfoItem title={"达成成就"} value={hsrInGameInfo?.player?.level} />
          <InfoItem title={"战利品"} value={hsrFullData?.stats?.chest_num} />
          <InfoItem title={"忘却之庭"} value={`${moc?.battle_num}/12`} />
        </View>
        {/*  */}
        <View className="absolute bottom-6">
          <Text className="text-text text-[12px] font-[HY65]">
            由 Stargazer 制作
          </Text>
        </View>
      </View>
    </View>
  );
}

const InfoItem = ({ title, value }: { title: string; value: string }) => (
  <View style={{ alignItems: "center" }}>
    <Text className="text-text text-[24px] font-[HY65]">{value}</Text>
    <Text className="text-text text-[12px] font-[HY65]">{title}</Text>
  </View>
);

const Avatar = ({ image, onPress }: { image: string; onPress: () => void }) => (
  <TouchableOpacity className="z-50" onPress={onPress} activeOpacity={0.35}>
    <Image
      source={{
        uri: image,
      }}
      className="w-[73px] h-[73px] rounded-full"
      style={{
        backgroundColor: "rgba(144, 124, 84, 0.4)",
      }}
    />
  </TouchableOpacity>
);

const ShareBtn = () => (
  <TouchableOpacity onPress={() => {}}>
    <Image
      style={{ width: 40, height: 40 }}
      source={require("../../../../assets/icons/Share.svg")}
    />
  </TouchableOpacity>
);
