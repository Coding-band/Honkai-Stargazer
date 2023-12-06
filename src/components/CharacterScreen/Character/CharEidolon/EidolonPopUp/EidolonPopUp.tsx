import { Text, View } from "react-native";
import React, { useContext, useEffect } from "react";
import PopUpCard from "../../../../global/PopUpCard/PopUpCard";
import CharacterContext from "../../../../../context/CharacterContext";
import * as characterListMap from "../../../../../../data/character_data/@character_data_map/character_data_map";
import { CharacterName } from "../../../../../types/character";
import { HtmlText } from "@e-mine/react-native-html-text";
import FixedContext from "../../../../global/Fixed/FixedContext";
import formatDesc from "../../../../../utils/formatDesc";
import { useNavigation, useRoute } from "@react-navigation/native";
import { getCharFullData } from "../../../../../utils/getDataFromMap";

type Props = {
  id: number;
  onClose: () => void;
};

export default React.memo(function EidolonPopUp({ id, onClose }: Props) {
  const charData = useContext(CharacterContext);
  const charId = charData?.id!;
  const charFullData = getCharFullData(charId);
  const charEidolonRank = charFullData.ranks?.filter(
    (rank) => rank.id === id
  )[0];

  const { setFixed } = useContext(FixedContext)!;
  const navigation = useNavigation();
  const currentRoute =
    navigation.getState().routes[navigation.getState().routes.length - 1];
  const route = useRoute();

  useEffect(() => {
    if (id < 1 || id > 6 || currentRoute.key != route.key) {
      setFixed(null);
    } else {
      setFixed(
        <View className="w-[350px] mb-6">
          <PopUpCard
            onClose={onClose}
            title={charEidolonRank?.name || ""}
            content={
              <View className="px-4 py-[18px]" style={{ gap: 3 }}>
                <Text className="text-[#333] text-[14px] font-[HY65]">
                  星魂{id}
                </Text>
                <HtmlText
                  style={{ fontSize: 14, color: "#666", fontFamily: "HY65" }}
                >
                  {formatDesc(
                    charEidolonRank?.descHash,
                    charEidolonRank?.params
                  )}
                </HtmlText>
              </View>
            }
          />
        </View>
      );
    }
  }, [charEidolonRank, id]);

  return <></>;
});
