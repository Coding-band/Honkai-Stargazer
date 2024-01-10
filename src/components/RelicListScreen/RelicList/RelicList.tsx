import { View, Text, ScrollView } from "react-native";
import React, { useEffect, useMemo, useState } from "react";
import { useNavigation } from "@react-navigation/native";
import useTextLanguage from "../../../language/TextLanguage/useTextLanguage";
import { RelicName } from "../../../types/relic";
import relicList from "../../../../data/relic_data/relic_list.json";
import Relic from "../../../../assets/images/images_map/relic";
import { getRelicFullData } from "../../../utils/dataMap/getDataFromMap";
import RelicsCard from "../../global/RelicsCard/RelicsCard";
import { SCREENS } from "../../../constant/screens";
import { ExpoImage } from "../../../types/image";
import useRelicSearch from "../../../redux/relicSearch/useRelicSearch";

type RelicListItem = {
  id: string;
  name: string;
  rare: number;
  image: ExpoImage;
};

export default function RelicList() {
  const navigation = useNavigation();
  const { language: textLanguage } = useTextLanguage();

  const [relicCardListData, setRelicCardListData] = useState<RelicListItem[]>();

  useEffect(() => {
    setRelicCardListData(
      relicList.map((relic) => {
        const relicId = relic.name as RelicName;
        const relicFullData = getRelicFullData(relicId, textLanguage);
        return {
          id: relicId,
          name: relicFullData?.name || relic.name,
          rare: 5,
          image: Relic[relicId]?.icon1,
          version: relic.version,
        };
      })
    );
  }, []);

  const { searchValue } = useRelicSearch();

  const relicCardListJSX = useMemo(() => {
    const sortData = (relicCardListData: RelicListItem[] | undefined) => {
      let sortedData = relicCardListData;

      //* 搜尋

      if (searchValue) {
        sortedData = sortedData?.filter((data) =>
          data.name.includes(searchValue)
        );
      }

      return sortedData;
    };

    const sortedData = sortData(relicCardListData);

    return sortedData?.map((item, i) => (
      <RelicsCard
        key={i}
        onPress={() => {
          // @ts-ignore
          navigation.navigate(SCREENS.RelicPage.id, {
            id: item?.id,
            name: item?.name,
          });
        }}
        {...item}
      />
    ));
  }, [relicCardListData, searchValue]);

  return (
    <ScrollView className="z-30 pt-[127px] pb-0">
      <View
        style={{
          flexDirection: "row",
          flexWrap: "wrap",
          columnGap: 11,
          justifyContent: "center",
        }}
        className="pb-60"
      >
        {relicCardListJSX}
      </View>
    </ScrollView>
  );
}
