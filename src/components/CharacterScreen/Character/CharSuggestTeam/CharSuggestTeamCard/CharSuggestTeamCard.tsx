import React, { useCallback, useState } from "react";
import { ExpoImage } from "../../../../../types/image";
import CharCard from "../../../../global/CharCard/CharCard";
import { Pressable, View } from "react-native";
import Modal from "react-native-modal";
import PopUpCard from "../../../../global/PopUpCard/PopUpCard";
import { useNavigation } from "@react-navigation/native";
import { SCREENS } from "../../../../../constant/screens";
import { Path } from "../../../../../types/path";
import { CombatType } from "../../../../../types/combatType";
import { LOCALES } from "../../../../../../locales";
import useAppLanguage from "../../../../../language/AppLanguage/useAppLanguage";

type Props = {
  name: string;
  team: {
    image: ExpoImage;
    rare: number;
    name: string;
    id: string;
    path: Path;
    combatType: CombatType;
  }[];
};

export default React.memo(function CharSuggestTeamCard(props: Props) {
  const { language } = useAppLanguage();
  const navigation = useNavigation();

  const [isSelected, setIsSelected] = useState(false);

  const handleTeamPress = useCallback(() => {
    setIsSelected(true);
  }, [isSelected]);

  const handleCharPress = useCallback((charId: string, charName: string) => {
    // @ts-ignore
    navigation.push(SCREENS.CharacterPage.id, {
      id: charId,
      name: charName,
    });
    setIsSelected(false);
  }, []);

  return (
    <View>
      <View
        className="w-full"
        style={{
          opacity: isSelected ? 0 : 1,
          flexDirection: "row",
          gap: 0,
          rowGap:8,
        }}
      >
        {props.team.map((char, i) => (
          <CharCard onPress={handleTeamPress} key={i} {...char} />
        ))}
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
            gap: 14,
            transform: [{ translateY: -32 }],
          }}
        >
          <View
            className="w-full"
            style={{
              flexDirection: "row",
              justifyContent: "space-between",
            }}
          >
            {props.team.map((char, i) => (
              <CharCard onPress={handleCharPress} key={i} {...char} />
            ))}
          </View>
          <PopUpCard
            title={props.name}
            content={LOCALES[language].NoDataYet}
            onClose={() => {
              setIsSelected(false);
            }}
          />
        </Pressable>
      </Modal>
    </View>
  );
});
