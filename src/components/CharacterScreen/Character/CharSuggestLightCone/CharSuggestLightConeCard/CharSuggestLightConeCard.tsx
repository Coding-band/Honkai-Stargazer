import { View, Text, GestureResponderEvent, Pressable } from "react-native";
import React, { useCallback, useState } from "react";
import LightConeCard from "../../../../global/layout/LightConeCard/LightConeCard";
import { ExpoImage } from "../../../../../types/image";
import Modal from "react-native-modal";
import { BlurView } from "expo-blur";
import { Image } from "expo-image";
import PopUpCard from "../../../../global/layout/PopUpCard/PopUpCard";

type Props = {
  image?: ExpoImage;
  rare: 4 | 5;
  name: string;
};

export default function CharSuggestLightConeCard(props: Props) {
  const [isSelected, setIsSelected] = useState(false);

  const handlePress = useCallback(() => {
    setIsSelected(true);
  }, [isSelected]);

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
        useNativeDriverForBackdrop
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
          <PopUpCard
            title="于夜色中"
            content="希儿的限定毕业光锥，提供了非常暴力的输出数值，同时对她的速度有一定要求，推荐副词条中尽量选择带有速度的遗器。"
          />
        </Pressable>
      </Modal>
    </View>
  );
}
