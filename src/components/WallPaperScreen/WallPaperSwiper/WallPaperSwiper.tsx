import { View, Text, Dimensions, StyleSheet } from "react-native";
import React, { memo, useEffect, useRef } from "react";
import Swiper from "react-native-swiper";
import { Image } from "expo-image";

const { width } = Dimensions.get("window");

type Props = {
  wallPapers: any[];
  index: number;
  onIndexChange: (i: number) => void;
};

export default memo(function WallPaperSwiper(props: Props) {
  /**
   * this solved "onIndexChanged not called, wrong screen rendered"
   */
  // @ts-ignore
  const swiperRef = useRef<React.Element<Swiper>>();
  useEffect(() => {
    swiperRef.current?.scrollBy(0);
  }, []);

  return (
    <View className="w-full h-[486px]" style={{ alignItems: "center" }}>
      <Swiper
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
        {props.wallPapers.map((w, k) => (
          <View key={k} style={styles.slideContainer}>
            <Image style={styles.slide} source={{ uri: w.url }} />
          </View>
        ))}
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
