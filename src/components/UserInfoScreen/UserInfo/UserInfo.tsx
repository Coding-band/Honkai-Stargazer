import { View, Text, Dimensions, ScrollView } from "react-native";
import React from "react";
import Header2 from "../../global/Header2/Header2";
import { TouchableOpacity } from "react-native";
import { Image } from "expo-image";
import useHsrInGameInfo from "../../../hooks/mihomo/useHsrInGameInfo";
import useHsrUUID from "../../../hooks/hoyolab/useHsrUUID";
import UserInfoCharacters from "./UserInfoCharacters/UserInfoCharacters";
import Toast from "../../../utils/toast/Toast";
import UserAvatar from "../../global/UserAvatar/UserAvatar";
import AvatarIcon from "../../../../assets/images/images_map/avatarIcon";
import { animated } from "@react-spring/native";
import UUIDBox from "../../global/UUIDBox/UUIDBox";
import Loading from "../../global/Loading/Loading";
import ProducedByStargazer from "../../global/ProducedByStargazer/ProducedByStargazer";
import NoPublicData from "./NoPublicData/NoPublicData";
import useUserByUUID from "../../../firebase/hooks/User/useUserByUUID";
import useUserMocByUUID from "../../../firebase/hooks/UserMoc/useUserMocByUUID";

type Props = {
  uuid: string;
};

export default function UserInfo(props: Props) {
  const profileUUID = props.uuid;
  const hsrUUID = useHsrUUID();

  const isOwner = profileUUID === hsrUUID;

  // 資料來自崩鐵
  const { data: hsrInGameInfo } = useHsrInGameInfo(profileUUID);
  // 資料來自 firebase 資料庫
  const { data: userData } = useUserByUUID(profileUUID);
  const { data: userMocData } = useUserMocByUUID(profileUUID);

  const isShowInfo = userData?.show_info;

  const playerAvatar =
    // @ts-ignore
    AvatarIcon[hsrInGameInfo?.player?.avatar?.icon?.match(/\d+/g).join("")];
  const latestUserMocData = userMocData?.[Object.keys(userMocData).pop() || ""];

  return (
    <View className="z-30">
      <Header2 rightBtn={isOwner ? <ShareBtn /> : null} />
      <ScrollView style={{ height: Dimensions.get("screen").height }}>
        {hsrInGameInfo && userData && userMocData ? (
          <AnimatedView
            className="mt-12 px-4"
            style={{
              alignItems: "center",
              gap: 18,
            }}
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
              <Text className="text-text text-[24px] font-[HY65] leading-8">
                {hsrInGameInfo?.player?.nickname}
              </Text>
              {/* UUID & 伺服器 */}
              <UUIDBox uuid={props.uuid} />
            </View>
            {/* 等級 */}
            <View
              style={{ flexDirection: "row", gap: 16, alignItems: "center" }}
            >
              <InfoItem
                title={"開拓等級"}
                value={hsrInGameInfo?.player?.level}
              />
              <View className="w-[1px] h-6 bg-[#F3F9FF40]"></View>
              <InfoItem
                title={"均衡等级"}
                value={hsrInGameInfo?.player?.world_level}
              />
            </View>
            <View className="w-full" style={{ alignItems: "center", gap: 8 }}>
              {/* 擁有角色 */}
              <UserInfoCharacters uuid={props.uuid} />
              {/* 其他資訊 */}
              {isOwner || isShowInfo ? (
                <View
                  className="w-full px-3"
                  style={{
                    flexDirection: "row",
                    justifyContent: "space-between",
                  }}
                >
                  <InfoItem
                    title={"活躍天數"}
                    value={userData?.active_days || 0}
                  />
                  <InfoItem
                    title={"達成成就"}
                    value={hsrInGameInfo?.player?.space_info?.achievement_count}
                  />
                  <InfoItem title={"戰利品"} value={userData?.chest_num} />
                  <InfoItem
                    title={"忘卻之庭"}
                    value={`${latestUserMocData?.max_floor || 0}/12`}
                  />
                </View>
              ) : (
                <NoPublicData />
              )}
            </View>
            {/* 由 Stargazer 製作 */}
            <View className="mb-16 mt-0">
              {isOwner && <ProducedByStargazer />}
            </View>
          </AnimatedView>
        ) : (
          <Loading />
        )}
      </ScrollView>
    </View>
  );
}

const InfoItem = ({
  title,
  value,
}: {
  title: string;
  value: string | number | undefined;
}) => (
  <View style={{ alignItems: "center" }}>
    <Text className="text-text text-[24px] font-[HY65]">{value}</Text>
    <Text className="text-text text-[12px] font-[HY65] leading-5">{title}</Text>
  </View>
);

const ShareBtn = () => (
  <TouchableOpacity
    onPress={() => {
      Toast.StillDevelopingToast();
    }}
  >
    <Image
      style={{ width: 40, height: 40 }}
      source={require("../../../../assets/icons/Share.svg")}
    />
  </TouchableOpacity>
);

const AnimatedView = animated(View);
