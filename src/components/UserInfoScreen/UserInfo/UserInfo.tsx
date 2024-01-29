import { View, Text, Dimensions, ScrollView } from "react-native";
import React, { useState } from "react";
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
import TopTabs from "./TopTabs/TopTabs";
import Comment from "./Comment/Comment";
import auth from "@react-native-firebase/auth";
import { formatTimeDurationSimple } from "../../../utils/date/formatTime";
import useUserComments from "../../../firebase/hooks/UserComments/useUserComments";
import useFirebaseUidByUUID from "../../../firebase/hooks/FirebaseUid/useFirebaseUidByUUID";
import Animated, {
  useAnimatedRef,
  useAnimatedStyle,
  useScrollViewOffset,
  withSpring,
} from "react-native-reanimated";
import NoComment from "./NoComment/NoComment";
import useAppLanguage from "../../../language/AppLanguage/useAppLanguage";
import { LOCALES } from "../../../../locales";
// import PagerView from "react-native-pager-view";

type Props = {
  uuid: string;
};

export default function UserInfo(props: Props) {
  const profileUUID = props.uuid;
  const hsrUUID = useHsrUUID();
  const { language } = useAppLanguage();

  // 資料來自崩鐵
  const { data: hsrInGameInfo } = useHsrInGameInfo(profileUUID) as any;

  // 資料來自 firebase 資料庫
  const { data: userData } = useUserByUUID(profileUUID);
  const { data: userMocData } = useUserMocByUUID(profileUUID);

  const isOwner = profileUUID === hsrUUID;
  const isShowInfo = userData?.show_info;

  const playerAvatar =
    // @ts-ignore
    AvatarIcon[hsrInGameInfo?.player?.avatar?.icon?.match(/\d+/g)?.join("")];

  const [activeTab, setActiveTab] = useState("game-data");
  const isGameDataPage = activeTab === "game-data";

  //UserInfoLastLoginAt
  const lastLoginDuration =
    Date.now() / 1000 - Number(userData?.last_login?.seconds);
  const timeString =
    lastLoginDuration < 300
      ? LOCALES[language].StatusNow
      : LOCALES[language].UserInfoLastLoginAt.replace(
          "${1}",
          formatTimeDurationSimple(lastLoginDuration, language)
        );

  // 用戶留言
  const parts = timeString.split(/([A-Za-z\u4e00-\u9fa5]+)/);
  const firebaseUID = useFirebaseUidByUUID(profileUUID);
  const { data: userComments } = useUserComments(firebaseUID || "");

  const aref = useAnimatedRef<Animated.ScrollView>();
  const scrollHandler = useScrollViewOffset(aref);

  const headerAnimatedStyles = useAnimatedStyle(() => {
    if (scrollHandler.value > 0) {
      return {
        opacity: withSpring(0),
      };
    } else {
      return {
        opacity: withSpring(1),
      };
    }
  });

  return (
    <View className="z-30">
      <Header2 rightBtn={isOwner ? <ShareBtn /> : null}>
        <Animated.View style={headerAnimatedStyles}>
          <TopTabs
            tabs={[
              { name: LOCALES[language].UserInfoGameData, value: "game-data" },
              { name: LOCALES[language].UserInfoStatus, value: "more-info" },
            ]}
            active={activeTab}
            onChange={(a) => {
              setActiveTab(a);
            }}
          />
        </Animated.View>
      </Header2>
      <Animated.ScrollView
        ref={aref}
        style={{ height: Dimensions.get("screen").height }}
      >
        {hsrInGameInfo ? (
          <AnimatedView
            className="mt-28"
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
              style={{
                flexDirection: "row",
                alignItems: "center",
              }}
            >
              <InfoItem
                title={
                  isGameDataPage
                    ? LOCALES[language].UserInfoGamePlayerLevel
                    : LOCALES[language].UserInfoCountComments
                }
                value={
                  isGameDataPage
                    ? hsrInGameInfo?.player?.level
                    : userComments?.comments_num || 0
                }
              />
              <View className="w-[1px] h-6 bg-[#F3F9FF40]"></View>
              <InfoItem
                title={
                  isGameDataPage
                    ? LOCALES[language].UserInfoGameWorldLevel
                    : LOCALES[language].UserInfoLastOnlineTime
                }
                value={
                  isGameDataPage ? (
                    hsrInGameInfo?.player?.world_level
                  ) : (
                    <Text>
                      {userData?.last_login ? (
                        parts.map((part: any, index: number) =>
                          // 检查每个部分是否为纯数字
                          /^\d+$/.test(part) ? (
                            <Text key={index}>{part}</Text>
                          ) : (
                            <Text className="text-[12px] leading-5" key={index}>
                              {part}
                            </Text>
                          )
                        )
                      ) : (
                        <Text className="text-[12px] leading-4">
                          {LOCALES[language].NoOnlineData}
                        </Text>
                      )}
                    </Text>
                  )
                }
              />
              {isGameDataPage && (
                <>
                  <View className="w-[1px] h-6 bg-[#F3F9FF40]"></View>
                  <InfoItem
                    title={LOCALES[language].UserInfoOwnedCharacters}
                    value={hsrInGameInfo?.player?.space_info?.avatar_count}
                  />
                </>
              )}
            </View>
            {/* 遊戲數據頁顯示 */}
            <View
              className="w-full"
              style={{
                position: isGameDataPage ? "relative" : "absolute",
                left: isGameDataPage ? 0 : -10000,
                alignItems: "center",
                gap: 8,
              }}
            >
              <UserInfoCharacters uuid={props.uuid} />

              {isOwner || isShowInfo ? (
                <View
                  className="w-full px-4"
                  style={{
                    flexDirection: "row",
                    justifyContent: "space-around",
                  }}
                >
                  <InfoItem
                    title={LOCALES[language].UserInfoGameActiveDays}
                    value={userData?.active_days || 0}
                  />
                  <InfoItem
                    title={LOCALES[language].UserInfoGameAchievements}
                    value={hsrInGameInfo?.player?.space_info?.achievement_count}
                  />
                  <InfoItem
                    title={LOCALES[language].UserInfoGameOpenedChests}
                    value={userData?.chest_num}
                  />
                  <InfoItem
                    title={LOCALES[language].UserInfoGameForgottenHall}
                    value={`${userMocData?.max_floor || 0}/12`}
                  />
                </View>
              ) : (
                <NoPublicData />
              )}
            </View>
            {/* 其他頁顯示 */}
            <View
              className="w-full mb-12"
              style={{
                position: !isGameDataPage ? "relative" : "absolute",
                left: !isGameDataPage ? 0 : -10000,
                alignItems: "center",
                gap: 8,
              }}
            >
              {userComments?.comments ? (
                userComments?.comments
                  ?.slice()
                  ?.reverse()
                  ?.map((comment: any) => (
                    <Comment {...comment} key={comment.id} />
                  ))
              ) : (
                <NoComment />
              )}
            </View>
            {/* 由 Stargazer 製作 */}
            {isGameDataPage && (
              <View className="mb-16 mt-0">
                <ProducedByStargazer />
              </View>
            )}
          </AnimatedView>
        ) : (
          <Loading />
        )}
      </Animated.ScrollView>
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
  <View style={{ alignItems: "center" }} className="w-20">
    <Text className="text-text text-[24px] font-[HY65] leading-5">{value}</Text>
    <Text className="text-text text-[12px] font-[HY65] leading-5">{title}</Text>
  </View>
);

const ShareBtn = () => (
  <TouchableOpacity
    className="translate-x-[-2px]"
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
