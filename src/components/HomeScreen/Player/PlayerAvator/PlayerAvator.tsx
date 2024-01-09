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

export default function PlayerAvator() {
  const navigation = useNavigation();
  const { language } = useAppLanguage();

  const hsrUUID = useHsrUUID();
  const playerFullData = useHsrFullData();
  const avatar = playerFullData?.data?.cur_head_icon_url;

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
      <UserAvatar
        image={
          avatar ||
          "https://act.hoyoverse.com/darkmatter/hkrpg/prod_gf_cn/item_icon_763646/c86d9128cff46891e47275f3b48b5eeb.png?x-oss-process=image%2Fformat%2Cwebp"
        }
        onPress={handleNavigatUserInfoPage}
      />
    </View>
  );
}
