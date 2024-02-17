import { View, Text, Dimensions, StyleSheet } from "react-native";
import React, { memo, useEffect, useLayoutEffect, useRef } from "react";
import Swiper from "react-native-swiper";
import { Image } from "expo-image";
import { Lock } from "phosphor-react-native";
import useHsrCharList from "../../../hooks/hoyolab/useHsrCharList";
import { includes } from "lodash";
import useAppLanguage from "../../../language/AppLanguage/useAppLanguage";
import { LOCALES } from "../../../../locales";
import useIsAdmin from "../../../firebase/hooks/Role/useIsAdmin";
import useIsTester from "../../../firebase/hooks/Role/useIsTester";
import { dynamicHeightWallpaperSwiper } from "../../../constant/ui";

const { width } = Dimensions.get("window");

type Props = {
  wallPapers: any[];
  index: number;
  onIndexChange: (i: number) => void;
};

export default memo(function WallPaperSwiper(props: Props) {
  const { language } = useAppLanguage();

  const playerCharIdList = useHsrCharList().data?.map((char: any) => char.id);
  const isAdmin = useIsAdmin();
  const isTester = useIsTester();

  /**
   * this solved "onIndexChanged not called, wrong screen rendered"
   */
  // @ts-ignore
  const swiperRef = useRef<React.Element<Swiper>>();
  // useLayoutEffect(() => {
  //   swiperRef.current?.scrollBy(0);
  // }, []);

  return (
    <View
      style={{
        alignItems: "center",
        width: Dimensions.get("screen").width,
        height: Dimensions.get("screen").height - dynamicHeightWallpaperSwiper,
      }}
    >
      <Swiper
        key={props.wallPapers.length}
        ref={swiperRef}
        index={props.index}
        onIndexChanged={props.onIndexChange}
        loadMinimal={false}
        horizontal={true}
        showsPagination={false}
        width={width - 120}
        removeClippedSubviews={false}
        // @ts-ignore
        scrollViewStyle={styles.wrapper}
      >
        {props.wallPapers.map((w, k) => {
          // 判斷用戶是否有該角色
          const playerHasCharacter =
            props.wallPapers[k].id.toString().length === 6 ||
            props.wallPapers[k].id.toString().length === 3 ||
            includes(
              playerCharIdList,
              Number(props.wallPapers[k].id.toString().split("-")[0])
            );

          return (
            <View key={k} style={styles.slideContainer}>
              <Image
                contentFit={"cover"}
                style={[styles.slide]}
                source={w.url}
              />
              {isAdmin || isTester || playerHasCharacter || (
                <>
                  <View className="absolute w-full h-full opacity-60 bg-[#000]" />
                  <View
                    className="absolute -translate-y-4 items-center"
                    style={{ gap: 8 }}
                  >
                    <Lock size={32} color="white" />
                    <Text className="text-text font-[HY65] text-[14px] leading-4">
                      {LOCALES[language].GetCharAndUnLock}
                    </Text>
                  </View>
                </>
              )}
            </View>
          );
        })}
      </Swiper>
    </View>
  );
});

const styles = StyleSheet.create({
  wrapper: {
    overflow: "visible",
  },
  slideContainer: {
    flex: 1,
    justifyContent: "center",
    alignItems: "center",
  },
  slide: {
    position: "relative",
    width: width - 120 - 30,
    height: "100%",
  },
  text: {
    color: "#000",
    fontSize: 30,
    fontWeight: "bold",
  },
});
