import { Image } from "expo-image";
import React, { useEffect, useState } from "react";
import { Pressable, View } from "react-native";
import useHsrFullData from "../../../../hooks/hoyolab/useHsrFullData";

export default function PlayerAvator() {
  const playerFullData = useHsrFullData();
  const avator = playerFullData?.data?.cur_head_icon_url;

  const [rotate, setRotate] = useState(0);
  const [flag, setFlag] = useState(false);

  useEffect(() => {
    setTimeout(() => {
      if (flag) {
        setRotate(rotate + 10);
      }
    }, 10);
  }, [rotate, flag]);

  return (
    <Pressable
      onPressIn={() => {
        setFlag(true);
      }}
      onPressOut={() => {
        setFlag(false);
      }}
    >
      <View
        className="w-[73px] h-[73px] rounded-full mr-2 bg-white"
        style={{ transform: [{ rotate: rotate + "deg" }] }}
      >
        <Image
          source={{
            uri:
              avator ||
              "https://act.hoyoverse.com/darkmatter/hkrpg/prod_gf_cn/item_icon_763646/c86d9128cff46891e47275f3b48b5eeb.png?x-oss-process=image%2Fformat%2Cwebp",
          }}
          className="w-[73px] h-[73px] rounded-full"
          style={{
            backgroundColor: "rgba(144, 124, 84, 0.4)",
          }}
        />
      </View>
    </Pressable>
  );
}
