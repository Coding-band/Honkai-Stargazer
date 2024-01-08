import { Image } from "expo-image";
import React, { useEffect, useState } from "react";
import { Pressable, View } from "react-native";
import useHsrFullData from "../../../../hooks/hoyolab/useHsrFullData";
import { animated, useSpring } from "@react-spring/native";
import { useNavigation } from "@react-navigation/native";
import { SCREENS } from "../../../../constant/screens";
import useHsrUUID from "../../../../hooks/hoyolab/useHsrUUID";

export default function PlayerAvator() {
  const navigation = useNavigation();

  const hsrUUID = useHsrUUID();
  const playerFullData = useHsrFullData();
  const avatar = playerFullData?.data?.cur_head_icon_url;

  const handleNavigatUserInfoPage = () => {
    // @ts-ignore
    navigation.push(SCREENS.UserInfoPage.id, { uuid: hsrUUID });
  };

  return (
    <Pressable className="z-50" onPress={handleNavigatUserInfoPage}>
      <AnimatedView className="w-[73px] h-[73px] rounded-full mr-2 bg-white">
        <Image
          source={{
            uri:
              avatar ||
              "https://act.hoyoverse.com/darkmatter/hkrpg/prod_gf_cn/item_icon_763646/c86d9128cff46891e47275f3b48b5eeb.png?x-oss-process=image%2Fformat%2Cwebp",
          }}
          className="w-[73px] h-[73px] rounded-full"
          style={{
            backgroundColor: "rgba(144, 124, 84, 0.4)",
          }}
        />
      </AnimatedView>
    </Pressable>
  );
}

const AnimatedView = animated(View);
