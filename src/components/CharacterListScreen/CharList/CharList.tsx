import React, { useEffect, useState } from "react";
import { ScrollView, View } from "react-native";

import CharCard from "../CharCard/CharCard";
import { map } from "lodash";
import { SCREENS } from "../../../constant/screens";
import { useNavigation } from "@react-navigation/native";
import testCharsData from "../../../../data/test-chars-data";

/*
const testImage1 = require("../../../../assets/images/test-charlist-img-1.png");
const testImage2 = require("../../../../assets/images/test-charlist-img-2.png");
const testImage3 = require("../../../../assets/images/test-charlist-img-3.png");
const testImage4 = require("../../../../assets/images/test-charlist-img-4.png");


let DATA_SET = [
  { image: testImage1, star: 5, name: "镜流" },
  // 可以添加更多的元素
];
*/


export default function CharList() {
  const navigation = useNavigation();

  const [DATA, SetDATA] = useState<any>([]);

  //const charJson = require("../../../../data/character_data/character_list.json")

  useEffect(() => {
    let result = [];
    for (let i = 0; i < testCharsData.length; i++) {
      result.push({
        star: testCharsData[i].rare,
        name: testCharsData[i].name,
        image: testCharsData[i].image
      });
    }
    SetDATA(result);
  }, []);

  return (
    <View style={{ width: "100%" }} className="p-[17px]">
      <ScrollView>
        <View
          style={{
            paddingVertical: 110,
            flexDirection: "row",
            flexWrap: "wrap",
            gap: 12,
            justifyContent: "center",
          }}
        >
          {map(DATA, (item, i) => (
            <CharCard
              key={i}
              onPress={() => {
                // @ts-ignore
                navigation.navigate(SCREENS.CharacterPage.id, {
                  id: 0,
                });
              }}
              image={item.image}
              stars={item.star}
              name={item.name}
            />
          ))}
        </View>
      </ScrollView>
    </View>
  );
}
