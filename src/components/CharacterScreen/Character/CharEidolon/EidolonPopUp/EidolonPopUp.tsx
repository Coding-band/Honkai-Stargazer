import { Text, View } from "react-native";
import React, { useContext, useEffect } from "react";
import PopUpCard from "../../../../global/PopUpCard/PopUpCard";
import { HtmlText } from "@e-mine/react-native-html-text";
import FixedContext from "../../../../global/Fixed/FixedContext";
import formatDesc from "../../../../../utils/format/formatDesc";
import { useNavigation, useRoute } from "@react-navigation/native";
import { Shadow } from "react-native-shadow-2";
import { Image } from "expo-image";
import CharacterSoul from "../../../../../../assets/images/@images_map/characterSoul";
import useCharData from "../../../../../hooks/data/useCharData";

type Props = {
  id: number;
  onClose: () => void;
};

export default React.memo(function EidolonPopUp({ id, onClose }: Props) {

    const { charId, charFullData } = useCharData();

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
              <View
                className="px-4 py-[18px]"
                style={{ flexDirection: "row", gap: 14, alignItems: "center" }}
              >
                <Shadow style={{ borderRadius: 100 }} startColor="#31B5FF60">
                  <View
                    className="w-[60px] h-[60px] bg-[#333] rounded-full border-2 border-white"
                    style={{ justifyContent: "center", alignItems: "center" }}
                  >
                    <Image
                      className="w-12 h-12"
                      // @ts-ignore
                      source={CharacterSoul[charId]["soul" + id]}
                    />
                  </View>
                </Shadow>
                <View className="w-[250px]" style={{ gap: 4 }}>
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
              </View>
            }
          />
        </View>
      );
    }
  }, [charEidolonRank, id]);

  return <></>;
});
