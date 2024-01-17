import {
  View,
  Text,
  KeyboardAvoidingView,
  Platform,
  Pressable,
  Keyboard,
} from "react-native";
import React, { useEffect, useRef, useState } from "react";
import Animated, {
  useAnimatedStyle,
  useSharedValue,
  withSpring,
} from "react-native-reanimated";
import { Dimensions } from "react-native";
import { Gesture, GestureDetector } from "react-native-gesture-handler";
import { Image } from "expo-image";
import useCharComments from "../../../../../firebase/hooks/CharComments/useCharComments";
import useCharId from "../../../../../context/CharacterData/hooks/useCharId";
import { findKey } from "lodash";
import officalCharId from "../../../../../../map/character_offical_id_map";
import { AnimatedScrollView } from "react-native-reanimated/lib/typescript/reanimated2/component/ScrollView";
import { useRoute } from "@react-navigation/native";

const UpArrow = require("./icons/UpArrow.svg");
const DownArrow = require("./icons/DownArrow.svg");

type Props = {
  containerRef: any;
  children: any;
  bottom: any;
};

export default function CommentBox(props: Props) {
  // data
  const charId = useCharId();
  const officalId = findKey(officalCharId, (v) => v === charId);

  const { data: charComments } = useCharComments(officalId || "");

  // animation
  const hieght = useSharedValue(Dimensions.get("screen").height - 240);
  const isPressed = useSharedValue(false);
  const translation = useSharedValue({ x: 0, y: 0 });

  useEffect(() => {
    if (translation.value.y < 0) {
      props.containerRef?.current?.scrollTo({
        x: 0,
        y: 3400,
        animated: true,
      });
    }
  }, [translation.value.y]);

  const gesture = Gesture.Pan()
    .onBegin(() => {
      isPressed.value = true;
    })
    .onUpdate((e) => {
      translation.value = {
        x: e.translationX,
        y: e.translationY,
      };
    })
    .onEnd(() => {})
    .onFinalize(() => {
      isPressed.value = false;
    });

  const animatedStyles = useAnimatedStyle(() => {
    return {
      transform: [{ scale: withSpring(isPressed.value ? 1.2 : 1) }],
    };
  });

  const animatedStyles2 = useAnimatedStyle(() => {
    return {
      height: translation.value.y < 0 ? hieght.value : /* withSpring(0) */ 0,
      display: translation.value.y < 0 ? "flex" : "none",
    };
  });

  const animatedStyles3 = useAnimatedStyle(() => {
    return {
      opacity: translation.value.y < 0 ? withSpring(1) : 0,
    };
  });

  const scrollRef = useRef<AnimatedScrollView>();
  useEffect(() => {
    setTimeout(() => {
      scrollRef.current?.scrollToEnd();
    }, 1000);
  }, []);

  return (
    <Pressable
      onPress={() => {
        Keyboard.dismiss();
      }}
    >
      <GestureDetector gesture={gesture}>
        <Animated.View style={[animatedStyles]}>
          <View
            className="w-full pt-[32px] pb-[16px]"
            style={{ alignItems: "center", gap: 16 }}
          >
            <Image
              source={translation.value.y < 0 ? DownArrow : UpArrow}
              className="w-[12px] h-[12px]"
            />
            <Text className="text-white text-[16px] font-[HY65]">
              {charComments?.length || 0}條評論
            </Text>
          </View>
        </Animated.View>
      </GestureDetector>
      <Animated.ScrollView
        //@ts-ignore
        ref={scrollRef}
        nestedScrollEnabled
        style={[animatedStyles2]}
        keyboardShouldPersistTaps="always"
      >
        <Animated.View className="w-full mb-20">{props.children}</Animated.View>
      </Animated.ScrollView>
      {translation.value.y < 0 && (
        <KeyboardAvoidingView
          className="absolute bottom-4 w-full"
          behavior="padding"
          keyboardVerticalOffset={118}
        >
          <Animated.View style={[animatedStyles3]}>
            {props.bottom}
          </Animated.View>
        </KeyboardAvoidingView>
      )}
    </Pressable>
  );
}
