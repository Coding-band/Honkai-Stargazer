import { View, Pressable } from "react-native";
import React, { useCallback, useState } from "react";
import LightConeCard from "../../../../global/LightConeCard/LightConeCard";
import { ExpoImage } from "../../../../../types/image";
import Modal from "react-native-modal";
import PopUpCard from "../../../../global/PopUpCard/PopUpCard";
import { useNavigation } from "@react-navigation/native";
import { SCREENS } from "../../../../../constant/screens";
import { Path } from "../../../../../types/path";
import { HtmlText } from "@e-mine/react-native-html-text";
import RelicsCard from "../../../../global/RelicsCard/RelicsCard";

type Props = {
  id: string;
  image?: ExpoImage;
  rare: number;
  name: string;
  description: string;
};

export default function CharSuggestRelicsCard(props: Props) {
  const navigation = useNavigation();

  const [isSelected, setIsSelected] = useState(false);

  const handlePress = useCallback(() => {
    setIsSelected(true);
  }, [isSelected]);

  const handlePopupPress = useCallback(() => {
    // @ts-ignore
    navigation.push(SCREENS.RelicPage.id, {
      id: props.id,
      name: props.name,
    });
    setIsSelected(false);
  }, []);

  return (
    <View>
      <View style={{ opacity: isSelected ? 0 : 1 }}>
        <RelicsCard onPress={handlePress} {...props} />
      </View>
      <Modal
        useNativeDriverForBackdrop
        animationIn="fadeIn"
        animationOut="fadeOut"
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
            gap: 12,
            transform: [{ translateY: -32 }],
          }}
        >
          <View style={{ transform: [{ scale: 1.2 }] }}>
            <RelicsCard onPress={handlePopupPress} {...props} />
          </View>
          <PopUpCard
            title={props.name}
            content={props.description}
            onClose={() => {
              setIsSelected(false);
            }}
          />
        </Pressable>
      </Modal>
    </View>
  );
}
