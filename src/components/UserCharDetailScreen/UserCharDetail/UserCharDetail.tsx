import {
  View,
  Text,
  TouchableOpacity,
  Dimensions,
  ScrollView,
  Share,
} from "react-native";
import React, { useRef, useState } from "react";
import Header2 from "../../global/Header2/Header2";
import useHsrUUID from "../../../hooks/hoyolab/useHsrUUID";
import { Image } from "expo-image";
import UUIDBox from "../../global/UUIDBox/UUIDBox";
import CharacterImage from "../../../../assets/images/images_map/chacracterImage";
import UserCharDetailStars from "./UserCharDetailStars/UserCharDetailStars";
import { globalStyles } from "../../../../styles/global";
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
import Toast from "../../../utils/toast/Toast";
import Loading from "../../global/Loading/Loading";
import Animated, {
  useAnimatedRef,
  useAnimatedStyle,
  useScrollViewOffset,
  withSpring,
} from "react-native-reanimated";
import ProducedByStargazer from "../../global/ProducedByStargazer/ProducedByStargazer";
import UserCharScore from "./UserCharScore/UserCharScore";
import UserCharStats from "./UserCharStats/UserCharStats";
import useAppLanguage from "../../../language/AppLanguage/useAppLanguage";
import ViewShot, { captureRef } from "react-native-view-shot";
import WallPaper from "../../global/WallPaper/WallPaper";
import { LinearGradient } from "expo-linear-gradient";
import { GestureResponderEvent } from "react-native-modal";

type Share = {
  onPress?: (e: GestureResponderEvent) => void;
};
export default function UserCharDetail() {
  const charId = useProfileCharId();
  const hsrUUID = useHsrUUID();
  const profileUUID = useProfileUUID();
  const { inGameInfo } = useProfileHsrInGameInfo() as any;
  const charJsonData = useProfileCharJsonData();
  const charFullData = useProfileCharFullData();
  const { language } = useAppLanguage();

  const isOwner = profileUUID === hsrUUID;

  const aref = useAnimatedRef<Animated.ScrollView>();
  const scrollHandler = useScrollViewOffset(aref);

  //截圖
  const captureFullRef = useRef(null);
  const [isCapture, setCapture] = useState(false);
  const captureFull = () => {
    setCapture(true)
    setTimeout(() => {
      captureRef(captureFullRef).then(async (url : any) => {
        try{
          setCapture(false)
          await Share.share({url: url});
        }catch(error){
          Toast.FailToCopy(language)
        }
      })
    }, 500)
  }

  const contentAnimatedStyle = useAnimatedStyle(() => {
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

  const playerNameAnimatedStyle = useAnimatedStyle(() => {
    return { display: scrollHandler.value > 260 ? "none" : "flex" };
  });

  const playerName2AnimatedStyle = useAnimatedStyle(() => {
    return { display: scrollHandler.value < 260 ? "none" : "flex" };
  });

  return (
    <View className="z-30">
      <Header2 rightBtn={isOwner ? <ShareBtn onPress={captureFull} /> : null} />
        {
          !isCapture ? (
            <>
              <View className="mt-12 z-40" style={{ alignItems: "center", gap: 4 }}>
              {/* 用戶名 */}
              <Animated.Text
                className="text-[#FFFFFF] font-[HY65] text-[16px] leading-5"
                style={[globalStyles.textShadow, playerNameAnimatedStyle]}
              >
                {inGameInfo?.player?.nickname}
              </Animated.Text>
              <Animated.Text
                className="text-[#FFFFFF] font-[HY65] text-[16px] leading-5"
                style={[globalStyles.textShadow, playerName2AnimatedStyle]}
              >
                {inGameInfo?.player?.nickname} · {charFullData?.name}
              </Animated.Text>
              {/* UUID & 伺服器 */}
              <UUIDBox uuid={profileUUID} />
              </View>
              <Animated.Image
                style={contentAnimatedStyle}
                source={CharacterImage[charId]?.fade}
                className="absolute w-full h-[580px]"
              />
            </>
          ) : (<></>)
        }
        <Animated.ScrollView
          // @ts-ignore
          ref={aref}
          style={{ height: Dimensions.get("screen").height - (isCapture ? 12 : 110) }}
        >
        <ViewShot ref={captureFullRef} captureMode="mount">
        {isCapture ? (
          <>
            <WallPaper isBlur />
            <LinearGradient
              className="absolute w-full h-full"
              colors={["#00000080", "#00000020"]}
            />
            <LinearGradient
              className="w-full h-[600px] absolute bottom-0"
              colors={["#00000000", "#000000"]}
            />

            <View className="mt-12 z-40" style={{ alignItems: "center", gap: 4 }}>
              {/* 用戶名 */}
              <Animated.Text
                className="text-[#FFFFFF] font-[HY65] text-[16px] leading-5"
                style={[globalStyles.textShadow, playerNameAnimatedStyle]}
              >
                {inGameInfo?.player?.nickname}
              </Animated.Text>
              <Animated.Text
                className="text-[#FFFFFF] font-[HY65] text-[16px] leading-5"
                style={[globalStyles.textShadow, playerName2AnimatedStyle]}
              >
                {inGameInfo?.player?.nickname} · {charFullData?.name}
              </Animated.Text>
              {/* UUID & 伺服器 */}
              <UUIDBox uuid={profileUUID} />
            </View>

            <Animated.Image
          style={contentAnimatedStyle}
          source={CharacterImage[charId]?.fade}
          className="absolute w-full h-[580px]"
        />
          </>
        ) : (<></>)}
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
                  className="text-[#FFFFFF] font-[HY65] text-[32px] leading-10"
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
            <UserCharScore />
            <UserCharStats />
            {/* 由 Stargazer 製作 */}
            <View className="mb-16 mt-0">
              <ProducedByStargazer />
            </View>
          </View>
        </ViewShot>  
      </Animated.ScrollView>
    </View>
  );
}

const ShareBtn = React.memo((props : Share) => (
  <TouchableOpacity
    className="translate-x-[-2px]"
    onPress={props.onPress}
  >
    <Image
      style={{ width: 40, height: 40 }}
      source={require("../../../../assets/icons/Share.svg")}
    />
  </TouchableOpacity>
))