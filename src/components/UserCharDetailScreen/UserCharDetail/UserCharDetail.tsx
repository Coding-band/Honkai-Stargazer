import { View, Text, TouchableOpacity, Dimensions } from "react-native";
import React, { createContext } from "react";
import Header2 from "../../global/Header2/Header2";
import useHsrUUID from "../../../hooks/hoyolab/useHsrUUID";
import useAppLanguage from "../../../language/AppLanguage/useAppLanguage";
import { Image, ImageBackground } from "expo-image";
import UUIDBox from "../../global/UUIDBox/UUIDBox";
import { CharacterName } from "../../../types/character";
import {
  getCharFullData,
  getCharJsonData,
} from "../../../utils/dataMap/getDataFromMap";
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

export default function UserCharDetail() {
  const { language: appLanguage } = useAppLanguage();
  const { language: textLanguage } = useTextLanguage();

  const hsrUUID = useHsrUUID();
  const profileUUID = useProfileUUID();

  const isOwner = profileUUID === hsrUUID;

  const { inGameInfo } = useProfileHsrInGameInfo();
  const charJsonData = useProfileCharJsonData();
  const charFullData = useProfileCharFullData();
  const charId = useProfileCharId();

  return (
    <View className="z-30">
      <Header2 rightBtn={isOwner ? <ShareBtn /> : null} />

      <View
        style={[
          {
            alignItems: "center",
            gap: 18,
            height: Dimensions.get("window").height,
          },
        ]}
      >
        <ImageBackground
          source={CharacterImage[charId].fade}
          className="w-full"
        >
          <View className="mt-12" style={{ alignItems: "center", gap: 228 }}>
            <View style={{ alignItems: "center", gap: 4 }}>
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
        </ImageBackground>
        <UserCharAttribute />
        <UserCharLightcone />
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
