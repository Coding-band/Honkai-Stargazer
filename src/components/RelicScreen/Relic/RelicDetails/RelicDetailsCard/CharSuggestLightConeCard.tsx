import { View, Pressable } from "react-native";
import React, { useCallback, useState } from "react";
import { ExpoImage } from "../../../../../types/image";
import Modal from "react-native-modal";
import PopUpCard from "../../../../global/PopUpCard/PopUpCard";
import RelicsCard from "../../../../global/RelicsCard/RelicsCard";

type Props = {
  image?: ExpoImage;
  rare: number;
  name: string;
  description: string;
};

export default React.memo(function RelicDetailsCard(props: Props) {
  const [isSelected, setIsSelected] = useState(false);

  const handlePress = useCallback(() => {
    setIsSelected(true);
  }, [isSelected]);

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
            <RelicsCard {...props} />
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
});
