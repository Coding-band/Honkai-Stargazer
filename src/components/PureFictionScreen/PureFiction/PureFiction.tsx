import { View, Text } from "react-native";
import React from "react";
import PureFictionHeader from "../PureFictionHeader/PureFictionHeader";
import Animated, { useAnimatedRef, useScrollViewOffset } from "react-native-reanimated";

export default function PureFiction() {

    const aref = useAnimatedRef<Animated.ScrollView>();
  const scrollHandler = useScrollViewOffset(aref);

  return (
    <View>
      <PureFictionHeader scrollHandler={scrollHandler} />
      <Animated.ScrollView
        ref={aref}
        className="z-30 pt-[110px] pb-0"
        contentContainerStyle={{ alignItems: "center", gap: 14 }}
      ></Animated.ScrollView>
    </View>
  );
}
