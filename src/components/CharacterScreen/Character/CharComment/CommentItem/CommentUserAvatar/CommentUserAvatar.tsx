import { View, Text, TouchableWithoutFeedback, TouchableOpacity } from "react-native";
import React from "react";
import { Image } from "expo-image";
import { ExpoImage } from "../../../../../../types/image";
import Users from "../../../../../../firebase/models/Users";
import { useNavigation } from "@react-navigation/native";
import { SCREENS } from "../../../../../../constant/screens";

type Props = {
  user: Users | undefined;
};

export default function CommentUserAvatar(props: Props) {
  const navigation = useNavigation();

  return (
    <TouchableOpacity activeOpacity={0.35}
      onPress={() => {
        // @ts-ignore
        navigation.navigate(SCREENS.UserInfoPage.id, {
          uuid: props.user?.uuid,
        });
      }}
    >
      <Image
        className="w-9 h-9"
        source={
          props.user?.avatar_url ||
          "https://act.hoyoverse.com/darkmatter/hkrpg/prod_gf_cn/item_icon_763646/c86d9128cff46891e47275f3b48b5eeb.png?x-oss-process=image%2Fformat%2Cwebp"
        }
      />
    </TouchableOpacity>
  );
}
