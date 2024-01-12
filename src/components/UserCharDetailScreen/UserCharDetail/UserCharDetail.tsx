import {
  View,
  Text,
  TouchableOpacity,
  Dimensions,
  ScrollView,
} from "react-native";
import React, { createContext } from "react";
import Header2 from "../../global/Header2/Header2";
import useHsrUUID from "../../../hooks/hoyolab/useHsrUUID";
import useAppLanguage from "../../../language/AppLanguage/useAppLanguage";
import { Image, ImageBackground } from "expo-image";
import UUIDBox from "../../global/UUIDBox/UUIDBox";
import useTextLanguage from "../../../language/TextLanguage/useTextLanguage";
import useHsrInGameInfo from "../../../hooks/mihomo/useHsrInGameInfo";
import CharacterImage from "../../../../assets/images/images_map/chacracterImage";
import UserCharDetailStars from "./UserCharDetailStars/UserCharDetailStars";
import { globalStyles } from "../../../../styles/global";
import CombatType from "../../../../assets/images/images_map/combatType";
import Path from "../../../../assets/images/images_map/path";
import UserCharLevel from "./UserCharLevel/UserCharLevel";
import useProfileUUID from "../../../context/UserCharDetailData/hooks/useProfileUUID";
import useProfileCharJsonData from "../../../context/UserCharDetailData/hooks/useProfileCharJsonData";
import useProfileCharFullData from "../../../context/UserCharDetailData/hooks/useProfileCharFullData";
import useProfileCharId from "../../../context/UserCharDetailData/hooks/useProfileCharId";
import useProfileHsrInGameInfo from "../../../context/UserCharDetailData/hooks/useProfileHsrInGameInfo";
import UserCharCombatTypeAndPath from "./UserCharCombatTypeAndPath/UserCharCombatTypeAndPath";
import UserCharSkills from "./UserCharSkills/UserCharSkills";
import UserCharAttribute from "./UserCharAttribute/UserCharAttribute";
import UserCharLightcone from "./UserCharLightcone/UserCharLightcone";
import UserCharRelics from "./UserCharRelics/UserCharRelics";
import useDelayLoad from "../../../hooks/useDelayLoad";
import Toast from "../../../utils/toast/Toast";
import Loading from "../../global/Loading/Loading";
import Animated, {
  useAnimatedRef,
  useAnimatedStyle,
  useScrollViewOffset,
  withSpring,
} from "react-native-reanimated";
import ProducedByStargazer from "../../global/ProducedByStargazer/ProducedByStargazer";

export default function UserCharDetail() {
  const hsrUUID = useHsrUUID();
  const profileUUID = useProfileUUID();
  const { inGameInfo } = useProfileHsrInGameInfo();
  const charJsonData = useProfileCharJsonData();
  const charFullData = useProfileCharFullData();
  const charId = useProfileCharId();

  const isOwner = profileUUID === hsrUUID;

  const aref = useAnimatedRef<Animated.ScrollView>();
  const scrollHandler = useScrollViewOffset(aref);

  const contentAnimatedStyles = useAnimatedStyle(() => {
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

  if (!inGameInfo) return <Loading />;
  return (
    <View className="z-30">
      <Header2 rightBtn={isOwner ? <ShareBtn /> : null} />
      <View className="mt-12 z-40" style={{ alignItems: "center", gap: 4 }}>
        {/* 用戶名 */}
        <Text
          className="text-[#FFFFFF] font-[HY65] text-[16px]"
          style={globalStyles.textShadow}
        >
          {inGameInfo?.player?.nickname}
        </Text>
        {/* UUID & 伺服器 */}
        <UUIDBox uuid={profileUUID} />
      </View>
      <Animated.Image
        style={contentAnimatedStyles}
        source={CharacterImage[charId].fade}
        className="absolute w-full h-[580px]"
      />
      <Animated.ScrollView
        // @ts-ignore
        ref={aref}
        style={{ height: Dimensions.get("screen").height - 110 }}
      >
        <View
          style={{
            alignItems: "center",
            gap: 18,
          }}
        >
          <View style={{ alignItems: "center", marginTop: 228 }}>
            <View style={{ alignItems: "center", gap: 4 }}>
              {/* 角色名 */}
              <Text
                className="text-[#FFFFFF] font-[HY65] text-[32px]"
                style={globalStyles.textShadow}
              >
                {charFullData.name}
              </Text>
              {/* 星星數 */}
              <UserCharDetailStars count={charJsonData.rare} />
              {/* 等級，星魂 */}
              <UserCharLevel />
              {/* 屬性，命途 */}
              <UserCharCombatTypeAndPath />
              <UserCharSkills />
            </View>
          </View>
          <UserCharAttribute />
          <UserCharLightcone />
          <UserCharRelics />
          {/* 由 Stargazer 製作 */}
          <View className="mb-16 mt-0">
            {isOwner && <ProducedByStargazer />}
          </View>
        </View>
      </Animated.ScrollView>
    </View>
  );
}

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
