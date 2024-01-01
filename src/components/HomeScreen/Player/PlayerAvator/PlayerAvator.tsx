import { Image } from "expo-image";
import React, { useEffect, useState } from "react";
import { Pressable, View } from "react-native";
import useHsrFullData from "../../../../hooks/hoyolab/useHsrFullData";
import { animated, useSpring } from "@react-spring/native";

export default function PlayerAvator() {
  const playerFullData = useHsrFullData();
  const avator = playerFullData?.data?.cur_head_icon_url;

  const [scale, setScale] = useState(1);
  const [flag, setFlag] = useState(false);

  const avatorAnimation = useSpring({ scale, config: { tension: 220 } });
  useEffect(() => {
    setTimeout(() => {
      if (flag) {
        setScale(scale + 0.1);
      } else {
        setScale(1);
      }
    });
  }, [scale, flag]);

  return (
    <Pressable
      className="z-50"
      onPressIn={() => {
        setFlag(true);
      }}
      onPressOut={() => {
        setFlag(false);
      }}
    >
      <AnimatedView
        className="w-[73px] h-[73px] rounded-full mr-2 bg-white"
        style={{ transform: [avatorAnimation] }}
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
      </AnimatedView>
    </Pressable>
  );
}

const AnimatedView = animated(View);
