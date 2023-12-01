import {
  View,
  Text,
  GestureResponderEvent,
  Pressable,
  Dimensions,
} from "react-native";
import React, { useCallback, useState } from "react";
import LightConeCard from "../../../../global/layout/LightConeCard/LightConeCard";
import { ExpoImage } from "../../../../../types/image";
import Modal from "react-native-modal";
import { LinearGradient } from "expo-linear-gradient";
import { BlurView } from "expo-blur";
import { Image } from "expo-image";

type Props = {
  image?: ExpoImage;
  rare: 4 | 5;
  name: string;
  onPress?: (e: GestureResponderEvent) => void;
};

export default function CharSuggestLightConeCard(props: Props) {
  const [isSelected, setIsSelected] = useState(false);

  const handlePress = useCallback(
    (e: GestureResponderEvent) => {
      props.onPress && props.onPress(e);
      setIsSelected(true);
    },
    [isSelected, props.onPress]
  );

  return (
    <View>
      <View style={{ opacity: isSelected ? 0 : 1 }}>
        <LightConeCard
          onPress={handlePress}
          image={props.image}
          name={props.name}
          rare={props.rare}
        />
      </View>
      <Modal
        animationIn="fadeInLeft"
        animationOut="fadeOutRight"
        isVisible={isSelected}
      >
        <Pressable
          onPress={() => {
            setIsSelected(false);
          }}
          style={{
            flex: 1,
            justifyContent: "center",
            alignItems: "center",
            gap: 24,
            transform: [{ translateY: -32 }],
          }}
        >
          <View style={{ transform: [{ scale: 1.2 }] }}>
            <LightConeCard
              onPress={handlePress}
              image={props.image}
              name={props.name}
              rare={props.rare}
            />
          </View>
          <BlurView
            intensity={250}
            style={{ backgroundColor: "rgba(243, 249, 255, 0.80)" }}
            className="w-full rounded-[4px] rounded-tr-[24px] overflow-hidden"
          >
            <View
              className="w-full p-4"
              style={{
                flexDirection: "row",
                justifyContent: "space-between",
                alignItems: "center",
              }}
            >
              <Text className="font-[HY65] text-[20px]">于夜色中</Text>
              <Image
                style={{ width: 40, height: 40 }}
                source={require("../../../../../../assets/icons/CloseBlack.svg")}
              />
            </View>
            <View className="w-full px-4">
              <View className="w-full h-[2px] bg-[#00000010]"></View>
            </View>
            <View>
              <Text className="text-[#666] text-[14px] font-[HY65] leading-5 px-4 pb-2 pt-3">
                希儿的限定毕业光锥，提供了非常暴力的输出数值，同时对她的速度有一定要求，推荐副词条中尽量选择带有速度的遗器。
              </Text>
            </View>
          </BlurView>
        </Pressable>
      </Modal>
    </View>
  );
}
