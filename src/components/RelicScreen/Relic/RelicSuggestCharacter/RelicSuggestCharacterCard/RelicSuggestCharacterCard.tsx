import { View, Pressable } from "react-native";
import React, { useCallback, useState } from "react";
import { ExpoImage } from "../../../../../types/image";
import Modal from "react-native-modal";
import PopUpCard from "../../../../global/PopUpCard/PopUpCard";
import CharCard from "../../../../global/CharCard/CharCard";
import { useNavigation } from "@react-navigation/native";
import { SCREENS } from "../../../../../constant/screens";
import { Path } from "../../../../../types/path";
import { CombatType } from "../../../../../types/combatType";

type Props = {
  id: string;
  image?: ExpoImage;
  rare: number;
  name: string;
  path: Path;
  combatType: CombatType;
};

export default function RelicSuggestCharacterCard(props: Props) {
  const navigation = useNavigation();

  const [isSelected, setIsSelected] = useState(false);

  const handlePress = useCallback(() => {
    setIsSelected(true);
  }, [isSelected]);

  const handlePopupPress = useCallback(() => {
    // @ts-ignore
    navigation.push(SCREENS.CharacterPage.id, {
      id: props.id,
      name: props.name,
    });
    setIsSelected(false);
  }, []);

  return (
    <View>
      <View style={{ opacity: isSelected ? 0 : 1 }}>
        <CharCard onPress={handlePress} {...props} />
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
            gap: 24,
            transform: [{ translateY: -32 }],
          }}
        >
          <View style={{ transform: [{ scale: 1.2 }] }}>
            <CharCard onPress={handlePopupPress} {...props} />
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
