import {
  View,
  Text,
  TouchableWithoutFeedback,
  TouchableOpacity,
} from "react-native";
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
    <TouchableOpacity
      activeOpacity={0.35}
      onPress={() => {
        // @ts-ignore
        navigation.navigate(SCREENS.UserInfoPage.id, {
          uuid: props.user?.uuid,
        });
      }}
    >
      <Image className="w-9 h-9" source={props.user?.avatar_url} />
    </TouchableOpacity>
  );
}
