import { View, Text, Dimensions, ActivityIndicator } from "react-native";
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
import UserAvatar from "../../global/UserAvatar/UserAvatar";
import AvatarIcon from "../../../../assets/images/images_map/avatarIcon";
import getServerFromUUID from "../../../utils/hoyolab/servers/getServerFromUUID";
import { animated, useSpring } from "@react-spring/native";
import UUIDBox from "../../global/UUIDBox/UUIDBox";

type Props = {
  uuid: string;
};

export default function UserInfo(props: Props) {
  const { language: appLangauge } = useAppLanguage();

  const hsrUUID = useHsrUUID();
  const isOwner = props.uuid === hsrUUID;

  const { data: hsrFullData } = useHsrFullData();
  const { data: hsrInGameInfo } = useHsrInGameInfo(props.uuid);
  const { data: moc } = useMemoryOfChaos();
  const playerAvatar =
    // @ts-ignore
    AvatarIcon[hsrInGameInfo?.player?.avatar?.icon?.match(/\d+/g).join("")];

  const animation = useSpring({ from: { opacity: 0 }, to: { opacity: 1 } });

  return (
    <View className="z-30">
      <Header2 rightBtn={isOwner ? <ShareBtn /> : null} />
      {hsrInGameInfo ? (
        <AnimatedView
          className="mt-12 px-4"
          style={[
            {
              alignItems: "center",
              gap: 18,
              height: Dimensions.get("window").height,
            },
            animation,
          ]}
        >
          <View style={{ alignItems: "center", gap: 6 }}>
            {/* 頭像 */}
            <UserAvatar
              image={playerAvatar}
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
            <UUIDBox uuid={props.uuid} />
          </View>
          {/* 等級 */}
          <View style={{ flexDirection: "row", gap: 16, alignItems: "center" }}>
            <InfoItem title={"開拓等級"} value={hsrInGameInfo?.player?.level} />
            <View className="w-[1px] h-6 bg-[#F3F9FF40]"></View>
            <InfoItem
              title={"均衡等级"}
              value={hsrInGameInfo?.player?.world_level}
            />
          </View>
          {/* 擁有角色 */}
          <UserInfoCharacters uuid={props.uuid} />
          {/* 其他資訊 */}
          {isOwner && (
            <View
              className="w-full px-3"
              style={{ flexDirection: "row", justifyContent: "space-between" }}
            >
              <InfoItem
                title={"活躍天數"}
                value={hsrFullData?.stats.active_days}
              />
              <InfoItem
                title={"達成成就"}
                value={hsrInGameInfo?.player?.space_info?.achievement_count}
              />
              <InfoItem
                title={"戰利品"}
                value={hsrFullData?.stats?.chest_num}
              />
              <InfoItem title={"忘卻之庭"} value={`${moc?.battle_num}/12`} />
            </View>
          )}
          {/* 由 Stargazer 製作 */}
          <View className="absolute bottom-6">
            <Text className="text-text text-[12px] font-[HY65]">
              由 Stargazer 製作
            </Text>
          </View>
        </AnimatedView>
      ) : (
        <ActivityIndicator />
      )}
    </View>
  );
}

const InfoItem = ({ title, value }: { title: string; value: string }) => (
  <View style={{ alignItems: "center" }}>
    <Text className="text-text text-[24px] font-[HY65]">{value}</Text>
    <Text className="text-text text-[12px] font-[HY65]">{title}</Text>
  </View>
);

const ShareBtn = () => (
  <TouchableOpacity onPress={() => {}}>
    <Image
      style={{ width: 40, height: 40 }}
      source={require("../../../../assets/icons/Share.svg")}
    />
  </TouchableOpacity>
);

const AnimatedView = animated(View);
