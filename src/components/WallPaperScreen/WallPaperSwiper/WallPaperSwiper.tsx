import { View, Text, Dimensions, StyleSheet } from "react-native";
import React, { memo } from "react";
import Swiper from "react-native-swiper";
import { Image } from "expo-image";

const { width } = Dimensions.get("window");

type Props = {
  wallPapers: any[];
  index: number;
  onIndexChange: (i: number) => void;
};

export default memo(function WallPaperSwiper(props: Props) {
  return (
    <View className="w-full h-[486px]" style={{ alignItems: "center" }}>
      <Swiper
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
        {props.wallPapers.map((w) => (
          <View key={w.uri} style={styles.slideContainer}>
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
