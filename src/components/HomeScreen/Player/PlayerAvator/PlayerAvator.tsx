import React from "react";
import { View } from "react-native";
import useHsrFullData from "../../../../hooks/hoyolab/useHsrFullData";
import { useNavigation } from "@react-navigation/native";
import { SCREENS } from "../../../../constant/screens";
import useHsrUUID from "../../../../hooks/hoyolab/useHsrUUID";
import UserAvatar from "../../../global/UserAvatar/UserAvatar";
import Toast from "../../../../utils/toast/Toast";
import { LOCALES } from "../../../../../locales";
import useAppLanguage from "../../../../language/AppLanguage/useAppLanguage";
import CharacterImage from "../../../../../assets/images/images_map/chacracterImage";
import useHsrInGameInfo from "../../../../hooks/mihomo/useHsrInGameInfo";
import AvatarIcon from "../../../../../assets/images/images_map/avatarIcon";

export default function PlayerAvator() {
  const navigation = useNavigation();
  const { language } = useAppLanguage();

  const hsrUUID = useHsrUUID();
  const playerFullData = useHsrFullData();

  // 資料來自崩鐵
  const { data: hsrInGameInfo } = useHsrInGameInfo(hsrUUID) as any;
  const avatar = AvatarIcon[hsrInGameInfo?.player?.avatar?.icon?.match(/\d+/g)?.join("")];

  const handleNavigatUserInfoPage = () => {
    if (hsrUUID) {
      // @ts-ignore
      navigation.push(SCREENS.UserInfoPage.id, { uuid: hsrUUID });
    } else {
      Toast(LOCALES[language].PleaseLogin);
    }
  };

  return (
    <View className="mr-2">
      <UserAvatar image={avatar} onPress={handleNavigatUserInfoPage} />
    </View>
  );
}
