import { View, Pressable, Text } from "react-native";
import React, { useCallback, useState } from "react";
import LightConeCard from "../../../../global/LightConeCard/LightConeCard";
import { ExpoImage } from "../../../../../types/image";
import Modal from "react-native-modal";
import PopUpCard from "../../../../global/PopUpCard/PopUpCard";
import { useNavigation } from "@react-navigation/native";
import { SCREENS } from "../../../../../constant/screens";
import { Path } from "../../../../../types/path";
import Sliderbar from "../../../../global/Sliderbar/Sliderbar";
import { HtmlText } from "@e-mine/react-native-html-text";
import formatDesc from "../../../../../utils/format/formatDesc";

type Props = {
  id: string;
  image?: ExpoImage;
  rare: number;
  name: string;
  skill: any;
  description: string;
  path: Path;
};

export default React.memo(function CharSuggestLightConeCard(props: Props) {
  const navigation = useNavigation();

  const [isSelected, setIsSelected] = useState(false);

  const handlePress = useCallback(() => {
    setIsSelected(true);
  }, [isSelected]);

  const handlePopupPress = useCallback(() => {
    // @ts-ignore
    navigation.push(SCREENS.LightconePage.id, {
      id: props.id,
      name: props.name,
    });
    setIsSelected(false);
  }, []);

  const [skillLevel, setSkillLevel] = useState(0);

  return (
    <View>
      <View style={{ opacity: isSelected ? 0 : 1 }}>
        <LightConeCard onPress={handlePress} {...props} />
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
            <LightConeCard onPress={handlePopupPress} {...props} />
          </View>
          <PopUpCard
            title={props.name}
            content={
              <View className="w-full p-4">
                <Text className="text-black font-[HY65] text-[20px] leading-[40px]">
                  {props.skill.name}
                </Text>
                <View
                  className="pb-3"
                  style={{
                    flexDirection: "row",
                    justifyContent: "space-between",
                    alignItems: "center",
                  }}
                >
                  <Text className="text-black font-[HY65] text-[16px]">
                    Lv.{skillLevel + 1}/5
                  </Text>
                  <Sliderbar
                    width={230}
                    point={5}
                    hasDot={false}
                    value={skillLevel}
                    onChange={setSkillLevel}
                  />
                </View>
                <HtmlText
                  style={{
                    color: "black",
                    fontFamily: "HY65",
                    fontSize: 14,
                    lineHeight: 20,
                  }}
                  // className="text-text2 font-[HY65] text-[14px]"
                >
                  {formatDesc(
                    props.skill.descHash,
                    props.skill.levelData[skillLevel].params
                  )}
                </HtmlText>
              </View>
            }
            onClose={() => {
              setIsSelected(false);
            }}
          />
        </Pressable>
      </Modal>
    </View>
  );
});
