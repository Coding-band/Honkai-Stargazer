import { View, Text } from "react-native";
import React from "react";
import CharPageHeading from "../../../global/layout/CharPageHeading";
import { Person } from "phosphor-react-native";
import CharCard from "../../../global/layout/CharCard/CharCard";

const testImage1 = require("../../../../../assets/images/test-charlist-img-1.png");
const testImage2 = require("../../../../../assets/images/test-charlist-img-2.png");
const testImage3 = require("../../../../../assets/images/test-charlist-img-3.png");
const testImage4 = require("../../../../../assets/images/test-charlist-img-4.png");

const testData = [
  { image: testImage1, star: 5, name: "镜流" },
  { image: testImage2, star: 4, name: "停云" },
  { image: testImage3, star: 5, name: "刃" },
  { image: testImage4, star: 5, name: "希儿" },
  { image: testImage2, star: 4, name: "停云" },
  { image: testImage3, star: 5, name: "刃" },
  { image: testImage1, star: 5, name: "镜流" },
  { image: testImage4, star: 5, name: "希儿" },
];

export default function CharSuggestTeam() {
  return (
    <View style={{ alignItems: "center" }}>
      <CharPageHeading Icon={Person}>推荐队伍</CharPageHeading>
      <View
        style={{
          flexDirection: "row",
          justifyContent: "space-between",
          flexWrap: "wrap",
          rowGap: 16,
        }}
      >
        {testData.map((char, i) => (
          // @ts-ignore
          <CharCard key={i} {...char} />
        ))}
      </View>
    </View>
  );
}
